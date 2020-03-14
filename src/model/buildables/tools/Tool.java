package model.buildables.tools;

import model.Buildable;
import model.GameState;

public abstract class Tool extends Buildable {

    @Override
    public GameState takeAction() {
        return GameState.SCAVENGE;
    }

    @Override
    public boolean isRescue() {
        return false;
    }

    @Override
    public boolean requiresFirePlace() {
        return false;
    }
}
