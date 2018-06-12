package e.josephmolina.saywhat.MainScreen;

import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

import e.josephmolina.saywhat.BuildConfig;
import e.josephmolina.saywhat.Model.YandexClient;
import e.josephmolina.saywhat.Model.YandexResponse;
import e.josephmolina.saywhat.R;
import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainController implements MainLayout.MainLayoutListener {

    private String API_KEY = BuildConfig.ApiKey;
    private MainLayout mainLayout;
    private MainActivity mainActivity;

    public MainController(MainActivity mainActivity) {
        mainLayout = new MainLayout(mainActivity, this);
        this.mainActivity = mainActivity;
    }

    private Single<YandexResponse> getTranslationTest(String text) {
        return YandexClient.getRetrofitInstance().getSpokenLanguage(API_KEY, text).flatMap(detectLanguageResponse -> {
            String languagePair = getLanguagePair(detectLanguageResponse.getLang());
            return YandexClient.getRetrofitInstance().getTranslation(API_KEY, text, languagePair);
        });
    }

    @Override
    public void onTranslateClicked(String text) {
        if (text.isEmpty()) {
            displayToast("You must enter a word or sentence");
        } else {
            getTranslationTest(text)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleSubscriber<YandexResponse>() {
                        @Override
                        public void onSuccess(YandexResponse value) {
                            TextView resultView = mainActivity.findViewById(R.id.translatedTextResults);
                            resultView.setText(value.getText().get(0));
                        }

                        @Override
                        public void onError(Throwable error) {
                            displayToast(error.getMessage());
                        }
                    });
        }
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
