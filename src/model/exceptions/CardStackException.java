package model.exceptions;

/**
 * A card stack exception is thrown if an error occurs during
 * the creation of the card stack or during any operations on it.
 *
 * @author Moritz
 * @version 1.0
 * @see controller.CardHolder
 */
public class CardStackException extends Exception {

    /**
     * Creates a new card stack exception
     *
     * @param message the error message
     */
    public CardStackException(String message) {
        super(message);
    }
}
