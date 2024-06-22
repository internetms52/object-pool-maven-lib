import hobby.internetms52.object_pool.ObjectPool;
import hobby.internetms52.object_pool.exception.AmbiguousConstructorException;
import hobby.internetms52.object_pool.exception.IllegalStateException;
import hobby.internetms52.object_pool.exception.ObjectPoolInstantiationException;
import object_sample.EmptyConstructorObject;
import object_sample.MultiConstructorObject;
import object_sample.UserObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class ObjectPoolConstructorBasicFunctionTest {
    ObjectPool pool = new ObjectPool();

    @Test
    public void overrideTest1() {
        try {
            pool.getObject(UserObject.class);
            pool.getObject(UserObject.class);
            Assert.assertTrue(true);
        } catch (IllegalStateException | ObjectPoolInstantiationException | AmbiguousConstructorException ex) {
            Assert.fail();
        }
    }

    @Test
    public void overrideTest2() {
        try {
            UserObject uo1 = new UserObject(
                    UUID.randomUUID().toString(), UUID.randomUUID().toString()
            );
            pool.addObject(uo1);
            pool.getObject(UserObject.class);
            Assert.assertTrue(true);
        } catch (IllegalStateException | ObjectPoolInstantiationException | AmbiguousConstructorException e) {
            Assert.fail();
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
            pool.getObject(MultiConstructorObject.class);
            Assert.fail();
        } catch (IllegalStateException | ObjectPoolInstantiationException e) {
            Assert.fail();
        } catch (AmbiguousConstructorException e) {
            Assert.assertTrue(true);
        }
    }
}
