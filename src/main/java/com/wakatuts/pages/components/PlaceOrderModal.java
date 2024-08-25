package com.wakatuts.pages.components;

import com.wakatuts.pages.attributes.Fillable;
import com.wakatuts.utils.RunUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.ClickOptions.usingJavaScript;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PlaceOrderModal extends BaseComponent implements Fillable<PlaceOrderModal> {

    private final By nameInput = By.id("name");
    private final By countryInput = By.id("country");
    private final By cityInput = By.id("city");
    private final By creditCardInput = By.id("card");
    private final By monthInput = By.id("month");
    private final By yearInput = By.id("year");

    private final By purchaseButton = By.xpath("//*[text()='Purchase']");
    private final By animationLogo = By.xpath("//*[contains(@class,'sa-line sa-tip animateSuccessTip')]");

    @Step("Click Purchase")
    public PlaceOrderModal clickPurchase() {
        $(purchaseButton).click();
        return this;
    }

    @Step("Confirm Purchase")
    public void confirmPurchase() {
        RunUtils.delay(1);
        $(byText("OK"))
                .shouldBe()
                .shouldBe(visible).shouldBe(clickable)
                .click(usingJavaScript().force());
    }

}
