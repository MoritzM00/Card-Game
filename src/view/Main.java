package view;

import controller.CardGameManager;
import view.cli.CommandLineInterface;

/**
 * The Main class.
 *
 * @author Moritz
 * @version 1.00
 */
public final class Main {

    private Main() {
    }

    /**
     * Starts the program.
     *
     * @param args - ignored
     */
    public static void main(String[] args) {
        new CommandLineInterface(new CardGameManager()).run();
    }
}
