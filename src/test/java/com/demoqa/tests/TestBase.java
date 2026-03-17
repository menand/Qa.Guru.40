package com.demoqa.tests;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Configuration;
import com.demoqa.testData.TestData;
import helpers.AttachmentsHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

class TestBase {

    @BeforeAll
    static void setupConfig() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> loggingPrefs = new HashMap<>();
        loggingPrefs.put("browser", "ALL");
        options.setCapability("goog:loggingPrefs", loggingPrefs);
        Configuration.browserCapabilities = options; // базовые опции

        Configuration.browserSize = "1280x1024";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = "chrome";
        if (System.getProperty("selenide.remote") == null) {
            Configuration.browserVersion = "144";
        } else {
            DesiredCapabilities merged = new DesiredCapabilities();
            merged.merge(options); // копируем все настройки из options (включая логи)
            merged.setCapability(
                    "selenoid:options",
                    Map.of(
                            "enableVNC", true,
                            "enableVideo", true));
            Configuration.browserCapabilities = merged;
        }
    }

    @AfterEach
    void closeBrowser() {
        AttachmentsHelper.attachAll();
        closeWebDriver();
    }

    @BeforeEach
    void prepareTestDate() {
        TestData.prepareTestDate();
    }
}
