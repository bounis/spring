package com.myspring.ioc.services;

import com.myspring.ioc.models.ServiceDetails;
import java.util.Set;

public interface ServicesScanningService {

    Set<ServiceDetails<?>> mapServices(Set<Class<?>> locatedClasses);
}
