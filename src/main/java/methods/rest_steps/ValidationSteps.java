package methods.rest_steps;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class ValidationSteps {

    @Step("Check status code")
    public Response checkStatusCode(Response response, Integer expectedCode){
        Integer actualCode = response.getStatusCode();
        Assertions.assertEquals(expectedCode, actualCode);
        return response;
    }

    @Step("Check JsonPath exists: {}")
    public void jsonPathExists(Response response, String jsonPath){
        JsonPath json = response.jsonPath();
        Object value = json.get(jsonPath);
        Assertions.assertNotNull(value, "JSON-path: " + jsonPath + "did NOT find in response");
    }
}
