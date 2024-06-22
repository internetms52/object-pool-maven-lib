package hobby.internetms52.object_pool.getter;

public class UnsatisfiedObjectPoolConstructor extends Exception {
    public UnsatisfiedObjectPoolConstructor(String message) {
        super(message);
    }

    public UnsatisfiedObjectPoolConstructor(Throwable cause) {
        super(cause);
    }
}
