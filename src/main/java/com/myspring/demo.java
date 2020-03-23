package com.myspring;


import com.myspring.ioc.config.MagicConfiguration;
import com.myspring.ioc.enums.DirectoryType;
import com.myspring.ioc.models.Directory;
import com.myspring.ioc.services.ClassLocator;
import com.myspring.ioc.services.ClassLocatorForDirectory;
import com.myspring.ioc.services.ClassLocatorForJarFile;
import com.myspring.ioc.services.DirectoryResolverImpl;
import com.myspring.ioc.models.ServiceDetails;
import com.myspring.ioc.services.ServicesScanningServiceImpl;
import java.util.Set;

public class demo {

    public static void main(String[] args) {
        run(demo.class);
    }

    public static void run(Class<?> startupClass) {
        run(startupClass, new MagicConfiguration());
    }

    public static void run(Class<?> startupClass, MagicConfiguration magicConfiguration) {
        ServicesScanningServiceImpl servicesScanningService = new ServicesScanningServiceImpl(
            magicConfiguration.annotations());
        Directory directory = new DirectoryResolverImpl().resplveDirectory(startupClass);
        ClassLocator classLocator = new ClassLocatorForDirectory();
        if (directory.getDirectoryType() == DirectoryType.JAR_FILE) {
            classLocator = new ClassLocatorForJarFile();
        }
        Set<Class<?>> locatedClasses = classLocator.locateClasses(directory.getDirectory());
        Set<ServiceDetails<?>> serviceDetails = servicesScanningService.mapServices(locatedClasses);
        System.out.println(serviceDetails);
    }
}
