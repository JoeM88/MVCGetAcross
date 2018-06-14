package e.josephmolina.saywhat.TextToSpeechManager;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TextToSpeechManager {
    private static TextToSpeech textToSpeech = null;

    private TextToSpeechManager() {
    }

    public static TextToSpeech getTextToSpeechInstance(Context context) {
        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(context, onInitListener);
        }
        return textToSpeech;
    }

    private static TextToSpeech.OnInitListener onInitListener = status -> {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.ENGLISH);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("error", "This Language is not supported");
            }
        } else {
            Log.e("error", "Initialization Failed!");
        }
    };
}
