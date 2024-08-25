package com.wakatuts.pages;

import com.google.inject.Inject;
import com.wakatuts.pages.components.PlaceOrderModal;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Accessors(fluent = true)
public class CartPage extends BasePage {

    //COMPONENTS
    @Inject @Getter PlaceOrderModal placeOrderModal;

    //SELECTORS
    private final By productTitleTexts = By.xpath("//*[@id='tbodyid']//td[2]");
    private final By placeOrderButton = By.xpath("//*[@data-target='#orderModal']");
    private final By successfulOrderIcon = By.xpath("//*[contains(@class, 'sa-icon sa-success animate')]");

    //ACTIONS
    @Step("Place Order")
    public CartPage placeOrder() {
        $(placeOrderButton).click();
        return this;
    }

    //VERIFICATION
    @Step("Verify that items count is {0}")
    public void verifyItemCount(int count) {
        $$(productTitleTexts).shouldHave(size(count));
    }

    @Step("Verify that purchase is successful")
    public void verifySuccessfulPurchase() {
        $(successfulOrderIcon).shouldBe(visible);
    }


}
