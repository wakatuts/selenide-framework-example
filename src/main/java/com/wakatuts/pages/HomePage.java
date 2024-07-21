package com.wakatuts.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class HomePage extends BasePage {

    private final By logo = By.xpath("(//img[@src='bm.png'])[1]");

    @Step("Verify that navigated to Home Page")
    public void verifyNavigated() {
        $(logo).shouldBe(visible);
    }

}
