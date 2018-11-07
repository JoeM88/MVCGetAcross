package e.josephmolina.saywhat.Language;

import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;

import e.josephmolina.saywhat.MainScreen.MainActivity;
import e.josephmolina.saywhat.Utils.Utils;

public class TranslationService {
    private AmazonTranslateClient translateClient;

    private void initializeTranslationClient(MainActivity activity) {
        CognitoCachingCredentialsProvider credentialsProvider = Utils.getCognitoCachingCredentialsProvider(activity);
        translateClient = new AmazonTranslateClient(credentialsProvider);
    }

    public TranslationService(MainActivity activity) {
        initializeTranslationClient(activity);
    }

    public String translateText(String text, String sourceLanguageCode, String targetLanguageCode) {
        TranslateTextRequest request = new TranslateTextRequest()
            .withText(text)
            .withSourceLanguageCode(sourceLanguageCode)
            .withTargetLanguageCode(targetLanguageCode);

        TranslateTextResult result = translateClient.translateText(request);
        return result.getTranslatedText();
    }
}