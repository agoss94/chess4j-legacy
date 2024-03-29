package org.chess4j.moves;

import org.chess4j.Board;
import org.chess4j.EnumMapBoard;
import org.chess4j.Tile;
import org.chess4j.Player.Color;
import org.chess4j.pieces.Piece;

/**
 * A pawn leap can only occur the first time a pawn is moved. In the first move
 * a Pawn can move two tiles at once opposed to one normally. As with all moves
 * a static factory method {@link #perform(Tile, Tile, Board)} is offered which
 * performs a validity check before creation. If the move is invalid then
 * {@code null} is returned.
 */
public final class PawnLeap implements Move {

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

    // Private constructor is only invoked after a validity check.
    private PawnLeap(Tile start, Tile end, Board initial) {
        this.start = start;
        this.end = end;
        this.initial = Board.copy(initial);
        Board result = new EnumMapBoard(initial);
        result.put(end, result.remove(start));
        this.result = Board.copy(result);
    }

    /**
     * Returns a standard move or {@code null} if the move would be invalid.
     *
     * @param former the initial position.
     * @param start  the start coordinate.
     * @param end    the end coordinate.
     * @return a valid move or {@code null} if the move would be invalid.
     */
    public static Move perform(Tile start, Tile end, Board board) {
        return isValid(start, end, board) ? new PawnLeap(start, end, board) : null;
    }

    /**
     * Checks if the pawn move with the given board position is valid. In order to
     * determine if the given pawn on start has moved or not the row of the start
     * tile is checked. If that is the case then it is checked if the pawn leaps two
     * row in its direction. If not the move is {@code false}.
     *
     * @param former the initial position.
     * @param start  the start coordinate.
     * @param end    the end coordinate.
     * @return {@code true} if the move is valid.
     */
    public static boolean isValid(Tile start, Tile end, Board board) {
        // There must not be a piece on the end position.
        if (board.containsKey(end)) {
            return false;
        }

        Piece pawn = board.get(start);
        if (Piece.isPawn(pawn)) {
            Color pawnColor = pawn.color();

            int dir = pawnColor == Color.WHITE ? 1 : -1;
            int deltaRow = end.row() - start.row();
            int deltaColumn = end.column() - start.column();

            boolean isOnStart = pawnColor == Color.WHITE ? start.row() == 2 : start.row() == 7;

            return isOnStart && deltaRow * dir == 2 && deltaColumn == 0;
        } else {
            return false;
        }
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
        return initial;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board result() {
        return result;
    }
}
