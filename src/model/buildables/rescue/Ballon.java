package model.buildables.rescue;

import model.GameState;

import static model.BuildableObject.BALLON;

public class Ballon extends Rescue {

    private static final int WOOD = 1;
    private static final int METAL = 0;
    private static final int PLASTIC = 6;

    private static final int FIGHT_BONUS = 0;

    @Override
    public int[] getAmountOfResourcesNeeded() {
        return assignResourceArray(WOOD, METAL, PLASTIC);
    }

    @Override
    public boolean requiresFirePlace() {
        return true;
    }

    @Override
    public int getFightBonus() {
        return FIGHT_BONUS;
    }

    @Override
    public String getTypeAsString() {
        return BALLON.getTypeAsString();
    }

    @Override
    public GameState takeAction() {
        return GameState.WIN;
    }

    @Override
    public int getRequiredNumOfPips() {
        return UNDEFINED;
    }

    @Override
    public int getMinimumNumberForWin() {
        return UNDEFINED;
    }
}
