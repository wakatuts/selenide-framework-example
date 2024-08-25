package com.wakatuts;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.inject.*;
import com.wakatuts.api.LoginAPI;
import com.wakatuts.config.ConfigFactory;
import com.wakatuts.config.FrameworkConfig;
import com.wakatuts.constants.TestCategory;
import com.wakatuts.data.creds.UserCredData;
import com.wakatuts.data.model.ContactUsData;
import com.wakatuts.data.model.ExecutionData;
import com.wakatuts.data.model.PlaceOrderData;
import com.wakatuts.data.model.SignUpData;
import com.wakatuts.pages.HomePage;
import com.wakatuts.logging.ConsoleSelenide;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;

import static com.codeborne.selenide.Selenide.*;
import static com.wakatuts.data.TestDataHandler.getUserCredData;

public class BaseTest {

    protected final FrameworkConfig CONFIG = ConfigFactory.config();
    protected Injector testInjector = Guice.createInjector(Stage.DEVELOPMENT, new TestContext());

    //EXECUTION DATA
    @Inject protected ExecutionData executionData;

    //PAGE OBJECTS
    @Inject protected HomePage homePage;

    //API
    @Inject protected LoginAPI loginAPI;

    //DATA OBJECTS PROVIDER. MODIFY IN TEST CONTEXT CLASS.
    @Inject protected Provider<ContactUsData> contactUsData;
    @Inject protected Provider<SignUpData> signUpData;
    @Inject protected Provider<PlaceOrderData> orderDataProvider;


    @BeforeMethod(alwaysRun = true)
    void mainSetUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        SelenideLogger.addListener("ConsoleSelenide", new ConsoleSelenide());

        Configuration.browserCapabilities = new ChromeOptions().addArguments(CONFIG.driverOptions());
        open(CONFIG.webBaseUrl());
        testInjector.injectMembers(this);
    }

    protected void injectTokenUsing(UserCredData data, TestCategory category) {
        String token = loginAPI.getTokenUsing(data);
        if(category == TestCategory.WEB || category == TestCategory.ALL)
            WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("tokenp_", token));
        if (category == TestCategory.API || category == TestCategory.ALL)
            executionData.setApiToken(token);
    }

}
