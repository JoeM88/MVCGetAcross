package e.josephmolina.saywhat.Comprehend;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.comprehend.AmazonComprehendClient;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageRequest;
import com.amazonaws.services.comprehend.model.DetectDominantLanguageResult;

import e.josephmolina.saywhat.MainScreen.MainActivity;

public class AWSComprehend {

    String text = "It is raining today in Seattle";
    private static String COGNITO_POOL_ID = "us-west-2:080abe63-8a82-4f9a-8a03-b8f06da5c3af";
    private static Regions MY_REGION = Regions.US_WEST_2;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void AWSComprehend(MainActivity activity) {

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                activity.getApplicationContext(),
                COGNITO_POOL_ID,
                MY_REGION
        );

        AmazonComprehendClient comprehendClient = new AmazonComprehendClient(credentialsProvider);
        DetectDominantLanguageRequest detectDominantLanguageRequest =  new DetectDominantLanguageRequest().withText(text);

        DetectDominantLanguageResult result = comprehendClient.detectDominantLanguage(detectDominantLanguageRequest);
        Log.d("dominant lang", result.toString());
    }

    public void detectDominantLanguage(String text) {

    }


}
