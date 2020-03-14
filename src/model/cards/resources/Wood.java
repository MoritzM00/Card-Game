package model.cards.resources;


import model.CardType;

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
