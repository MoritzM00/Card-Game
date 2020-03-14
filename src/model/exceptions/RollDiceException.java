package model.exceptions;

public class RollDiceException extends Exception {

    private final int minimumNumberOfPips;
    private final int rolledNumber;

    public RollDiceException(final int minimumNumberOfPips,
                             final int rolledNumber) {
        this.minimumNumberOfPips = minimumNumberOfPips;
        this.rolledNumber = rolledNumber;
    }

    public String getMessage() {
        if (rolledNumber > minimumNumberOfPips) {
            return String.format("rolled number (%d)" +
                                         " exceeds the maximum (%d) of this dice.",
                                 rolledNumber,
                                 minimumNumberOfPips);
        }
        return String.format("wrong dice. Required dice: %d", minimumNumberOfPips);
    }
}
