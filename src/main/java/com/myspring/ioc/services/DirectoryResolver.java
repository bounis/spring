package com.myspring.ioc.services;

import com.myspring.ioc.models.Directory;

public interface DirectoryResolver {

    Directory resplveDirectory(Class<?> startuoClass);
}
