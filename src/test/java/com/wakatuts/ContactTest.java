package com.wakatuts;

import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class ContactTest extends BaseTest {

    @Test
    @Description("Contact Dummy Test One")
    void fillingContactForm() {
        homePage.menuBar().chooseInMenuBar("Contact");
        homePage.contactModal()
                .populate(contactUsData.get())
                .sendMessage();
    }

    @Test
    @Description("Contact Dummy Test Two")
    void fillingContactForm2() {
        homePage.menuBar().chooseInMenuBar("Contact");
        homePage.contactModal()
                .populate(contactUsData.get())
                .sendMessage();
    }

    @Test
    @Description("Contact Dummy Test Three")
    void fillingContactForm3() {
        homePage.menuBar().chooseInMenuBar("Contact");
        homePage.contactModal()
                .populate(contactUsData.get())
                .sendMessage();
    }

    @Test
    @Description("Contact Dummy Test Four")
    void fillingContactForm4() {
        homePage.menuBar().chooseInMenuBar("Contact");
        homePage.contactModal()
                .populate(contactUsData.get())
                .sendMessage();
    }

}
