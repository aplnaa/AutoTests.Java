package examples.authkey_tests;

import io.restassured.response.Response;
import jdk.jfr.Description;
import model.BaseApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.authkey.RequestPostClientBL;

import java.util.Arrays;

@DisplayName("Добавление ID иконок в черный список")
public class PostClientBlackListTests extends BaseApiTest {

    private String endpoint;

    @Test
    @DisplayName("POST: /v2/client/blacklist/id?type=icon")
    @Description("Проверка успешного добавления ID иконок в черный список")
    public void basePostTest(){
        endpoint = "/v2/client/blacklist/id?type=icon";
        RequestPostClientBL request = new RequestPostClientBL(Arrays.asList(140, 150, 2376), false);
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 200);
        steps.jsonPathExists(response, "blacklist.icon_ids");
        steps.jsonPathContains(response, "blacklist.icon_ids", 140);

        logTestResult(response, 200);
    }
}
