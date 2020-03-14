package model.buildables.tools;

import model.Buildable;
import model.GameState;

/**
 * A tool is a special type of {@link Buildable}.
 *
 * A tool gives the user a fight bonus during fights against animals.
 *
 * @author Moritz
 * @version 1.0
 * @see Axe
 * @see Club
 */
public abstract class Tool extends Buildable {

    @Override
    public GameState takeAction() {
        return GameState.SCAVENGE;
    }

    @Override
    public boolean requiresFirePlace() {
        return false;
    }
}
