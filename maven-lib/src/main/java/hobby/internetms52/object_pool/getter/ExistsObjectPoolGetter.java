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

    public static boolean checkGenericInterface(Class<?> baseInterface, Class<?> implementation, Class<?>... expectedGenericTypes) {
        Type[] interfaces = implementation.getGenericInterfaces();
        for (Type type : interfaces) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                if (parameterizedType.getRawType().equals(baseInterface)) {
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    if (actualTypeArguments.length == expectedGenericTypes.length) {
                        for (int i = 0; i < actualTypeArguments.length; i++) {
                            if (!actualTypeArguments[i].equals(expectedGenericTypes[i])) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
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
