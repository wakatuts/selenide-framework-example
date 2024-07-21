package com.wakatuts;

import com.google.inject.Inject;
import com.wakatuts.pages.HomePage;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class DummyTest extends BaseTest {

    @Inject HomePage homePage;

    @Test
    @Description("Dummy Test One")
    void dummyTest() {
        homePage.menuBar().chooseInMenuBar("Home");
    }

    @Test
    @Description("Dummy Test Two")
    void dummyTest2() {
        homePage.verifyNavigated();
    }

}
