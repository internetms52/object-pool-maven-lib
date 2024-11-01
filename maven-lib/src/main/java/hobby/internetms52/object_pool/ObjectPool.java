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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
    private final ConcurrentHashMap<Integer, Object> pool = new ConcurrentHashMap<>();
    private final ClassInfoConverter classInfoConverter = new ClassInfoConverter();
    private final ExistsObjectPoolGetter existsObjectPoolGetter = new ExistsObjectPoolGetter(pool);
    private final NoArgObjectPoolGetter noArgObjectPoolGetter = new NoArgObjectPoolGetter();

    public <T> T addObject(T o) throws IllegalStateException {
        int hashId = getHashId(o.getClass());
        Object existing = pool.putIfAbsent(hashId, o);
        if (existing != null) {
            throw new IllegalStateException("Object of type " + o.getClass().getName() + " already exists in the pool.");
        }
        return o;
    }

    private String getId(Class<?> clazz) {
        StringBuilder stringBuilder = new StringBuilder();
        //parent generic check
        Type extendedType = clazz.getGenericSuperclass();
        stringBuilder.append(clazz.getName());
        if (extendedType instanceof ParameterizedType parameterizedType) {
            Type[] multiTypeArguments = parameterizedType.getActualTypeArguments();
            Arrays.stream(multiTypeArguments).forEach(genericType -> {
                if (genericType instanceof TypeVariable<?>) {
                    throw new UnsupportedOperationException("class information been erased.");
                }
                stringBuilder.append(getId(genericType.getClass()));
            });
        }
        //interface check
        Type[] interfaceType = clazz.getGenericInterfaces();
        Arrays.stream(interfaceType).forEach(genericType -> {
            if (genericType instanceof TypeVariable<?>) {
                throw new UnsupportedOperationException("class information been erased.");
            }
            stringBuilder.append(getId(genericType.getClass()));
        });
        return stringBuilder.toString();
    }

    private int getHashId(Class<?> clazz) {
        return Objects.hash(getId(clazz));
    }

    public <T> T getObject(Class<?> clazz) throws AmbiguousConstructorException, ObjectPoolInstantiationException, IllegalStateException {
        ClassInfo classInfo = classInfoConverter.convert(clazz);
        T result = null;
        boolean addObject = true;
        if (existsObjectPoolGetter.accept(classInfo)) {
            addObject = false;
            result = (T) existsObjectPoolGetter.getObject(classInfo);
        } else if (noArgObjectPoolGetter.accept(classInfo)) {
            result = (T) noArgObjectPoolGetter.getObject(classInfo);
        } else {
            result = getMultiArgObject(classInfo.getAvailableConstructor());
        }
        if (result == null) {
            throw new ObjectPoolInstantiationException(clazz.getName());
        } else {
            if (addObject) {
                result = addObject(result);
            }
            return result;
        }
    }

    private <T> T getMultiArgObject(Constructor<?> constructor) throws ObjectPoolInstantiationException {
        try {
            // 獲取構造函數的參數類型
            Type[] parameterTypes = constructor.getGenericParameterTypes();
            List<Object> objects = new ArrayList<>();
            Arrays.stream(parameterTypes).forEach(type -> {
                try {
                    int hashId = getHashId(getId(Class.forName(type.getTypeName())).getClass());
                    if (pool.containsKey(hashId)) {
                        objects.add(pool.get(hashId));
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            return (T) constructor.newInstance(objects.toArray());
        } catch (Exception e) {
            throw new ObjectPoolInstantiationException(e);
        }
    }
}
