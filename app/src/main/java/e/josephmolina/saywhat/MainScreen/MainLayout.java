package e.josephmolina.saywhat.MainScreen;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import e.josephmolina.saywhat.Model.YandexResponse;
import e.josephmolina.saywhat.R;
import e.josephmolina.saywhat.Utils.Utils;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class MainLayout implements SingleObserver<YandexResponse> {

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

    @OnClick(R.id.yandexCredit)
    public void onYandexCredit() {
        mainLayoutListener.onYandexCreditClicked();
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

    @Override
    public void onSubscribe(Disposable d) {
        indetermindateBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(YandexResponse yandexResponse) {
        indetermindateBar.setVisibility(View.GONE);
        translatedText.setText(yandexResponse.getText().get(0));
    }

    @Override
    public void onError(Throwable error) {
        indetermindateBar.setVisibility(View.GONE);
        displayToast(error.getMessage());
    }

    public void displayToast(String message) {
        Utils.onDisplayToast(mainActivity, message);
    }

    interface MainLayoutListener {
        void onTranslateClicked(String text);

        void onYandexCreditClicked();

        void onSpeakClicked(String text);

        void onViewTranslationsClicked();

        void onMainScreenClicked();
    }
}
