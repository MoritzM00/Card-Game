package controller;

import model.*;
import model.buildables.building.Shack;
import model.cards.resources.Resource;
import model.exceptions.ScavengerException;

import java.util.*;

import static model.CardType.AMOUNT_OF_DIFFERENT_RESOURCES;

/**
 * The scavenger class manages resource distribution as well as building objects.
 *
 * @author Moritz
 * @version 1.0
 */
public class Scavenger {

    // stores the amount of resources the player currently has by type ID
    // e.g. wood has type 0, so [0] represents the amount of wood which the player has
    private int[] resourcesByTypeID;

    private LinkedList<Resource> resources;

    private boolean hasFirePlace;
    private Shack shack;

    private LinkedList<BuildableObject> alreadyBuiltObjects;

    private int fightBonus;

    /**
     * Creates a new scavenger.
     */
    public Scavenger() {
        this.resources = new LinkedList<>();
        this.resourcesByTypeID = new int[AMOUNT_OF_DIFFERENT_RESOURCES];
        this.hasFirePlace = false;
        this.shack = null;
        this.alreadyBuiltObjects = new LinkedList<>();
        this.fightBonus = 0;
    }

    /**
     * Creates a buildable object.
     *
     * @param buildable the buildable that will be created
     * @return the buildable
     * @throws ScavengerException if it cannot be built or already exists.
     */
    public Buildable build(BuildableObject buildable) throws ScavengerException {
        Buildable builtObject = checkBuildableObject(buildable);
        if (builtObject == null) {
            throw new ScavengerException(buildable.getTypeAsString() + " cannot be built.");
        }
        // check if this built object has already been built
        if (checkIfBuiltAlready(buildable)) {
            throw new ScavengerException("this has already been built.");
        }
        // else add it to the built objects list
        alreadyBuiltObjects.addFirst(buildable);

        // can be built, delete resources
        // first, check the shack, then unsaved resources.
        if (shack != null) {
            shack.setSavedResources(deleteResourcesAfterBuild(builtObject, shack.getSavedResources()));
        }
        resources = deleteResourcesAfterBuild(builtObject, resources);

        // update the resource distribution
        updateShackAfterBuild();

        //check if a shack or fireplace has been built
        checkForShackOrFirePlace(buildable);

        // update fight bonus for possible encounters
        this.fightBonus = Math.max(builtObject.getFightBonus(), fightBonus);
        return builtObject;
    }

