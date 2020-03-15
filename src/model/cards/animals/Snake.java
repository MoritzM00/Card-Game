package model.cards.animals;

import model.CardType;

/**
 * A snake is a special card of type {@link Animal}.
 *
 * @author Moritz
 * @version 1.0
 */
public class Snake extends Animal {

    private static final int REQUIRED_NUMBER_OF_PIPS = 6;

    private static final int MINIMUM_NUMBER_ROLLED_FOR_WIN = 4;

    @Override
    public int getTypeID() {
        return SNAKE_TYPE_ID;
    }

    @Override
    public String getTypeAsString() {
        return CardType.SNAKE;
    }

    @Override
    public int getRequiredNumOfPips() {
        return REQUIRED_NUMBER_OF_PIPS;
    }

    @Override
    public int getMinimumNumberRolledForWin() {
        return MINIMUM_NUMBER_ROLLED_FOR_WIN;
    }
}
