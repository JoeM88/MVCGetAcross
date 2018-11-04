package e.josephmolina.saywhat.Comprehend;

import com.amazonaws.services.comprehend.AmazonComprehendClient;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageRequest;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageResult;

import e.josephmolina.saywhat.MainScreen.MainActivity;
import e.josephmolina.saywhat.Utils.Utils;

public class AWSComprehendClient {
    private static AmazonComprehendClient comprehendClient;

    private AmazonComprehendClient getAWSComprehendClienInstance(MainActivity activity) {

        if (comprehendClient != null) {
            return comprehendClient;
        }
        comprehendClient = new AmazonComprehendClient(Utils.getCognitoCachingCredentialsProvider(activity));
        return comprehendClient;
    }

    public String detectDominantLanguage(String text) {
        DetectDominantLanguageRequest detectDominantLanguageRequest =  new DetectDominantLanguageRequest().withText(text);
        DetectDominantLanguageResult detectDominantLanguageResult = comprehendClient.detectDominantLanguage(detectDominantLanguageRequest);
        return detectDominantLanguageResult.getLanguages().get(0).getLanguageCode();
    }
}
