import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import com.codeborne.selenide.Configuration;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    @BeforeAll
    static void setupConfig(){
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        //Configuration.holdBrowserOpen = true;
        //Configuration.timeout = 5000; // default 4000
    }

    @AfterEach()
	void closeBrowser() {
        closeWebDriver();
    }


}
