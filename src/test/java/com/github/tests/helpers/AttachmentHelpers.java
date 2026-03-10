package com.github.tests.helpers;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class AttachmentHelpers {
    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public static byte[] screenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/html", fileExtension = "html")
    public static String pageSource() {
        return WebDriverRunner.getWebDriver().getPageSource();
    }

    @Attachment(value = "Browser console logs", type = "text/plain")
    public static String browserLogs() {
        return String.join(
                "\n",
                WebDriverRunner.getWebDriver().manage().logs().get("browser").getAll().stream()
                        .map(Object::toString)
                        .toList());
    }

    @Attachment(value = "URL", type = "text/plain")
    public static String url() {
        return WebDriverRunner.url();
    }

    public static void attachAll() {
        screenshot();
        pageSource();
        browserLogs();
        url();
    }
}
