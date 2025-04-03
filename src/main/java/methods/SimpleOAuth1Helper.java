package methods;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;
import org.apache.http.client.methods.HttpRequestBase;

public class SimpleOAuth1Helper {
    private final CommonsHttpOAuthConsumer consumer;

    public SimpleOAuth1Helper(String apiKey, String apiSecret) {
        this.consumer = new CommonsHttpOAuthConsumer(apiKey, apiSecret);
        this.consumer.setSigningStrategy(new AuthorizationHeaderSigningStrategy());
    }

    public void signRequest(HttpRequestBase request) {
        try {
            consumer.sign(request);
            System.out.println("OAuth Header: " + request.getFirstHeader("Authorization").getValue());
        } catch (Exception e) {
            System.err.println("Error signing request: " + e.getMessage());
        }
    }
}
