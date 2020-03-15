package model.cards.resources;

import model.CardType;

/**
 * The metal card is a special type of {@link Resource}.
 *
 * @author Moritz
 * @version 1.0
 */
public class Metal extends Resource {
    @Override
    public int getTypeID() {
        return METAL_TYPE_ID;
    }

    @Override
    public String getTypeAsString() {
        return CardType.METAL;
    }
}
