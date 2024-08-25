package com.wakatuts.pages.components;

import io.qameta.allure.Step;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MenuBar extends BaseComponent {

    //SELECTORS
    private final By menuBarButtons = By.xpath("//li[contains(@class,'nav-item')]/a");

    //ACTIONS
    @Step("Choose {0} in Menu Bar")
    public void chooseInMenuBar(String menu) {
        $$(menuBarButtons).find(text(menu)).click();
    }

    //ASSERTIONS

}
