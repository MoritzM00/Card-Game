package view.cli;

import controller.Backend;
import model.BuildableObject;
import model.Card;
import model.CardType;
import model.GameState;
import model.exceptions.BadInputException;
import model.exceptions.CardStackException;
import model.exceptions.IllegalGameStateException;
import model.exceptions.RollDiceException;
import model.exceptions.ScavengerException;
import view.Frontend;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.GameState.CATASTROPHE;
import static model.GameState.SCAVENGE;
import static view.Frontend.OK;

/**
 * This enum defines text commands and their implementation for the card game.
 *
 * @author Moritz
 * @version 1.0
 */
public enum CardGameCommand implements Command {

    /**
     * Starts the game.
     *
     * The user has to provide the 64 cards.
     */
    START("^start ([\\w\\s,]+)$") {
        @Override
        public void execute(Frontend frontend, Backend backend) throws BadInputException {
            String[] input = getArguments();
            String[] cards = input[0].split(",");
            try {
                backend.start(parseCards(cards));
            } catch (CardStackException | IllegalGameStateException e) {
                frontend.showError(e.getMessage());
                return;
            }
            frontend.showMessage(OK);
        }
    },

    /**
     * Rolls the dice.
     * The user has to provide the dice (number of pips) and the rolled number
     */
    ROLLDX("^rollD[+]?[0]*(4|6|8) ([+]?[0]*\\d)$") {
        @Override
        public void execute(Frontend frontend, Backend backend) throws BadInputException {
            final String[] arguments = getArguments();
            int numOfPips = parseInteger(arguments[0]);
            int roll = parseInteger(arguments[1]);

            GameState result;
            try {
                result = backend.rollDice(numOfPips, roll);
            } catch (IllegalGameStateException | RollDiceException e) {
                frontend.showError(e.getMessage());
                return;
            }
            // first show the result of the roll
            frontend.showGameStateMessage(result);
            // if the card stack is empty and there is no action left
            // then lost is returned now
            if (backend.noActionPossible()) {
                // it means that the game state is "END"
                // therefore "lost" will be printed
                frontend.showGameStateMessage(backend.getGameState());
            }
        }
    },

    /**
     * Draws the next card.
     * No arguments allowed.
     */
    DRAW("^draw$") {
        @Override
        public void execute(Frontend frontend, Backend backend) {
            Card card;
            try {
                card = backend.draw();
            } catch (IllegalGameStateException e) {
                frontend.showError(e.getMessage());
                return;
            }
            if (card == null) {
                return;
            }
            frontend.showMessage(card.getTypeAsString());

            GameState stateAfterDraw = backend.getGameState();

            if (stateAfterDraw == CATASTROPHE) {
                // if a thunderstorm card has been drawn, the game state has to
                // be updated to scavenge again.
                backend.setGameState(SCAVENGE);
            }

            // then check for empty card stack
            if (backend.noActionPossible()) {
                // update the game state variable after the method execution
                // of nextActionPossible()
                stateAfterDraw = backend.getGameState();
                frontend.showGameStateMessage(stateAfterDraw);
            }
        }
    },

    /**
     * Builds a buildable object.
     * The user has to provide the name of the buildable.
     */
    BUILD("^build (\\w+)$") {
        @Override
        public void execute(Frontend frontend, Backend backend) throws BadInputException {
            final String[] arguments = getArguments();
            try {
                backend.build(parseBuildableObject(arguments[0]));
            } catch (IllegalGameStateException | ScavengerException e) {
                frontend.showError(e.getMessage());
                return;
            }
            frontend.showGameStateMessage(backend.getGameState());
        }
    },

    /**
     * Shows the user all buildables that he/she can currently build
     */
    POSSIBLE_BUILDS("^build\\?$") {
        @Override
        public void execute(Frontend frontend, Backend backend) {
            try {
                frontend.showAllBuildableObjects();
            } catch (IllegalGameStateException ex) {
                frontend.showError(ex.getMessage());
            }
        }
    },

    /**
     * Lists all buildables that the player already built
     */
    LIST_BUILDINGS("^list-buildings$") {
        @Override
        public void execute(Frontend frontend, Backend backend) {
            try {
                frontend.showAllBuiltObjects();
            } catch (IllegalGameStateException ex) {
                frontend.showError(ex.getMessage());
            }
        }
    },

    /**
     * Lists all resources which the player has drawn and not used.
     */
    LIST_RESOURCES("^list-resources$") {
        @Override
        public void execute(Frontend frontend, Backend backend) {
            try {
                frontend.showResources();
            } catch (IllegalGameStateException ex) {
                frontend.showError(ex.getMessage());
            }
        }
    },

    /**
     * Resets the game to the starting point.
     * The player starts with the same card deck which he provided
     * in the beginning of the game
     */
    RESET("^reset$") {
        @Override
        public void execute(Frontend frontend, Backend backend) {
            try {
                backend.reset();
            } catch (IllegalGameStateException e) {
                frontend.showError(e.getMessage());
                return;
            }
            frontend.showMessage(OK);
        }
    },

