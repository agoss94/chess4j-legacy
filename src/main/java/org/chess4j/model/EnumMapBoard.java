package org.chess4j.model;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.EnumMap;
import java.util.Iterator;
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

    /*
     * Column declaration for printing the board.
     */
    private static final String COLUMN_DECLARATION = "\t A\t B\t C\t D\t E\t F\t G\t H";

    /*
     * New line character
     */
    private static final String NEW_LINE = String.format("%n");

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
        return new FilteredBoard(this, predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return boardAsString(this);
    }

    /**
     * Returns an unmodifiable Board that is backed the given board.
     *
     * @param board the given board.
     * @return an unmodifiable board.
     */
    protected static Board unmodifiable(Board board) {
        return new FilteredBoard(board, p -> true);
    }

    /**
     * Returns an unmodifiable copy of the given Board. Changes on the returned
     * Board are not reflected on this instance.
     *
     * @param board the given board.
     * @return an unmodifiable board.
     */
    protected static Board copy(Board board) {
        return new FilteredBoard(new EnumMapBoard(board), p -> true);
    }

    /**
     * Returns a new fully setup board.
     *
     * @return a new setup board.
     */
    protected static Board newGame() {
        EnumMapBoard position = new EnumMapBoard();

        // Put white Pieces on Board
        // First Row
        position.put(Tile.a1, Rook.white());
        position.put(Tile.b1, Knight.white());
        position.put(Tile.c1, Bishop.white());
        position.put(Tile.d1, Queen.white());
        position.put(Tile.e1, King.white());
        position.put(Tile.f1, Bishop.white());
        position.put(Tile.g1, Knight.white());
        position.put(Tile.h1, Rook.white());

        // Second Row of Pawns
        position.put(Tile.a2, Pawn.white());
        position.put(Tile.b2, Pawn.white());
        position.put(Tile.c2, Pawn.white());
        position.put(Tile.d2, Pawn.white());
        position.put(Tile.e2, Pawn.white());
        position.put(Tile.f2, Pawn.white());
        position.put(Tile.g2, Pawn.white());
        position.put(Tile.h2, Pawn.white());

        // Put black Pieces on Board
        // First Row
        position.put(Tile.a8, Rook.black());
        position.put(Tile.b8, Knight.black());
        position.put(Tile.c8, Bishop.black());
        position.put(Tile.d8, Queen.black());
        position.put(Tile.e8, King.black());
        position.put(Tile.f8, Bishop.black());
        position.put(Tile.g8, Knight.black());
        position.put(Tile.h8, Rook.black());

        // Second Row of Pawns
        position.put(Tile.a7, Pawn.black());
        position.put(Tile.b7, Pawn.black());
        position.put(Tile.c7, Pawn.black());
        position.put(Tile.d7, Pawn.black());
        position.put(Tile.e7, Pawn.black());
        position.put(Tile.f7, Pawn.black());
        position.put(Tile.g7, Pawn.black());
        position.put(Tile.h7, Pawn.black());

        return position;
    }

    /**
     * Prints the given board onto the console.
     *
     * @param board the given board.
     */
    private static String boardAsString(Board board) {
        StringBuilder sb = new StringBuilder();
        sb.append(COLUMN_DECLARATION);
        sb.append(NEW_LINE);
        sb.append(NEW_LINE);
        for (Tile tile : Tile.values()) {
            if (tile.column() == 1) {
                sb.append(String.format("%d\t", tile.row()));
            }
            if (board.containsKey(tile)) {
                sb.append(String.format("[%s]\t", board.get(tile)));
            } else {
                sb.append(String.format("[ ]\t"));
            }
            if (tile.column() == 8) {
                sb.append(String.format(" %d", tile.row()));
                sb.append(NEW_LINE);
                sb.append(NEW_LINE);
            }
        }
        sb.append(COLUMN_DECLARATION);

        return sb.toString();
    }

    /**
     * A private implementation of a Board that is backed by a given board.
     */
    private static class FilteredBoard extends AbstractMap<Tile, Piece> implements Board {

        /**
         * The filter condition.
         */
        private final Predicate<Piece> condition;

        /**
         * The entry set will be updated with the original map.
         */
        private final Board board;

        /**
         * @param condition
         */
        private FilteredBoard(Board board, Predicate<Piece> condition) {
            this.condition = condition;
            this.board = board;
        }

        @Override
        public Board filter(Predicate<Piece> predicate) {
            return board.filter(predicate.and(condition));
        }

        @Override
        public Set<Entry<Tile, Piece>> entrySet() {
            return new AbstractSet<Map.Entry<Tile, Piece>>() {

                @Override
                public Iterator<Entry<Tile, Piece>> iterator() {
                    return board.entrySet().stream().filter(e -> condition.test(e.getValue())).iterator();
                }

                @Override
                public int size() {
                    return (int) board.entrySet().stream().filter(e -> condition.test(e.getValue())).count();
                }
            };
        }

        @Override
        public String toString() {
            return boardAsString(this);
        }
    }
}
