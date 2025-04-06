package methods.rest_steps;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValidationSteps {

    @Step("Проверка статус кода")
    public Response checkStatusCode(Response response, Integer expectedCode){
        Integer actualCode = response.getStatusCode();
        Assertions.assertEquals(expectedCode, actualCode);
        return response;
    }

    @Step("Проверка: существует параметр в ответе")
    public void jsonPathExists(Response response, String jsonPath){
        JsonPath json = response.jsonPath();
        Object value = json.get(jsonPath);
        Assertions.assertNotNull(value, "JSON-path: " + jsonPath + "did NOT find in response");
    }

    @Step("Проверка: существуют параметры в ответе")
    public void verifyJsonObjectStructure(Response response, String basePath, List<String> requiredFields) {
        JsonPath json = response.jsonPath();
        Object baseObject = json.get(basePath);

        Assertions.assertNotNull(baseObject,
                "Объект по пути '" + basePath + "' не найден в ответе");

        for (String field : requiredFields) {
            String fullPath = basePath.isEmpty() ? field : basePath + "." + field;
            Object value = json.get(fullPath);

            Assertions.assertNotNull(value,
                    "Обязательное поле '" + field + "' отсутствует в объекте по пути '" + basePath + "'");
        }
    }

    @Step("Проверка: параметр содержит определенное значение")
    public void jsonBodyContains(Response response, String jsonPath, Object expectedValue){
        JsonPath json = response.jsonPath();
        List<?> list = json.getList(jsonPath);
        Assertions.assertNotNull(list, "Json-путь: " + jsonPath + " не найден");
        Assertions.assertTrue(list.contains(expectedValue), "Массив по Json-пути: " + jsonPath + " не содержит значения: " + expectedValue);
    }

    @Step("Проверка: отсутствуют дубликаты в массиве")
    public void verifyNoDuplicatesInJsonArray(Response response, String jsonPath) {
        JsonPath json = response.jsonPath();
        List<?> list = json.getList(jsonPath);
        Assertions.assertNotNull(list, "JSON-путь '" + jsonPath + "' не найден или не является массивом");

        Set<Object> uniqueItems = new HashSet<>();
        List<Object> duplicates = new ArrayList<>();

        // поиск дубликатов
        for (Object item : list) {
            if (!uniqueItems.add(item)) {
                duplicates.add(item);
            }
        }
        Assertions.assertTrue(duplicates.isEmpty(),
                "В массиве по JSON-пути '" + jsonPath + "' найдены дубликаты: " + duplicates);
    }

    @Step("Проверка: строковое поле {jsonPath} содержит подстроку {substring}")
    public void verifyJsonPathContainsSubstring(Response response, String jsonPath, String substring) {
        JsonPath json = response.jsonPath();
        String value = json.getString(jsonPath);
        Assertions.assertNotNull(value, "JSON-путь '" + jsonPath + "' не найден или не является строкой");
        Assertions.assertTrue(value.contains(substring),
                "Строка по JSON-пути '" + jsonPath + "' не содержит подстроку '" + substring + "'");
    }
}
