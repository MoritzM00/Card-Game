package model.cards.animals;

import model.Card;
import model.GameState;

public abstract class Animal implements Card {

    public abstract int getRequiredNumOfPips();

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
