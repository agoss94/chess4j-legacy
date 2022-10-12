package org.chess4j.model;

/**
 * Dedicated exception for the case that an invalid move is attempted to be
 * created.
 */
@SuppressWarnings("serial")
public class InvalidMoveException extends IllegalArgumentException {

    /**
     * Constructor for InvalidMoveException with the given message.
     *
     * @param message the message of the exception.
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
