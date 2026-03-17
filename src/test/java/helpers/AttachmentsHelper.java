package helpers;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static com.codeborne.selenide.Selenide.sessionId;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AttachmentsHelper {
    @Attachment(value = "{attachName", type = "image/png", fileExtension = "png")
    public static byte[] screenshotAs(String attachName) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }


    @Attachment(value = "Page source", type = "text/html", fileExtension = "html")
    public static String pageSource() {
        return WebDriverRunner.getWebDriver().getPageSource();
    }

    @Attachment(value = "Browser console logs", type = "text/plain")
    public static void browserConsoleLogs() {
        String.join(
                "\n",
                getWebDriver().manage().logs().get("browser").getAll().stream()
                        .map(Object::toString)
                        .toList());
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
}
