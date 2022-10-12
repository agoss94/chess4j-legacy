package org.chess4j.model;

import org.chess4j.model.Player.Color;

public final class Rook implements Piece {

    private final Color color;

    /**
     * @param color
     */
    private Rook(Color color) {
        this.color = color;
    }

    public static Rook white() {
        return new Rook(Color.WHITE);
    }

    public static Rook black() {
        return new Rook(Color.BLACK);
    }

    @Override
    public boolean isValid(Tile start, Tile end) {
        int dirX = end.column() - start.column();
        int dirY = end.row() - start.row();
        /*
         * A Rook can only move in one direction.
         */
        return dirX == 0 || dirY == 0;
    }

    @Override
    public Type type() {
        return Type.ROOK;
    }

    @Override
    public Color color() {
        return color;
    }

    @Override
    public String toString() {
    	return color == Color.WHITE ? "\u2656" : "\u265C";
    }
}
