package org.chess4j.exceptions;

/**
 * A PawnNotPromotedException occurs if a pawn is not promoted at the beginning
 * of a move although it reached the last line
 */
@SuppressWarnings("serial")
public final class PawnNotPromotedException extends IllegalStateException {

    /**
     * Constructs a new PawnNotPromotedException with the given message.
     *
     * @param message the message of the exception.
     */
    public PawnNotPromotedException(String message) {
        super(message);
    }

}
