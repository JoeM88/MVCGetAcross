package e.josephmolina.saywhat.MainScreen;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import e.josephmolina.saywhat.BuildConfig;
import e.josephmolina.saywhat.Model.YandexClient;
import e.josephmolina.saywhat.Model.YandexResponse;
import e.josephmolina.saywhat.R;
import e.josephmolina.saywhat.TextToSpeechManager.TextToSpeechManager;
import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainController implements MainLayout.MainLayoutListener {

    private MainLayout mainLayout;
    private MainActivity mainActivity;
    private TextToSpeech textToSpeechManager;

    public MainController(MainActivity mainActivity) {
        mainLayout = new MainLayout(mainActivity, this);
        this.mainActivity = mainActivity;

        textToSpeechManager = TextToSpeechManager.getTextToSpeechInstance(mainActivity);
    }

    private Single<YandexResponse> getTranslation(String text) {
        String API_KEY = BuildConfig.ApiKey;
        return YandexClient.getRetrofitInstance().getSpokenLanguage(API_KEY, text).flatMap(detectLanguageResponse -> {
            String languagePair = getLanguagePair(detectLanguageResponse.getLang());
            return YandexClient.getRetrofitInstance().getTranslation(API_KEY, text, languagePair);
        });
    }

    @Override
    public void onTranslateClicked(String text) {
        if (text.isEmpty()) {
            displayToast(mainActivity.getResources().getString(R.string.empty_text_error_message));
        } else {
            final ProgressBar progressBar = mainActivity.findViewById(R.id.indeterminateBar);
            getTranslation(text)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(() -> {
                        progressBar.setVisibility(View.VISIBLE);
                    })
                    .subscribe(new SingleSubscriber<YandexResponse>() {
                        @Override
                        public void onSuccess(YandexResponse value) {
                            progressBar.setVisibility(View.GONE);

                            TextView resultView = mainActivity.findViewById(R.id.translatedTextResults);
                            resultView.setText(value.getText().get(0));
                        }

                        @Override
                        public void onError(Throwable error) {
                            progressBar.setVisibility(View.GONE);
                            displayToast(error.getMessage());
                        }
                    });
        }
    }

    public void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        try {
            mainActivity.startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException a) {
            displayToast(mainActivity.getString(R.string.speech_to_text_unavailable_message));
        }
    }

    public void displaySpeechToTextResults(String text) {
        TextView resultView = mainActivity.findViewById(R.id.textToTranslateEditText);
        resultView.setText(text);
        onTranslateClicked(text);
    }

    @Override
    public void onYandexCreditClicked() {
        Uri webpage = Uri.parse("http://translate.yandex.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(mainActivity.getPackageManager()) != null) {
            mainActivity.startActivity(intent);
        }
    }

    private void displayNetworkError(Throwable error) {
        TextView resultView = mainActivity.findViewById(R.id.translatedTextResults);
        resultView.setText(error.getMessage());
    }

    private void displayToast(String message) {
        Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show();
    }

    private String getLanguagePair(String spokenLanguage) {
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
}
