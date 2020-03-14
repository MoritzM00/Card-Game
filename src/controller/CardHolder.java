package controller;

import model.Card;
import model.CardType;
import model.exceptions.CardStackException;

import java.util.LinkedList;
import java.util.List;

import static model.Card.*;

/**
 * This class holds all cards and manages the interactions of drawing cards
 *
 * @author Moritz
 * @version 1.0
 */
public class CardHolder {

    private LinkedList<Card> backupStack;

    // the top-most card is the first in this list.
    private LinkedList<Card> cardStack;

    private Card lastDrawnCard;

    /**
     * Creates a new card holder.
     */
    public CardHolder() {
        this.backupStack = null;
        this.cardStack = null;
        this.lastDrawnCard = null;
    }

    /**
     * Returns the next card of the card stack.
     *
     * @return the drawn card
     */
    Card getNextCard() {
        if (cardStack.isEmpty()) {
            return null;
        }
        Card next = cardStack.removeFirst();
        lastDrawnCard = next;
        return next;
    }

    /**
     * Returns true if there are cards left on the stack.
     *
     * @return true if there are still cards on the stack
     */
    protected boolean hasCards() {
        return !cardStack.isEmpty();
    }

    /**
     * Initializes the card and backup stack.
     * Checks if the number of cards is correct (64 in total)
     *
     * @param cards all cards as a list
     * @throws CardStackException if number of cards is incorrect
     */
    void initializeCardStack(List<Card> cards) throws CardStackException {
        // initialize backup stack and card stack
        this.backupStack = new LinkedList<>();
        this.cardStack = new LinkedList<>();

        // check for the right amount of cards
        int[] checkAmount = new int[CardType.AMOUNT_OF_CARD_TYPES];
        for (Card card : cards) {
            checkAmount[card.getTypeID()]++;
            cardStack.addLast(card);
            backupStack.addLast(card);
        }
        if (!checkCardAmount(checkAmount)) {
            throw new CardStackException("invalid number of cards");
        }
    }

    /**
     * Accepts an array of integers. The index in this array represents the card type ID.
     * If a number is not the specified amount, then false is returned.
     *
     * @param checkAmount an array of integers
     * @return true if the amount of each card is correct
     */
    private boolean checkCardAmount(int[] checkAmount) {
        return !(checkAmount[WOOD_TYPE_ID] != CardType.AMOUNT_OF_EACH_RESOURCE
                || checkAmount[METAL_TYPE_ID] != CardType.AMOUNT_OF_EACH_RESOURCE
                || checkAmount[PLASTIC_TYPE_ID] != CardType.AMOUNT_OF_EACH_RESOURCE
                || checkAmount[SPIDER_TYPE_ID] != CardType.AMOUNT_OF_EACH_ANIMAL
                || checkAmount[SNAKE_TYPE_ID] != CardType.AMOUNT_OF_EACH_ANIMAL
                || checkAmount[TIGER_TYPE_ID] != CardType.AMOUNT_OF_EACH_ANIMAL
                || checkAmount[THUNDERSTORM_TYPE_ID] != CardType.AMOUNT_OF_EACH_CATASTROPHE);
    }

    /**
     * Gets the last drawn card.
     *
     * @return the last drawn card
     */
    protected Card getLastDrawnCard() {
        return lastDrawnCard;
    }

    /**
     * Resets the card stack to the state when it was first set up with cards.
     */
    void reset() {
        this.cardStack = new LinkedList<>();
        for (Card card : backupStack) {
            this.cardStack.addLast(card);
        }
        this.lastDrawnCard = null;
    }
}
