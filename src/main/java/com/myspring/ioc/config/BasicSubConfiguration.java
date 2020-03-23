package com.myspring.ioc.config;

import com.myspring.ioc.config.configuration.CustomAnnotationsConfigurations;

public abstract class BasicSubConfiguration {

    private final MagicConfiguration parentConfig;

    protected BasicSubConfiguration(MagicConfiguration parentConfig) {
        this.parentConfig = parentConfig;
    }

    public MagicConfiguration and() {
        return this.parentConfig;
    }
}
