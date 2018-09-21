package e.josephmolina.saywhat.MainScreen;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.tts.TextToSpeech;

import e.josephmolina.saywhat.BuildConfig;
import e.josephmolina.saywhat.Dialog.SayWhatDialog;
import e.josephmolina.saywhat.Model.SavedTranslation;
import e.josephmolina.saywhat.Model.YandexClient;
import e.josephmolina.saywhat.Model.YandexResponse;
import e.josephmolina.saywhat.R;
import e.josephmolina.saywhat.RoomDB.SayWhatDatabase;
import e.josephmolina.saywhat.TextToSpeechManager.TextToSpeechManager;
import e.josephmolina.saywhat.Utils.Utils;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


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
        return YandexClient.getRetrofitInstance().getSpokenLanguage(API_KEY, text)
                .flatMap(detectLanguageResponse -> {
                    String languagePair = Utils.getLanguagePair(detectLanguageResponse.getLang());
                    return YandexClient.getRetrofitInstance().getTranslation(API_KEY, text, languagePair);
                });
    }

    @Override
    public void onTranslateClicked(String text) {
        if (text.isEmpty()) {
            mainLayout.displayToast(mainActivity.getString(R.string.empty_text_error_message));
        } else {

            getTranslation(text)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mainLayout);
        }
    }

    public void startVoiceInput() {
        if (textToSpeechManager != null) {
            Intent voiceIntent = Utils.createVoiceInputIntent();
            try {
                mainActivity.startActivityForResult(voiceIntent, 1);
            } catch (ActivityNotFoundException a) {
                mainLayout.displayToast(mainActivity.getString(R.string.speech_to_text_unavailable_message));
            }
        }
    }


    public void displaySpeechToTextResults(String results) {
        mainLayout.spokenText.setText(results);
        onTranslateClicked(results);
    }

    @Override
    public void onYandexCreditClicked() {
        if (Utils.isNetworkAvailable(mainActivity)) {
            Intent openYandexSiteIntent = Utils.createYandexCreditIntent();

            if (openYandexSiteIntent.resolveActivity(mainActivity.getPackageManager()) != null) {
                mainActivity.startActivity(openYandexSiteIntent);
            }
        } else {
            mainLayout.displayToast(mainActivity.getResources().getString(R.string.no_network_message));
        }
    }

    @Override
    public void onSpeakClicked(String text) {
        textToSpeechManager.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void onViewTranslationsClicked() {
       Utils.launchSavedTranslationsScreen(mainActivity);
    }

    @Override
    public void onMainScreenClicked() {
        Utils.onScreenPressed(mainActivity);
    }

    public void saveTranslation(String title) {
        if (title.isEmpty() || mainLayout.translatedText.getText().length() == 0
                || mainLayout.spokenText.getText().length() == 0) {

            mainLayout.displayToast(mainActivity.getString(R.string.empty_text_error_message));

        } else {
            String spokenText = mainLayout.spokenText.getText().toString();
            String translatedText = mainLayout.translatedText.getText().toString();
            SavedTranslation savedTranslation = new SavedTranslation(title, spokenText, translatedText);

            mainLayout.displayToast(mainActivity.getString(R.string.saving_toast_message));

            Completable.fromAction(() -> SayWhatDatabase.getSayWhatDatabaseInstance(mainActivity)
                    .savedTranslationDAO()
                    .insertTranslation(savedTranslation))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onComplete() {
                            mainLayout.displayToast(mainActivity.getString(R.string
                                    .translation_saved_successfully_message));
                        }

                        @Override
                        public void onError(Throwable e) {
                            mainLayout.displayToast(mainActivity.getString(R.string
                                    .translation_unsuccessfully_saved_message));
                        }
                    });
        }
    }

    public void displaySaveDialog() {
        new SayWhatDialog().show(mainActivity.getSupportFragmentManager(), "saving");
    }

    public void cleanUp() {
        textToSpeechManager.shutdown();
    }
}