    private boolean checkIfBuiltAlready(BuildableObject buildable) {
        if (!alreadyBuiltObjects.isEmpty()) {
            for (BuildableObject alreadyBuilt : alreadyBuiltObjects) {
                if (buildable.getTypeAsString().equals(
                        alreadyBuilt.getTypeAsString())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks a card. If it is a resource it gets added to resources.
     *
     * @param card the checked card
     */
    void checkForResource(Card card) {
        if (card == null) {
            return;
        }
        int cardID = card.getTypeID();
        if (CardType.isResource(card)) {
            Resource resourceCard = (Resource) card;
            // update resource distribution
            updateShackAfterDraw(resourceCard);
            resourcesByTypeID[cardID]++;
        }
    }

    /**
     * Deletes the resources needed for the specified built object from the specified resource stack.
     *
     * @param builtObject   the built object
     * @param resourceStack the resource stack that will be modified
     * @return the updated resource list
     */
    LinkedList<Resource> deleteResourcesAfterBuild(Buildable builtObject, LinkedList<Resource> resourceStack) {
        // resource level before building the specified object
        int[] oldResourceLevel = this.resourcesByTypeID.clone();

        // amount of needed resources
        int[] resourceArr = builtObject.getAmountOfResourcesNeeded();

        // new level of resources, after building the object
        // updates the attribute itself
        subtractResourceArr(resourceArr);

        // store the unused resources
        LinkedList<Resource> unusedResources = new LinkedList<>();

        // while the int at the index of the resource
        // is higher than the new resource level,
        // delete the resource from the stack
        for (Resource resource : resourceStack) {
            int typeID = resource.getTypeID();
            if (oldResourceLevel[typeID] > resourcesByTypeID[typeID]) {
                // if true, then this resource will be left out from the resulting card stack
                // update the old resource level
                oldResourceLevel[typeID]--;
            } else {
                unusedResources.addLast(resource);
            }
        }
        return unusedResources;
    }

    // subtracts the given array from the resource array, has to be length 3
    private void subtractResourceArr(int[] subtrahend) {
        if (subtrahend.length != AMOUNT_OF_DIFFERENT_RESOURCES) {
            return;
        }
        for (int i = 0; i < AMOUNT_OF_DIFFERENT_RESOURCES; i++) {
            this.resourcesByTypeID[i] -= subtrahend[i];
        }
    }

    /**
     * Returns all buildable objects that can be built with the current resources.
     *
     * @return all buildable objects that can be built
     */
    List<BuildableObject> allBuildableObjects() {
        List<BuildableObject> result = new ArrayList<>();
        for (BuildableObject buildableObject : BuildableObject.values()) {
            // if null is returned, it cannot be built
            if (checkBuildableObject(buildableObject) != null
                    && !checkIfBuiltAlready(buildableObject)) {
                result.add(buildableObject);
            }
        }
        result.sort(Comparator.comparing(BuildableObject::getTypeAsString));
        return result;
    }

    // updates the resource distribution after a build command
    private void updateShackAfterBuild() {
        if (shack == null) {
            return;
        }
        // moves cards to the shack while there is space
        while (shack.hasSpace() && !resources.isEmpty()) {
            Resource res = resources.removeFirst();
            shack.addLast(res);
        }
    }

    // updates the resource distribution after a draw command
    private void updateShackAfterDraw(Resource resource) {
        if (shack == null) {
            resources.addFirst(resource);
            return;
        }
        if (!shack.isEmpty()) {
            // the drawn card has to go to the shack, the last card
            // in the shack to the other resource stack
            Resource lastInShack = shack.removeLast();
            resources.addFirst(lastInShack);
        }
        shack.addFirst(resource);
    }

    /**
     * Gets executed after a draw command.
     * Deletes the resources if game state is catastrophe
     *
     * @param gameState the current game state
     */
    public void updateResources(GameState gameState) {
        switch (gameState) {
            case LOSE:
                resetResourceStack();
                break;
            case CATASTROPHE:
                this.hasFirePlace = false;
                alreadyBuiltObjects.remove(BuildableObject.FIREPLACE);
                resetResourceStack();
                break;
            default:
        }
    }

    /**
     * Checks if the given buildable object can be built with the current resources.
     *
     * @param buildableObject the buildable object that gets checked
     * @return the buildable if it can be built, null otherwise
     */
    private Buildable checkBuildableObject(BuildableObject buildableObject) {
        Buildable buildable = buildableObject.getAccordingBuildable();
        int[] resourceArr = buildable.getAmountOfResourcesNeeded();
        if (buildable.requiresFirePlace() && !hasFirePlace) {
            return null;
        }
        // check if there are enough resources
        for (int i = 0; i < AMOUNT_OF_DIFFERENT_RESOURCES; i++) {
            if (resourcesByTypeID[i] < resourceArr[i]) {
                return null;
            }
        }
        // enough resources, return the buildable
        return buildable;
    }

    // resets the resources stack for example when the player lost against an animal
    private void resetResourceStack() {
        for (Card resourceCard : resources) {
            // delete the resources which are not in the shack.
            resourcesByTypeID[resourceCard.getTypeID()]--;
        }
        this.resources = new LinkedList<>();
    }

    // checks if the given buildable object is a shack or a fireplace and updates
    // the attributes accordingly
    private void checkForShackOrFirePlace(BuildableObject buildableObject) {
        switch (buildableObject) {
            case SHACK:
                this.shack = (Shack) buildableObject.getAccordingBuildable();
                updateShackAfterBuild();
                break;
            case FIREPLACE:
                this.hasFirePlace = true;
                break;
            default:
        }
    }

    protected boolean playerCanBuild() {
        // check if the player is able to build sth
        return (!allBuildableObjects().isEmpty());
    }

    /**
     * Returns the last built object.
     *
     * @return the last built object
     */
    protected BuildableObject getLastBuiltObject() {
        // the last built object is at the beginning of the list
        return alreadyBuiltObjects.getFirst();
    }

    /**
     * Returns the fight bonus that the player currently has.
     *
     * @return the fight bonus
     */
    protected int getFightBonus() {
        return this.fightBonus;
    }

    /**
     * Returns all already built objects as a list.
     *
     * @return all already built objects
     */
    protected List<BuildableObject> getAllBuiltObjectsAsList() {
        return alreadyBuiltObjects;
    }

    /**
     * Returns all resources as a list.
     *
     * @return all resources as a list
     */
    protected List<Resource> getAllResourcesAsList() {
        ArrayList<Resource> result = new ArrayList<>();
        if (shack != null) {
            result.addAll(shack.getSavedResources());
        }
        result.addAll(resources);
        Collections.reverse(result);
        return result;
    }
}
