package com.demoqa.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static helpers.AttachmentsHelper.enableClickHighlight;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.demoqa.pages.components.CalendarComponent;
import com.demoqa.pages.components.ResultsTableComponent;
import com.demoqa.testData.Subject;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RegistrationPage extends DemoqaComParentPage {
    private final CalendarComponent calendar = new CalendarComponent();
    private final ResultsTableComponent resultsTable = new ResultsTableComponent();

    private final SelenideElement firstNameInput = $("#firstName"),
            lastNameInput = $("#lastName"),
            userEmailInput = $("#userEmail"),
            userNumberInput = $("#userNumber"),
            genderWrapper = $("#genterWrapper"), // bug in name on Site
            subjectInput = $("#subjectsInput"),
            subjectAutoComplete = $(".subjects-auto-complete__menu-list"),
            hobbiesWrapper = $("#hobbiesWrapper"),
            uploadPictureInput = $("#uploadPicture"),
            currentAddressInput = $("#currentAddress"),
            stateDropdown = $("#state"),
            stateCityWrapper = $("#stateCity-wrapper"),
            cityDropdown = $("#city"),
            submitButton = $("#submit");

        public RegistrationPage openForm() {
        open("");
        enableClickHighlight();
        $(byText("Forms")).click();
        removeBanners();
        $(byText("Practice Form")).click();
        return this;
    }

    public RegistrationPage setFirstName(String firstName) {
        firstNameInput.setValue(firstName);
        return this;
    }

    public RegistrationPage setLastName(String lastName) {
        lastNameInput.setValue(lastName);
        return this;
    }

    public RegistrationPage setGender(String gender) {
        genderWrapper.$(byText(gender)).click();
        return this;
    }

    public RegistrationPage setUserNumber(String number) {
        userNumberInput.setValue(number);
        return this;
    }

    public RegistrationPage setUserEmail(String email) {
        userEmailInput.setValue(email);
        return this;
    }

    public RegistrationPage setDateOfBirth(LocalDate date) {
        $("#dateOfBirthInput").click();
        calendar.setDate(date);

        return this;
    }

    public RegistrationPage setSubjects(List<Subject> subjects) {
        subjects.forEach(subject -> {
            String displayName = subject.getDisplayName();
            subjectInput.setValue(displayName.substring(0, 2));
            subjectAutoComplete.$(byText(displayName)).click();
        });
        return this;
    }

    public RegistrationPage setHobbies(String hobbies) {
        hobbiesWrapper.$(byText(hobbies)).click();
        return this;
    }

    public RegistrationPage uploadPicture(String picturePath) {
        try {
            String fileName = picturePath.substring(picturePath.lastIndexOf('/') + 1);
            InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(picturePath);
            if (resourceStream == null) {
                throw new IllegalArgumentException("Resource not found: " + picturePath);
            }

            File tempFile = new File(System.getProperty("java.io.tmpdir"), fileName);
            tempFile.deleteOnExit();
            Files.copy(resourceStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            var driver = WebDriverRunner.getWebDriver();
            if (driver instanceof RemoteWebDriver) {
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            }

            uploadPictureInput.uploadFile(tempFile);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload picture: " + picturePath, e);
        }
        return this;
    }

    public RegistrationPage setCurrentAddress(String address) {
        currentAddressInput.setValue(address);
        return this;
    }

    private RegistrationPage setState(String state) {
        stateDropdown.scrollTo().click();
        stateCityWrapper.$(byText(state)).click();
        return this;
    }

    private RegistrationPage setCity(String city) {
        cityDropdown.click();
        stateCityWrapper.$(byText(city)).click();
        return this;
    }

    public RegistrationPage setStateAndCity(String state, String city) {
        setState(state).setCity(city);
        return this;
    }

    public RegistrationPage submitForm() {
        submitButton.scrollTo().click();
        return this;
    }

    public RegistrationPage resultTableIsVisible() {
        resultsTable.isVisible();
        return this;
    }

    public RegistrationPage checkResult(String key, String value) {
        resultsTable.checkResult(key, value);
        return this;
    }

    public RegistrationPage checkResult(String key, List<Subject> subjects) {
        String value = subjects.stream()
                .map(Subject::getDisplayName)
                .collect(Collectors.joining(", "));
        resultsTable.checkResult(key, value);
        return this;
    }

    public RegistrationPage checkRedBorderForEmptyName() {
        firstNameInput.shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        return this;
    }

    public RegistrationPage checkRedColorForEmptyGender() {
        genderWrapper
                .$(".form-check-label")
                .shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        return this;
    }

    public RegistrationPage checkIconForEmptyNumber() {
        userNumberInput.shouldBe(
                match(
                        "background-image contains error icon",
                        el -> el.getCssValue("background-image").contains("stroke='%23dc3545'")));
        return this;
    }

    public RegistrationPage checkIconForWrongEmail() {
        userEmailInput.shouldBe(
                match(
                        "background-image contains error icon",
                        el -> el.getCssValue("background-image").contains("stroke='%23dc3545'")));
        return this;
    }
}
