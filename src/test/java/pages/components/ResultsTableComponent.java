package pages.components;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ResultsTableComponent {
    public void checkResult(String key, String value) {
        $(".table-responsive").$(byText(key)).parent().shouldHave(text(value));
    }

    public void isVisible(){
        $(".modal-header").shouldHave(text("Thanks for submitting the form"));
    }
}
