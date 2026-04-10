package cloud.autotests.selenoid;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag("REGRESS")
@Tag("API")
@Epic("Selenoid API")
@Feature("Status endpoint")
class ApiTest {

    private static final Logger log = LoggerFactory.getLogger(ApiTest.class);

    @BeforeAll
    @Step("Setup base URI, request/response specifications, and Allure Rest Assured filter")
    static void setUp() {
        RestAssured.baseURI = "https://selenoid.autotests.cloud";
        RestAssured.requestSpecification =
                given().auth().basic("user1", "1234").contentType(ContentType.JSON).log().all();
        RestAssured.responseSpecification = new ResponseSpecBuilder().log(LogDetail.ALL).build();
    }

    @BeforeEach
    void addAllureListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @Test
    @Owner("Andrey Menshov")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка сообщения статуса: версия Selenoid и дата сборки не в будущем")
    @Description(
            "Извлекаем поле message из ответа /wd/hub/status, проверяем формат 'Selenoid X.Y.Z"
                    + " built at ...' и убеждаемся, что дата сборки не позднее текущего момента.")
    void messageContainSelenoidVersionAndBuildDateTest() {
        String message = extractStatusMessage();
        checkMessageFormat(message);
        checkBuildDateNotInFuture(message);
    }

    @Step("Получить поле 'message' из ответа /wd/hub/status")
    private String extractStatusMessage() {
        return given().when()
                .get("/wd/hub/status")
                .then()
                .statusCode(200)
                .extract()
                .path("value.message");
    }

    @Step("Проверить, что сообщение соответствует формату 'Selenoid x.y.z built at ...'")
    private void checkMessageFormat(String message) {
        assertThat(
                "Сообщение должно содержать версию Selenoid и 'built at'",
                message,
                matchesPattern(".*Selenoid \\d+\\.\\d+\\.\\d+ built at.*"));
    }

    @Step("Извлечь дату сборки и проверить, что она не в будущем")
    private void checkBuildDateNotInFuture(String message) {
        Pattern datePattern =
                Pattern.compile("built at (\\d{4}-\\d{2}-\\d{2}_\\d{2}:\\d{2}:\\d{2}[AP]M)");
        Matcher dateMatcher = datePattern.matcher(message);
        assertThat("Дата сборки не найдена в сообщении", dateMatcher.find(), is(true));

        String dateStr = dateMatcher.group(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ssa");
        LocalDateTime buildDate = LocalDateTime.parse(dateStr, formatter);
        LocalDateTime now = LocalDateTime.now();

        assertThat(
                "Дата сборки не может быть в будущем",
                buildDate.isBefore(now) || buildDate.isEqual(now),
                is(true));
    }

    @Test
    @Owner("Andrey Menshov")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка флага 'ready' = true")
    @Description("Убеждаемся, что Selenoid готов принимать команды (value.ready == true)")
    void messageContainReadyTrueTest() {
        given().when()
                .get("/wd/hub/status")
                .then()
                .statusCode(200)
                .body("value.ready", equalTo(true));
    }

    @Test
    @Owner("Andrey Menshov")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("JSON Schema validation")
    @Description(
            "Проверяем, что структура ответа соответствует заранее определённой JSON схеме"
                    + " (status_response_schema.json)")
    void schemaValidationTest() {
        given().when()
                .get("/wd/hub/status")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status_response_schema.json"));
    }

    @Test
    @Owner("Andrey Menshov")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Неверный пароль → 401")
    @Description(
            "Попытка аутентификации с неверным паролем должна возвращать 401 и страницу с ошибкой")
    void wrongPasswordTest() {
        given().auth()
                .basic("user1", "123456")
                .when()
                .get("/wd/hub/status")
                .then()
                .statusCode(401)
                .body(containsString("401 Authorization Required"));
    }

    @Test
    @Owner("Andrey Menshov")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Пустой пароль → 401")
    @Description("Попытка аутентификации с пустым паролем должна возвращать 401")
    void emptyPasswordTest() {
        given().auth()
                .basic("user1", "")
                .when()
                .get("/wd/hub/status")
                .then()
                .statusCode(401)
                .body(containsString("401 Authorization Required"));
    }

    @Test
    @Owner("Andrey Menshov")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Отсутствие авторизации → 401")
    @Description("Запрос без заголовка Authorization должен возвращать 401")
    void noAuthorisationTest() {
        given().auth()
                .none()
                .when()
                .get("/wd/hub/status")
                .then()
                .statusCode(401)
                .body(containsString("401 Authorization Required"));
    }

    @Test
    @Owner("Andrey Menshov")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Несуществующий эндпоинт → 404")
    @Description("Запрос к '/stat' возвращает 404 с текстом '404 page not found'")
    void wrongPageTest() {
        given().auth()
                .none()
                .when()
                .get("/stat")
                .then()
                .statusCode(404)
                .body(containsString("404 page not found"));
    }

    @Test
    @Owner("Andrey Menshov")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка времени ответа")
    @Description("Убеждаемся, что запрос к /wd/hub/status выполняется быстрее 2 секунд")
    void responseTimeTest() {
        long time = given().when().get("/wd/hub/status").then().extract().time();
        log.info("Response time: {} ms", time);
        assertThat("Response time exceeds 5000 ms", time, lessThan(5000L));
    }
}
