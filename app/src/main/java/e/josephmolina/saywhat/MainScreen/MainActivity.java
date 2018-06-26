package e.josephmolina.saywhat.MainScreen;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;

import e.josephmolina.saywhat.Dialog.SayWhatDialog;
import e.josephmolina.saywhat.R;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity implements SayWhatDialog.DialogListener{

    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        controller = new MainController(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.voiceTranslateOption:
                controller.startVoiceInput();
                return true;
            case R.id.saveTranslationOption:
               controller.displaySaveDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1: {
                if (resultCode == RESULT_OK && data != null) {
                    String spokenData = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
                    controller.displaySpeechToTextResults(spokenData);
                }
                break;
            }
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment) {
        EditText editText = dialogFragment.getDialog().findViewById(R.id.translationTitle);
        String title = editText.getText().toString();
        controller.saveTranslation(title);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment) {
        // close dialog
        Log.d("Negative", "Was pressed");
        dialogFragment.dismiss();
    }
}
