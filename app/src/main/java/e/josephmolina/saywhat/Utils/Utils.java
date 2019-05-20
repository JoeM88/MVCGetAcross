package e.josephmolina.saywhat.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

import e.josephmolina.saywhat.MainScreen.MainActivity;
import e.josephmolina.saywhat.SavedChatsScreen.SavedTranslationsScreen;

public final class Utils {

    public static void onDisplayToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

    public static void initializeAWSMobileClient(MainActivity activity) {
        AWSMobileClient.getInstance().initialize(activity, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                IdentityManager.getDefaultIdentityManager().getUserID(new IdentityHandler() {

                    @Override
                    public void onIdentityId(String identityId) {}

                    @Override
                    public void handleError(Exception e) {
                        Log.d("AWS","Failed to initialize AWS Mobile Client" + e.getMessage());
                    }
                });
            }
        }).execute();
    }

    public static void dummyMethod() {}

    public static String determineTargetLanguageCode(String detectedLanguageCode) {
        return (detectedLanguageCode.equals("en")) ? "es" : "en";
    }
}
