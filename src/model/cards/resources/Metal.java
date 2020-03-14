package model.cards.resources;

import model.CardType;

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
