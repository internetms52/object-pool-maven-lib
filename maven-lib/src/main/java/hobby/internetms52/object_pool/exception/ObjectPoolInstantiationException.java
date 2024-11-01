package hobby.internetms52.object_pool.exception;

public class ObjectPoolInstantiationException extends RuntimeException {

    public ObjectPoolInstantiationException(Throwable cause) {
        super(cause);
    }

    public ObjectPoolInstantiationException(String message) {
        super(message);
    }
}
