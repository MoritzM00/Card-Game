package model.cards.catastrophes;

import model.Card;
import model.CardType;

public class Thunderstorm extends Catastrophe {

    @Override
    public int getTypeID() {
        return Card.THUNDERSTORM_TYPE_ID;
    }

    @Override
    public String getTypeAsString() {
        return CardType.THUNDERSTORM;
    }

}
