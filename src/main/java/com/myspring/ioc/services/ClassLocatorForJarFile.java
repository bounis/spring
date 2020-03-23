package com.myspring.ioc.services;

import com.myspring.ioc.constants.Constants;
import com.myspring.ioc.exceptions.ClassLocationException;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassLocatorForJarFile implements ClassLocator {

    @Override
    public Set<Class<?>> locateClasses(String directory) throws ClassLocationException {
        Set<Class<?>> locatedClasses = new HashSet<>();
        try {
            JarFile jarFile = new JarFile(new File(directory));
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (!jarEntry.getName().endsWith(Constants.JAVA_BINARY_EXTENSION)) {
                    continue;
                }
                String className = jarEntry.getName().replace(Constants.JAVA_BINARY_EXTENSION, "")
                    .replaceAll("\\\\", ".")
                    .replaceAll("/", ".");
                locatedClasses.add(Class.forName(className));
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new ClassLocationException(e.getMessage(), e);
        }

        return locatedClasses;
    }
}
