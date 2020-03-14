package model.buildables.building;

import model.Buildable;
import model.GameState;

/**
 * A building is a {@link Buildable}.
 *
 * @author Moritz
 * @version 1.0
 * @see model.Buildable
 */
public abstract class Building extends Buildable {

    @Override
    public GameState takeAction() {
        return GameState.SCAVENGE;
    }
}
