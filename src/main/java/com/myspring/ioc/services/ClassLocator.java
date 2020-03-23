package com.myspring.ioc.services;

import com.myspring.ioc.exceptions.ClassLocationException;
import com.myspring.ioc.models.Directory;
import java.util.Set;

public interface ClassLocator {

    Set<Class<?>> locateClasses(String directory) throws ClassLocationException;

}
