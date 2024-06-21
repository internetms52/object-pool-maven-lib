package com.internetms52.object_pool.exception;

public class AmbiguousConstructorException extends Exception {
    public AmbiguousConstructorException() {
    }

    public AmbiguousConstructorException(String message) {
        super(message);
    }
}
