import com.internetms52.object_pool.ObjectPool;
import com.internetms52.object_pool.exception.IllegalStateException;
import com.internetms52.object_pool.getter.UnsatisfiedObjectPoolConstructor;
import object_sample.EmptyConstructorObject;
import object_sample.MultiConstructorObject;
import object_sample.UserObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class ObjectPoolConstructorBasicFunctionTest {
    ObjectPool pool = new ObjectPool();

    @Test
    public void overrideTest1() throws UnsatisfiedObjectPoolConstructor {
        UserObject uo1 = pool.getObject(UserObject.class);
        UserObject uo2 = pool.getObject(UserObject.class);
        Assert.assertEquals(uo1.getUserName(), uo2.getUserName());
        Assert.assertEquals(uo1.getUserEmail(), uo2.getUserEmail());
    }

    @Test
    public void overrideTest2() throws UnsatisfiedObjectPoolConstructor, IllegalStateException {
        UserObject uo1 = new UserObject(
                UUID.randomUUID().toString(), UUID.randomUUID().toString()
        );
        pool.addObject(uo1);
        UserObject uo2 = pool.getObject(UserObject.class);
        Assert.assertEquals(uo1.getUserName(), uo2.getUserName());
        Assert.assertEquals(uo1.getUserEmail(), uo2.getUserEmail());
    }

    @Test
    public void emptyConstructorTest1() {
        try {
            pool.getObject(EmptyConstructorObject.class);
            Assert.assertTrue(true);
        } catch (UnsatisfiedObjectPoolConstructor e) {
            Assert.fail();
        }
    }

    @Test
    public void multiConstructorTest1() {
        try {
            pool.getObject(MultiConstructorObject.class);
            Assert.fail();
        } catch (UnsatisfiedObjectPoolConstructor e) {
            Assert.assertTrue(true);
        }
    }
}
