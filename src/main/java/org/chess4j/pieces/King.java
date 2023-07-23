package org.chess4j.pieces;

import java.util.Objects;

import org.chess4j.Player.Color;
import org.chess4j.Tile;
import org.chess4j.moves.Rochade;

/**
 * The King is the most important Piece of both players as the King must not be
 * captured. If the King is about to be captured the game is over. The King can
 * only move one tile at a time with the exception of a {@link Rochade} move.
 * The class offers two static factory methods {@link #white()} and
 * {@link #black()} for constructing a king. Once created a king is immutable
 * and stateless. Also note that no two king are equal as the class does not
 * overwrite {@link #hashCode()} nor {@link #equals(Object)}.
 */
public final class King implements Piece {

    /**
     * The color of the piece.
     */
    private final Color color;

    // Private constructor
    private King(Color color) {
        this.color = color;
    }

    /**
     * Static factory that returns a new white king.
     *
     * @return a white king.
     */
    public static King white() {
        return new King(Color.WHITE);
    }

    /**
     * Static factory that returns a new black king.
     *
     * @return a black king.
     */
    public static King black() {
        return new King(Color.BLACK);
    }

    /**
     * {@inheritDoc}
     *
     * A King can only move one row and one column at most.
     */
    @Override
    public boolean isValid(Tile start, Tile end) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
        int deltaColumn = end.column() - start.column();
        int deltaRow = end.row() - start.row();
        /*
         * The King can move only one tile at a time.
         */
        return Math.max(Math.abs(deltaColumn), Math.abs(deltaRow)) <= 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type type() {
        return Type.KING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color color() {
        return color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return color == Color.WHITE ? "\u2654" : "\u265A";
    }
}
