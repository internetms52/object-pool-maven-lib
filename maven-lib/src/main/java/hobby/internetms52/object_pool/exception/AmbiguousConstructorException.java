package hobby.internetms52.object_pool.exception;

public class AmbiguousConstructorException extends RuntimeException {
    public AmbiguousConstructorException() {
    }

    public AmbiguousConstructorException(String message) {
        super(message);
    }
}
