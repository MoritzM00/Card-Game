package model.buildables.rescue;

import model.GameState;

import static model.BuildableObject.STEAM_BOAT;

/**
 * A steam boat is a buildable of type {@link Rescue}.
 *
 * @author Moritz
 * @version 1.0
 */
public class SteamBoat extends Rescue {

    private static final int WOOD = 0;
    private static final int METAL = 6;
    private static final int PLASTIC = 1;

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
    public GameState takeAction() {
        return GameState.WIN;
    }

    @Override
    public int getFightBonus() {
        return FIGHT_BONUS;
    }

    @Override
    public String getTypeAsString() {
        return STEAM_BOAT.getTypeAsString();
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
