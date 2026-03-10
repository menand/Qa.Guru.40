package com.github.tests;

import static com.codeborne.selenide.Selenide.closeWebDriver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class GitHubTestBase {
    static final String PROJECTNAME = "menand/Qa.Guru.40";

    @BeforeAll
    static void setupConfig() {
        Configuration.browserSize = "1280x1024";
        Configuration.baseUrl = "https://github.com";
        Configuration.pageLoadStrategy = "normal";
        Configuration.browser = "chrome";
        Configuration.browserVersion = "144";
    }

    @AfterEach
    void closeBrowser() {
        closeWebDriver();
    }

    @BeforeEach
    void addAllureListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }
}
