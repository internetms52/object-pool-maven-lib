#### install plugin
mvn clean install
#### test plugin
``mvn com.internetms52:object-pool-maven-plugin:0.1.3:object-pool
``
#### deploy plugin
``
mvn clean deploy
``

## Maven
To use this library in your Maven project, add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>io.github.internetms52</groupId>
    <artifactId>object-pool</artifactId>
    <version>0.1.3</version>
</dependency>
```

## Usage Example

This library provides an object pool for efficient object reuse. Here's a basic example of how to use it:
### Get Object from Object Pool
This example demonstrates how to retrieve an object from the pool:
```java
import io.github.internetms52.objectpool.ObjectPool;

public class GetExample {
    public static void main(String[] args) {
        // Create an object pool
        ObjectPool objectPool = new ObjectPool();

        // Get an object from the pool
        Foo foo = objectPool.getObject(Foo.class);

        // Use the object
        foo.doSomething();

    }
}

// User-defined class
class Foo {
    public void doSomething() {
        System.out.println("Foo is doing something");
    }
}
```

### Add Object into Object Pool
This example shows how to add a custom object to the pool:
```java
import io.github.internetms52.objectpool.ObjectPool;

public class AddExample {
    public static void main(String[] args) {
        ObjectPool pool = new ObjectPool();

        // Create a custom object
        Foo foo = new Foo();

        // Add the custom object to the pool
        pool.addObject(foo);

        // Retrieve an object of the same type from the pool
        Foo retrievedObject = pool.getObject(Foo.class);

        // The retrieved object might be the one we just added, or a new one if the pool created it
        retrievedObject.doSomething();
    }
}

// User-defined class
class Foo {
    public void doSomething() {
        System.out.println("Foo is doing something");
    }
}
```
Note: The Foo class in these examples is user-defined. Replace it with your own implementation as needed.
