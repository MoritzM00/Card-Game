package model.cards.resources;


import model.CardType;

/**
 * The wood card is a special type of {@link Resource}.
 *
 * @author Moritz
 * @version 1.0
 */
public class Wood extends Resource {

    @Override
    public String getTypeAsString() {
        return CardType.WOOD;
    }

    @Override
    public int getTypeID() {
        return WOOD_TYPE_ID;
    }
}
