package model.exceptions;

/**
 * A bad input exception occurs if the user provides wrong input.
 *
 * @author Moritz
 * @version 1.00
 */
public class BadInputException extends Exception {

    /**
     * Gets a new Bad Input Exception.
     *
     * @param message the error message
     */
    public BadInputException(String message) {
        super(message);
    }
}
