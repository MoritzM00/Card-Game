package model.exceptions;

/**
 * A scavenger exception is used for errors in {@link controller.Scavenger}.
 *
 * @author Moritz
 * @version 1.0
 */
public class ScavengerException extends Exception {

    /**
     * Creates a scavenger exception
     *
     * @param message the error message
     */
    public ScavengerException(String message) {
        super(message);
    }
}
