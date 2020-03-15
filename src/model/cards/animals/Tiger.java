package model.cards.animals;

import model.CardType;

/**
 * A tiger is special card of type {@link Animal}.
 *
 * @author Moritz
 * @version 1.0
 */
public class Tiger extends Animal {

    private static final int REQUIRED_NUMBER_OF_PIPS = 8;

    private static final int MINIMUM_NUMBER_ROLLED_FOR_WIN = 5;

    @Override
    public int getTypeID() {
        return TIGER_TYPE_ID;
    }

    @Override
    public String getTypeAsString() {
        return CardType.TIGER;
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
