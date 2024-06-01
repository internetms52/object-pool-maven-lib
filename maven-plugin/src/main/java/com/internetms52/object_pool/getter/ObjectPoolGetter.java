package com.internetms52.object_pool.getter;

public interface ObjectPoolGetter {
    boolean accept(Class<?> clazz);

    Object getObject(Class<?> clazz);
}
