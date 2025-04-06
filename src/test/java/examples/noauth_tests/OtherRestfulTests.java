package examples.noauth_tests;

import io.restassured.response.Response;
import jdk.jfr.Description;
import methods.rest_steps.Steps;
import org.junit.jupiter.api.*;
import requests.noauth.RequestPatchRestful;
import requests.noauth.RequestPostRestful;
import requests.RequestPutRestful;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

/*
    Автотесты без авторизации по методам: POST, PUT, PATCH, DELETE
*/
@DisplayName("POST, PUT, PATCH and DELETE tests")
@TestMethodOrder(OrderAnnotation.class)
public class OtherRestfulTests {
    private static String BASE_URL = "https://api.restful-api.dev";
    private static String createdId;
    private String endpoint;
    private Steps steps;

    @BeforeEach
    void setUp(){
        steps = new Steps(BASE_URL);
    }

    @Test
    @Order(1)
    @DisplayName("POST: /objects")
    @Description("Создание объекта")
    public void sendPostRequest(){
        endpoint = "/objects";
        RequestPostRestful request = new RequestPostRestful("Apple MacBook Pro 16", new RequestPostRestful.Data(2019, 1849.99, "Intel Core i9", "1 TB"));
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 200);

        createdId = response.jsonPath().getString("id");
    }

    @Test
    @Order(2)
    @DisplayName("PUT: /objects/{id}")
    @Description("Обновление всего объекта по айди")
    public void testPut(){
        endpoint = "/objects/" + createdId;
        RequestPutRestful request = new RequestPutRestful("Apple MacBook Pro 16", new RequestPutRestful.Data(2019, 1849.99, "gray","Intel Core i9", "1 TB"));
        Response response = steps.sendPutRequest(endpoint, request);
        steps.checkStatusCode(response, 200);
    }

    @Test
    @Order(3)
    @DisplayName("PATCH: /objects/{id}")
    @Description("Обновление конкретного параметра в объекте по айди")
    public void testPatch(){
        endpoint = "/objects/" + createdId;
        RequestPatchRestful request = new RequestPatchRestful("Apple MacBook Air 13 2020");
        Response response = steps.sendPatchRequest(endpoint, request);
        steps.checkStatusCode(response, 200);
    }

    @Test
    @Order(4)
    @DisplayName("DELETE: /objects/{id}")
    @Description("Удаление всего объекта по айди")
    public void testDelete(){
        endpoint = "/objects/" + createdId;
        Response response = steps.sendDeleteRequest(endpoint);
        steps.checkStatusCode(response, 200);
    }
}
