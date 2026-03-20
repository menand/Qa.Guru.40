package com.github.tests;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static helpers.AttachmentsHelper.videoEnabled;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.AttachmentsHelper;
import io.qameta.allure.selenide.AllureSelenide;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

class GitHubTestBase {
    static final String PROJECTNAME = "menand/Qa.Guru.40";

    @BeforeAll
    static void setupConfig() {
        String browser = System.getProperty("browser", "chrome");
        String resolution = System.getProperty("resolution", "1280x1024");
        videoEnabled = Boolean.parseBoolean(System.getProperty("video.enabled", "false"));

        // 2. Общие настройки Selenide
        Configuration.browser = browser;
        Configuration.browserSize = resolution;
        MutableCapabilities browserOptions = createBrowserOptions(browser);

        Configuration.browserCapabilities = browserOptions; // базовые опции
        Configuration.baseUrl = "https://github.com";
        Configuration.pageLoadStrategy = "normal";
        String remoteUrl = System.getProperty("selenide.remote");
        if (remoteUrl == null || remoteUrl.isBlank()) {
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
            Configuration.remote = remoteUrl;
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
    void addAllureListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }
}
