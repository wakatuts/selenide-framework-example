package com.wakatuts.config;

import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/selenide.properties",
        "file:src/test/resources/${environment}-config.properties",
        "file:src/test/resources/${selenide.browser}-webdriver.properties"
})
public interface FrameworkConfig extends Config {

    String environment();

    @Key("${environment}.web.url")
    String webBaseUrl();

    @Key("${selenide.browser}options.args")
    String[] driverOptions();

    @Key("selenide.browser")
    String browser();

}
