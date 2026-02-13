import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
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
        $(".modal-header").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text("Ivan Ivanov"));
        $(".table-responsive").$(byText("Student Email")).parent().shouldHave(text("ivanov@mail.ru"));
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text("Male"));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text("1122334455"));
        $(".table-responsive").$(byText("Date of Birth")).parent().shouldHave(text("10 June,1985"));
        $(".table-responsive").$(byText("Subjects")).parent().shouldHave(text("Maths"));
        $(".table-responsive").$(byText("Hobbies")).parent().shouldHave(text("Reading"));
        $(".table-responsive").$(byText("Picture")).parent().shouldHave(text("avatar.png"));
        $(".table-responsive").$(byText("Address")).parent().shouldHave(text("Sezam street, 1"));
        $(".table-responsive").$(byText("State and City")).parent().shouldHave(text("Haryana Panipat"));
    }

    @Test
    void positiveMinimumDataTest() {
        open("/automation-practice-form");
        //Filling
        $("#firstName").setValue("Ivan");
        $("#lastName").setValue("Ivanov");
        $("#userNumber").setValue("1122334455");
        $("#genterWrapper").$(byText("Male")).click();
        $("#submit").click();
        //Checkings
        $(".modal-header").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text("Ivan Ivanov"));
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text("Male"));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text("1122334455"));
    }

    @Test
	void negativeEmptyFields(){
        open("/automation-practice-form");
        $("#submit").click();
        $("#firstName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#genterWrapper").$(byText("Male")).shouldHave(cssValue("color", "rgba(220, 53, 69, 1)"));
        $("#userNumber").shouldBe(match("background-image contains error icon",
                el -> el.getCssValue("background-image").contains("stroke='%23dc3545'")));
    }
}