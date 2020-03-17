package view.cli;

import static view.Frontend.NO_ARGUMENTS_ALLOWED;

/**
 * Checks if a bad input has some valid parts and helps the user correct his mistakes.
 *
 * @author Moritz
 * @version 1.0
 */
public enum BadInputChecker {

    /**
     * Help string for the start command.
     */
    START("start", "<card1,card2,...,card64>"),

    /**
     * Help string for the rolldx command.
     */
    ROLLDX("rollD", "rollD<4|6|8> <rolledNumber>"),

    /**
     * Help string for the build command.
     */
    BUILD("build ", "<buildable>"),

    /**
     * Help string for the draw command.
     */
    DRAW("draw", NO_ARGUMENTS_ALLOWED),

    /**
     * Help string for the build? command.
     */
    POSSIBLE_BUILDS("build?", NO_ARGUMENTS_ALLOWED),

    /**
     * Help string for the list-buildings command.
     */
    LIST_BUILDINGS("list-buildings", NO_ARGUMENTS_ALLOWED),

    /**
     * Help string for the list-resources command.
     */
    LIST_RESOURCES("list-resources", NO_ARGUMENTS_ALLOWED),

    /**
     * Help string for the reset command.
     */
    RESET("reset", NO_ARGUMENTS_ALLOWED),

    /**
     * Help string for the quit command.
     */
    QUIT("quit", NO_ARGUMENTS_ALLOWED);


    private final String command;
    private final String helpString;

    /**
     * Creates a Bad input checker
     *
     * @param command    the command as a string
     * @param helpString the help string to the command
     */
    BadInputChecker(String command, String helpString) {
        this.command = command;
        this.helpString = helpString;
    }

    /**
     * Checks if the given input is completely invalid or if it happens to be the start of a valid command.
     *
     * @param input the invalid input
     * @return true if it is the start of a valid command
     */
    boolean checkInvalidInput(String input) {
        return (input.startsWith(command));
    }

    /**
     * Returns the help string for the according command.
     *
     * @return the help string
     */
    String getHelpString() {
        return helpString;
    }
}


