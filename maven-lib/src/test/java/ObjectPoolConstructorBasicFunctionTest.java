import hobby.internetms52.object_pool.ObjectPool;
import hobby.internetms52.object_pool.exception.AmbiguousConstructorException;
import hobby.internetms52.object_pool.exception.IllegalStateException;
import hobby.internetms52.object_pool.exception.ObjectPoolInstantiationException;
import object_sample.EmptyConstructorObject;
import object_sample.MultiConstructorObject;
import object_sample.MultiConstructorWithoutAnnotationObject;
import object_sample.UserObject;
import object_sample.extended_generic_type.GenericTypeObject;
import object_sample.extended_generic_type.GenericTypeObject2;
import object_sample.extended_generic_type.GenericTypeObject4;
import object_sample.extended_generic_type.GenericTypeObject5;
import object_sample.interface_type.*;
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
            Assert.assertTrue(false);
        } catch (UnsupportedOperationException e) {
            Assert.assertTrue(true);
        } catch (IllegalStateException e) {
            Assert.assertTrue(false);
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

    @Test
    public void genericTypeTest3() {
        try {
            GenericTypeObject2<String> genericTypeString = new GenericTypeObject2<>();
            GenericTypeObject2<Integer> genericTypeInteger = new GenericTypeObject2<>();
            pool.addObject(genericTypeInteger);
            pool.addObject(genericTypeString);
            Assert.assertTrue(false);
        } catch (IllegalStateException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void genericTypeTest4() {
        try {
            GenericTypeObject4 genericTypeString = new GenericTypeObject4();
            pool.addObject(genericTypeString);
            Assert.assertTrue(true);
        } catch (IllegalStateException e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void genericTypeTest5() {
        try {
            GenericTypeObject5 genericTypeString = new GenericTypeObject5();
            pool.addObject(genericTypeString);
            Assert.assertTrue(true);
        } catch (IllegalStateException e) {
            Assert.assertTrue(false);
        }
    }


    @Test
    public void interfaceTypeTest1() {
        try {
            InterfaceTypeT<String> interfaceTypeTString = new InterfaceTypeTString();
            InterfaceTypeT<Integer> interfaceTypeTInteger = new InterfaceTypeTInteger();
            pool.addObject(interfaceTypeTInteger);
            pool.addObject(interfaceTypeTString);
        } catch (IllegalStateException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void interfaceTypeTest2() {
        try {
            InterfaceTypeK<InterfaceTypeT<String>> interfaceTypeTString = new InterfaceTypeStringNested();
            InterfaceTypeK<InterfaceTypeT<Integer>> interfaceTypeTInteger = new InterfaceTypeIntegerNested();
            pool.addObject(interfaceTypeTInteger);
            pool.addObject(interfaceTypeTString);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void findByInterfaceTest1() {
        try {
            InterfaceTypeK<InterfaceTypeT<String>> interfaceTypeTString = new InterfaceTypeStringNested();
            InterfaceTypeK<InterfaceTypeT<Integer>> interfaceTypeTInteger = new InterfaceTypeIntegerNested();
            pool.addObject(interfaceTypeTInteger);
            pool.addObject(interfaceTypeTString);
            List<InterfaceTypeK> interfaceTypeTList = pool.filterByInterface(InterfaceTypeK.class);
            Assert.assertEquals(2, interfaceTypeTList.size());
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
