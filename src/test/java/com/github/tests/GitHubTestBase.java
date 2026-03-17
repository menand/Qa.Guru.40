package com.github.tests;

import static com.codeborne.selenide.Selenide.closeWebDriver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.AttachmentsHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

class GitHubTestBase {
    static final String PROJECTNAME = "menand/Qa.Guru.40";

    @BeforeAll
    static void setupConfig() {
        Configuration.browserSize = "1280x1024";
        Configuration.baseUrl = "https://github.com";
        Configuration.pageLoadStrategy = "normal";
        Configuration.browser = "chrome";
        if (System.getProperty("selenide.remote") == null) {
            Configuration.browserVersion = "144";
        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }
    }

    @AfterEach
    void closeBrowser() {
        AttachmentsHelper.attachAll();
        closeWebDriver();
    }

    @BeforeEach
    void addAllureListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }
}
