package com.github.tests;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("REGRESS")
@Tag("UI")
@Tag("GITHUB")
class LambdaNotationTest extends GitHubTestBase {

    @Test
    @DisplayName("Тест c Лямбда-нотацией")
    void testWithPureSelenide() {
        step("Открываем главную страницу", () -> open("/"));
        step("Ищем репозиторий " + PROJECTNAME,
                () -> {
                    $(".input-button").click();
                    $("#query-builder-test").setValue("menand/Qa.Guru.40").submit();
                });
        step("Открываем репозиторий " + PROJECTNAME, () -> $(".search-title").click());
        step("Проверяем что в проекте есть закрытая Issue с названнием \"Получить ачивку\"",
                () -> {
                    $("[data-content='Issues']").click();
                    $$("div[class^='SectionFilterLink-module__title']")
                            .findBy(text("Closed"))
                            .parent()
                            .$("span")
                            .shouldBe(visible, enabled)
                            .click();
                    $$("a[data-testid='issue-pr-title-link']")
                            .findBy(text("Получить ачивку"))
                            .shouldBe(visible);
                });
    }
}
