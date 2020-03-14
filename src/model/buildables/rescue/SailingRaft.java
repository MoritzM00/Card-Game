package model.buildables.rescue;

import static model.BuildableObject.SAILING_RAFT;

public class SailingRaft extends Rescue {
    private static final int WOOD = 4;
    private static final int METAL = 2;
    private static final int PLASTIC = 2;

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
        return SAILING_RAFT.getTypeAsString();
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
