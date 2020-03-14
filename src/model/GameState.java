package model;

import model.exceptions.IllegalGameStateException;
import view.cli.CardGameCommand;

import java.util.Arrays;
import java.util.List;

/**
 * Defines the possible states of the card game.
 *
 * @author Moritz
 * @version 1.0
 */
public enum GameState {

    // if there are no commands specified, it means
    // that all commands are allowed

    // win, lose, catastrophe and survived are only temporary game states and
    // are used for communication between backend and frontend.
    // The game will not stay in those states, but evaluates to the result of
    // those states and updates to the corresponding game state.

    // catastrophe, lose and survived result in the scavenge state,
    // win results in the end state

    /**
     * The invalid game state. No commands except start is allowed.
     */
    INVALID(CardGameCommand.START),

    /**
     * The scavenge mode. This is the "normal" game state where other states
     * can be accessed from
     */
    SCAVENGE(CardGameCommand.BUILD,
             CardGameCommand.POSSIBLE_BUILDS,
             CardGameCommand.DRAW),

    /**
     * The end of the game. If this state has been reached,
     * the player lost the game
     */
    END(CardGameCommand.START),

    /**
     * The win state.
     */
    WIN,

    /**
     * The loose state.
     */
    LOSE,

    /**
     * The survived state. The player survived in an animal fight.
     */
    SURVIVED,

    /**
     * The endeavor state. From this, the player can either win the game
     * or go back to the scavenger state
     */
    ENDEAVOR(CardGameCommand.ROLLDX),

    /**
     * The encounter state. The player has to fight against an animal
     */
    ENCOUNTER(CardGameCommand.ROLLDX),

    /**
     * The catastrophe. Only occurs if the player draws a card of the type
     * catastrophe, e.g. thunderstorm
     */
    CATASTROPHE;

    private final List<CardGameCommand> allowedCommands;

    GameState(CardGameCommand... commands) {
        if (commands == null) {
            allowedCommands = null;
        } else {
            this.allowedCommands = Arrays.asList(commands);
        }
    }

    public void checkGameState(CardGameCommand command)
            throws IllegalGameStateException {

        if (allowedCommands != null
                && !allowedCommands.contains(command)) {
            throw new IllegalGameStateException("this command is currently"
                                                        + " not allowed");
        }
    }
}

