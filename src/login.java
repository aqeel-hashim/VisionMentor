import org.apache.commons.httpclient.Credentials;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class login {
    private JTextField username;
    private JPasswordField password;
    private JButton loginButton;

    public login() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cognitoIdentityId = "your user's identity id";
                String openIdToken = "open id token for the user created on backend";

                Map<String,String> logins = new HashMap<>();
                logins.put("cognito-identity.amazonaws.com", openIdToken);
                GetCredentialsForIdentityRequest getCredentialsRequest =
                        new GetCredentialsForIdentityRequest()
                                .withIdentityId(cognitoIdentityId)
                                .withLogins(logins);
                AmazonCognitoIdentityClient cognitoIdentityClient = new AmazonCognitoIdentityClient();
                GetCredentialsForIdentityResult getCredentialsResult = cognitoIdentityClient.getCredentialsForIdentity(getCredentialsRequest);
                Credentials credentials = getCredentialsResult.getCredentials();
                AWSSessionCredentials sessionCredentials = new BasicSessionCredentials(
                        credentials.getAccessKeyId(),
                        credentials.getSecretKey(),
                        credentials.getSessionToken()
                );

                AmazonS3Client s3Client = new AmazonS3Client(sessionCredentials);









            }
        });
    }
}
