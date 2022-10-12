package org.chess4j.model;

import org.chess4j.model.Player.Color;

/**
 * The Knight is the only Piece that can jump over other pieces on its path. The
 * class offers two static factory methods {@link #white()} and {@link #black()}
 * for constructing a knight. Once created a knight is immutable and stateless.
 * Also note that no two knight are equal as the class does not overwrite
 * {@link #hashCode()} nor {@link #equals(Object)}.
 */
public final class Knight implements Piece {

    /**
     * The color of the knight
     */
    private final Color color;

    // Private constructor
    private Knight(Color color) {
        this.color = color;
    }

    /**
     * Static factory that returns a new white knight.
     *
     * @return a white knight.
     */
    public static Knight white() {
        return new Knight(Color.WHITE);
    }

    /**
     * Static factory that returns a new black knight.
     *
     * @return a black knight.
     */
    public static Knight black() {
        return new Knight(Color.BLACK);
    }

    /**
     * {@inheritDoc}
     *
     * A knight can move exactly three tiles which are not entirely in one
     * direction.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Type type() {
        return Type.KNIGHT;
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
        return color == Color.WHITE ? "\u2658" : "\u265E";
    }
}
