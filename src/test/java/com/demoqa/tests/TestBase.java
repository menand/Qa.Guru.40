package com.demoqa.tests;

import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Configuration;
import com.demoqa.testData.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class TestBase {

    @BeforeAll
    static void setupConfig() {
        Configuration.browserSize = "1280x1024";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.browserVersion = "144"; //last for Selenide 7.14
    }

    @AfterEach
    void closeBrowser() {
        closeWebDriver();
    }

    @BeforeEach
    void prepareTestDate() {
        TestData.prepareTestDate();
    }
}
