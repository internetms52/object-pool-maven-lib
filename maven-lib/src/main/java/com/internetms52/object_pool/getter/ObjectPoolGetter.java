package com.internetms52.object_pool.getter;

import java.lang.reflect.InvocationTargetException;

public interface ObjectPoolGetter {
    boolean accept(Class<?> clazz);

    Object getObject(Class<?> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;
}
