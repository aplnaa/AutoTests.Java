package methods.config;
public class OAuthConfig {
    private String apiKey;
    private String apiSecret;
    private String baseUrl;

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {return apiSecret;}

    public String getBaseUrl() {return baseUrl;}

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
