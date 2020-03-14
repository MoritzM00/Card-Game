package model.exceptions;

/**
 * A roll dice exception occurs if the player chose the wrong
 * dice or if the given rolled number exceeds the maximum of the dice.
 *
 * @author Moritz
 * @version 1.0
 */
public class RollDiceException extends Exception {

    private final int requiredDice;
    private final int rolledNumber;

    /**
     * Creates a new roll dice exception.
     *
     * @param requiredDice the required dice, e.g 4 6 or 8
     * @param rolledNumber the rolled number ( <= required number )
     */
    public RollDiceException(final int requiredDice,
                             final int rolledNumber) {
        this.requiredDice = requiredDice;
        this.rolledNumber = rolledNumber;
    }

    @Override
    public String getMessage() {
        if (rolledNumber > requiredDice) {
            return String.format("rolled number (%d)"
                                         + " exceeds the maximum (%d) of this dice.",
                                 rolledNumber,
                                 requiredDice);
        }
        return String.format("wrong dice. Required dice: %d", requiredDice);
    }
}
