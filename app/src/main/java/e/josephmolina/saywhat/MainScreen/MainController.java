package e.josephmolina.saywhat.MainScreen;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.tts.TextToSpeech;

import java.util.concurrent.Callable;

import e.josephmolina.saywhat.Dialog.SayWhatDialog;
import e.josephmolina.saywhat.Model.SavedTranslation;
import e.josephmolina.saywhat.R;
import e.josephmolina.saywhat.RoomDB.SayWhatDatabase;
import e.josephmolina.saywhat.language.Speech;
import e.josephmolina.saywhat.TextToSpeechManager.TextToSpeechManager;
import e.josephmolina.saywhat.Utils.Utils;
import e.josephmolina.saywhat.language.Interpret;
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
    private Interpret interpret;
    private Speech pollyClient;

    public MainController(MainActivity mainActivity) {
        mainLayout = new MainLayout(mainActivity, this);
        this.mainActivity = mainActivity;
        textToSpeechManager = TextToSpeechManager.getTextToSpeechInstance(mainActivity);
        pollyClient = new Speech();

        interpret = new Interpret();
    }

    private Single<String> getTranslation(String text) {
        return Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String detectedLanguageCode = interpret.detectLanguage(text);
                String targetLanguageCode = (detectedLanguageCode.equals("en")) ? "es" : "en";
                return interpret.translateText(text, detectedLanguageCode, targetLanguageCode);
            }
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
    public void onSpeakClicked(String text) {

        Completable.fromCallable((Callable<Void>) () -> {
            pollyClient.speak(text);
            return null;
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mainLayout.displayToast("Speaking...");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainLayout.displayToast("Unable to speak");
                    }
                });
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
