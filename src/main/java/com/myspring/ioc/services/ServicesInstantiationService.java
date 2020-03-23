package com.myspring.ioc.services;

import com.myspring.ioc.exceptions.ServiceInstantiationException;
import com.myspring.ioc.models.ServiceDetails;
import java.util.List;
import java.util.Set;

public interface ServicesInstantiationService {

    List<ServiceDetails<?>> instantiateServicesAndBeans(Set<ServiceDetails<?>> mappedServices)
        throws ServiceInstantiationException;
}
