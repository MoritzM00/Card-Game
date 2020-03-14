package model.cards.catastrophes;

import model.Card;
import model.GameState;

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
