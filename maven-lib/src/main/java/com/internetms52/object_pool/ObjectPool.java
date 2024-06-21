package com.internetms52.object_pool;

import com.internetms52.object_pool.annotation.ObjectPoolConstructor;
import com.internetms52.object_pool.getter.*;
import com.internetms52.object_pool.util.NativeLogger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class ObjectPool {
    private final NativeLogger nativeLogger = new NativeLogger(ObjectPool.class);
    private final ConcurrentHashMap<Class<?>, Object> pool = new ConcurrentHashMap<>();
    private final ExistsObjectPoolGetter existsObjectPoolGetter = new ExistsObjectPoolGetter(pool);
    private final NoArgObjectPoolGetter noArgObjectPoolGetter = new NoArgObjectPoolGetter();

    public void addObject(Object o) {
        pool.putIfAbsent(o.getClass(), o);
    }

    public <T> T getObject(Class<?> clazz) throws UnsatisfiedObjectPoolConstructor {
        try {
            if (existsObjectPoolGetter.accept(clazz)) {
                return (T) existsObjectPoolGetter.getObject(clazz);
            }
            // 獲取所有的構造函數
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            Constructor<?> availableConstructor = getAvailableConstructor(constructors);
            if (availableConstructor == null) {
                throw new AmbiguousConstructorException(clazz.getName());
            }
            T result = getMultiArgObject(new Constructor<?>[]{availableConstructor});
            if (availableConstructor.getParameterTypes().length == 0
                    && noArgObjectPoolGetter.accept(clazz)) {
                result = (T) noArgObjectPoolGetter.getObject(clazz);
            }
            if (result == null) {
                throw new MultiArgInstantiateException(clazz.getName());
            } else {
                addObject(result);
                return result;
            }
        } catch (Exception e) {
            nativeLogger.error(e);
            throw new UnsatisfiedObjectPoolConstructor(e);
        }
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

    private <T> T getMultiArgObject(Constructor<?>[] constructors) throws InvocationTargetException, InstantiationException, IllegalAccessException {
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
        return null;
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
