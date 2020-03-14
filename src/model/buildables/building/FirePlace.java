package model.buildables.building;

import static model.BuildableObject.FIREPLACE;

/**
 * A fire place is buildable of type {@link Building}.
 *
 * Some buildables can only be built if the player already
 * has a fire place.
 *
 * @author Moritz
 * @version 1.0
 * @see model.buildables.building.Building
 * @see model.buildables.rescue.Ballon
 * @see model.buildables.rescue.SteamBoat
 */
public class FirePlace extends Building {
    private static final int WOOD = 3;
    private static final int METAL = 1;
    private static final int PLASTIC = 0;

    private static final int FIGHT_BONUS = 0;

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
        return FIREPLACE.getTypeAsString();
    }
}
