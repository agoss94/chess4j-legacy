package org.chess4j.model;

import java.util.AbstractMap;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/**
 * A Board implementation that is backed by an EnumMap. Essentially an instance
 * {@link EnumMap} is created and forwarded by this class. As stated by the
 * {@link Map} interface this class offers two constructors
 * {@link #EnumMapBoard()} and {@link #EnumMapBoard(Map)}
 */
public final class EnumMapBoard extends AbstractMap<Tile, Piece> implements Board {

    /**
     * Private map that is forwarded.
     */
    private final Map<Tile, Piece> board;

    /**
     * Constructor instantiates an empty board.
     */
    public EnumMapBoard() {
        board = new EnumMap<>(Tile.class);
    }

    /**
     * Creates a board with all the mappings as in the given board.
     *
     * @param board the board that is used as template.
     */
    public EnumMapBoard(Map<Tile, Piece> board) {
        this();
        putAll(board);
    }

    /**
     * Associates the given piece with the given tile. If there was previously a
     * piece associated with the tile, then this piece is returned.
     *
     * @return the previously associated mapping or {@code null} if none is present.
     * @throws NullPointerException if the key or value is {@code null}.
     */
    @Override
    public Piece put(Tile key, Piece value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        return board.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Entry<Tile, Piece>> entrySet() {
        return board.entrySet();
    }

    /**
     * Returns view of all pieces that test {@code true} to the given predicate. The
     * view momentarily view of the given board. Later modifications are not
     * reflected on the suboard.
     *
     * @param predicate the predicate all mappings of the submap must fulfill.
     * @return a submap view of all pieces on the board that test positive to the
     *         given predicate.
     */
    public Board filter(Predicate<Piece> predicate) {
        return Utils.subBoard(this, predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return Utils.boardAsString(this);
    }
}
