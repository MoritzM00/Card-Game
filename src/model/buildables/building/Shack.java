package model.buildables.building;

import model.cards.resources.Resource;

import java.util.LinkedList;

import static model.BuildableObject.SHACK;

public class Shack extends Building {

    private static final int WOOD = 2;
    private static final int METAL = 1;
    private static final int PLASTIC = 2;

    private static final int FIGHT_BONUS = 0;

    private static final int MAX_SAVED_RESOURCES = 5;

    private LinkedList<Resource> savedResources;

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

    public LinkedList<Resource> getSavedResources() {
        return savedResources;
    }

    public void addFirst(Resource resource) {
        savedResources.addFirst(resource);
    }

    public void addLast(Resource resource) {
        savedResources.addLast(resource);
    }

    public void setSavedResources(LinkedList<Resource> savedResources) {
        this.savedResources = savedResources;
    }

    public boolean isEmpty() {
        return savedResources.isEmpty();
    }

    public Resource removeLast() {
        return savedResources.removeLast();
    }
}
