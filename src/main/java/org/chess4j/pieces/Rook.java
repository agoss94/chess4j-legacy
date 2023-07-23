package org.chess4j.pieces;

import java.util.Objects;

import org.chess4j.Player.Color;
import org.chess4j.Tile;

/**
 * A rook is a chess piece that can move only in straight lines. The class
 * offers two static factory methods {@link #white()} and {@link #black()} for
 * constructing a rook. Once created a rook is immutable and stateless. Also
 * note that no two rook are equal as the class does not overwrite
 * {@link #hashCode()} nor {@link #equals(Object)}.
 */
public final class Rook implements Piece {

    /**
     * The color of the rook.
     */
    private final Color color;

    // Private constructor
    private Rook(Color color) {
        this.color = color;
    }

    /**
     * Static factory that returns a new white rook.
     *
     * @return a white rook.
     */
    public static Rook white() {
        return new Rook(Color.WHITE);
    }

    /**
     * Static factory that returns a new black rook.
     *
     * @return a black rook.
     */
    public static Rook black() {
        return new Rook(Color.BLACK);
    }

    /**
     * {@inheritDoc}
     *
     * A rook move is valid in principle if the number of moved rows or the number
     * of moved columns is zero.
     */
    @Override
    public boolean isValid(Tile start, Tile end) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
        int dirX = end.column() - start.column();
        int dirY = end.row() - start.row();
        // A Rook can only move in one direction.
        return dirX == 0 || dirY == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type type() {
        return Type.ROOK;
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
        return color == Color.WHITE ? "\u2656" : "\u265C";
    }
}
