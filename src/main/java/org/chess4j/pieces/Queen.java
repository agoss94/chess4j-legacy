package org.chess4j.pieces;

import java.util.Objects;

import org.chess4j.Tile;
import org.chess4j.Player.Color;

/**
 * A queen is a chess piece that can move in diagonal lines or straight lines.
 * The class offers two static factory methods {@link #white()} and
 * {@link #black()} for constructing a queen. Once created a queen is immutable
 * and stateless. Also note that no two queen are equal as the class does not
 * overwrite {@link #hashCode()} nor {@link #equals(Object)}.
 */
public final class Queen implements Piece {

    /**
     * The color of the queen.
     */
    private final Color color;

    // Private constructor
    private Queen(Color color) {
        this.color = color;
    }

    /**
     * Static factory that returns a new white queen.
     *
     * @return a white queen.
     */
    public static Queen white() {
        return new Queen(Color.WHITE);
    }

    /**
     * Static factory that returns a new black queen.
     *
     * @return a black queen.
     */
    public static Queen black() {
        return new Queen(Color.BLACK);
    }

    /**
     * {@inheritDoc}
     *
     * A queen move is valid in principle if the number of moved rows equals the
     * number of moved columns or if the piece only changes the column or row.
     */
    @Override
    public boolean isValid(Tile start, Tile end) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
        int dirX = end.column() - start.column();
        int dirY = end.row() - start.row();
        // A queen can move like a bishop and a rook.
        return Math.abs(dirX) == Math.abs(dirY) || dirX == 0 || dirY == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type type() {
        return Type.QUEEN;
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
        return color == Color.WHITE ? "\u2655" : "\u265B";
    }
}
