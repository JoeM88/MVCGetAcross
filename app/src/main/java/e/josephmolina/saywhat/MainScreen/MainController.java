package e.josephmolina.saywhat.MainScreen;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import e.josephmolina.saywhat.BuildConfig;
import e.josephmolina.saywhat.Dialog.SayWhatDialog;
import e.josephmolina.saywhat.Model.SavedTranslation;
import e.josephmolina.saywhat.SavedChatsScreen.SavedTranslationsScreen;
import e.josephmolina.saywhat.Model.YandexClient;
import e.josephmolina.saywhat.Model.YandexResponse;
import e.josephmolina.saywhat.R;
import e.josephmolina.saywhat.RoomDB.SayWhatDatabase;
import e.josephmolina.saywhat.TextToSpeechManager.TextToSpeechManager;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
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
                    String languagePair = getLanguagePair(detectLanguageResponse.getLang());
                    return YandexClient.getRetrofitInstance().getTranslation(API_KEY, text, languagePair);

                });
    }

    @Override
    public void onTranslateClicked(String text) {
        if (text.isEmpty()) {
            displayToast(mainActivity.getResources().getString(R.string.empty_text_error_message));
        } else {
            if (isNetworkAvailable()) {
                getTranslation(text)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<YandexResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                mainLayout.indetermindateBar.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onSuccess(YandexResponse value) {
                                mainLayout.indetermindateBar.setVisibility(View.GONE);
                                mainLayout.translatedText.setText(value.getText().get(0));
                            }

                            @Override
                            public void onError(Throwable error) {
                                mainLayout.indetermindateBar.setVisibility(View.GONE);
                                displayToast(error.getMessage());
                            }
                        });
            } else {
                displayToast(mainActivity.getString(R.string.no_network_message));
            }
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
        mainLayout.spokenText.setText(text);
        onTranslateClicked(text);
    }

    @Override
    public void onYandexCreditClicked() {
        if (isNetworkAvailable()) {
            Uri webpage = Uri.parse("http://translate.yandex.com/");
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(mainActivity.getPackageManager()) != null) {
                mainActivity.startActivity(intent);
            }
        } else {
            displayToast(mainActivity.getString(R.string.no_network_message));
        }
    }

    @Override
    public void onSpeakClicked(String text) {
        textToSpeechManager.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void onViewTranslationsClicked() {
     Intent goToSavedTranslationScreen = new Intent(mainActivity, SavedTranslationsScreen.class);
     mainActivity.startActivity(goToSavedTranslationScreen);

    }

    @Override
    public void onMainScreenClicked() {
        InputMethodManager imm = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainActivity.getCurrentFocus().getWindowToken(), 0);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }


    public void saveTranslation(String title) {
        if (title.isEmpty() || mainLayout.translatedText.getText().length() == 0
                || mainLayout.spokenText.getText().length() == 0) {
            displayToast(mainActivity.getResources().getString(R.string.empty_text_error_message));
        } else {
            String spokenText = mainLayout.spokenText.getText().toString();
            String translatedText = mainLayout.translatedText.getText().toString();
            SavedTranslation savedTranslation = new SavedTranslation(title, spokenText, translatedText);

            displayToast(mainActivity.getString(R.string.saving_toast_message));
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
                            displayToast(mainActivity.getString(R.string.translation_saved_successfully_message));
                        }

                        @Override
                        public void onError(Throwable e) {
                            displayToast(mainActivity.getString(R.string.translation_unsuccessfully_saved_message));
                        }
                    });
        }
    }

    public void displaySaveDialog() {
        DialogFragment newFragment = new SayWhatDialog();
        newFragment.show(mainActivity.getSupportFragmentManager(), "saving");
    }

    public void cleanUp() {
        textToSpeechManager.shutdown();
    }
}
