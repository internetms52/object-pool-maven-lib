package hobby.internetms52.object_pool.getter;

import hobby.internetms52.object_pool.class_info.ClassInfo;
import hobby.internetms52.object_pool.exception.ObjectPoolInstantiationException;

import java.lang.reflect.InvocationTargetException;

public interface ObjectPoolGetter {
    boolean accept(ClassInfo classInfo);

    Object getObject(ClassInfo classInfo) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, ObjectPoolInstantiationException;
}
