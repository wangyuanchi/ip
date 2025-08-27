package shibe;

public class InvalidArgumentException extends ShibeException {
    public InvalidArgumentException(String correctUsage) {
        super("Invalid argument! Usage: " + correctUsage);
    }
}
