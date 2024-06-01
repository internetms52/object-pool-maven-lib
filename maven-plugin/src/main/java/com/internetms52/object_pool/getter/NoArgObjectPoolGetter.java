package com.internetms52.object_pool.getter;

import com.internetms52.object_pool.util.NativeLogger;

import java.lang.reflect.InvocationTargetException;

public class NoArgObjectPoolGetter implements ObjectPoolGetter {
    NativeLogger nativeLogger = new NativeLogger(NoArgObjectPoolGetter.class);

    @Override
    public boolean accept(Class<?> clazz) {
        try {
            clazz.getConstructor().newInstance();
            return true;
        } catch (Exception e) {
            nativeLogger.error(e);
        }
        return false;
    }

    @Override
    public Object getObject(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            nativeLogger.error(e);
        }
        return null;
    }
}
