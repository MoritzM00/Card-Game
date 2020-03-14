package model.buildables.rescue;

import model.Buildable;
import model.GameState;

/**
 * A rescue is a special type of {@link Buildable}.
 * When a rescue is built, the player has to roll the dice.
 * Only some special rescues like {@link Ballon} do not require an
 * and lead directly to a win.
 *
 * @author Moritz
 * @version 1.0
 * @see model.Buildable
 */
public abstract class Rescue extends Buildable {

    /**
     * Undefined number, in case of those special rescues that do not require
     * the player to roll the dice
     */
    protected static final int UNDEFINED = -1;

    /**
     * Returns the required number of pips of the dice.
     *
     * @return the required number of pips
     */
    public abstract int getRequiredNumOfPips();

    /**
     * Returns the minimum number for win
     *
     * @return the minimum number for win
     */
    public abstract int getMinimumNumberForWin();

    @Override
    public GameState takeAction() {
        return GameState.ENDEAVOR;
    }


}
