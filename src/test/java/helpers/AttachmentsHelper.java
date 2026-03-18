package helpers;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.sessionId;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.stream.Collectors;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

public class AttachmentsHelper {
    @Attachment(value = "{attachName}", type = "image/png", fileExtension = "png")
    public static byte[] screenshotAs(String attachName) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/html", fileExtension = "html")
    public static String pageSource() {
        return WebDriverRunner.getWebDriver().getPageSource();
    }

    @Attachment(value = "Browser console logs", type = "text/plain")
    public static String browserConsoleLogs() {
        LogEntries logs = WebDriverRunner.getWebDriver().manage().logs().get("browser");
        return logs.getAll().stream().map(LogEntry::toString).collect(Collectors.joining("\n"));
    }

    @Attachment(value = "URL", type = "text/plain")
    public static String url() {
        return WebDriverRunner.url();
    }

    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String addVideo() {
        return "<html><body><video width='100%' height='100%' controls autoplay><source src='"
                + getVideoUrl()
                + "' type='video/mp4'></video></body></html>";
    }

    public static URL getVideoUrl() {
        String videoUrl = "https://selenoid.autotests.cloud/video/" + sessionId() + ".mp4";
        try {
            return URI.create(videoUrl).toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void attachAll() {
        screenshotAs("ScreenShot");
        pageSource();
        browserConsoleLogs();
        url();
        addVideo();
    }

    public static void enableClickHighlight() {
        executeJavaScript("""
        document.addEventListener('click', function(e) {
            const circle = document.createElement('div');
            circle.style.position = 'fixed';
            circle.style.left = e.clientX + 'px';
            circle.style.top = e.clientY + 'px';
            circle.style.width = '20px';
            circle.style.height = '20px';
            circle.style.border = '2px solid red';
            circle.style.borderRadius = '50%';
            circle.style.zIndex = 9999;
            document.body.appendChild(circle);
            setTimeout(() => circle.remove(), 500);
        });
    """);
    }
}
