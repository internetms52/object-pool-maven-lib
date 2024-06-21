package com.internetms52.object_pool;

import com.internetms52.object_pool.class_info.ClassInfo;
import com.internetms52.object_pool.class_info.ClassInfoConverter;
import com.internetms52.object_pool.exception.AmbiguousConstructorException;
import com.internetms52.object_pool.exception.IllegalStateException;
import com.internetms52.object_pool.exception.MultiArgInstantiateException;
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
    private final NativeLogger nativeLogger = new NativeLogger(ObjectPool.class);
    private final ConcurrentHashMap<Class<?>, Object> pool = new ConcurrentHashMap<>();
    private final ClassInfoConverter classInfoConverter = new ClassInfoConverter();
    private final ExistsObjectPoolGetter existsObjectPoolGetter = new ExistsObjectPoolGetter(pool);
    private final NoArgObjectPoolGetter noArgObjectPoolGetter = new NoArgObjectPoolGetter();

    public void addObject(Object o) throws IllegalStateException {
        if (pool.containsKey(o.getClass())) {
            throw new IllegalStateException();
        }
        pool.putIfAbsent(o.getClass(), o);
    }

    public <T> T getObject(Class<?> clazz) throws UnsatisfiedObjectPoolConstructor {
        try {
            ClassInfo classInfo = classInfoConverter.convert(clazz);
            if (classInfo.getAvailableConstructor() == null) {
                throw new AmbiguousConstructorException(clazz.getName());
            }
            T result = null;
            if (existsObjectPoolGetter.accept(classInfo)) {
                result = (T) existsObjectPoolGetter.getObject(classInfo);
            } else if (noArgObjectPoolGetter.accept(classInfo)) {
                result = (T) noArgObjectPoolGetter.getObject(classInfo);
            } else {
                result = getMultiArgObject(new Constructor<?>[]{classInfo.getAvailableConstructor()});
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

    private <T> T getMultiArgObject(Constructor<?>[] constructors) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Constructor<?> constructor : constructors) {
            // 獲取構造函數的參數類型
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            // 判斷是否為多參數構造函數
            if (parameterTypes.length > 0) {
                List<Object> constructorParameterObjects = getParameterTypeObjectList(parameterTypes);
                if (!constructorParameterObjects.isEmpty()) {
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
