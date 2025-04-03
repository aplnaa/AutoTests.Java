package examples.noauth_tests;

import io.restassured.response.Response;
import jdk.jfr.Description;
import methods.rest_steps.Steps;
import requests.noauth.RequestPatchRestful;
import requests.noauth.RequestPostRestful;
import requests.RequestPutRestful;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
    Автотесты без авторизации по методам: POST, PUT, PATCH, DELETE
*/
@DisplayName("POST, PUT, PATCH and DELETE tests")
public class OtherRestfulTests {
    private static String BASE_URL = "https://api.restful-api.dev";
    private String endpoint;
    private Steps steps;

    @BeforeEach
    void setUp(){
        steps = new Steps(BASE_URL);
    }

    @Test
    @DisplayName("POST: /objects")
    @Description("Создание объекта")
    public void sendPostRequest(){
        endpoint = "/objects";
        RequestPostRestful request = new RequestPostRestful("Apple MacBook Pro 16", new RequestPostRestful.Data(2019, 1849.99, "Intel Core i9", "1 TB"));
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 200);
    }

    @Test
    @DisplayName("PUT: /objects/{id}")
    @Description("Обновление всего объекта по айди")
    public void testPut(){
        endpoint = "/objects/ff808181932badb60195f74dd2690615";
        RequestPutRestful request = new RequestPutRestful("Apple MacBook Pro 16", new RequestPutRestful.Data(2019, 1849.99, "gray","Intel Core i9", "1 TB"));
        Response response = steps.sendPutRequest(endpoint, request);
        steps.checkStatusCode(response, 200);
    }

    @Test
    @DisplayName("PATCH: /objects/{id}")
    @Description("Обновление конкретного параметра в объекте по айди")
    public void testPatch(){
        endpoint = "/objects/ff808181932badb60195f74dd2690615";
        RequestPatchRestful request = new RequestPatchRestful("Apple MacBook Air 13 2020");
        Response response = steps.sendPatchRequest(endpoint, request);
        steps.checkStatusCode(response, 200);
    }

    @Test
    @DisplayName("DELETE: /objects/{id}")
    @Description("Удаление всего объекта по айди")
    public void testDelete(){
        endpoint = "/objects/ff808181932badb60195f74dd2690615";
        Response response = steps.sendDeleteRequest(endpoint);
        steps.checkStatusCode(response, 200);
    }
}
