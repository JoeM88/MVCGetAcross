package e.josephmolina.saywhat.MainScreen;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import e.josephmolina.saywhat.R;
import e.josephmolina.saywhat.Utils.Utils;

public class MainLayout {

    private MainLayoutListener mainLayoutListener;
    private MainActivity mainActivity;

    @BindView(R.id.translateButton)
    Button translateButton;
    @BindView(R.id.textToTranslateEditText)
    EditText spokenText;
    @BindView(R.id.translatedTextResults)
    TextView translatedText;
    @BindView(R.id.speakButton)
    ImageView speakButton;
    @BindView(R.id.indeterminateBar)
    ProgressBar indetermindateBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_translations_menu_item)
    ImageView viewTranslationsButton;

    public MainLayout(final MainActivity mainActivity, MainLayoutListener mainLayoutListener) {
        mainActivity.setContentView(R.layout.activity_main);
        ButterKnife.bind(this, mainActivity);
        this.mainActivity = mainActivity;
        this.mainLayoutListener = mainLayoutListener;
        mainActivity.setSupportActionBar(toolbar);
        mainActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @OnClick(R.id.translateButton)
    public void onTranslateButton() {
        mainLayoutListener.onTranslateClicked(spokenText.getText().toString());
    }

    @OnClick(R.id.speakButton)
    public void onSpeakButton() {
        mainLayoutListener.onSpeakClicked(translatedText.getText().toString());
    }

    @OnClick(R.id.view_translations_menu_item)
    public void setViewTranslationsButton() {
        mainLayoutListener.onViewTranslationsClicked();
    }

    @OnClick(R.id.mainScreenLayout)
    public void onMainScreen() {
        mainLayoutListener.onMainScreenClicked();
    }


    public void displayToast(String message) {
        Utils.onDisplayToast(mainActivity, message);
    }

    interface MainLayoutListener {
        void onTranslateClicked(String text);

        void onSpeakClicked(String text);

        void onViewTranslationsClicked();

        void onMainScreenClicked();
    }
}
