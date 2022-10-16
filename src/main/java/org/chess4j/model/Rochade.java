package org.chess4j.model;

import org.chess4j.model.Player.Color;

/**
 * A Rochade is the only move that can move two pieces on the board. A Rochade
 * moves the king two tiles in the direction of the involved rook. The rook
 * changes sides with the king. In order to perform a successful Rochade
 * multiple Conditions must be meet.
 * <ol>
 * <li>The involved king and rook must be unmoved.</li>
 * <li>The player cannot be in check at the beginning of the Rochade</li>
 * <li>The king cannot move over a tile which is in reach of an enemy
 * piece.</li>
 * <li>All tiles in between the moved king and rook must be clear.</li>
 * </ol>
 * Please note, that although two pieces are moved as a result of the move only
 * the moved king is returned by {@link #moved()}
 */
public final class Rochade implements Move {

    /**
     * The start position of the move
     */
    private final Tile start;

    /**
     * The end position of the move
     */
    private final Tile end;

    /**
     * The former board position.
     */
    private final Board initial;

    /**
     * The later board position.
     */
    private final Board result;

    // Private constructor is only invoked after validity check.
    private Rochade(Tile start, Tile end, Chronicle chronicle) {
        this.start = start;
        this.end = end;
        this.initial = chronicle.current();
        Board result = new EnumMapBoard(initial);
        Piece rook = result.remove(getRookPosition(start, end));
        Tile inBeetween = Tile.path(start, end).stream().findFirst().get();
        result.put(inBeetween, rook);
        result.put(end, result.remove(start));
        this.result = result;
    }

    /**
     * Static factory that returns a valid Rochade move or {@code null} if none
     * can be constructed on the given board from the given start to end tile.
     *
     * @param start     the start tile of the move.
     * @param end       the end tile of the move.
     * @param chronicle the given chronicle of the game.
     * @return a valid constructed move or {@code null}.
     */
    public static Move perform(Tile start, Tile end, Chronicle game) {
        return isValid(start, end, game) ? new Rochade(start, end, game) : null;
    }

    /**
     * A Rochade moves the king two tiles in the direction of the involved rook. The
     * rook changes sides with the king. In order to perform a successful Rochade
     * multiple Conditions must be meet.
     * <ol>
     * <li>The involved king and rook must be unmoved.</li>
     * <li>The player cannot be in check at the beginning of the Rochade</li>
     * <li>The king cannot move over a tile which is in reach of an enemy
     * piece.</li>
     * <li>All tiles in between the moved king and rook must be clear.</li>
     * </ol>
     *
     * @param start     the start tile of the move
     * @param end       the end tile of the move
     * @param chronicle the history of the game
     * @return {@code true} if the Rochade is valid and {@code false} otherwise.
     */
    public static boolean isValid(Tile start, Tile end, Chronicle chronicle) {
        // Validity check for coordinates
        if (!validCoordinates(start, end)) {
            return false;
        }

        // Get the board and relevant pieces.
        Board board = chronicle.current();
        Piece king = board.get(start);
        Tile rookPosition = getRookPosition(start, end);
        Piece rook = board.get(rookPosition);

        // Validity check for pieces
        if (!(Piece.isKing(king) && Piece.isRook(rook) && king.color() == rook.color())) {
            return false;
        }

        // Check if the path is clear
        if (Tile.path(start, rookPosition).stream().anyMatch(board::containsKey)) {
            return false;
        }

        // Check if neither piece has been moved.
        if (chronicle.hasBeenMoved(king) || chronicle.hasBeenMoved(rook)) {
            return false;
        }

        Color color = king.color();
        // Check if any of the tiles is in check when the king moves over them.
        if (Player.inCheck(board, color)) {
            return false;
        }

        // Create a move where the king moves one tile to the end destination.
        Tile inBeetween = Tile.path(start, end).stream().findFirst().get();
        Move move = SimpleMove.perform(start, inBeetween, board);
        if (move == null) {
            return false;
        }
        // If the king is in check on the inBetween tile then the rochade cannot be
        // performed.
        if (Player.inCheck(move.result(), color)) {
            return false;
        }

        return true;
    }

    /*
     * Returns the position of the involved rook with given valid rochade
     * coordinates.
     */
    private static Tile getRookPosition(Tile start, Tile end) {
        return end.column() - start.column() < 0 ? Tile.valueOf(1, start.row()) : Tile.valueOf(8, start.row());
    }

    /*
     * Validity check for the given coordinates
     */
    private static boolean validCoordinates(Tile start, Tile end) {
        return (start == Tile.e1 && (end == Tile.c1 || end == Tile.g1))
                || (start == Tile.e8 && (end == Tile.c8 || end == Tile.g8));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tile start() {
        return start;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tile end() {
        return end;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board initial() {
        return Board.unmodifiable(initial);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board result() {
        return Board.unmodifiable(result);
    }

    /**
     * {@inheritDoc}
     *
     * Although two pieces are effectively moved as a result of the move only the
     * involved king is returned by this method.
     */
    @Override
    public Piece moved() {
        return Move.super.moved();
    }
}
