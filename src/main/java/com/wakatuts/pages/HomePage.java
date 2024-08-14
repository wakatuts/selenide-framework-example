package com.wakatuts.pages;

import com.google.inject.Inject;
import com.wakatuts.pages.components.ContactModal;
import com.wakatuts.pages.components.LoginModal;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.SetValueMethod.JS;
import static com.codeborne.selenide.SetValueOptions.withText;

@Accessors(fluent = true)
public class HomePage extends BasePage {

    //COMPONENTS
    @Inject @Getter ContactModal contactModal;
    @Inject @Getter LoginModal loginModal;

    //SELECTORS
    private final By logo = By.xpath("(//img[@src='bm.png'])[1]");

    //VERIFICATIONS
    @Step("Verify that navigated to Home Page")
    public void verifyNavigated() {
        $(logo).shouldBe(visible);
        $(logo).setValue(withText("user").usingMethod(JS));
    }

}
