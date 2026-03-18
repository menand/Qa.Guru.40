package com.demoqa.pages;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class DemoqaComParentPage {
    protected void removeBanners() {
        executeJavaScript(
                """
                    const style = document.createElement('style');
                    style.innerHTML = `
                        iframe, .adsbygoogle, #fixedban, footer {
                            display: none !important;
                        }
                    `;
                    document.head.appendChild(style);
                """);
    }
}
