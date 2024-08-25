package com.wakatuts;

import com.codeborne.selenide.WebDriverRunner;
import com.google.inject.Inject;
import com.wakatuts.api.CartAPI;
import com.wakatuts.constants.TestCategory;
import com.wakatuts.data.api.WrappedResponse;
import com.wakatuts.pages.CartPage;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.wakatuts.data.TestDataHandler.getUserCredData;

public class OrderTest extends BaseTest {

    @Inject CartAPI cartAPI;
    @Inject CartPage cartPage;

    @BeforeMethod
    void loginSetup() {
        injectTokenUsing(getUserCredData("default"), TestCategory.ALL);
        WebDriverRunner.getWebDriver().navigate().refresh();
    }

    @Test
    @Feature("Orders")
    @Feature("API")
    void successfulOrder() {
        homePage.menuBar().chooseInMenuBar("Home");
        cartAPI.addItemsToCart(1,2,3);
        homePage.menuBar().chooseInMenuBar("Cart");
        cartPage.verifyItemCount(3);

        cartPage.placeOrder()
                .placeOrderModal()
                .populate(orderDataProvider.get())
                .clickPurchase();
        cartPage.verifySuccessfulPurchase();
        cartPage.placeOrderModal().confirmPurchase();
        homePage.verifyNavigated();
        homePage.menuBar().chooseInMenuBar("Cart");
        cartPage.verifyItemCount(0);
    }

    @AfterMethod(alwaysRun = true)
    void deleteOrders() {
        WrappedResponse response = cartAPI.viewCart();
        List<String> ids = response.getValue("idsArray");
        cartAPI.deleteItemsInCart(ids.toArray(new String[0]));
    }

}
