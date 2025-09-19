package shibe;

/**
 * Base exception type for application specific errors in Shibe. Extend this
 * class to represent well defined failure cases.
 */
public class ShibeException extends Exception {
    /**
     * Constructs a new ShibeException with the specified detail message.
     *
     * @param message the detail message describing the error.
     */
    public ShibeException(String message) {
        super(message);
    }
}
