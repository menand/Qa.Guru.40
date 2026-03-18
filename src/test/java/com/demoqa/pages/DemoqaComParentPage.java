package com.demoqa.pages;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class DemoqaComParentPage {
    protected void removeBanners() {
        executeJavaScript(
                """
                document.querySelectorAll('iframe, .adsbygoogle, #fixedban')
                        .forEach(el => el.remove());
                """
        );

        // ждём пока iframe реально исчезнут
        $$("iframe").shouldHave(size(0));
    }
}
