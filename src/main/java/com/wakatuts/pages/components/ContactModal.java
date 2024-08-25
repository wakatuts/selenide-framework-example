package com.wakatuts.pages.components;

import com.wakatuts.pages.attributes.Fillable;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ContactModal extends BaseComponent implements Fillable<ContactModal> {

    //SELECTORS
    private final By contactEmailInput = By.id("recipient-email");
    private final By contactNameInput = By.id("recipient-name");
    private final By messageInput = By.id("message-text");
    private final By sendMessageButton = By.xpath("//*[@class='modal-dialog']//button[@onclick='send()']");

    //ACTIONS
    @Step("Sending the contact")
    public ContactModal sendMessage() {
        $(sendMessageButton).click();
        return this;
    }

}
