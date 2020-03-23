package com.myspring.ioc.models;

public class EnqueudServiceDetails {

    private final ServiceDetails<?> serviceDetails;
    private final Class<?>[] dependencies;
    private  final Object[] dependencyInstances;

    public EnqueudServiceDetails(ServiceDetails<?> serviceDetails) {
        this.serviceDetails = serviceDetails;
        dependencies = serviceDetails.getTargetConstructor().getParameterTypes();
        dependencyInstances = new Object[this.dependencies.length];
    }

    public ServiceDetails<?> getServiceDetails() {
        return serviceDetails;
    }

    public Class<?>[] getDependencies() {
        return dependencies;
    }

    public Object[] getDependencyInstances() {
        return dependencyInstances;
    }

    public void addDependencyInstance(Object instance) {
        Class<?> dependencyType = instance.getClass();
        for (int i = 0; i < dependencies.length; i++) {
            if (dependencies[i].isAssignableFrom(instance.getClass())) {
                dependencyInstances[i] = instance;
                return;
            }
        }
    }

    public boolean isResolved() {
        for (Object dependencyInstance : dependencyInstances) {
            if (dependencyInstance == null) {
                return false;
            }
        }
        return true;
    }

    public boolean isDependencyRequired(Class<?> dependencyType) {
        for (Class<?> dependency : dependencies) {
            if (dependency.isAssignableFrom(dependencyType)) {
                return true;
            }
        }
        return false;
    }
}
