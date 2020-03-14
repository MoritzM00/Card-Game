package model.exceptions;

/**
 * A card stack exception is thrown if an error occurs during
 * the creation of the card stack or if it is empty.
 */
public class CardStackException extends Exception {

    public CardStackException(String message) {
        super(message);
    }
}
