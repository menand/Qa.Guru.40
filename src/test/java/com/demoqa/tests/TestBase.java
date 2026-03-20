package com.demoqa.tests;

import static com.codeborne.selenide.Selenide.*;
import static helpers.AttachmentsHelper.videoEnabled;

import com.codeborne.selenide.Configuration;
import com.demoqa.testData.TestData;
import helpers.AttachmentsHelper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

class TestBase {

    @BeforeAll
    static void setupConfig() {
        String browser = System.getProperty("browser", "chrome");
        String resolution = System.getProperty("resolution", "1280x1024");
        videoEnabled = Boolean.parseBoolean(System.getProperty("video.enabled", "false"));

        // 2. Общие настройки Selenide
        Configuration.browser = browser;
        Configuration.browserSize = resolution;
        MutableCapabilities browserOptions = createBrowserOptions(browser);

        // Включаем логи браузера
        Map<String, Object> loggingPrefs = new HashMap<>();
        loggingPrefs.put("browser", "ALL");
        browserOptions.setCapability("goog:loggingPrefs", loggingPrefs);

        Configuration.browserCapabilities = browserOptions; // базовые опции
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "normal";
        if (System.getProperty("selenide.remote") == null) {
            Configuration.browserVersion = "144";
        } else {
            DesiredCapabilities merged = new DesiredCapabilities();
            merged.merge(browserOptions); // копируем все настройки из options (включая логи)
            merged.setCapability(
                    "selenoid:options",
                    Map.of(
                            "enableVNC", true,
                            "enableVideo", true));
            Configuration.browserCapabilities = merged;
        }
    }

    private static MutableCapabilities createBrowserOptions(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome", "opera":
                ChromeOptions chromeOpts = new ChromeOptions();
                Map<String, Object> loggingPrefs = new HashMap<>();
                loggingPrefs.put("browser", "ALL");
                chromeOpts.setCapability("goog:loggingPrefs", loggingPrefs);
                return chromeOpts;
            case "firefox":
                return new FirefoxOptions();
            default:
                return new DesiredCapabilities();
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
