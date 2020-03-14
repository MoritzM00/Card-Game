package model;

/**
 * The defines the method signatures of a buildable.
 *
 * @author Moritz
 * @version 1.0
 */
public abstract class Buildable {

    /**
     * Assigns the resource array with the given amount of each resource.
     * The resource array stores the amount of each resource that are required for this
     * buildable in order to be able to build it, similarly to the resourceByTypeID array
     * of the {@link controller.Scavenger} class.
     *
     * @param amountOfWood    the amount of wood.
     * @param amountOfMetal   the amount of metal
     * @param amountOfPlastic the amount of plastic
     * @return the resources array
     */
    public int[] assignResourceArray(int amountOfWood, int amountOfMetal, int amountOfPlastic) {
        int[] resourceArr = new int[CardType.AMOUNT_OF_DIFFERENT_RESOURCES];
        resourceArr[Card.WOOD_TYPE_ID] = amountOfWood;
        resourceArr[Card.METAL_TYPE_ID] = amountOfMetal;
        resourceArr[Card.PLASTIC_TYPE_ID] = amountOfPlastic;
        return resourceArr;
    }

    /**
     * Returns the game state that results if this object is built.
     *
     * @return the resulting game state of this buildable
     */
    public abstract GameState takeAction();

    /**
     * Returns the amount of resources needed, stored in a integer array
     * of length 3. The index indicates the type of resource.
     *
     * @return the resource array of this object
     */
    public abstract int[] getAmountOfResourcesNeeded();

    /**
     * Returns true if this buildable requires a fireplace for successful building
     *
     * @return true if requires a fireplace
     */
    public abstract boolean requiresFirePlace();

    /**
     * Returns the fight bonus that this object gives.
     *
     * @return the fight bonus of this buildable
     */
    public abstract int getFightBonus();

    /**
     * Returns the type of this buildable as a string
     *
     * @return the type of this buildable
     */
    public abstract String getTypeAsString();
}
