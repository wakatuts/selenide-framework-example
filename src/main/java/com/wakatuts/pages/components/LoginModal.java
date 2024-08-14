package com.wakatuts.pages.components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.SetValueOptions.withText;

public class LoginModal {

    //SELECTORS
    private final By usernameInput = By.id("loginusername");
    private final By passwordInput = By.id("loginpassword");

    //ACTIONS
    @Step("Login as {0}")
    public LoginModal loginUser(String username, String password) {
        $(usernameInput).setValue(username);
        $(passwordInput).setValue(withText(password).sensitive());
        return this;
    }

}
