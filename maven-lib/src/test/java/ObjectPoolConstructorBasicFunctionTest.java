import hobby.internetms52.object_pool.ObjectPool;
import hobby.internetms52.object_pool.exception.AmbiguousConstructorException;
import hobby.internetms52.object_pool.exception.IllegalStateException;
import object_sample.EmptyConstructorObject;
import object_sample.MultiConstructorWithoutAnnotationObject;
import object_sample.generic_type.GenericTypeObject2;
import object_sample.generic_type.GenericTypeObject4;
import object_sample.interface_type.InterfaceTypeIntegerNested;
import object_sample.interface_type.InterfaceTypeK;
import object_sample.interface_type.InterfaceTypeStringNested;
import object_sample.interface_type.InterfaceTypeT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectPoolConstructorBasicFunctionTest {
    private ObjectPool pool;

    @BeforeEach
    void setUp() {
        pool = new ObjectPool();
    }

    @Nested
    class ConstructorTests {
        @Test
        void shouldCreateObjectWithEmptyConstructor() {
            EmptyConstructorObject result = pool.getObject(EmptyConstructorObject.class);
            assertNotNull(result);
        }

        @Test
        void shouldThrowExceptionForAmbiguousConstructors() {
            assertThrows(AmbiguousConstructorException.class,
                    () -> pool.getObject(MultiConstructorWithoutAnnotationObject.class));
        }
    }

    @Nested
    class GenericTypeTests {
        @Test
        void shouldRejectRawGenericList() {
            List<String> stringList = new ArrayList<>();
            assertThrows(UnsupportedOperationException.class,
                    () -> pool.addObject(stringList));
        }

        @Test
        void shouldAllowConcreteGenericImplementation() {
            GenericTypeObject4 genericType = new GenericTypeObject4();
            assertDoesNotThrow(() -> pool.addObject(genericType));
        }
    }

    @Nested
    class InterfaceTypeTests {
        @Test
        void shouldFindAllImplementationsOfInterface() {
            // 準備測試數據
            InterfaceTypeK<InterfaceTypeT<String>> stringImpl = new InterfaceTypeStringNested();
            InterfaceTypeK<InterfaceTypeT<Integer>> integerImpl = new InterfaceTypeIntegerNested();
            pool.addObject(stringImpl);
            pool.addObject(integerImpl);

            // 執行測試
            List<InterfaceTypeK> result = pool.filterByInterface(InterfaceTypeK.class);

            // 驗證結果
            assertEquals(2, result.size());
            assertTrue(result.contains(stringImpl));
            assertTrue(result.contains(integerImpl));
        }
    }

    @Nested
    class GenericTypeErasureTests {
        @Test
        void shouldRejectGenericTypeWithTypeParameter() {
            // Given
            GenericTypeObject2<String> stringVersion = new GenericTypeObject2<>();
            GenericTypeObject2<Integer> integerVersion = new GenericTypeObject2<>();

            // When & Then
            assertThrows(IllegalStateException.class,
                    () -> {
                        pool.addObject(integerVersion);
                        pool.addObject(stringVersion);
                    },
                    "Should reject generic types due to type erasure"
            );
        }

        @Test
        void shouldRejectRawGenericList() {
            // Given
            List<String> stringList = new ArrayList<>();
            // When & Then
            assertThrows(UnsupportedOperationException.class,
                    () -> pool.addObject(stringList),
                    "Should reject raw generic list due to type erasure"
            );
        }
    }
}
