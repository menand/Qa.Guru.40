package com.demoqa.pages;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class DemoqaComParentPage {
    protected void removeBanners() {
        executeJavaScript(
                """
                setTimeout(() => {
                    document.getElementById('fixedban')?.remove();
                    document.querySelector('footer')?.remove();
                    document.querySelectorAll('iframe, .adsbygoogle')
                            .forEach(el => el.remove());
                }, 500);
                """
        );
    }
}
