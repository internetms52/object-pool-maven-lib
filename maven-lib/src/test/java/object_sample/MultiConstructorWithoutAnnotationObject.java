package object_sample;

import hobby.internetms52.object_pool.annotation.ObjectPoolConstructor;

public class MultiConstructorWithoutAnnotationObject {
    String test1;
    String test2;

    public MultiConstructorWithoutAnnotationObject(String test1, String test2) {
        this.test1 = test1;
        this.test2 = test2;
    }

    public MultiConstructorWithoutAnnotationObject() {
    }
}
