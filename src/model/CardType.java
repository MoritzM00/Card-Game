package model;

import model.cards.animals.Snake;
import model.cards.animals.Spider;
import model.cards.animals.Tiger;
import model.cards.catastrophes.Thunderstorm;
import model.cards.resources.Metal;
import model.cards.resources.Plastic;
import model.cards.resources.Wood;

import static model.Card.*;

/**
 * Defines the different card types and the amount of each card.
 *
 * @author Moritz
 * @version 1.0
 */
public abstract class CardType {

    /**
     * The amount of card types that exist
     */
    public static final int AMOUNT_OF_CARD_TYPES = 7;

    /**
     * The amount of different resources.
     */
    public static final int AMOUNT_OF_DIFFERENT_RESOURCES = 3;

    /**
     * The amount of cards of each resource.
     */
    public static final int AMOUNT_OF_EACH_RESOURCE = 16;

    /**
     * The amount of cards of each animal.
     */
    public static final int AMOUNT_OF_EACH_ANIMAL = 5;

    /**
     * The amount of cards of each catastrophe.
     */
    public static final int AMOUNT_OF_EACH_CATASTROPHE = 1;

    /**
     * The string representation for wood.
     */
    public static final String WOOD = "wood";

    /**
     * The string representation for metal.
     */
    public static final String METAL = "metal";

    /**
     * The string representation for plastic.
     */
    public static final String PLASTIC = "plastic";

    /**
     * The string representation for spider.
     */
    public static final String SPIDER = "spider";

    /**
     * The string representation for tiger.
     */
    public static final String TIGER = "tiger";

    /**
     * The string representation for snake.
     */
    public static final String SNAKE = "snake";

    /**
     * The string representation for thunderstorm.
     */
    public static final String THUNDERSTORM = "thunderstorm";


    /**
     * Returns the card associated with the given string.
     *
     * @param cardAsString the card as a string
     * @return the card if possible
     */
    public static Card createCardByStringValue(String cardAsString) {
        Card result;
        switch (cardAsString) {
            case WOOD:
                result = new Wood();
                break;
            case METAL:
                result = new Metal();
                break;
            case PLASTIC:
                result = new Plastic();
                break;
            case TIGER:
                result = new Tiger();
                break;
            case SNAKE:
                result = new Snake();
                break;
            case SPIDER:
                result = new Spider();
                break;
            case THUNDERSTORM:
                result = new Thunderstorm();
                break;
            default:
                return null;
        }
        return result;
    }

    /**
     * Checks if the given card is a resource card.
     *
     * @param card the checked card
     * @return true if the card is a resource
     */
    public static boolean isResource(Card card) {
        int cardID = card.getTypeID();
        return (cardID == WOOD_TYPE_ID
                || cardID == METAL_TYPE_ID
                || cardID == PLASTIC_TYPE_ID);
    }
}