    INIT2("init2") {
        @Override
        public void execute(Frontend frontend, Backend backend) throws BadInputException {
            String[] input = new String[]{"wood,wood,metal,metal,metal,plastic,plastic,plastic,"
                    + "wood,wood,wood,metal,metal,metal,plastic,plastic,plastic,"
                    + "wood,wood,wood,metal,metal,metal,plastic,plastic,plastic,"
                    + "wood,wood,wood,metal,metal,metal,plastic,plastic,plastic,"
                    + "wood,wood,wood,metal,metal,metal,plastic,plastic,plastic,"
                    + "wood,metal,plastic,"
                    + "thunderstorm,"
                    + "spider,spider,spider,spider,spider,"
                    + "snake,snake,snake,snake,snake,"
                    + "tiger,tiger,tiger,tiger,tiger,wood"};
            String[] cards = input[0].split(",");
            try {
                backend.start(parseCards(cards));
            } catch (CardStackException | IllegalGameStateException e) {
                frontend.showError(e.getMessage());
                return;
            }
            try {
                for (int i = 0; i < 47; i++) {
                    backend.draw();
                }
            } catch (IllegalGameStateException e) {
                frontend.showError(e.getMessage());
            }

        }
    },
    GAME_STATE("gs") {
        @Override
        public void execute(Frontend frontend, Backend backend) throws BadInputException {
            frontend.showMessage(backend.getGameState().name());
        }
    },


    INIT("init") {
        @Override
        public void execute(Frontend frontend, Backend backend) throws BadInputException {
            String[] input = new String[]{"wood,wood,wood,metal,metal,metal,plastic,plastic,plastic,"
                    + "wood,wood,wood,metal,metal,metal,plastic,plastic,plastic,"
                    + "wood,wood,wood,metal,metal,metal,plastic,plastic,plastic,"
                    + "wood,wood,wood,metal,metal,metal,plastic,plastic,plastic,"
                    + "wood,wood,wood,metal,metal,metal,plastic,plastic,plastic,"
                    + "wood,metal,plastic,"
                    + "snake,spider,tiger,"
                    + "thunderstorm,"
                    + "snake,spider,tiger,"
                    + "snake,spider,tiger,"
                    + "snake,spider,tiger,"
                    + "snake,spider,tiger"};
            String[] cards = input[0].split(",");
            try {
                backend.start(parseCards(cards));
            } catch (CardStackException | IllegalGameStateException e) {
                frontend.showError(e.getMessage());
                return;
            }
            frontend.showMessage(OK);
            for (int i = 0; i < 45; i++) {
                DRAW.execute(frontend, backend);
            }
        }
    },

    /**
     * Quits the game.
     */
    QUIT("^quit$") {
        @Override
        public void execute(Frontend frontend, Backend backend) {
            frontend.quit();
        }
    };

    private String[] arguments;
    private Pattern pattern;

    /**
     * A Game command instance, e.g. one of the command defined above.
     *
     * @param regex the regex for this command
     */
    CardGameCommand(String regex) {
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean parseInput(String input)
            throws BadInputException {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            checkCommandWithWrongArgs(this, input);
            return false;
        }

        arguments = new String[matcher.groupCount()];

        for (int i = 0; i < matcher.groupCount(); i++) {
            arguments[i] = matcher.group(i + 1);
        }

        return true;
    }

    /**
     * Parses a buildable object by its string value.
     *
     * @param typeOfBuildable the type of the buildable as a string
     * @return the buildable object
     * @throws BadInputException if type couldn't be parsed
     */
    BuildableObject parseBuildableObject(String typeOfBuildable) throws BadInputException {
        BuildableObject result = BuildableObject.getBuildableObjectByTypeString(typeOfBuildable);
        if (result == null) {
            throw new BadInputException("cannot parse buildable object");
        }
        return result;
    }

    /**
     * Returns the arguments as an array of strings
     *
     * @return the arguments
     */
    protected String[] getArguments() {
        return arguments;
    }

    private void checkCommandWithWrongArgs(CardGameCommand command, String input)
            throws BadInputException {
        BadInputChecker checker;
        try {
            checker = BadInputChecker.valueOf(command.name());
        } catch (IllegalArgumentException iae) {
            return;
        }
        if (checker.checkInvalidInput(input.trim())) {
            throw new BadInputException("invalid arguments for this command. "
                                                + "Required arguments: " + checker.getHelpString());
        }
    }

    /**
     * Parses an integer.
     *
     * @param integer the integer as a numeric string
     * @return the parsed int
     * @throws BadInputException if integer couldn't be parsed
     */
    protected static int parseInteger(String integer) throws BadInputException {
        int result;
        try {
            result = Integer.parseInt(integer);
        } catch (NumberFormatException nfe) {
            throw new BadInputException("cannot parse number");
        }
        return result;
    }

    /**
     * Parses the cards that the user provides with the start command
     *
     * @param cards the cards
     * @return the list of parsed cards
     * @throws BadInputException if cards couldn't be parsed
     */
    protected LinkedList<Card> parseCards(String[] cards) throws BadInputException {
        LinkedList<Card> result = new LinkedList<>();
        for (String cardAsString : cards) {
            Card realCard = CardType.createCardByStringValue(cardAsString);
            if (realCard == null) {
                throw new BadInputException("cannot parse cards");
            }
            result.addLast(realCard);
        }
        return result;
    }
}
