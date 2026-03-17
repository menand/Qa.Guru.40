package com.demoqa.pages;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class DemoqaComParentPage {
    protected void removeBanners() {
        executeJavaScript(
                """
                    document.getElementById('fixedban')?.remove();
                    document.querySelector('footer')?.remove();
                    document.querySelectorAll('iframe, #fixedban, .adsbygoogle')" +
                                ".forEach(el => el.remove());
                    """);
    }
}
