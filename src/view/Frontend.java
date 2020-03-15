package view;

import model.GameState;
import model.exceptions.IllegalGameStateException;

/**
 * Defines the methods of a frontend.
 *
 * @author Moritz
 * @version 1.0
 */
public interface Frontend {

    /**
     * unknown command error message
     */
    String UNKNOWN_COMMAND = "unknown command";
    /**
     * no arguments allowed error message
     */
    String NO_ARGUMENTS_ALLOWED = "no arguments allowed";

    /**
     * The string "empty"
     */
    String EMPTY = "EMPTY";

    /**
     * OK Message
     */
    String OK = "OK";

    /**
     * Output if the player survived a fight
     */
    String SURVIVED = "survived";

    /**
     * Output if the player loses a fight
     */
    String LOSE = "lose";

    /**
     * Output if the player wins a fight
     */
    String WIN = "win";

    /**
     * Output if the player lost the game
     */
    String LOST = "lost";


    /**
     * Quits the frontend and therefore closes the user interface
     */
    void quit();

    /**
     * Shows the user a message on the user interface
     *
     * @param message is the message to show
     */
    void showMessage(String message);

    /**
     * Shows the user an error message on the user interface.
     *
     * @param error the error message
     */
    void showError(String error);

    /**
     * Shows all resources that the user has drawn until this point of time.
     *
     * @throws IllegalGameStateException if this command is currently not allowed
     */
    void showResources() throws IllegalGameStateException;

    /**
     * Shows the appropriate message to the user, which depends
     * on the current game state of the backend
     *
     * @param gameState the output depends on the specified game state
     */
    void showGameStateMessage(GameState gameState);

    /**
     * Shows all buildable objects to the user, e.g. those that the user
     * can build but hasn't done so yet
     *
     * @throws IllegalGameStateException if this command is currently not allowed
     */
    void showAllBuildableObjects() throws IllegalGameStateException;

    /**
     * Shows all already built objects to the user
     *
     * @throws IllegalGameStateException if this command is currently not allowed
     */
    void showAllBuiltObjects() throws IllegalGameStateException;
}
