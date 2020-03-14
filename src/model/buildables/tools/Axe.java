package model.buildables.tools;

import static model.BuildableObject.AXE;

/**
 * An axe is a {@link Tool}.
 *
 * The fight bonus of an axe is 2.
 *
 * @author Moritz
 * @version 1.0
 */
public class Axe extends Tool {

    private static final int WOOD = 0;
    private static final int METAL = 3;
    private static final int PLASTIC = 0;

    private static final int FIGHT_BONUS = 2;


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
        return AXE.getTypeAsString();
    }
}
