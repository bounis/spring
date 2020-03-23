package com.myspring.ioc.services;

import com.myspring.ioc.config.configuration.InstantiationConfiguration;
import com.myspring.ioc.exceptions.ServiceInstantiationException;
import com.myspring.ioc.models.EnqueudServiceDetails;
import com.myspring.ioc.models.ServiceDetails;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ServicesInstantiationServiceImpl implements ServicesInstantiationService {

    private static final String MAX_NUMBER_OF_ALLOWED_ITERATION_REACHED = "Maximum number of allowed iteration was reached '%s'";
    private final InstantiationConfiguration configuration;
    private final ObjectInstantiationService instantiationService;
    private final LinkedList<EnqueudServiceDetails> enqueudServiceDetails;
    private final List<Class<?>> allAvailableClasses;

    public ServicesInstantiationServiceImpl(
        InstantiationConfiguration configuration,
        ObjectInstantiationService instantiationService) {
        this.configuration = configuration;
        this.instantiationService = instantiationService;
        this.enqueudServiceDetails = new LinkedList<>();
        this.allAvailableClasses = new ArrayList<>();
    }

    @Override
    public List<ServiceDetails<?>> instantiateServicesAndBeans(
        Set<ServiceDetails<?>> mappedServices) throws ServiceInstantiationException {
        int counter = 0;
        int maxNumberOfIteration = configuration.getMaximumAllowedIteration();
        while (!enqueudServiceDetails.isEmpty()) {
            if (counter > maxNumberOfIteration) {
                throw new ServiceInstantiationException(String.format(
                    MAX_NUMBER_OF_ALLOWED_ITERATION_REACHED, maxNumberOfIteration));
            }
        }
        EnqueudServiceDetails enqueudServiceDetails = this.enqueudServiceDetails.removeFirst();
        if (enqueudServiceDetails.isResolved()) {
            ServiceDetails<?> serviceDetails = enqueudServiceDetails.getServiceDetails();
            Object[] dependencyInstances = enqueudServiceDetails.getDependencyInstances();

            instantiationService.createInstance(serviceDetails, dependencyInstances);
            registerInstantiatedService(serviceDetails);
            registerBeans(serviceDetails);
        } else {

            counter++;
        }
        return null;
    }

    private void registerBeans(ServiceDetails<?> serviceDetails) {
        serviceDetails.getBeans()
    }

    private void registerInstantiatedService(ServiceDetails<?> serviceDetails) {

    }

    private void init(Set<ServiceDetails<?>> mappedServices) {
        enqueudServiceDetails.clear();
        allAvailableClasses.clear();
        for (ServiceDetails<?> serviceDetails : mappedServices) {
            enqueudServiceDetails.add(new EnqueudServiceDetails(serviceDetails));
            allAvailableClasses.add(serviceDetails.getServiceType());
            allAvailableClasses.addAll(
                    Arrays.stream(serviceDetails.getBeans())
                    .map(Method::getReturnType)
                    .collect(Collectors.toList())
                );
        }
    }
}
