package com.demoqa.pages.components;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

public class ResultsTableComponent {
    private final SelenideElement tableResponsive = $(".table-responsive"),
            modalHeader = $(".modal-header");

    public void checkResult(String key, String value) {
        tableResponsive.$(byText(key)).parent().shouldHave(text(value));
    }

    public void isVisible() {
        modalHeader.shouldHave(text("Thanks for submitting the form"));
    }
}
