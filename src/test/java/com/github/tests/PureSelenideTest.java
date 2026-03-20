package com.github.tests;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("REGRESS")
@Tag("UI")
@Tag("GITHUB")
class PureSelenideTest extends GitHubTestBase {

    @Test
    @DisplayName("Тест на голом Селениде")
    void testWithPureSelenide() {
        open("/");
        $(".input-button").click();
        $("#query-builder-test").setValue("menand/Qa.Guru.40").submit();
        $(".search-title").click();
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
