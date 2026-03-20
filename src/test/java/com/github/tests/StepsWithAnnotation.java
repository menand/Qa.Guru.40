package com.github.tests;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Tag;

@Tag("REGRESS")
@Tag("UI")
@Tag("GITHUB")
class StepsWithAnnotation {

    @Step("Открываем github.com")
    void openGitHub() {
        open("/");
    }

    @Step("Ищем репозиторий {repo}")
    void searchForRepository(String repo) {
        $(".input-button").click();
        $("#query-builder-test").setValue(repo).submit();
        $(".search-title").click();
    }

    @Step("Открываем закрытые Issue")
    void openClosedIssues() {
        $("[data-content='Issues']").click();
        $$("div[class^='SectionFilterLink-module__title']")
                .findBy(text("Closed"))
                .parent()
                .$("span")
                .shouldBe(visible, enabled)
                .click();
    }

    @Step("Проверяем что в проекте есть закрытая Issue с названнием \"Получить ачивку\"")
    void shouldHaveClosedIssue() {
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
    }
}
