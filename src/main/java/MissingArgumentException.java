public class MissingArgumentException extends ShibeException {
    public MissingArgumentException(String actualUsage) {
        super("Missing argument! Usage: " + actualUsage);
    }
}
