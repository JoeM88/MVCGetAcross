package e.josephmolina.saywhat.language;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.comprehend.AmazonComprehendClient;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageRequest;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageResult;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

import e.josephmolina.saywhat.MainScreen.MainActivity;
import e.josephmolina.saywhat.Utils.Utils;

public class Interpret {
    private AmazonComprehendClient comprehendClient;
    private AmazonTranslateClient translateClient;

    public Interpret(MainActivity activity) {
        CognitoCachingCredentialsProvider credentialsProvider = Utils.getCognitoCachingCredentialsProvider(activity);
        comprehendClient = new AmazonComprehendClient(credentialsProvider);
        translateClient = new AmazonTranslateClient(credentialsProvider);
    }

    public String detectLanguage(String text) {
        DetectDominantLanguageRequest request = new DetectDominantLanguageRequest().withText(text);
        DetectDominantLanguageResult result = comprehendClient.detectDominantLanguage(request);
        return result.getLanguages().get(0).getLanguageCode();
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
