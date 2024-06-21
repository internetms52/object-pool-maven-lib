package com.internetms52.object_pool.class_info;

import com.internetms52.object_pool.annotation.ObjectPoolConstructor;

import java.lang.reflect.Constructor;

public class ClassInfoConverter {
    public ClassInfo convert(Class<?> clazz) {
        // 獲取所有的構造函數
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        Constructor<?> availableConstructor = getAvailableConstructor(constructors);
        return new ClassInfo(
                clazz, availableConstructor
        );
    }

    private Constructor<?> getAvailableConstructor(Constructor<?>[] constructors) {
        Constructor<?> annotatedConstructor = null;
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(ObjectPoolConstructor.class)) {
                annotatedConstructor = constructor;
            }
        }
        if (annotatedConstructor != null) {
            return annotatedConstructor;
        }
        if (constructors.length == 1) {
            return constructors[0];
        }
        return null;
    }
}
