package examples.noauth_tests;

import io.qameta.allure.*;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import jdk.jfr.Description;
import methods.rest_steps.Steps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Автотесты без авторизации для метода: GET
*/
@DisplayName("GET: Restful-api")
public class GetRestfulTests {
    private final String BASE_URL = "https://api.restful-api.dev";
    private String endpoint;
    private Steps steps;

    @BeforeEach
    void setUp(){
        steps = new Steps(BASE_URL);
    }

    @Test
    @DisplayName("GET: /objects")
    @Description("Получение данных всех объектам")
    public void sendGetSimpleRequest(){
        endpoint = "/objects";
        Response response = steps.sendGetRequest(endpoint);
        steps.checkStatusCode(response, 200);
    }

    @Test
    @DisplayName("GET: /objects/{id}")
    @Description("Получение данных определенного объекта")
    public void sendGetCertainRequest(){
        endpoint = "/objects/7";
        Response response = steps.sendGetRequest(endpoint);
        steps.checkStatusCode(response, 200);
    }

    @Test
    @DisplayName("GET: /objects?id=3&id=5&id=10")
    @Description("Получение данных нескольких объектов")
    public void sendGetParamRequest(){
        endpoint = "/objects";
        Map<String, List<Object>> params = new HashMap<>();
        params.put("id", Arrays.asList(3,5,10));
        Response response = steps.sendGetRequestParam(endpoint, params);
        steps.checkStatusCode(response, 200);
    }
}