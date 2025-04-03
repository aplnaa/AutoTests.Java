package examples.authkey_tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import model.BaseApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("GET tests")
public class GetTests extends BaseApiTest {
    private String endpoint;

    @Test
    @DisplayName("GET: /v2/client/blacklist")
    @Description("Проверка успешного получения черного списка клиента")
    public void getBlackList(){
        endpoint = "/v2/client/blacklist";
        Response response = steps.sendGetRequest(endpoint);
        steps.checkStatusCode(response, 200);

        logTestResult(response, 200);
    }
}
