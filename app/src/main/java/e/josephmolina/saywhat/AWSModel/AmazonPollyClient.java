package e.josephmolina.saywhat.Model;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyPresigningClient;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechPresignRequest;

import java.io.IOException;
import java.net.URL;

import e.josephmolina.saywhat.MainScreen.MainActivity;

public class AmazonPollyClient {
    private static AmazonPollyPresigningClient amazonPollyInstance;
    private static String COGNITO_POOL_ID = "us-west-2:080abe63-8a82-4f9a-8a03-b8f06da5c3af";
    private static Regions MY_REGION = Regions.US_WEST_2;

    public AmazonPollyClient(MainActivity activity) {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                activity.getApplicationContext(),
                COGNITO_POOL_ID,
                MY_REGION
        );
        amazonPollyInstance = new AmazonPollyPresigningClient(credentialsProvider);
    }

    public void playSpeech(String text, String language) {
        String voice = getPollyVoice(language);
        URL url = getAudioStreamURL(text, voice);
        playSynthesizedSpeech(url);
    }

    private String getPollyVoice(String language) {
        String voice;
        switch (language) {
            case "SPANISH":
                voice = "Penelope";
                break;
            case "ENGLISH":
                voice = "Joey";
                break;
            default:
                throw new Error("Unrecognized language provided.");
        }
        return voice;
    }

    private static URL getAudioStreamURL(String text, String id) {
        SynthesizeSpeechPresignRequest synthesizeSpeechPresignRequest = new SynthesizeSpeechPresignRequest()
                        .withText(text)
                        .withVoiceId(id)
                        .withOutputFormat(OutputFormat.Mp3);

        return amazonPollyInstance.getPresignedSynthesizeSpeechUrl(synthesizeSpeechPresignRequest);
    }


    private static void playSynthesizedSpeech(URL presignedSynthesizeSpeechUrl) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(presignedSynthesizeSpeechUrl.toString());
        } catch (IOException e) {
            Log.e("ERROR", "Unable to set data source for the media player! " + e.getMessage());
        }

        // Prepare the MediaPlayer asynchronously (since the data source is a network stream).
        mediaPlayer.prepareAsync();

        // Set the callback to start the MediaPlayer when it's prepared.
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        // Set the callback to release the MediaPlayer after playback is completed.
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }
}
