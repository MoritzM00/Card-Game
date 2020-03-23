package controller;

import model.Buildable;
import model.BuildableObject;
import model.Card;
import model.CardType;
import model.GameState;
import model.buildables.building.Shack;
import model.cards.resources.Resource;
import model.exceptions.ScavengerException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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

    // the last drawn resource card is at the beginning of this list
    // and will be reversed before printing to the user.
    // Due to changes to the order of resources, it would have been necessary to change the whole
    // implementation of resource distribution methods, because of direction of iterating.
    // this is the reason why I used linked lists over stacks, to be able to insert resources at the
    // beginning and at the end of the list
    private Stack<Resource> resources;

    private boolean hasFirePlace;
    private Shack shack;

    private LinkedList<BuildableObject> alreadyBuiltObjects;

    private int fightBonus;

    /**
     * Creates a new scavenger.
     */
    public Scavenger() {
        this.resources = new Stack<>();
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

        // first, check the shack, then unsaved resources.
        if (shack != null) {
            int[] remainingResourceArr = deleteResourcesFromShack(builtObject);
            // delete the possible remaining resources from the main stack
            deleteResourcesFromMainStack(remainingResourceArr);
        } else {
            deleteResourcesFromMainStack(builtObject.getAmountOfResourcesNeeded());
        }

        //check if a shack or fireplace has been built
        checkForShackOrFirePlace(buildable);

        // update the resource distribution
        updateShackAfterBuild();

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
        if (CardType.isResource(card)) {
            Resource resourceCard = (Resource) card;
            // update resource distribution
            updateShackAfterDraw(resourceCard);
            resourcesByTypeID[card.getTypeID()]++;
        }
    }

    /**
     * Deletes the resources needed for the given built object from the shack.
     *
     * @param builtObject the built object
     * @return the remaining resource array, if the shack didn't contain all needed resources
     */
    private int[] deleteResourcesFromShack(Buildable builtObject) {
        // resource level before building the specified object
        int[] newResourceLevel = this.resourcesByTypeID.clone();

        // amount of needed resources
        int[] resourceArr = builtObject.getAmountOfResourcesNeeded();

        // store the unused resources
        LinkedList<Resource> unusedResources = new LinkedList<>();

        // while the int at the index of the resource
        // is higher than the new resource level,
        // delete the resource from the stack
        int iterations = shack.getSavedResources().size();
        for (int i = 0; i < iterations; i++) {
            Resource resource = shack.removeLast();
            int typeID = resource.getTypeID();
            int currentLevel = newResourceLevel[typeID]; // the current value at that position

            if (currentLevel > (currentLevel - resourceArr[typeID])) {
                // if true, then this resource will be left out from the resulting card stack
                // update the old resource level
                newResourceLevel[typeID]--;
                resourceArr[typeID]--;
            } else {
                unusedResources.addFirst(resource);
            }
        }
        this.resourcesByTypeID = newResourceLevel;
        this.shack.setSavedResources(unusedResources);
        return resourceArr;
    }

    /**
     * Deletes the resources needed for the specified built object from the specified resource stack.
     *
     * @param resourceArr the resource array from the built object
     */
    void deleteResourcesFromMainStack(int[] resourceArr) {


        // amount of needed resources
        int[] neededResources = resourceArr.clone();

        // new resource level
        int[] newResourceLevel = subtractEqualSizeArr(resourcesByTypeID, neededResources);

        // store the unused resources
        Stack<Resource> unusedResources = new Stack<>();

        // while the int at the index of the resource
        // is higher than the new resource level,
        // delete the resource from the stack
        int iterations = resources.size();
        for (int i = 0; i < iterations; i++) {
            Resource resource = resources.pop();
            int typeID = resource.getTypeID();
            if (resourcesByTypeID[typeID] > newResourceLevel[typeID]) {
                // if true, then this resource will be left out from the resulting card stack
                resourcesByTypeID[typeID]--;
            } else {
                unusedResources.push(resource);
            }
        }
        Collections.reverse(unusedResources);
        resources = unusedResources;
    }


    private int[] subtractEqualSizeArr(int[] arr, int[] subtrahend) {
        if (subtrahend.length != arr.length) {
            return null;
        }
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i] - subtrahend[i];
        }
        return result;
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
            Resource res = resources.pop();
            shack.addFirst(res);
        }
    }

    // updates the resource distribution after a draw command
    private void updateShackAfterDraw(Resource resource) {
        if (shack == null) {
            resources.push(resource);
            return;
        }
        if (!shack.hasSpace()) {
            // then the last card in the shack has to go to the other resource stack
            Resource lastInShack = shack.removeFirst();
            resources.push(lastInShack);
        }
        shack.addLast(resource);
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
        this.resources = new Stack<>();
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

    /**
     * Returns true if the player can build something.
     *
     * @return if the player can build
     */
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
        ArrayList<Resource> result = new ArrayList<>(resources);
        if (shack != null) {
            result.addAll(shack.getSavedResources());
        }
        return result;
    }
}
