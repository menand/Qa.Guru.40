package com.demoqa.tests;

import static com.codeborne.selenide.Selenide.*;
import static helpers.AttachmentsHelper.videoEnabled;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
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
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

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

        Configuration.browserCapabilities = browserOptions; // базовые опции
        Configuration.baseUrl = "https://demoqa.com";
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
                FirefoxOptions firefoxOpts = new FirefoxOptions();
                // Включаем поддержку загрузки файлов через LocalFileDetector
                firefoxOpts.addPreference("dom.file.createInChild", true);
                // Дополнительные настройки для стабильности
                firefoxOpts.addPreference("browser.download.folderList", 2);
                firefoxOpts.addPreference("browser.download.dir", "/home/seluser/Downloads");
                return firefoxOpts;
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
        if (Configuration.remote != null) {
            var driver = WebDriverRunner.getWebDriver();
            if (driver instanceof RemoteWebDriver remoteDriver) {
                remoteDriver.setFileDetector(new LocalFileDetector());
            }
        }
    }
}
