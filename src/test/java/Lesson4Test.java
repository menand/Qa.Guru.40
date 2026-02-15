import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static testData.TestData.*;

public class Lesson4Test extends TestBase{
    @Test
    void positiveFullDataTest() {
        openTestForm();
        //Filling
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(userEmail);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(userNumber);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__year-select").click();
        $("option[value='" + yearOfBirth + "']").click();
        $(".react-datepicker__month-select").click();
        $("option[value='" + (java.time.Month.valueOf(monthOfBirth.toUpperCase()).getValue()-1) + "']").click();
        $(".react-datepicker__day.react-datepicker__day--0" + dayOfBirth).click();
        $("#subjectsInput").setValue(subjects.substring(0, 2)).pressEnter();
        $("#hobbies-checkbox-2").parent().click();
        $("#uploadPicture").uploadFromClasspath(picturePath);
        $("#currentAddress").setValue(currentAddress);
        $("#state").scrollTo().click();
        $(byText(state)).click();
        $("#city").click();
        $(byText(city)).click();
        $("#submit").scrollTo().click();
        //Checkings
        $(".modal-header").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text(firstName + " " + lastName));
        $(".table-responsive").$(byText("Student Email")).parent().shouldHave(text(userEmail));
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text(gender));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text(userNumber));
        $(".table-responsive").$(byText("Date of Birth")).parent().shouldHave(text(dayOfBirth + " " + monthOfBirth + "," + yearOfBirth));
        $(".table-responsive").$(byText("Subjects")).parent().shouldHave(text(subjects));
        $(".table-responsive").$(byText("Hobbies")).parent().shouldHave(text(hobbies));
        $(".table-responsive").$(byText("Picture")).parent().shouldHave(text(picturePath.substring(picturePath.lastIndexOf('/') + 1)));
        $(".table-responsive").$(byText("Address")).parent().shouldHave(text(currentAddress));
        $(".table-responsive").$(byText("State and City")).parent().shouldHave(text(state + " " + city));
    }

    @Test
    void positiveMinimumDataTest() {
        openTestForm();
        //Filling
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userNumber").setValue(userNumber);
        $("#genterWrapper").$(byText(gender)).click();
        $("#submit").scrollTo().click();
        //Checkings
        $(".modal-header").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text(firstName + " " + lastName));
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text(gender));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text(userNumber));
    }

    @Test
	void negativeEmptyFieldsTest(){
        openTestForm();

        $("#submit").scrollTo().click();
        $("#firstName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#genterWrapper").$(byText(gender)).shouldHave(cssValue("color", "rgba(220, 53, 69, 1)"));
        $("#userNumber").shouldBe(match("background-image contains error icon",
                el -> el.getCssValue("background-image").contains("stroke='%23dc3545'")));
    }

    @Test
    void negativeWrongEmailFormatTest(){
        openTestForm();

        $("#userEmail").setValue("ivanov@mail");
        $("#submit").scrollTo().click();
        $("#userEmail").shouldBe(match("background-image contains error icon",
                el -> el.getCssValue("background-image").contains("stroke='%23dc3545'")));
    }
}