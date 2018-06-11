package e.josephmolina.saywhat.MainScreen;

import android.widget.TextView;
import android.widget.Toast;
import e.josephmolina.saywhat.Model.YandexClient;
import e.josephmolina.saywhat.Model.YandexResponse;
import e.josephmolina.saywhat.R;
import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainController implements MainLayout.MainLayoutListener {

    private String API_KEY = "trnsl.1.1.20180131T235045Z.b989ff118bdf2e59.0fe722c34ea88e447d238e4c0e8c5ac56d46522d";
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
