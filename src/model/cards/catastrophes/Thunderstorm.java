package model.cards.catastrophes;

import model.Card;
import model.CardType;

/**
 * A thunderstorm card is a special type of {@link Catastrophe}.
 * If the user draws this card he uses all resources, besides those
 * which are saved in the shack.
 * The fireplace is lost as well.
 *
 * @author Moritz
 * @version 1.0
 */
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
