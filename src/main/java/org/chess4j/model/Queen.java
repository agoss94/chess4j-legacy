package org.chess4j.model;

import org.chess4j.model.Player.Color;

public final class Queen implements Piece {

    private final Color color;

    /**
     * @param color
     */
    private Queen(Color color) {
        this.color = color;
    }

    public static Queen white() {
        return new Queen(Color.WHITE);
    }

    public static Queen black() {
        return new Queen(Color.BLACK);
    }

    @Override
    public boolean isValid(Tile start, Tile end) {
        int dirX = end.column() - start.column();
        int dirY = end.row() - start.row();
        /*
         * A queen can move like a bishop and a rook.
         */
        return Math.abs(dirX) == Math.abs(dirY) || dirX == 0 || dirY == 0;
    }

    @Override
    public Type type() {
        return Type.QUEEN;
    }

    @Override
    public Color color() {
        return color;
    }

    @Override
    public String toString() {
        return color == Color.WHITE ? "\u2655" : "\u265B";
    }
}
