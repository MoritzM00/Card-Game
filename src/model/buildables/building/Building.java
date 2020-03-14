package model.buildables.building;

import model.Buildable;
import model.GameState;

public abstract class Building extends Buildable {

    @Override
    public boolean isRescue() {
        return false;
    }

    @Override
    public GameState takeAction() {
        return GameState.SCAVENGE;
    }
}
