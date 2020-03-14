package view.cli;

import controller.Backend;
import model.exceptions.BadInputException;
import view.Frontend;

/**
 * Defines the method signatures of a command.
 *
 * @author Moritz Mistol
 * @version 1.0
 */
public interface Command {
    /**
     * Checks if an input matches this command
     * and, if true, extracts its arguments for executing
     *
     * @param input is the input to match
     * @return whether the input matches this command and is ready to be executed
     * @throws BadInputException if the args of the input were invalid
     */
    boolean parseInput(String input) throws BadInputException;

    /**
     * Executes this command with the given frontend and backend
     *
     * @param frontend is the frontend to run on
     * @param backend  is the backend to run on
     * @throws BadInputException if the args of the input were invalid
     */
    void execute(Frontend frontend, Backend backend) throws BadInputException;
}
