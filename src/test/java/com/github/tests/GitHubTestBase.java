package com.github.tests;

import static com.codeborne.selenide.Selenide.closeWebDriver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;

class GitHubTestBase {
    static final String PROJECTNAME = "menand/Qa.Guru.40";

    @BeforeAll
    static void setupConfig() {
        if (System.getProperty("selenide.remote") == null) {
            Configuration.browserSize = "1280x1024";
            Configuration.baseUrl = "https://github.com";
            Configuration.pageLoadStrategy = "normal";
            Configuration.browser = "chrome";
            Configuration.browserVersion = "144";
        } else {
            Configuration.browser = "chrome";
            Configuration.browserSize = "1920x1080";
            Configuration.timeout = 10000;
            Configuration.pageLoadTimeout = 30000;
            Configuration.headless = true; // Обязательно для GitHub Actions

            // КРИТИЧЕСКИ ВАЖНО: настройки для GitHub Actions
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new"); // новый headless режим
            options.addArguments("--no-sandbox"); // обязательно для CI
            options.addArguments("--disable-dev-shm-usage"); // обязательно для CI
            options.addArguments("--disable-gpu"); // опционально
            options.addArguments("--disable-setuid-sandbox");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--window-size=1920,1080");

            // Дополнительные настройки для стабильности
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-notifications");
            options.addArguments("--ignore-certificate-errors");

            Configuration.browserCapabilities = options;
        }
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
