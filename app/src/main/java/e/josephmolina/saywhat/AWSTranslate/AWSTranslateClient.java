package e.josephmolina.saywhat.AWSTranslate;

import android.util.Log;

import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.translate.AmazonTranslateAsyncClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

import e.josephmolina.saywhat.Utils.Utils;

public class AWSTranslateClient {
    private static AmazonTranslateAsyncClient translateAsyncClient;

    private AWSTranslateClient() {

    }

    public static AmazonTranslateAsyncClient getAWSTranslateClient() {
        if (translateAsyncClient == null) {
            return new AmazonTranslateAsyncClient(Utils.getAWSCredentials());
        }
        return translateAsyncClient;
    }

    public static TranslateTextRequest buildTranslateTextRequest(String text) {
        return new TranslateTextRequest()
                .withText(text)
                .withSourceLanguageCode("en")
                .withTargetLanguageCode("es");
    }

    public static void translateText(String text) {
        TranslateTextRequest textRequest = buildTranslateTextRequest(text);

        getAWSTranslateClient().translateTextAsync(textRequest, new AsyncHandler<TranslateTextRequest, TranslateTextResult>() {
            @Override
            public void onError(Exception exception) {
                Log.d("onError", "error reached");
            }

            @Override
            public void onSuccess(TranslateTextRequest request, TranslateTextResult translateTextResult) {
                Log.d("OnSuccess", "Original Text: " + request.getText());
                Log.d("OnSuccess", "Translated Text: " + translateTextResult.getTranslatedText());
            }
        });
    }
}
