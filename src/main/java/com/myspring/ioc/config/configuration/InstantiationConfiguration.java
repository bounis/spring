package com.myspring.ioc.config.configuration;

import com.myspring.ioc.config.BasicSubConfiguration;
import com.myspring.ioc.config.MagicConfiguration;
import com.myspring.ioc.constants.Constants;

public class InstantiationConfiguration extends BasicSubConfiguration {

    private int maximumAllowedIteration;
    protected InstantiationConfiguration(MagicConfiguration parentConfig) {
        super(parentConfig);
        maximumAllowedIteration = Constants.MAX_NUMBER_OF_INSTANTIATION_ITERATION;
    }

    public InstantiationConfiguration setMaximumNumberOfAllowedIteration(int num) {
        maximumAllowedIteration = num;
        return this;
    }

    public int getMaximumAllowedIteration() {
        return maximumAllowedIteration;
    }
}
