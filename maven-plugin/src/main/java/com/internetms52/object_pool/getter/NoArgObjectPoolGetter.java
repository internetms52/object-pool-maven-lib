package com.internetms52.object_pool.getter;

public class NoArgObjectPoolGetter implements ObjectPoolGetter {
    private Object newInstance;

    @Override
    public boolean accept(Class<?> clazz) {
        try {
            newInstance = clazz.getConstructor().newInstance();
        } catch (Exception e) {
        }
        return newInstance != null;
    }

    @Override
    public Object getObject(Class<?> clazz) {
        return newInstance;
    }
}
