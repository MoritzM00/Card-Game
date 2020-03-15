package view.cli;

import controller.Backend;
import edu.kit.informatik.Terminal;
import model.BuildableObject;
import model.GameState;
import model.cards.resources.Resource;
import model.exceptions.BadInputException;
import model.exceptions.IllegalGameStateException;
import view.Frontend;

import java.util.List;


/**
 * The command line interface takes care of showing output to the user.
 *
 * @author Moritz
 * @version 1.00
 */
public class CommandLineInterface implements Frontend {
    private final Backend backend;
    private boolean isRunning = true;

    /**
     * Gets a new command lines interface instance.
     *
     * @param backend the backend for this command line interface
     */
    public CommandLineInterface(Backend backend) {
        this.backend = backend;
    }

    /**
     * Starts the program loop and accepts user input.
     */
    public void run() {

        do {
            try {
                parseInput(Terminal.readLine());
            } catch (BadInputException e) {
                this.showError(e.getMessage());
            }
        } while (isRunning);
    }

    private void parseInput(String input)
            throws BadInputException {
        for (CardGameCommand command : CardGameCommand.values()) {
            if (command.parseInput(input)) {
                command.execute(this, backend);
                return;
            }
        }
        throw new BadInputException(UNKNOWN_COMMAND);
    }

    @Override
    public void quit() {
        isRunning = false;
    }

    @Override
    public void showMessage(String message) {
        Terminal.printLine(message);
    }

    @Override
    public void showError(String error) {
        Terminal.printError(error);
    }

    @Override
    public void showResources() throws IllegalGameStateException {
        List<Resource> resources = backend.getResourcesAsList();
        if (resources.isEmpty()) {
            showMessage(EMPTY);
        }
        for (Resource resource : resources) {
            showMessage(resource.getTypeAsString());
        }
    }

    /**
     * Shows a list of buildable objects to the user.
     *
     * @param buildableObjects the list of buildable objects
     */
    public void showListOfBuildable(List<BuildableObject> buildableObjects) {
        if (buildableObjects.isEmpty()) {
            showMessage(EMPTY);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (BuildableObject buildableObject : buildableObjects) {
            sb.append(buildableObject.getTypeAsString());
            sb.append("\n");
        }
        showMessage(sb.toString().trim());
    }

    @Override
    public void showGameStateMessage(GameState gameState) {
        String result;
        switch (gameState) {
            case WIN:
                result = WIN;
                break;
            case LOSE:
                result = LOSE;
                break;
            case SURVIVED:
                result = SURVIVED;
                break;
            case END:
                result = LOST;
                break;
            default:
                result = OK;
        }
        showMessage(result);
    }

    @Override
    public void showAllBuildableObjects() throws IllegalGameStateException {
        showListOfBuildable(backend.getAllBuildableObjectsAsList());
    }

    @Override
    public void showAllBuiltObjects() throws IllegalGameStateException {
        showListOfBuildable(backend.getAllBuiltObjectsAsList());
    }
}
