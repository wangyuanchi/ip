package shibe;

/**
 * Thrown to indicate that a required argument was not provided in a command or
 * operation. This is a specific subclass of {@link ShibeException} for missing
 * argument errors.
 */
public class MissingArgumentException extends ShibeException {
    /**
     * Creates a new MissingArgumentException with a message that includes the
     * correct usage.
     *
     * @param actualUsage The usage string that shows the required arguments.
     */
    public MissingArgumentException(String actualUsage) {
        super("Missing argument! Usage: " + actualUsage);
    }
}
