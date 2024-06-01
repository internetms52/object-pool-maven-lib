package com.internetms52.object_pool.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.CONSTRUCTOR) // Specify that this annotation can be applied to classes
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectPool {
    // Specify that this annotation should be retained at runtimel
    String value() default ""; // Define a member for the annotation

    int priority() default 0; // Define another member for the annotation

}
