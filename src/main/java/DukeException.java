/**
 * A DukeException that is being thrown when Duke encounters errors in processing user input.
 */
public class DukeException extends Exception {
    /**
     * Instantiates the exception with an error message
     *
     * @param message Error message describing exceptions.
     */
    public DukeException(String message) {
        super(message);
    }
}
