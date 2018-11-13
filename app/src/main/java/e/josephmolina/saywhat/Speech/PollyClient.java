package e.josephmolina.saywhat.Speech;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.services.polly.AmazonPollyPresigningClient;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechPresignRequest;

import java.io.IOException;
import java.net.URL;

import e.josephmolina.saywhat.Utils.Utils;
import e.josephmolina.saywhat.language.Interpret;

public class PollyClient {
    private AmazonPollyPresigningClient pollyClient;
    private Interpret interpret;
    private final String ENGLISH_VOICE_ID = "Joey";
    private final String SPANISH_VOICE_ID = "Mia";

    public PollyClient() {
        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        pollyClient = new AmazonPollyPresigningClient(credentialsProvider);
        interpret = new Interpret();
    }

    private String getCorrespondingVoice(String text) {
        String dominantLanguage = interpret.detectLanguage(text);
        String targetLanguage = Utils.determineTargetLanguageCode(dominantLanguage);
        return targetLanguage.equals("en") ? SPANISH_VOICE_ID : ENGLISH_VOICE_ID;
    }

    private URL getAudioStreamURL(String text) {
        SynthesizeSpeechPresignRequest synthesizeSpeechPresignRequest =
                new SynthesizeSpeechPresignRequest()
                .withText(text)
                .withVoiceId(getCorrespondingVoice(text))
                .withOutputFormat(OutputFormat.Mp3);
        return pollyClient.getPresignedSynthesizeSpeechUrl(synthesizeSpeechPresignRequest);
    }


    public void speak(String text) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        String speechURL = getAudioStreamURL(text).toString();

        try {
            mediaPlayer.setDataSource(speechURL);
        }catch (IOException e) {
            Log.e("Polly Client", "Unable to set data source for the media player! " + e.getMessage());
        }

        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(MediaPlayer::start);
        mediaPlayer.setOnCompletionListener(MediaPlayer::release);
    }
}
