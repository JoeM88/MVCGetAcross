package e.josephmolina.saywhat.Utils;

import android.content.Context;
import android.widget.Toast;

public final class Utils {

    public static void onDisplayToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
