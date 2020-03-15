package model.cards.catastrophes;

import model.Card;
import model.GameState;

/**
 * A catastrophe is type of a {@link Card}.
 *
 * @author Moritz
 * @version 1.0
 */
public abstract class Catastrophe implements Card {

    @Override
    public boolean isResource() {
        return false;
    }

    @Override
    public GameState takeAction() {
        return GameState.CATASTROPHE;
    }
}
