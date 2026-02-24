package com.demoqa.tests;

import static com.demoqa.testData.TestData.*;

import com.demoqa.pages.TextBoxPage;
import org.junit.jupiter.api.Test;

class TextBoxTest extends TestBase {
    private final TextBoxPage textBoxPage = new TextBoxPage();

    @Test
    void positiveFillAllFieldsTest() {
        textBoxPage
                .openPage()
                .typeUserName(firstName)
                .typeUserEmail(userEmail)
                .submitForm()
                .checkField("name", firstName)
                .checkField("email", userEmail);
    }

    @Test
    void negativeWrongEmail() {
        textBoxPage
                .openPage()
                .typeUserName(firstName)
                .typeUserEmail("wrong@mail")
                .submitForm()
                .checkRedBorderForWrongEmail();
    }
}
