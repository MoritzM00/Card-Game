package model.buildables.tools;

import static model.BuildableObject.CLUB;

/**
 * A club is a {@link Tool}.
 *
 * The fight bonus of a club is 1.
 *
 * @author Moritz
 * @version 1.0
 */
public class Club extends Tool {

    private static final int WOOD = 3;
    private static final int METAL = 0;
    private static final int PLASTIC = 0;

    private static final int FIGHT_BONUS = 1;

    @Override
    public int[] getAmountOfResourcesNeeded() {
        return assignResourceArray(WOOD, METAL, PLASTIC);
    }

    @Override
    public int getFightBonus() {
        return FIGHT_BONUS;
    }

    @Override
    public String getTypeAsString() {
        return CLUB.getTypeAsString();
    }


}
