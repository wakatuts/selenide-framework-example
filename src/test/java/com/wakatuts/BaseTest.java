package com.wakatuts;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.inject.*;
import com.wakatuts.config.ConfigFactory;
import com.wakatuts.config.FrameworkConfig;
import com.wakatuts.data.model.ContactUsData;
import com.wakatuts.data.model.SignUpData;
import com.wakatuts.pages.HomePage;
import com.wakatuts.utils.ConsoleSelenide;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;

import static com.codeborne.selenide.Selenide.*;

public class BaseTest {

    protected final FrameworkConfig CONFIG = ConfigFactory.config();
    protected Injector testInjector = Guice.createInjector(Stage.DEVELOPMENT, new TestContext());

    //PAGE OBJECTS.
    @Inject protected HomePage homePage;

    //DATA OBJECTS PROVIDER. MODIFY IN TEST CONTEXT CLASS.
    @Inject protected Provider<ContactUsData> contactUsData;
    @Inject protected Provider<SignUpData> signUpData;


    @BeforeMethod
    void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        SelenideLogger.addListener("ConsoleSelenide", new ConsoleSelenide());

        Configuration.browserCapabilities = new ChromeOptions().addArguments(CONFIG.driverOptions());
        open(CONFIG.webBaseUrl());
        testInjector.injectMembers(this);
    }

}
