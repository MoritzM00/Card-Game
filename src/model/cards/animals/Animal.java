package model.cards.animals;

import model.Card;
import model.GameState;

/**
 * A animal is a type of a {@link Card} in this game.
 *
 * If the player draws a card of this type, then the player has
 * to fight against it by rolling the dice.
 *
 * @author Moritz
 * @version 1.0
 */
public abstract class Animal implements Card {

    /**
     * Returns the required number of pips (of the dice).
     * The player has to use this exact dice.
     * This number is defined by each sub class of animal.
     *
     * @return the required number of pips
     */
    public abstract int getRequiredNumOfPips();

    /**
     * Gets the minimum number rolled for a win in the fight
     * against an animal card
     *
     * @return the minimum number rolled for win
     */
    public abstract int getMinimumNumberRolledForWin();

    @Override
    public boolean isResource() {
        return false;
    }

    @Override
    public GameState takeAction() {
        return GameState.ENCOUNTER;
    }
}
