package e.josephmolina.saywhat.Language;

import com.amazonaws.services.comprehend.AmazonComprehendClient;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageRequest;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageResult;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;

import e.josephmolina.saywhat.MainScreen.MainActivity;
import e.josephmolina.saywhat.Utils.Utils;

public class DetectionService {
    private AmazonComprehendClient comprehendClient;

    private void initializeComprehendClient(MainActivity activity) {
        CognitoCachingCredentialsProvider credentialsProvider = Utils.getCognitoCachingCredentialsProvider(activity);
        comprehendClient = new AmazonComprehendClient(credentialsProvider); 
    }

    public DetectionService(MainActivity activity) {
        initializeComprehendClient(activity);
    }

    public String detectLanguage(String text) {
        DetectDominantLanguageRequest request = new DetectDominantLanguageRequest().withText(text);
        DetectDominantLanguageResult result = comprehendClient.detectDominantLanguage(request);
        return result.getLanguages().get(0).getLanguageCode();
    }
}