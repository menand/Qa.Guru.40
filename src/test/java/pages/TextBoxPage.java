package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TextBoxPage {
    private final SelenideElement userNameInput = $("#userName"),
            userEmailInput = $("#userEmail"),
            submitButton = $("#submit"),
            outputResults = $("#output");

    public TextBoxPage openPage() {
        open("");
        $(byText("Elements")).click();
        $(byText("Text Box")).click();
        return this;
    }

    public TextBoxPage typeUserName(String value) {
        userNameInput.setValue(value);
        return this;
    }

    public TextBoxPage typeUserEmail(String value) {
        userEmailInput.setValue(value);
        return this;
    }

    public TextBoxPage submitForm() {
        submitButton.click();
        return this;
    }

    public TextBoxPage checkField(String key, String value) {
        outputResults.$(byId(key)).shouldHave(text(value));
        return this;
    }

    public TextBoxPage checkRedBorderForWrongEmail() {
        userEmailInput.shouldHave(cssValue("border-color", "rgb(255, 0, 0)"));
        return this;
    }
}