package sky;

/**
 * Represents an application-specific exception in Sky.
 * <p>
 * Used to indicate invalid user input or command errors.
 */
public class SkyException extends Exception {

    /**
     * Constructs a SkyException with the specified error message.
     *
     * @param message Error message
     */
    public SkyException(String message) {
        super(message);
    }
}
    