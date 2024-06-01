package com.internetms52.object_pool;

import com.internetms52.object_pool.getter.ExistsObjectPoolGetter;
import com.internetms52.object_pool.getter.MultiArgObjectPoolGetter;
import com.internetms52.object_pool.getter.NoArgObjectPoolGetter;
import com.internetms52.object_pool.getter.ObjectPoolGetterNotFoundException;
import com.internetms52.object_pool.util.NativeLogger;

import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class ObjectPool {
    NativeLogger nativeLogger = new NativeLogger(ObjectPool.class);
    private final ConcurrentHashMap<Class<?>, Object> pool = new ConcurrentHashMap<>();
    private final ExistsObjectPoolGetter existsObjectPoolGetter = new ExistsObjectPoolGetter(pool);
    private final NoArgObjectPoolGetter noArgObjectPoolGetter = new NoArgObjectPoolGetter();
    private final MultiArgObjectPoolGetter multiArgObjectPoolGetter = new MultiArgObjectPoolGetter();

    public <T> T getObject(Class<?> clazz) throws ObjectPoolGetterNotFoundException {
        try {
            if (existsObjectPoolGetter.accept(clazz)) {
                return (T) existsObjectPoolGetter.getObject(clazz);
            }
            if (noArgObjectPoolGetter.accept(clazz)) {
                return (T) noArgObjectPoolGetter.getObject(clazz);
            }
            if (multiArgObjectPoolGetter.accept(clazz)) {
                return (T) multiArgObjectPoolGetter.getObject(clazz);
            }
        } catch (Exception e) {
            nativeLogger.error(e);
        }
        throw new ObjectPoolGetterNotFoundException(clazz.getName());
    }

}
