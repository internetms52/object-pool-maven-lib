import com.internetms52.object_pool.ObjectPool;
import com.internetms52.object_pool.getter.UnsatisfiedObjectPoolConstructor;
import object_sample.UserObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class ObjectPoolConstructorBasicFunctionTest {
    ObjectPool pool = new ObjectPool();

    @Test
    public void overrideTest1() throws UnsatisfiedObjectPoolConstructor {
        UserObject uo1 = pool.getObject(UserObject.class);
        UserObject manualUo = new UserObject(
                UUID.randomUUID().toString(), UUID.randomUUID().toString()
        );
//        pool.addObject(manualUo);
        UserObject uo2 = pool.getObject(UserObject.class);
        Assert.assertEquals(uo1.getUserName(), uo2.getUserName());
        Assert.assertEquals(uo1.getUserEmail(), uo2.getUserEmail());
    }
}
