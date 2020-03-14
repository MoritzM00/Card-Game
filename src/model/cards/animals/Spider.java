package model.cards.animals;

import model.CardType;

public class Spider extends Animal {

    private static final int REQUIRED_NUMBER_OF_PIPS = 4;

    private static final int MINIMUM_NUMBER_ROLLED_FOR_WIN = 3;

    @Override
    public int getTypeID() {
        return SPIDER_TYPE_ID;
    }

    @Override
    public String getTypeAsString() {
        return CardType.SPIDER;
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
