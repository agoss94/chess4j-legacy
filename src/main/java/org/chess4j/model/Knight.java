package org.chess4j.model;

import org.chess4j.model.Player.Color;

public final class Knight implements Piece {

    private final Color color;

    /**
     * @param color
     */
    private Knight(Color color) {
        this.color = color;
    }

    public static Knight white() {
        return new Knight(Color.WHITE);
    }

    public static Knight black() {
        return new Knight(Color.BLACK);
    }

    @Override
    public boolean isValid(Tile start, Tile end) {
        int deltaColumn = end.column() - start.column();
        int deltaRow = end.row() - start.row();
        /*
         * A Knight moves exactly 3 tiles with the condition that they must not be all
         * in the same direction.
         */
        return Math.abs(deltaColumn) + Math.abs(deltaRow) == 3
                && Math.min(Math.abs(deltaColumn), Math.abs(deltaRow)) > 0;
    }

    @Override
    public Type type() {
        return Type.KNIGHT;
    }

    @Override
    public Color color() {
        return color;
    }

    @Override
    public String toString() {
    	return color == Color.WHITE ? "\u2658" : "\u265E";
    }
}
