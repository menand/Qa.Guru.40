import org.junit.jupiter.api.Test;
import pages.TextBoxPage;
import static testData.TestData.*;

public class Lesson5TextBoxTest extends TestBase
{
    private final TextBoxPage textBoxPage = new TextBoxPage();

    @Test
    void positiveFillAllFieldsTest() {
        textBoxPage.openPage()
                .typeUserName(firstName)
                .typeUserEmail(userEmail)
                .submitForm()
                .checkField("name", firstName)
                .checkField("email", userEmail);
    }

    @Test
    void negativeWrongEmail() {
        textBoxPage.openPage()
                .typeUserName(firstName)
                .typeUserEmail("wrong@mail")
                .submitForm()
                .checkRedBorderForWrongEmail();
    }
}
