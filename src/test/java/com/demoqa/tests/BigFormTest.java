package com.demoqa.tests;

import static com.demoqa.testData.TestData.*;

import com.demoqa.pages.RegistrationPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Проверка большой формы регистрации")
class BigFormTest extends TestBase {
    private final RegistrationPage registrationPage = new RegistrationPage();

    @Tag("REGRESS")
    @ParameterizedTest(name = "Проверка регистрации с полом: {0}")
    @ValueSource(strings = {"Male", "Female", "Other"})
    @DisplayName("Проверка максимального набора данных")
    void positiveFullDataAllGenderTest(String gender) {
        registrationPage
                .openForm()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setUserEmail(userEmail)
                .setGender(gender)
                .setUserNumber(userNumber)
                .setDateOfBirth(birthDate)
                .setSubjects(subjects)
                .setHobbies(hobbies)
                .uploadPicture(picturePath)
                .setCurrentAddress(currentAddress)
                .setStateAndCity(state, city)
                .submitForm()
                .resultTableIsVisible()
                .checkResult("Student Name", firstName + " " + lastName)
                .checkResult("Student Email", userEmail)
                .checkResult("Gender", gender)
                .checkResult("Mobile", userNumber)
                .checkResult("Date of Birth", dayOfBirth + " " + monthOfBirth + "," + yearOfBirth)
                .checkResult("Subjects", subjects)
                .checkResult("Hobbies", hobbies)
                .checkResult("Picture", picturePath.substring(picturePath.lastIndexOf('/') + 1))
                .checkResult("Address", currentAddress)
                .checkResult("State and City", state + " " + city);
    }

    @Tag("SMOKE")
    @ParameterizedTest(name = "Проверка регистрации с разными языками: {arguments}")
    @CsvSource(value = {
            "John, Doe",              // Английский
            "José, García",           // Испанский
            "王, 小明",                // Китайский
            "山田, 太郎",              // Японский
            "Matti, Meikäläinen"      // Финский
    })
    void positiveMinimumDataAllLanguageTest(String firstName, String lastName) {
        registrationPage
                .openForm()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setGender(gender)
                .setUserNumber(userNumber)
                .submitForm()
                .resultTableIsVisible()
                .checkResult("Student Name", firstName + " " + lastName)
                .checkResult("Gender", gender)
                .checkResult("Mobile", userNumber);
    }

    @Tag("SMOKE")
    @ParameterizedTest(name = "Проверка регистрации с разными данными: {arguments}")
    @CsvFileSource(resources = "/files/csv/namesForTests.csv", numLinesToSkip = 1)
    void positiveMinimumDataDifferentNamesLengthTest(String firstName, String lastName) {
        registrationPage
                .openForm()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setGender(gender)
                .setUserNumber(userNumber)
                .submitForm()
                .resultTableIsVisible()
                .checkResult("Student Name", firstName + " " + lastName)
                .checkResult("Gender", gender)
                .checkResult("Mobile", userNumber);
    }

    @Test
    @DisplayName("Негативная проверка - не заполненные поля")
    void negativeEmptyFieldsTest() {
        registrationPage
                .openForm()
                .submitForm()
                .checkRedBorderForEmptyName()
                .checkRedColorForEmptyGender()
                .checkIconForEmptyNumber();
    }

    @Test
    @DisplayName("Негативная проверка - не верный формат Email")
    void negativeWrongEmailFormatTest() {
        registrationPage
                .openForm()
                .setUserEmail("ivanov@mail")
                .submitForm()
                .checkIconForWrongEmail();
    }
}
