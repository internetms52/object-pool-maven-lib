package com.internetms52.object_pool.getter;

public class AmbiguousConstructorException extends Exception {
    public AmbiguousConstructorException() {
    }

    public AmbiguousConstructorException(String message) {
        super(message);
    }
}
