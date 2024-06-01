package com.internetms52.object_pool.getter;

import com.internetms52.object_pool.annotation.ObjectPool;
import com.internetms52.object_pool.util.NativeLogger;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class MultiArgObjectPoolGetter implements ObjectPoolGetter {
    NativeLogger nativeLogger = new NativeLogger(MultiArgObjectPoolGetter.class);
    private Object newInstance;

    @Override
    public boolean accept(Class<?> clazz) {
        try {
            List<Constructor<?>> multiArgConstructors = getMultiArgConstructor(clazz);

        } catch (Exception e) {

        }
        return newInstance != null;
    }

    @Override
    public Object getObject(Class<?> clazz) {
        return newInstance;
    }

    private List<Constructor<?>> getMultiArgConstructor(Class<?> clazz) throws UnsatisfiedObjectPoolConstructor {
        // 獲取所有的構造函數
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        List<Constructor<?>> resultList = new ArrayList<>();
        // 遍歷構造函數
        for (Constructor<?> constructor : constructors) {
            // 獲取構造函數的參數類型
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            // 判斷是否為多參數構造函數
            if (parameterTypes.length > 0) {
                // 遍歷參數類型
                for (Class<?> parameterType : parameterTypes) {
                    // 檢查參數類型是否包含指定的註解
                    if (parameterType.isAnnotationPresent(ObjectPool.class)) {
                        nativeLogger.info("找到包含指定註解的參數類型: " + parameterType.getSimpleName());
                        nativeLogger.info("構造函數: " + constructor);
                        List<Constructor<?>> subConstructorList = getMultiArgConstructor(parameterType);
                        if (subConstructorList.size() >= 1) {
                            resultList.add(constructor);
                        } else {
                            throw new UnsatisfiedObjectPoolConstructor(parameterType.getName());
                        }
                    }
                }
            }
        }
        return resultList;
    }
}
