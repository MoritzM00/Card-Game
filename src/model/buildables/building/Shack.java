package model.buildables.building;

import model.cards.resources.Resource;

import java.util.LinkedList;

import static model.BuildableObject.SHACK;

/**
 * A shack is buildable of type {@link Building}.
 *
 * A shack saves up to five resources at a time.
 * Those saved resources are not lost if the player loses
 * against an animal or if the player draws a catastrophe card
 *
 * @author Moritz
 * @version 1.0
 * @see model.buildables.building.Building
 */
public class Shack extends Building {

    private static final int WOOD = 2;
    private static final int METAL = 1;
    private static final int PLASTIC = 2;

    private static final int FIGHT_BONUS = 0;

    private static final int MAX_SAVED_RESOURCES = 5;

    private LinkedList<Resource> savedResources;

    /**
     * Creates a new shack.
     */
    public Shack() {
        this.savedResources = new LinkedList<>();
    }

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
        return SHACK.getTypeAsString();
    }

    /**
     * Returns true if there is still space for more resources
     *
     * @return true if there is still space for more resources
     */
    public boolean hasSpace() {
        return savedResources.size() < MAX_SAVED_RESOURCES;
    }

    /**
     * Returns the saved resources.
     *
     * @return the saved resoures
     */
    public LinkedList<Resource> getSavedResources() {
        return savedResources;
    }

    /**
     * Adds a resource to the beginning of the shack.
     *
     * @param resource the added resource
     */
    public void addFirst(Resource resource) {
        savedResources.addFirst(resource);
    }

    /**
     * Adds a resource to the end of the shack
     *
     * @param resource the added resource
     */
    public void addLast(Resource resource) {
        savedResources.addLast(resource);
    }

    /**
     * Sets the saved resoures to the given linked list.
     *
     * @param savedResources the resource list
     */
    public void setSavedResources(LinkedList<Resource> savedResources) {
        this.savedResources = savedResources;
    }

    /**
     * Returns true if the shack is empty
     *
     * @return true if the shack is empty
     */
    public boolean isEmpty() {
        return savedResources.isEmpty();
    }

    /**
     * Removes the last resource in the shack
     *
     * @return the last resource
     */
    public Resource removeLast() {
        return savedResources.removeLast();
    }
}
