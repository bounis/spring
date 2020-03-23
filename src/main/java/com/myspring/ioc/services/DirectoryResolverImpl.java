package com.myspring.ioc.services;

import com.myspring.ioc.enums.DirectoryType;
import com.myspring.ioc.models.Directory;
import java.io.File;

public class DirectoryResolverImpl implements DirectoryResolver {

    public static final String JAR_FILE_EXTENSION = ".jar";

    @Override
    public Directory resplveDirectory(Class<?> startupClass) {
        String directory = getDirectory(startupClass);
        return new Directory(directory, getDirectoryType(directory));
    }

    private String getDirectory(Class<?> cls) {
        return cls.getProtectionDomain().getCodeSource().getLocation().getFile();
    }

    private DirectoryType getDirectoryType(String directory) {
        File file = new File(directory);
        if (!file.isDirectory() && directory.endsWith(JAR_FILE_EXTENSION)) {
            return DirectoryType.JAR_FILE;
        }

        return DirectoryType.DIRECTORY;
    }
}
