package hobby.internetms52.object_pool.class_info;

import hobby.internetms52.object_pool.annotation.ObjectPoolConstructor;
import hobby.internetms52.object_pool.exception.AmbiguousConstructorException;

import java.lang.reflect.Constructor;

public class ClassInfoConverter {
    public ClassInfo convert(Class<?> clazz) throws AmbiguousConstructorException {
        Constructor<?> availableConstructor = getAvailableConstructor(clazz);
        return new ClassInfo(
                clazz, availableConstructor
        );
    }

    private Constructor<?> getAvailableConstructor(Class<?> clazz) throws AmbiguousConstructorException {
        // 獲取所有的構造函數
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
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
        } else {
            throw new AmbiguousConstructorException(clazz.getName());
        }
    }
}
