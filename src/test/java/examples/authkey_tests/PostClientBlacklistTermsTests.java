package examples.authkey_tests;

import io.restassured.response.Response;
import model.BaseApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requests.authkey.RequestPostClientBL;

import java.util.Arrays;

@DisplayName("POST: blacklist/term Добавление поисковых терминов в черный список")
public class PostClientBlacklistTermsTests extends BaseApiTest {
    private String endpoint = "/v2/client/blacklist/term";

    @Test
    @DisplayName("Проверка успешного добавления поисковых терминов в черный список")
    public void termPostTest(){
        RequestPostClientBL request = new RequestPostClientBL(Arrays.asList("breaking bad", "star wars"), false);
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 200);

        logTestResult(response, 200);
    }

    @Test
    @DisplayName("Проверка обработки дублирующихся терминов") //дубликатов не должно быть
    public void checkDuplicatesPostTest(){
        RequestPostClientBL request = new RequestPostClientBL(Arrays.asList("breaking bad", "breaking bad"), false);
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 200);
        steps.verifyNoDuplicatesInJsonArray(response, "blacklist.search_terms");

        logTestResult(response, 200);
    }

    @Test
    @DisplayName("Проверка поддержки unicode и спец символов")
    public void checkUnicodePostTest(){
        RequestPostClientBL request = new RequestPostClientBL(Arrays.asList("is ünîcōdę šüppørtëd and phråses?", "speci@l s&^&%ymbols()"), false);
        Response response = steps.sendPostRequest(endpoint, request);
        steps.checkStatusCode(response, 200);

        logTestResult(response, 200);
    }

}
