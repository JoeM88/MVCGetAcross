package e.josephmolina.saywhat.MainScreen;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import e.josephmolina.saywhat.R;

public class MainLayout {

    private MainLayoutListener mainLayoutListener;
    private MainActivity mainActivity;
    @BindView(R.id.translateButton)
    Button translateButton;
    @BindView(R.id.textToTranslateEditText)
    EditText spokenText;
    @BindView(R.id.translatedTextResults)
    TextView translatedText;
    @BindView(R.id.yandexCredit)
    TextView yandexCredit;
    @BindView(R.id.speakButton)
    ImageView speakButton;

    public MainLayout(final MainActivity mainActivity, MainLayoutListener mainLayoutListener) {
        mainActivity.setContentView(R.layout.activity_main);
        ButterKnife.bind(this, mainActivity);
        this.mainLayoutListener = mainLayoutListener;
    }

    @OnClick(R.id.translateButton)
    public void onTranslateButton() {
        mainLayoutListener.onTranslateClicked(spokenText.getText().toString());
    }

    @OnClick(R.id.yandexCredit)
    public void onYandexCredit() {
        mainLayoutListener.onYandexCreditClicked();
    }

    @OnClick(R.id.speakButton)
    public void onSpeakButton() {
        mainLayoutListener.onSpeakClicked(translatedText.getText().toString());
    }


    interface MainLayoutListener {
        void onTranslateClicked(String text);

        void onYandexCreditClicked();

        void onSpeakClicked(String text);
    }
}
