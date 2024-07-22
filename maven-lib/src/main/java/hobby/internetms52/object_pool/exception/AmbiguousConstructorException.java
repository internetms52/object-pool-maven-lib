package hobby.internetms52.object_pool.exception;

/**
 * found multiple constructor and no qualified annotation found.
 */
public class AmbiguousConstructorException extends Exception {
    /**
     * with exception message
     */
    public AmbiguousConstructorException(String message) {
        super(message);
    }
}
