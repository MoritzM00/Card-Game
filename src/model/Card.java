package model;

/**
 * The card interface.
 * Each card (animal, resource, catastrophe) has to implement this interface.
 *
 * @author Moritz
 * @version 1.0
 */
public interface Card {

    /**
     * The type ID of wood.
     */
    int WOOD_TYPE_ID = 0;

    /**
     * The type ID of metal.
     */
    int METAL_TYPE_ID = 1;

    /**
     * The type ID of plastic.
     */
    int PLASTIC_TYPE_ID = 2;

    /**
     * The type ID of spider.
     */
    int SPIDER_TYPE_ID = 3;

    /**
     * The type ID of snake.
     */
    int SNAKE_TYPE_ID = 4;

    /**
     * The type ID of tiger.
     */
    int TIGER_TYPE_ID = 5;

    /**
     * The type ID of thunderstorm.
     */
    int THUNDERSTORM_TYPE_ID = 6;


    /**
     * Each Card implements this method differently. For example, if a tiger has been drawn,
     * then the take action method of a tiger gets executed and returns a game state.
     * Defines the step that has to follow.
     *
     * @return the game state after drawing this card
     */
    GameState takeAction();

    /**
     * Returns true if this card is a resource card.
     *
     * @return true if it it is a resource card
     */
    boolean isResource();

    /**
     * Each card type has an unique identifier which is returned by this method.
     *
     * @return returns the id of the card type
     */
    int getTypeID();

    /**
     * Returns the type of the card as a string
     *
     * @return the type of the card as a string
     */
    String getTypeAsString();
}

