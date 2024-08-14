package com.wakatuts;

import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class DummyTest extends BaseTest {

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
