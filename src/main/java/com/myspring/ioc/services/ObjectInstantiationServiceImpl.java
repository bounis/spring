package com.myspring.ioc.services;

import com.myspring.ioc.exceptions.BeanInstantiationException;
import com.myspring.ioc.exceptions.PostConstructException;
import com.myspring.ioc.exceptions.ServiceInstantiationException;
import com.myspring.ioc.exceptions.PreDestroyException;
import com.myspring.ioc.models.ServiceBeanDetails;
import com.myspring.ioc.models.ServiceDetails;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectInstantiationServiceImpl implements ObjectInstantiationService {

    private static final String INVALID_PARAMETERS_COUNT_MSG = "invalid parameters count for '%'.";

    @Override
    public void createInstance(ServiceDetails<?> serviceDetails, Object... constructorParams)
        throws ServiceInstantiationException{
        Constructor<?> targetConstructor = serviceDetails.getTargetConstructor();

        if (constructorParams.length != targetConstructor.getParameterCount()) {
            throw new ServiceInstantiationException(String
                .format(INVALID_PARAMETERS_COUNT_MSG, serviceDetails.getServiceType().getName()));
        }

        try {
            Object instance = targetConstructor.newInstance(constructorParams);
            serviceDetails.setInstance(instance);
            invokePostConstruct(serviceDetails);

        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ServiceInstantiationException(e.getMessage(), e);
        }
    }

    private void invokePostConstruct(ServiceDetails<?> serviceDetails) throws PostConstructException {
        if (serviceDetails.getPostConstructMethod() == null) {
            return;
        }
        try {
            serviceDetails.getPostConstructMethod().invoke(serviceDetails.getInstance());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new PostConstructException(e.getMessage(), e);
        }
    }
    @Override
    public void createBeanInstance(ServiceBeanDetails<?> serviceBeanDetails) throws BeanInstantiationException {
        Method originMethod = serviceBeanDetails.getOriginMethod();
        Object rootInstance = serviceBeanDetails.getRootService().getInstance();
        try {
            Object instance = originMethod.invoke(rootInstance);
            serviceBeanDetails.setInstance(instance );
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanInstantiationException(e.getMessage(), e);
        }
    }

    @Override
    public void destroyInstance(ServiceDetails<?> serviceDetails) throws PreDestroyException {
        if (serviceDetails.getPreDestroyMethod() != null) {
            try {
                serviceDetails.getPreDestroyMethod().invoke(serviceDetails.getInstance());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new PreDestroyException(e.getMessage(), e);
            }
        }
        serviceDetails.setInstance(null);
    }
}
