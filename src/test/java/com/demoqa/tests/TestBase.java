package com.demoqa.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import testData.TestData;

import static com.codeborne.selenide.Selenide.*;

class TestBase {


    @BeforeAll
    static void setupConfig() {
        // Configuration.browserSize = "1920x1080";
        Configuration.browserSize = "1280x1024";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        //Configuration.holdBrowserOpen = true;
        //Configuration.timeout = 5000; // default 4000
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
