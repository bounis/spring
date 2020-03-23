package com.myspring.ioc.config;

import com.myspring.ioc.config.configuration.CustomAnnotationsConfigurations;

public class MagicConfiguration {

    private final CustomAnnotationsConfigurations annotations;

    public MagicConfiguration() {
        annotations = new CustomAnnotationsConfigurations(this);
    }

    public CustomAnnotationsConfigurations annotations() {
        return this.annotations;
    }

    public MagicConfiguration build() {
        return this;
    }
}
