package model.cards.resources;

import model.Card;
import model.GameState;

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
