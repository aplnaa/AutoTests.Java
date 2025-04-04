package methods.rest_steps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import methods.config.OAuthConfig;
import methods.config.OAuthConfigLoader;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;

import java.io.StringReader;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class Steps extends ValidationSteps {
    private String baseUrl;
    private String apiKey;
    private String apiSecret;
    private OAuthConsumer consumer;

    // Constructor without auth
    public Steps(String baseUrl){
        this.baseUrl = baseUrl;
        RestAssured.baseURI = baseUrl;
    }

    // Constructor with auth OAuth 1.0
    public Steps(){
        OAuthConfig config = OAuthConfigLoader.loadConfig();
        this.baseUrl = config.getBaseUrl();
        this.apiKey = config.getApiKey();
        this.apiSecret = config.getApiSecret();

        if (apiKey != null && apiSecret != null) {
            this.consumer = new CommonsHttpOAuthConsumer(apiKey, apiSecret);
        }

        RestAssured.baseURI = baseUrl;

        System.out.println("Initialized with API Key: " + apiKey);
        System.out.println("Base URL: " + baseUrl);
    }



    /**
     * Получение заголовка OAuth для конкретного эндпоинта и метода
     * @param endpoint эндпоинт (путь запроса)
     * @param method HTTP метод (GET, POST, etc.)
     * @return заголовок Authorization или null в случае ошибки
     */
    private String getOAuthHeader(String endpoint, String method) {
        if (apiKey == null || apiSecret == null || consumer == null) {
            return null;
        }

        try {
            String fullUrl = baseUrl + endpoint;
            System.out.println("Creating OAuth signature for: " + method + " " + fullUrl);

            // HTTP-запрос в зависимости от метода
            URI uri = new URI(fullUrl);
            org.apache.http.client.methods.HttpRequestBase request;

            switch(method.toUpperCase()) {
                case "POST":
                    request = new HttpPost(uri);
                    break;
                case "PUT":
                    request = new HttpPut(uri);
                    break;
                case "DELETE":
                    request = new HttpDelete(uri);
                    break;
                case "GET":
                default:
                    request = new HttpGet(uri);
            }

            // Подписываем запрос с OAuth
            consumer.sign(request);

            // Получаем заголовок Authorization
            String authHeader = request.getFirstHeader("Authorization").getValue();
            System.out.println("OAuth Header: " + authHeader);

            return authHeader;
        } catch (Exception e) {
            System.err.println("Error creating OAuth signature: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // GET request method
    @Step("Send GET request {endpoint}")
    public Response sendGetRequest(String endpoint){
        RequestSpecification spec = RestAssured.given();

        // Получаем OAuth заголовок для конкретного эндпоинта
        String authHeader = getOAuthHeader(endpoint, "GET");
        if (authHeader != null) {
            spec.header("Authorization", authHeader);
        }

        Response response = spec.get(endpoint).andReturn();
        System.out.println("Response Status Code: " + response.getStatusCode());
        returnResponse(response);
        return response;
    }

    // POST request method
    @Step("Send POST request {endpoint}")
    public Response sendPostRequest(String endpoint, Object request){
        RequestSpecification spec = RestAssured.given();

        // Получаем OAuth заголовок для конкретного эндпоинта
        String authHeader = getOAuthHeader(endpoint, "POST");
        if (authHeader != null) {
            spec.header("Authorization", authHeader);
        }

        Response response = spec
                .contentType(ContentType.JSON)
                .body(request)
                .post(endpoint);

        returnResponse(response);
        return response;
    }

    // PUT request method
    @Step
    public Response sendPutRequest(String endpoint, Object request){
        RequestSpecification spec = RestAssured.given();

        // Получаем OAuth заголовок для конкретного эндпоинта
        String authHeader = getOAuthHeader(endpoint, "PUT");
        if (authHeader != null) {
            spec.header("Authorization", authHeader);
        }

        Response response = spec
                .contentType(ContentType.JSON)
                .body(request)
                .put(endpoint);
        returnResponse(response);
        return response;
    }

    // PATCH request method
    @Step
    public Response sendPatchRequest(String endpoint, Object request){
        RequestSpecification spec = RestAssured.given();

        // PATCH не поддерживается напрямую в Apache HTTP Client, используем POST с заголовком
        String authHeader = getOAuthHeader(endpoint, "POST");
        if (authHeader != null) {
            spec.header("Authorization", authHeader);
        }

        Response response = spec
                .contentType(ContentType.JSON)
                .body(request)
                .patch(endpoint);
        returnResponse(response);
        return response;
    }

    // DELETE request method
    @Step
    public Response sendDeleteRequest(String endpoint){
        RequestSpecification spec = RestAssured.given();

        // Получаем OAuth заголовок для конкретного эндпоинта
        String authHeader = getOAuthHeader(endpoint, "DELETE");
        if (authHeader != null) {
            spec.header("Authorization", authHeader);
        }

        Response response = spec.delete(endpoint);
        returnResponse(response);
        return response;
    }

    @Step
    public Response sendGetRequestParam(String endpoint, Map<String, List<Object>> params){
        RequestSpecification spec = RestAssured.given();

        // Собираем строку с параметрами для OAuth подписи
        StringBuilder endpointWithParams = new StringBuilder(endpoint);
        endpointWithParams.append("?");
        boolean first = true;

        for(Map.Entry<String, List<Object>> entry: params.entrySet()){
            String paramName = entry.getKey();
            List<Object> paramValues = entry.getValue();

            for (Object value : paramValues){
                if (!first) {
                    endpointWithParams.append("&");
                }
                endpointWithParams.append(paramName).append("=").append(value);
                first = false;
            }
        }

        // Получаем OAuth заголовок с учетом параметров
        String authHeader = getOAuthHeader(endpointWithParams.toString(), "GET");
        if (authHeader != null) {
            spec.header("Authorization", authHeader);
        }

        // Добавляем параметры запроса
        for(Map.Entry<String, List<Object>> entry: params.entrySet()){
            String paramName = entry.getKey();
            List<Object> paramValue = entry.getValue();

            for (Object value : paramValue){
                spec.queryParam(paramName, value);
            }
        }

        Response response = spec.get(endpoint).andReturn();
        returnResponse(response);
        return response;
    }

    //Return response method in console
    @Attachment
    private String returnResponse(Response response){
        String jsonResponse = response.getBody().asString();

        try {
            if (jsonResponse != null && !jsonResponse.trim().isEmpty()) {
                Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
                JsonReader jsonReader = new JsonReader(new StringReader(jsonResponse));
                jsonReader.setLenient(true);

                JsonElement jsonElement = JsonParser.parseReader(jsonReader);
                String prettyJson = gson.toJson(jsonElement);
                System.out.println("Результат запроса: \n" + prettyJson);
            } else {
                System.out.println("Результат запроса: пустой ответ");
            }
        } catch (Exception e) {
            System.out.println("Результат запроса (не JSON или некорректный JSON): \n" + jsonResponse);
            System.out.println("Ошибка при парсинге JSON: " + e.getMessage());
        }

        return jsonResponse;
    }
}