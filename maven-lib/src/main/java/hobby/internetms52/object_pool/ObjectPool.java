package hobby.internetms52.object_pool;

import hobby.internetms52.object_pool.class_info.ClassInfo;
import hobby.internetms52.object_pool.class_info.ClassInfoConverter;
import hobby.internetms52.object_pool.exception.AmbiguousConstructorException;
import hobby.internetms52.object_pool.exception.IllegalStateException;
import hobby.internetms52.object_pool.exception.ObjectPoolInstantiationException;
import hobby.internetms52.object_pool.getter.ExistsObjectPoolGetter;
import hobby.internetms52.object_pool.getter.NoArgObjectPoolGetter;
import hobby.internetms52.object_pool.util.NativeLogger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
/**
 * A utility class that provides methods for managing an object pool.
 *
 * @author Eugene Lu.
 * @version 1.0
 */
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

    public <T> T getObject(Class<?> clazz) throws AmbiguousConstructorException, ObjectPoolInstantiationException, IllegalStateException {
        ClassInfo classInfo = classInfoConverter.convert(clazz);
        if (classInfo.getAvailableConstructor() == null) {
            throw new AmbiguousConstructorException(clazz.getName());
        }
        T result = null;
        boolean addObject = true;
        if (existsObjectPoolGetter.accept(classInfo)) {
            addObject = false;
            result = (T) existsObjectPoolGetter.getObject(classInfo);
        } else if (noArgObjectPoolGetter.accept(classInfo)) {
            result = (T) noArgObjectPoolGetter.getObject(classInfo);
        } else {
            result = getMultiArgObject(new Constructor<?>[]{classInfo.getAvailableConstructor()});
        }
        if (result == null) {
            throw new ObjectPoolInstantiationException(clazz.getName());
        } else {
            if (addObject) {
                addObject(result);
            }
            return result;
        }
    }

    private <T> T getMultiArgObject(Constructor<?>[] constructors) throws ObjectPoolInstantiationException {
        try {
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
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ObjectPoolInstantiationException(e);
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
