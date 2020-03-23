package com.myspring.ioc.models;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceDetails<T> {

    private Class<?> serviceType;
    private Annotation annotation;
    private Constructor<T> targetConstructor;
    private T instance;
    private Method postConstructMethod;
    private Method preDestroyMethod;
    private Method[] beans;
    private final List<ServiceDetails<?>> dependentServices;

    public ServiceDetails() {
        dependentServices = new ArrayList<>();
    }

    public ServiceDetails(Class<?> serviceType, Annotation annotation,
        Constructor<T> targetConstructor, Method postConstructMethod,
        Method preDestroyMethod, Method[] beans) {
        this();
        this.serviceType = serviceType;
        this.annotation = annotation;
        this.targetConstructor = targetConstructor;
        this.postConstructMethod = postConstructMethod;
        this.preDestroyMethod = preDestroyMethod;
        this.beans = beans;
    }

    public Class<?> getServiceType() {
        return serviceType;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public Constructor<T> getTargetConstructor() {
        return targetConstructor;
    }

    public T getInstance() {
        return instance;
    }

    public Method getPostConstructMethod() {
        return postConstructMethod;
    }

    public Method getPreDestroyMethod() {
        return preDestroyMethod;
    }

    public Method[] getBeans() {
        return beans;
    }

    public List<ServiceDetails<?>> getDependentServices() {
        return Collections.unmodifiableList(dependentServices);
    }

    public void addDependentService(ServiceDetails<?> serviceDetails) {
        dependentServices.add(serviceDetails);
    }

    public void setServiceType(Class<?> serviceType) {
        this.serviceType = serviceType;
    }

    public void setBeans(Method[] beans) {
        this.beans = beans;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public void setTargetConstructor(Constructor<T> targetConstructor) {
        this.targetConstructor = targetConstructor;
    }

    public void setInstance(Object instance) {
        this.instance = (T)instance;
    }

    public void setPostConstructMethod(Method postConstructMethod) {
        this.postConstructMethod = postConstructMethod;
    }

    public void setPreDestroyMethod(Method preDestroyMethod) {
        this.preDestroyMethod = preDestroyMethod;
    }

    @Override
    public int hashCode() {
        if (this.serviceType == null) {
            super.hashCode();
        }
        return this.serviceType.hashCode();
    }

    @Override
    public String toString() {
        if (this.serviceType == null) {
            return super.toString();
        }

        return this.serviceType.getName();
    }
}
