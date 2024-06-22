package hobby.internetms52.object_pool.getter;

import hobby.internetms52.object_pool.class_info.ClassInfo;
import hobby.internetms52.object_pool.exception.ObjectPoolInstantiationException;
import hobby.internetms52.object_pool.util.NativeLogger;

import java.lang.reflect.InvocationTargetException;

public class NoArgObjectPoolGetter implements ObjectPoolGetter {
    NativeLogger nativeLogger = new NativeLogger(NoArgObjectPoolGetter.class);

    @Override
    public boolean accept(ClassInfo classInfo) {
        try {
            if (classInfo.getAvailableConstructor().getParameterTypes().length == 0) {
                classInfo.getClazz().getConstructor().newInstance();
                return true;
            }
        } catch (Exception e) {
            nativeLogger.debug(e.getMessage());
        }
        return false;
    }

    @Override
    public Object getObject(ClassInfo classInfo) throws ObjectPoolInstantiationException {
        try {
            return classInfo.getClazz().getConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new ObjectPoolInstantiationException(e);
        }
    }
}
