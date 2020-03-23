package com.myspring.ioc.exceptions;

import java.io.IOException;

public class ClassLocationException extends RuntimeException {

    public ClassLocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassLocationException(String message) {
        super(message);
    }
}
