package com.myspring.ioc.config.configuration;

import com.myspring.ioc.config.BasicSubConfiguration;
import com.myspring.ioc.config.MagicConfiguration;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomAnnotationsConfigurations extends BasicSubConfiguration {

    private final Set<Class<? extends Annotation>> customServiceAnnotations;
    private final Set<Class<? extends Annotation>> customBeanAnnotations;
    public CustomAnnotationsConfigurations(MagicConfiguration parentConfig) {
        super(parentConfig);
        this.customServiceAnnotations = new HashSet<>();
        this.customBeanAnnotations = new HashSet<>();
    }

    public CustomAnnotationsConfigurations addCustomServiceAnnotation(
        Class<? extends Annotation> annotation) {
        customServiceAnnotations.add(annotation);
        return this;
    }

    public CustomAnnotationsConfigurations addCustomServiceAnnotations(
        Class<? extends Annotation>... annotations) {
        customServiceAnnotations.addAll(Arrays.asList(annotations));
        return this;
    }

    public CustomAnnotationsConfigurations addCustomBeanAnnotation(
        Class<? extends Annotation> annotation) {
        customServiceAnnotations.add(annotation);
        return this;
    }

    public CustomAnnotationsConfigurations addCustomBeanAnnotations(
        Class<? extends Annotation>... annotations) {
        customServiceAnnotations.addAll(Arrays.asList(annotations));
        return this;
    }

    public Set<Class<? extends Annotation>> getCustomServiceAnnotations() {
        return customServiceAnnotations;
    }

    public Set<Class<? extends Annotation>> getCustomBeanAnnotations() {
        return customBeanAnnotations;
    }
}

