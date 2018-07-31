package e.josephmolina.saywhat.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

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
}
