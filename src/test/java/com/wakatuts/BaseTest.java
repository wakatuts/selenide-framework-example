package com.wakatuts;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.wakatuts.config.ConfigFactory;
import com.wakatuts.config.FrameworkConfig;
import com.wakatuts.data.model.ContactUsData;
import com.wakatuts.data.model.SignUpData;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;

import static com.codeborne.selenide.Selenide.*;

public class BaseTest {

    protected final FrameworkConfig CONFIG = ConfigFactory.config();
    protected Injector injector = Guice.createInjector(Stage.DEVELOPMENT, new TestContext());

    //DATA OBJECTS. MODIFY IN TESTCONTEXT CLASS.
    @Inject protected ContactUsData contactUsData;
    @Inject protected SignUpData signUpData;


    @BeforeMethod
    void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        Configuration.browserCapabilities = new ChromeOptions()
                .addArguments(CONFIG.driverOptions());
        open(CONFIG.webBaseUrl());
        injector.injectMembers(this);
    }

}
