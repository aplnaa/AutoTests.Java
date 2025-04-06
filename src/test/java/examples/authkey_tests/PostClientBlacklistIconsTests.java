package examples.authkey_tests;

import io.restassured.response.Response;
import model.BaseApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.authkey.RequestPostClientBL;

import java.util.Arrays;

@DisplayName("POST: blacklist/id?type=icon добавление ID иконок в черный список")
public class PostClientBlacklistIconsTests extends BaseApiTest {

    private String endpoint = "/v2/client/blacklist/id?type=icon";

    @Test
    @DisplayName("Проверка успешного добавления ID иконок в черный список")
    public void blacklistPostTest(){
        RequestPostClientBL request = new RequestPostClientBL(Arrays.asList(42, 140, 150, 2376), false);
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 200);
        steps.jsonPathExists(response, "blacklist.icon_ids");
        steps.jsonBodyContains(response, "blacklist.icon_ids", 140);

        logTestResult(response, 200);
    }

    @Test
    @DisplayName("Проверка флага overwrite=true для замены существующего списка")
    public void rewritePostTest(){
        RequestPostClientBL request = new RequestPostClientBL(Arrays.asList(5555, 66666), true);
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 200);

        logTestResult(response, 200);
    }

    @Test
    @DisplayName("Проверка на создание больше 5 записей в BlackList") //в массиве 2 элемента: 5555, 66666
    public void createMoreThan5PostTest(){
        RequestPostClientBL request = new RequestPostClientBL(Arrays.asList(87, 150, 1337, 2376), false);
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 400);
        steps.paramEqualValue(response, "message", "Total blacklist size exceeds maximum allowed of 5");

        logTestResult(response, 400);
    }

    @Test
    @DisplayName("Проверка обработки запроса без параметра blacklist")
    public void noBlackListParameterPostTest(){
        RequestPostClientBL request = new RequestPostClientBL(null, false);
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 400);
        steps.paramEqualValue(response, "message", "No blacklist key found");

        logTestResult(response, 400);
    }

    @Test
    @DisplayName("Проверка обработки невалидных значений в массиве blacklist")
    public void notValidBlackListPostTest(){
        RequestPostClientBL request = new RequestPostClientBL(Arrays.asList("abc", false), false);
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 400);
        steps.paramEqualValue(response, "message", "Blacklist must be an array of integers");

        logTestResult(response, 400);
    }
}