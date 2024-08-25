package com.wakatuts;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.wakatuts.data.model.ContactUsData;
import com.wakatuts.data.model.SignUpData;
import org.instancio.Instancio;

import static org.instancio.Select.field;

public class TestContext extends AbstractModule {

    @Override
    public void configure() {

    }

    //MOCK DATA OBJECTS BELOW

    @Provides
    public ContactUsData contactUsData() {
        return Instancio.of(ContactUsData.class)
                .generate(field("contactEmail"), gen -> gen.net().email().length(8))
                .generate(field("contactName"), gen -> gen.text().loremIpsum().words(2))
                .generate(field("message"), gen -> gen.text().loremIpsum().words(50))
                .create();
    }

    @Provides
    public SignUpData signUpData() {
        return Instancio.of(SignUpData.class)
                .generate(field("username"), gen -> gen.net().email().length(8))
                .generate(field("password"), gen -> gen.string().length(12).mixedCase())
                .create();
    }

}
