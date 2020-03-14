package controller;

import model.Buildable;
import model.BuildableObject;
import model.Card;
import model.GameState;
import model.buildables.rescue.Rescue;
import model.cards.animals.Animal;
import model.cards.resources.Resource;
import model.exceptions.CardStackException;
import model.exceptions.IllegalGameStateException;
import model.exceptions.RollDiceException;
import model.exceptions.ScavengerException;
import view.cli.CardGameCommand;

import java.util.List;

import static model.GameState.*;

/**
 * This class manages the card game.
 * Consists of a {@link Scavenger} and a {@link CardHolder}.
 *
 * @author Moritz
 * @version 1.0
 */
public class CardGameManager implements Backend {

    private GameState gameState;
    private CardHolder cardHolder;
    private Scavenger scavenger;

    /**
     * Creates a new card game manager.
     */
    public CardGameManager() {
        this.gameState = INVALID;
        this.cardHolder = new CardHolder();
        this.scavenger = new Scavenger();

    }

    @Override
    public void start(List<Card> cards) throws CardStackException, IllegalGameStateException {
        this.gameState.checkGameState(CardGameCommand.START);
        cardHolder.initializeCardStack(cards);
        setGameState(SCAVENGE);
    }

    @Override
    public Card draw() throws IllegalGameStateException {
        checkInitialized();
        this.gameState.checkGameState(CardGameCommand.DRAW);

        // check if next action is possible
        if (!nextActionPossible()) {
            return null;
        }
        Card card = cardHolder.getNextCard();
        scavenger.checkForResource(card);
        setGameState(card.takeAction());
        scavenger.updateResources(gameState);
        return card;
    }

    @Override
    public void build(BuildableObject buildableObject) throws IllegalGameStateException, ScavengerException {
        checkInitialized();
        gameState.checkGameState(CardGameCommand.BUILD);
        // tries to built the object, an error gets thrown if it cannot be built
        Buildable builtObject = scavenger.build(buildableObject);

        // update game state
        setGameState(builtObject.takeAction());
    }

    @Override
    public void reset() throws IllegalGameStateException {
        checkInitialized();
        cardHolder.reset();
        this.scavenger = new Scavenger();
        setGameState(SCAVENGE);
    }

    @Override
    public GameState rollDice(int numOfPips, int roll) throws IllegalGameStateException, RollDiceException {
        checkInitialized();
        GameState result;
        switch (gameState) {
            case ENDEAVOR:
                // it is secure to down cast to rescue, because the last
                // drawn card has to be a rescue in this case
                Rescue rescue = (Rescue) scavenger.getLastBuiltObject().getAccordingBuildable();
                result = checkEndeavor(rescue, numOfPips, roll);
                if (result == WIN) {
                    // update the game state accordingly
                    setGameState(END);
                } else {
                    setGameState(SCAVENGE);
                }
                break;
            case ENCOUNTER:
                // last card has to be an animal, again, this is safe to assume
                Animal animalCard = (Animal) cardHolder.getLastDrawnCard();
                result = checkEncounter(animalCard, numOfPips, roll);
                scavenger.updateResources(result);
                setGameState(SCAVENGE);
                break;
            default:
                throw new IllegalGameStateException("roll dice command is not allowed right now.");
        }
        return result;
    }

    /**
     * Gets executed if the game is in the endeavor game state, meaning the player built a rescue object.
     *
     * @param rescue    the built rescue object
     * @param numOfPips the number of pips that player chose
     * @param roll      the rolled number
     * @return the resulting game state
     * @throws RollDiceException if the player chose the wrong dice.
     */
    private GameState checkEndeavor(Rescue rescue, int numOfPips, int roll) throws RollDiceException {
        int requiredNumOfPips = rescue.getRequiredNumOfPips();
        if (requiredNumOfPips != numOfPips
                || roll > numOfPips) {
            throw new RollDiceException(requiredNumOfPips, roll);
        }

        int minimumWinNumber = rescue.getMinimumNumberForWin();
        if (roll >= minimumWinNumber) {
            return GameState.WIN;
        } else {
            return GameState.LOSE;
        }
    }

    /**
     * Gets executed if the game is in the encounter game state, meaning the player has
     * to fight against an animal
     *
     * @param animalCard the animal that the player fights against
     * @param numOfPips  the number of pips (the dice) which the player chose
     * @param roll       the rolled number
     * @return the resulting game state
     * @throws RollDiceException if the player chose the wrong dice.
     */
    private GameState checkEncounter(Animal animalCard, int numOfPips, int roll)
            throws RollDiceException {
        int requiredNumOfPips = animalCard.getRequiredNumOfPips();
        if (requiredNumOfPips != numOfPips
                || roll > numOfPips) {
            throw new RollDiceException(requiredNumOfPips, roll);
        }

        int minimumWinNumber = animalCard.getMinimumNumberRolledForWin();
        if ((roll + scavenger.getFightBonus()) >= minimumWinNumber) {
            return GameState.SURVIVED;
        } else {
            return GameState.LOSE;
        }
    }

    @Override
    public List<Resource> getResourcesAsList() throws IllegalGameStateException {
        checkInitialized();
        return scavenger.getAllResourcesAsList();
    }

    @Override
    public void setGameState(GameState state) {
        this.gameState = state;
    }

    @Override
    public void checkInitialized() throws IllegalGameStateException {
        if (gameState == INVALID) {
            throw new IllegalGameStateException("game has to be started first.");
        }
    }

    @Override
    public boolean nextActionPossible() {
        if (!cardHolder.hasCards()) {
            // if the player has to fight, return true
            if (gameState == ENCOUNTER) {
                return true;
            }
            if (!scavenger.playerCanBuild()) {
                setGameState(END);
                return false;
            } else {
                setGameState(SCAVENGE);
                return true;
            }
        }
        return true;
    }


    @Override
    public List<BuildableObject> getAllBuildableObjectsAsList() throws IllegalGameStateException {
        checkInitialized();
        this.gameState.checkGameState(CardGameCommand.POSSIBLE_BUILDS);
        return scavenger.allBuildableObjects();
    }

    @Override
    public List<BuildableObject> getAllBuiltObjectsAsList() throws IllegalGameStateException {
        checkInitialized();
        return scavenger.getAllBuiltObjectsAsList();
    }

    @Override
    public GameState getGameState() {
        return this.gameState;
    }
}
