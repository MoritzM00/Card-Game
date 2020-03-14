package controller;

import model.BuildableObject;
import model.Card;
import model.GameState;
import model.cards.resources.Resource;
import model.exceptions.CardStackException;
import model.exceptions.IllegalGameStateException;
import model.exceptions.RollDiceException;
import model.exceptions.ScavengerException;

import java.util.List;


/**
 * Defines the method signatures of a backend of this card game.
 *
 * @author Moritz
 * @version 1.0
 */
public interface Backend {

    /**
     * Starts the game.
     * Initializes the card stack of the game.
     *
     * @param cards an array of strings representing the cards
     */
    void start(List<Card> cards) throws CardStackException, IllegalGameStateException;

    /**
     * Draws the next card from the card stack.
     *
     * @return the next card
     * @throws IllegalGameStateException if drawing is not allowed in the current game state
     */
    Card draw() throws IllegalGameStateException;

    /**
     * Builds the given object if possible.
     *
     * @param buildableObject the buildable which will be built
     * @throws IllegalGameStateException if building is not allowed
     * @throws ScavengerException        if the object cannot be built
     */
    void build(BuildableObject buildableObject) throws IllegalGameStateException, ScavengerException;

    /**
     * Resets the whole game. The player starts with zero resources
     * and has all 64 cards in the exact sequence which was specified at the
     * beginning of this game.
     */
    void reset() throws IllegalGameStateException;

    /**
     * Rolls the dice and evaluates it.
     *
     * @param numOfPips the number of pips (the dice)
     * @param roll      the rolled number
     * @return
     * @throws IllegalGameStateException if rolling the dice is not allowed
     * @throws RollDiceException         if the player chose the wrong dice
     */
    GameState rollDice(int numOfPips, int roll) throws IllegalGameStateException, RollDiceException;

    /**
     * Returns all resources as a list.
     *
     * @return all resources as a list.
     */
    List<Resource> getResourcesAsList() throws IllegalGameStateException;

    boolean nextActionPossible();

    /**
     * Returns all buildable objects as list, e.g. those objects that can be built
     * with the current level of resources.
     *
     * @return all buildable objects
     */
    List<BuildableObject> getAllBuildableObjectsAsList() throws IllegalGameStateException;

    /**
     * Returns all built objects as list, e.g. those that the player already built
     *
     * @return the built objects
     */
    List<BuildableObject> getAllBuiltObjectsAsList() throws IllegalGameStateException;

    /**
     * Returns the current game state.
     *
     * @return the current game state
     */
    GameState getGameState();

    /**
     * Checks if the game has already been initialized
     * and throws an error if it is not the case
     *
     * @throws IllegalGameStateException if it hasn't been initialized yet.
     */
    void checkInitialized() throws IllegalGameStateException;

    void setGameState(GameState gameState);

}
