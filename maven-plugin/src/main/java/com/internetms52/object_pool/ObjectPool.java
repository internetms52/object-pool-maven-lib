package com.internetms52.object_pool;

import com.internetms52.object_pool.getter.AmbiguousConstructorException;
import com.internetms52.object_pool.getter.ExistsObjectPoolGetter;
import com.internetms52.object_pool.getter.NoArgObjectPoolGetter;
import com.internetms52.object_pool.getter.UnsatisfiedObjectPoolConstructor;
import com.internetms52.object_pool.util.NativeLogger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class ObjectPool {
    NativeLogger nativeLogger = new NativeLogger(ObjectPool.class);
    private final ConcurrentHashMap<Class<?>, Object> pool = new ConcurrentHashMap<>();
    private final ExistsObjectPoolGetter existsObjectPoolGetter = new ExistsObjectPoolGetter(pool);
    private final NoArgObjectPoolGetter noArgObjectPoolGetter = new NoArgObjectPoolGetter();

    public <T> T getObject(Class<?> clazz) throws UnsatisfiedObjectPoolConstructor {
        try {
            if (existsObjectPoolGetter.accept(clazz)) {
                return (T) existsObjectPoolGetter.getObject(clazz);
            }
            // 獲取所有的構造函數
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            if (constructors.length > 1) {
                throw new AmbiguousConstructorException(clazz.getName());
            } else {
                if (noArgObjectPoolGetter.accept(clazz)) {
                    return (T) noArgObjectPoolGetter.getObject(clazz);
                }
                return getMultiArgObject(clazz);
            }
        } catch (Exception e) {
            nativeLogger.error(e);
            throw new UnsatisfiedObjectPoolConstructor(e);
        }
    }

    /**
     * 找到一個constructor，所有的參數都滿足
     *
     * @param clazz
     * @return
     */
    private <T> T getMultiArgObject(Class<?> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException, UnsatisfiedObjectPoolConstructor, AmbiguousConstructorException {
        // 檢查參數類型是否包含指定的註解
        if (!clazz.isAnnotationPresent(com.internetms52.object_pool.annotation.ObjectPool.class)) {
            throw new UnsatisfiedObjectPoolConstructor(clazz.getName());
        }
        // 獲取所有的構造函數
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            // 獲取構造函數的參數類型
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            // 判斷是否為多參數構造函數
            if (parameterTypes.length > 0) {
                List<Object> constructorParameterObjects = getParameterTypeObjectList(parameterTypes);
                if (constructorParameterObjects.size() > 0) {
                    return (T) constructor.newInstance(constructorParameterObjects.toArray());
                }
            }
        }
        throw new UnsatisfiedObjectPoolConstructor(clazz.getName());
    }

    private List<Object> getParameterTypeObjectList(Class<?>[] parameterTypes) {
        try {
            List<Object> constructorParameterList = new ArrayList<>();
            for (Class<?> parameterType : parameterTypes) {
                constructorParameterList.add(getObject(parameterType));
            }
            return constructorParameterList;
        } catch (Exception ex) {
            nativeLogger.debug(ex.getClass().getName(), ex.getMessage());
        }
        return new ArrayList<>();
    }
}
