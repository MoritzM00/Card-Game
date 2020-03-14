package model.buildables.rescue;

import model.Buildable;
import model.GameState;

public abstract class Rescue extends Buildable {

    protected static int UNDEFINED = -1;

    public abstract int getRequiredNumOfPips();

    public abstract int getMinimumNumberForWin();

    @Override
    public boolean isRescue() {
        return true;
    }

    @Override
    public GameState takeAction() {
        return GameState.ENDEAVOR;
    }


}
