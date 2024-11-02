package hobby.internetms52.object_pool.getter;

import hobby.internetms52.object_pool.class_info.ClassInfo;

import java.util.concurrent.ConcurrentHashMap;

public class ExistsObjectPoolGetter implements ObjectPoolGetter {
    ConcurrentHashMap<Integer, Object> pool;

    public ExistsObjectPoolGetter(ConcurrentHashMap<Integer, Object> pool) {
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
        return pool.containsKey(clazz);
    }

    private Object getFromObjectPool(Class<?> clazz) {
        return pool.get(clazz);
    }
}
