package org.chess4j;

/**
 * Dedicated exception for the case that an invalid move is attempted to be
 * created.
 */
@SuppressWarnings("serial")
public final class InvalidMoveException extends IllegalArgumentException {

    /**
     * Constructor for InvalidMoveException with the given message.
     *
     * @param message the message of the exception.
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
