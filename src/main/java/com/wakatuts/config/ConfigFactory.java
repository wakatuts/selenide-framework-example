package com.wakatuts.config;

import org.aeonbits.owner.ConfigCache;

public class ConfigFactory {

    private ConfigFactory() {

    }

    public static FrameworkConfig config() {
        return ConfigCache.getOrCreate(FrameworkConfig.class);
    }

}
