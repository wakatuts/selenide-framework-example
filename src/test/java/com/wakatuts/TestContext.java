package com.wakatuts;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.wakatuts.data.model.ContactUsData;
import com.wakatuts.data.model.ExecutionData;
import com.wakatuts.data.model.PlaceOrderData;
import com.wakatuts.data.model.SignUpData;
import org.instancio.Instancio;

import static org.instancio.Select.field;

public class TestContext extends AbstractModule {

    private final ThreadLocal<ExecutionData> executionDataThreadLocal = new ThreadLocal<>();

    @Override
    public void configure() {
    }

    //EXECUTION DATA
    @Provides
    public ExecutionData executionData() {
        if(executionDataThreadLocal.get() == null) executionDataThreadLocal.set(new ExecutionData());
        return executionDataThreadLocal.get();
    }

    //MOCK DATA OBJECTS BELOW
    @Provides
    public ContactUsData contactUsData() {
        return Instancio.of(ContactUsData.class)
                .generate(field("contactEmail"), gen -> gen.net().email().length(10))
                .generate(field("contactName"), gen -> gen.text().loremIpsum().words(2))
                .generate(field("message"), gen -> gen.text().loremIpsum().words(10))
                .create();
    }

    @Provides
    public SignUpData signUpData() {
        return Instancio.of(SignUpData.class)
                .generate(field("username"), gen -> gen.net().email().length(8))
                .generate(field("password"), gen -> gen.string().length(12).mixedCase())
                .create();
    }

    @Provides
    public PlaceOrderData placeOrderData() {
        Address address = new Faker().address();
        return Instancio.of(PlaceOrderData.class)
                .generate(field("name"), gen -> gen.text().loremIpsum().words(2))
                .set(field("country"), address.country())
                .set(field("city"), address.city())
                .generate(field("creditCard"), gen -> gen.finance().creditCard())
                .generate(field("month"), gen -> gen.temporal().yearMonth().future().as(ym -> String.valueOf(ym.getMonthValue())))
                .generate(field("year"), gen -> gen.temporal().year().future().as(y -> String.valueOf(y.getValue())))
                .create();
    }

}
