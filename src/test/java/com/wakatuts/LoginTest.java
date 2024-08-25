package com.wakatuts;

import com.wakatuts.data.creds.UserCredData;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static com.wakatuts.data.TestDataHandler.getUserCredData;

public class LoginTest extends BaseTest {

    @Test
    @Description("Login Test 1")
    void loginTest1() {
        UserCredData credData = getUserCredData("default");

        homePage.menuBar().chooseInMenuBar("Log in");
        homePage.loginModal().loginUser(credData.getUsername(), credData.getPassword());
    }

}
