package penguin;

/**
 * Exception to handle invalid user inputs.
 */
public class PenguinException extends Exception {
    public PenguinException(String message) {
        super(message);
    }
}
