package model.cards.resources;


import model.CardType;

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
