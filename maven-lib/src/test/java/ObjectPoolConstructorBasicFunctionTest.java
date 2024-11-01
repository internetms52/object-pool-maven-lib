import hobby.internetms52.object_pool.ObjectPool;
import hobby.internetms52.object_pool.exception.AmbiguousConstructorException;
import hobby.internetms52.object_pool.exception.IllegalStateException;
import hobby.internetms52.object_pool.exception.ObjectPoolInstantiationException;
import object_sample.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ObjectPoolConstructorBasicFunctionTest {
    ObjectPool pool = new ObjectPool();
    
    @Test
    public void overrideTest3() {
        try {
            UserObject uo1 = new UserObject(
                    UUID.randomUUID().toString(), UUID.randomUUID().toString()
            );
            UserObject uo2 = new UserObject(
                    UUID.randomUUID().toString(), UUID.randomUUID().toString()
            );
            pool.addObject(uo1);
            pool.addObject(uo2);
            Assert.fail();
        } catch (IllegalStateException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void emptyConstructorTest1() {
        try {
            pool.getObject(EmptyConstructorObject.class);
            Assert.assertTrue(true);
        } catch (IllegalStateException | ObjectPoolInstantiationException | AmbiguousConstructorException e) {
            Assert.fail();
        }
    }

    @Test
    public void multiConstructorTest1() {
        try {
            pool.getObject(MultiConstructorWithoutAnnotationObject.class);
            Assert.fail();
        } catch (IllegalStateException | ObjectPoolInstantiationException e) {
            Assert.fail();
        } catch (AmbiguousConstructorException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void multiConstructorTest2() {
        try {
            String text = "test";
            pool.addObject(text);
            pool.getObject(MultiConstructorObject.class);
            Assert.assertTrue(true);
        } catch (IllegalStateException | ObjectPoolInstantiationException e) {
            Assert.fail();
        } catch (AmbiguousConstructorException e) {
            Assert.assertTrue(true);
        }
    }


    @Test
    public void genericTypeTest1() {
        try {
            List<String> stringList = new ArrayList<>();
            pool.addObject(stringList);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void genericTypeTest2() {
        try {
            GenericTypeObject map = new GenericTypeObject();
            map.put("a", List.of("test"));
            pool.addObject(map);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
    }
}
