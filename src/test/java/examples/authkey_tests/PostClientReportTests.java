package examples.authkey_tests;

import io.restassured.response.Response;
import model.BaseApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.authkey.RequestReportIcons;

import java.util.Arrays;

@DisplayName("Отправка отчета по иконкам")
public class PostClientReportTests extends BaseApiTest {
    private String endpoint = "/v2/client/report";

    @Test
    @DisplayName("Проверка успешной отправки отчета")
    public void iconsPostTest(){
        RequestReportIcons request = new RequestReportIcons(Arrays.asList(42, 143, 1337), true);
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 200);
        steps.jsonBodyContains(response, "total_icons_reported", 3);
    }

    @Test
    @DisplayName("Проверка обработки пустого массива иконок")
    public void checkEmptyIconsPostTest(){
        RequestReportIcons request = new RequestReportIcons(Arrays.asList(), true);
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 200);
        steps.jsonBodyContains(response, "total_icons_reported", 0);
    }

    @Test
    @DisplayName("Проверка обработки невалидных ID иконок")
    public void checkNotValidIconsPostTest(){
        RequestReportIcons request = new RequestReportIcons(Arrays.asList("abc", false), true);
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 200);
        steps.jsonBodyContains(response, "message", "Icons must be an array of integers");
    }
}