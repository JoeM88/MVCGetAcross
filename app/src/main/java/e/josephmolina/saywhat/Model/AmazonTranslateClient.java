package e.josephmolina.saywhat.Model;

import com.amazonaws.auth.AWSCredentials;

public class AWSCreds {
    private static AWSCredentials awsCredentials;

    private AWSCreds() {

    }

    public static AWSCredentials getAWSCredsInstance() {
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
                return  "sLymCNHHT0n/qGMCqkD7j+hzZ/r/wIoJQF3DjAm";
            }
        };
        return awsCredentials;

    }
}

/*
        AmazonTranslateAsyncClient translateAsyncClient = new AmazonTranslateAsyncClient(awsCredentials);

 */