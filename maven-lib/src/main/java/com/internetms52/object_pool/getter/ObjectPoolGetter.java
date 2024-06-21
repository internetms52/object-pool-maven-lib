package com.internetms52.object_pool.getter;

import com.internetms52.object_pool.class_info.ClassInfo;

import java.lang.reflect.InvocationTargetException;

public interface ObjectPoolGetter {
    boolean accept(ClassInfo classInfo);

    Object getObject(ClassInfo classInfo) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;
}
