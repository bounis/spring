package com.myspring.ioc.services;

import com.myspring.ioc.exceptions.BeanInstantiationException;
import com.myspring.ioc.exceptions.PreDestroyException;
import com.myspring.ioc.exceptions.ServiceInstantiationException;
import com.myspring.ioc.models.ServiceBeanDetails;
import com.myspring.ioc.models.ServiceDetails;

public interface ObjectInstantiationService {

    void createInstance(ServiceDetails<?> serviceDetails, Object... constructorParams) throws ServiceInstantiationException;

    void createBeanInstance(ServiceBeanDetails<?> serviceBeanDetails) throws BeanInstantiationException;

    void destroyInstance(ServiceDetails<?> serviceDetails) throws PreDestroyException;


}
