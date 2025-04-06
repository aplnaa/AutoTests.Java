package examples.authkey_tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import model.BaseApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import utils.QueryParamsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@DisplayName("GET: Получение информации об иконке")
public class GetSingleIconsTests extends BaseApiTest {
    private String endpoint;

    @Test
    @DisplayName("Проверка успешного получения информации об иконке")
    public void iconInfoGetTest(){
        endpoint = "/v2/icon/1234?thumbnail_size=200";
        Response response = steps.sendGetRequest(endpoint);
        List<String> params = Arrays.asList(
                "attribution", "id", "license_description", "permalink",
                "term", "thumbnail_url", "creator"
        );
        steps.verifyJsonObjectStructure(response, "icon", params);
        steps.checkStatusCode(response, 200);
    }

    @Test
    @DisplayName("Проверка обработки запроса с несуществующим ID")
    @Tag("negative")
    public void iconInfoIncorrectGetTest(){
        endpoint = "/v2/icon/987654321987654321987654321?thumbnail_size=200";
        Response response = steps.sendGetRequest(endpoint);
        steps.checkStatusCode(response, 404);
    }

    @ParameterizedTest
    @CsvSource({
            "abc, 400",      // Нечисловой ID
            "-1, 400",       // Отрицательный ID
            "0, 404"         // Нулевой ID
    })
    @DisplayName("Проверка на невалидные данные параметра - thumbnail_size")
    @Severity(SeverityLevel.NORMAL)
    @Tag("negative")
    public void getIconWithMalformedId(String malformedId, int expectedStatusCode) {
        String endpoint = "/v2/icon/" + malformedId;
        Response response = steps.sendGetRequest(endpoint);
        steps.checkStatusCode(response, expectedStatusCode);
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 200, 500})
    @DisplayName("Проверка на различные данные параметра - thumbnail_size")
    @Severity(SeverityLevel.NORMAL)
    public void getIconWithDifferentThumbnailSizes(int thumbnailSize) {
        String endpoint = "/v2/icon/1234";

        Map<String, List<Object>> queryParams = new QueryParamsBuilder()
                .param("thumbnail_size", thumbnailSize)
                .build();

        Response response = steps.sendGetRequestParam(endpoint, queryParams);
        steps.checkStatusCode(response, 200);

        // Проверка URL миниатюры с нужным размером
        String expectedSizeInUrl = "-" + thumbnailSize + ".png";
        steps.verifyJsonPathContainsSubstring(response, "icon.thumbnail_url", expectedSizeInUrl);
    }

}