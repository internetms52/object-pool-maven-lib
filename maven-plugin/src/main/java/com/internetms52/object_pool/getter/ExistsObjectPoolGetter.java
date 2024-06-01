package com.internetms52.object_pool.getter;

import java.util.concurrent.ConcurrentHashMap;

public class ExistsObjectPoolGetter implements ObjectPoolGetter {
    ConcurrentHashMap<Class<?>, Object> pool;

    public ExistsObjectPoolGetter(ConcurrentHashMap<Class<?>, Object> pool) {
        this.pool = pool;
    }

    @Override
    public boolean accept(Class<?> clazz) {
        return contains(clazz);
    }

    @Override
    public Object getObject(Class<?> clazz) {
        return getFromObjectPool(clazz);
    }

    private boolean contains(Class<?> clazz) {
        return pool.containsKey(clazz);
    }

    private Object getFromObjectPool(Class<?> clazz) {
        return pool.get(clazz);
    }
}
