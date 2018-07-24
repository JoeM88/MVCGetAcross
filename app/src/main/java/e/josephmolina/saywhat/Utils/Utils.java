package e.josephmolina.saywhat.Dialog.Utils;

import android.content.Context;
import android.widget.Toast;

public final class ToastUtils {

    public static void onDisplayToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
