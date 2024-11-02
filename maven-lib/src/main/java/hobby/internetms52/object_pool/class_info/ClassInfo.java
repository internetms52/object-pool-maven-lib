package hobby.internetms52.object_pool.class_info;

import java.lang.reflect.Constructor;

public class ClassInfo {
    Class<?> clazz;
    Constructor<?> availableConstructor;

    public ClassInfo(Class<?> clazz, Constructor<?> availableConstructor) {
        this.clazz = clazz;
        this.availableConstructor = availableConstructor;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Constructor<?> getAvailableConstructor() {
        return availableConstructor;
    }
}
