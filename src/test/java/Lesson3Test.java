import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class Lesson3Test extends TestBase{
    @Test
    void positiveFullDataTest() {
        open("/automation-practice-form");
        //Filling
        $("#firstName").setValue("Ivan");
        $("#lastName").setValue("Ivanov");
        $("#userEmail").setValue("ivanov@mail.ru");
        $("#genterWrapper").$(byText("Male")).click();
        $("#userNumber").setValue("1122334455");
        $("#dateOfBirthInput").click();
        $(".react-datepicker__year-select").click();
        $("option[value='1985']").click();
        $(".react-datepicker__month-select").click();
        $("option[value='5']").click();
        $(".react-datepicker__day.react-datepicker__day--010").click();
        $("#subjectsInput").setValue("Ma").pressEnter();
        $("#hobbies-checkbox-2").parent().click();
        $("#uploadPicture").uploadFromClasspath("files/avatar.png");
        $("#currentAddress").setValue("Sezam street, 1");
        $("#state").click();
        $(byText("Haryana")).click();
        $("#city").click();
        $(byText("Panipat")).click();
        $("#submit").click();


        //Checkings
        $(".modal-header").shouldBe(text(
                        "Thanks for submitting the form"));
        $$("tr").find(Condition.text("Student Name"))
                .$$("td").get(1).shouldHave(Condition.text("Ivan Ivanov"));
        $$("tr").find(Condition.text("Student Email"))
                .$$("td").get(1).shouldHave(Condition.text("ivanov@mail.ru"));
        $$("tr").find(Condition.text("Gender"))
                .$$("td").get(1).shouldHave(Condition.text("Male"));
        $$("tr").find(Condition.text("Mobile"))
                .$$("td").get(1).shouldHave(Condition.text("1122334455"));
        $$("tr").find(Condition.text("Date of Birth"))
                .$$("td").get(1).shouldHave(Condition.text("10 June,1985"));
        $$("tr").find(Condition.text("Subjects"))
                .$$("td").get(1).shouldHave(Condition.text("Maths"));
        $$("tr").find(Condition.text("Hobbies"))
                .$$("td").get(1).shouldHave(Condition.text("Reading"));
        $$("tr").find(Condition.text("Picture"))
                .$$("td").get(1).shouldHave(Condition.text("avatar.png"));
        $$("tr").find(Condition.text("Address"))
                .$$("td").get(1).shouldHave(Condition.text("Sezam street, 1"));
        $$("tr").find(Condition.text("State and City"))
                .$$("td").get(1).shouldHave(Condition.text("Haryana Panipat"));
    }
}