package e.josephmolina.saywhat.AWSModel;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.translate.AmazonTranslateAsyncClient;

public class AmazonTranslateClient {
    private static AWSCredentials awsCredentials;
    private static AmazonTranslateAsyncClient amazonTranslateAsyncClient;

    private AmazonTranslateClient() {

    }

    private static AWSCredentials getAWSCredsInstance() {
        if (awsCredentials != null) {
            return awsCredentials;
        }
        awsCredentials = new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return "AKIAJGCDA5QOOXDIBGGQ";
            }

            @Override
            public String getAWSSecretKey() {
                return  "/sLymCNHHT0n/qGMCqkD7j+hzZ/r/wIoJQF3DjAm";
            }
        };
        return awsCredentials;

    }

    public static AmazonTranslateAsyncClient getAmazonTranslateAsyncClientInstance() {
        if (amazonTranslateAsyncClient != null) {
            return amazonTranslateAsyncClient;
        }
        amazonTranslateAsyncClient = new AmazonTranslateAsyncClient(getAWSCredsInstance());
        return amazonTranslateAsyncClient;
    }
}
