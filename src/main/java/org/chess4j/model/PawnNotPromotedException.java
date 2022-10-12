package org.chess4j.model;

@SuppressWarnings("serial")
public class PawnNotPromotedException extends IllegalStateException {

    public PawnNotPromotedException(String message) {
        super(message);
    }

}
