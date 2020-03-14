package model.buildables.rescue;

import static model.BuildableObject.HANG_GLIDER;

public class HangGlider extends Rescue {

    private static final int WOOD = 2;
    private static final int METAL = 2;
    private static final int PLASTIC = 4;

    private static final int FIGHT_BONUS = 0;

    private static final int REQUIRED_NUMBER_OF_PIPS = 6;
    private static final int MINIMUM_NUMBER_FOR_WIN = 4;

    @Override
    public int[] getAmountOfResourcesNeeded() {
        return assignResourceArray(WOOD, METAL, PLASTIC);
    }

    @Override
    public boolean requiresFirePlace() {
        return false;
    }

    @Override
    public int getFightBonus() {
        return FIGHT_BONUS;
    }

    @Override
    public String getTypeAsString() {
        return HANG_GLIDER.getTypeAsString();
    }

    @Override
    public int getRequiredNumOfPips() {
        return REQUIRED_NUMBER_OF_PIPS;
    }

    @Override
    public int getMinimumNumberForWin() {
        return MINIMUM_NUMBER_FOR_WIN;
    }
}
