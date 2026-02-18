import org.junit.jupiter.api.Test;
import pages.RegistrationPage;

import static testData.TestData.*;

public class Lesson5Test extends TestBase{
    RegistrationPage registrationPage = new RegistrationPage();

    @Test
    void positiveFullDataTest() {
        registrationPage
                .openForm()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setUserEmail(userEmail)
                .setGender(gender)
                .setUserNumber(userNumber)
                .setDateOfBirth(birthDate)
                .setSubject(subjects)
                .setHobbies(hobbies)
                .uploadPicture(picturePath)
                .setCurrentAddress(currentAddress)
                .setStateAndCity(state,city)
                .submitForm()
                .resultTableIsVisible()
                .checkResult("Student Name",firstName + " " + lastName)
                .checkResult("Student Email",userEmail)
                .checkResult("Gender",gender)
                .checkResult("Mobile",userNumber)
                .checkResult("Date of Birth",dayOfBirth + " " + monthOfBirth + "," + yearOfBirth)
                .checkResult("Subjects",subjects)
                .checkResult("Hobbies",hobbies)
                .checkResult("Picture",picturePath.substring(picturePath.lastIndexOf('/') + 1))
                .checkResult("Address",currentAddress)
                .checkResult("State and City",state + " " + city);
    }

    @Test
    void positiveMinimumDataTest() {
        registrationPage
                .openForm()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setGender(gender)
                .setUserNumber(userNumber)
                .submitForm()
                .resultTableIsVisible()
                .checkResult("Student Name",firstName + " " + lastName)
                .checkResult("Gender",gender)
                .checkResult("Mobile",userNumber);
    }

    @Test
	void negativeEmptyFieldsTest(){
        registrationPage
                .openForm()
                .submitForm()
                .checkRedBorderForEmptyName()
                .checkRedColorForEmptyGender()
                .checkIconForEmptyNumber();
    }

    @Test
    void negativeWrongEmailFormatTest(){
        registrationPage
                .openForm()
                .setUserEmail("ivanov@mail")
                .submitForm()
                .checkIconForWrongEmail();

    }
}