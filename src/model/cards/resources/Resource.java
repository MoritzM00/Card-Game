package model.cards.resources;

import model.Card;
import model.GameState;

/**
 * A resource is a type of a {@link Card}.
 * Resources are needed in order to be able to build objects in the game.
 *
 * @author Moritz
 * @version 1.0
 */
public abstract class Resource implements Card {

    @Override
    public boolean isResource() {
        return true;
    }

    @Override
    public GameState takeAction() {
        return GameState.SCAVENGE;
    }
}
