package e.josephmolina.saywhat.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import e.josephmolina.saywhat.MainScreen.MainActivity;
import e.josephmolina.saywhat.SavedChatsScreen.SavedTranslationsScreen;

public final class Utils {

    public static void onDisplayToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getLanguagePair(String spokenLanguage) {
        StringBuilder languagePair = new StringBuilder();
        switch (spokenLanguage) {
            case "en":
                languagePair.append(spokenLanguage).append("-es");
                break;

            case "es":
                languagePair.append(spokenLanguage).append("-en");
                break;
        }
        return languagePair.toString();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static void onScreenPressed(MainActivity mainActivity) {
        InputMethodManager imm = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainActivity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void launchSavedTranslationsScreen(MainActivity mainActivity) {
        Intent goToSavedTranslationScreen = new Intent(mainActivity, SavedTranslationsScreen.class);
        mainActivity.startActivity(goToSavedTranslationScreen);
    }

    public static Intent createVoiceInputIntent() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        return intent;
    }

    public static Intent createYandexCreditIntent() {
        Uri webpage = Uri.parse("http://translate.yandex.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        return intent;
    }
}
