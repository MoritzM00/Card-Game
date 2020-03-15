package model.cards.resources;


import model.CardType;

/**
 * The plastic card is a special type of {@link Resource}.
 *
 * @author Moritz
 * @version 1.0
 */
public class Plastic extends Resource {

    @Override
    public int getTypeID() {
        return PLASTIC_TYPE_ID;
    }

    @Override
    public String getTypeAsString() {
        return CardType.PLASTIC;
    }
}
