package hobby.internetms52.object_pool.getter;

import hobby.internetms52.object_pool.class_info.ClassInfo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ExistsObjectPoolGetter implements ObjectPoolGetter {
    ConcurrentHashMap<String, Object> pool;

    public ExistsObjectPoolGetter(ConcurrentHashMap<String, Object> pool) {
        this.pool = pool;
    }

    @Override
    public boolean accept(ClassInfo classInfo) {
        return contains(classInfo.getClazz());
    }

    @Override
    public Object getObject(ClassInfo classInfo) {
        return getFromObjectPool(classInfo.getClazz());
    }

    private boolean contains(Class<?> clazz) {
        return pool.containsKey(clazz.getName());
    }

    private Object getFromObjectPool(Class<?> clazz) {
        return pool.get(clazz.getName());
    }
}
