package e.josephmolina.saywhat.MainScreen;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;

import e.josephmolina.saywhat.R;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {

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
}
