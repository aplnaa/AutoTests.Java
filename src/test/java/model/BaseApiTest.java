package model;

import io.restassured.response.Response;
import methods.rest_steps.Steps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

public abstract class BaseApiTest {
    protected Steps steps;
    protected String testName;

    @BeforeEach
    void setUp(TestInfo testInfo){
        testName = testInfo.getDisplayName();
        steps = new Steps();
    }

    protected void logTestResult(Response response, int expectedStatusCode){
        boolean success = response.getStatusCode() == expectedStatusCode;
        System.out.println("Тест: " + testName);
        System.out.println("Результат: " + (success ? "УСПЕХ" : "ОШИБКА"));
        System.out.println("Ожидаемый статус: " + expectedStatusCode);
        System.out.println("Фактический статус: " + response.getStatusCode());
        System.out.println("-----------------------------------");
    }
}
