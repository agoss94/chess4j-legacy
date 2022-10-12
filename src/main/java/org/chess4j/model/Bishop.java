package org.chess4j.model;

import java.util.Objects;

import org.chess4j.model.Player.Color;

/**
 * A bishop is a chess piece that can move in diagonal lines. Therefore each
 * bishop can only move over one tile color for an entire game. The class offers
 * two static factory methods {@link #white()} and {@link #black()} for
 * constructing a bishop. Once created a bishop is immutable and stateless. Also
 * note that no two bishop are equal as the class does not overwrite
 * {@link #hashCode()} nor {@link #equals(Object)}.
 */
public final class Bishop implements Piece {

    /**
     * The color of the bishop.
     */
    private final Color color;

    // Private constructor. Class offers static factories.
    private Bishop(Color color) {
        this.color = color;
    }

    /**
     * Static factory that returns a new white bishop.
     *
     * @return a white bishop.
     */
    public static Bishop white() {
        return new Bishop(Color.WHITE);
    }

    /**
     * Static factory that returns a new black bishop.
     *
     * @return a black bishop.
     */
    public static Bishop black() {
        return new Bishop(Color.BLACK);
    }

    /**
     * {@inheritDoc}
     *
     * A bishop move is valid in principle if the number of moved rows equals the
     * number of moved columns.
     */
    @Override
    public boolean isValid(Tile start, Tile end) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
        int deltaColumn = end.column() - start.column();
        int deltaRow = end.row() - start.row();
        /*
         * A diagonal has the same difference in row numbers as in column numbers.
         */
        return Math.abs(deltaColumn) == Math.abs(deltaRow);
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
    public Type type() {
        return Type.BISHOP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return color == Color.WHITE ? "\u2657" : "\u265D";
    }
}
