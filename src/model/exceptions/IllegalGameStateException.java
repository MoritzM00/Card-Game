package model.exceptions;

/**
 * An illegal game state exception gets thrown if the user tries to
 * executes commands that are not allowed in the current game state.
 *
 * @author Moritz
 * @version 1.0
 * @see model.GameState
 */
public class IllegalGameStateException extends Exception {

    /**
     * Creates an illegal game state exception.
     *
     * @param message the error message
     */
    public IllegalGameStateException(String message) {
        super(message);
    }
}
