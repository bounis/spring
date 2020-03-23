package com.myspring.ioc.services;

import com.myspring.ioc.annotations.Autowired;
import com.myspring.ioc.annotations.Bean;
import com.myspring.ioc.annotations.PostConstruct;
import com.myspring.ioc.annotations.PreDestroy;
import com.myspring.ioc.annotations.Service;
import com.myspring.ioc.config.configuration.CustomAnnotationsConfigurations;
import com.myspring.ioc.models.ServiceDetails;
import com.myspring.ioc.utils.ServiceDetailsConstructComparator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ServicesScanningServiceImpl implements ServicesScanningService {

    private final CustomAnnotationsConfigurations configurations;

    public ServicesScanningServiceImpl(CustomAnnotationsConfigurations configurations) {
        this.configurations = configurations;
        init();
    }

    @Override
    public Set<ServiceDetails<?>> mapServices(Set<Class<?>> locatedClasses) {
        final Set<ServiceDetails<?>> serviceDetailsStorage = new HashSet<>();
        Set<Class<? extends Annotation>> customServiceAnnotations = configurations
            .getCustomServiceAnnotations();

        for (Class<?> cls : locatedClasses) {
            if (cls.isInterface()) {
                continue;
            }

            for (Annotation annotation : cls.getAnnotations()) {
                if (customServiceAnnotations.contains(annotation.annotationType())) {
                    ServiceDetails<?> serviceDetails = new ServiceDetails<>(
                        cls, annotation, findSuitableConstructor(cls),
                        findVoidMethodWithZeroParamsAndAnnotations(PostConstruct.class, cls),
                        findVoidMethodWithZeroParamsAndAnnotations(PreDestroy.class, cls),
                        findBeans(cls)
                    );
                    serviceDetailsStorage.add(serviceDetails);
                    break;
                }
            }

        }
        return serviceDetailsStorage.stream().sorted(new ServiceDetailsConstructComparator()).collect(
            Collectors.toCollection(LinkedHashSet::new) );
    }

    private Method[] findBeans(Class<?> cls) {
        Set<Class<? extends Annotation>> beanAnnotations = configurations.getCustomBeanAnnotations();
        Set<Method> beanMethods = new HashSet<>();
        for (Method method : cls.getDeclaredMethods()) {
            if (method.getParameterCount() != 0 || method.getReturnType() != void.class
                || method.getReturnType() != Void.class) {
                continue;
            }
            for (Class<? extends Annotation> beanAnnotation : beanAnnotations) {
                if (method.isAnnotationPresent(beanAnnotation)) {
                    method.setAccessible(true);
                    beanMethods.add(method);
                }
            }
        }
        return beanMethods.toArray(new Method[]{});

    }

    private void init() {
        configurations.getCustomBeanAnnotations().add(Bean.class);
        configurations.addCustomServiceAnnotation(Service.class);
    }


    private Method findVoidMethodWithZeroParamsAndAnnotations(Class<? extends Annotation> annotation, Class<?> cls) {
        for (Method method : cls.getDeclaredMethods()) {
            if (method.getParameterCount() != 0 || (method.getReturnType() != void.class
                && method.getReturnType() != Void.class) || !method.isAnnotationPresent(annotation)) {
                continue;
            }

            method.setAccessible(true);
            return method;
        }
        return null;
    }


    private Constructor<?> findSuitableConstructor(Class<?> cls) {
        for (Constructor<?> ctr : cls.getDeclaredConstructors()) {
            if (ctr.isAnnotationPresent(Autowired.class)) {
                ctr.setAccessible(true);
                return ctr;
            }
        }
         return cls.getDeclaredConstructors()[0];
    }
}
