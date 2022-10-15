package org.chess4j.model;

import org.chess4j.model.Player.Color;

/**
 * A valid pawn move is either a normal move one row forward the enemy camp or a
 * pawn capture of an enemy piece. As with all moves a static factory method
 * {@link #perform(Tile, Tile, Board)} is offered which performs a validity
 * check before creation. If the move is invalid then {@code null} is returned.
 */
public final class PawnMove implements Move {

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
    private PawnMove(Tile start, Tile end, Board initial) {
        this.start = start;
        this.end = end;
        this.initial = Board.copy(initial);
        Board result = new EnumMapBoard(initial);
        result.put(end, result.remove(start));
        this.result = Board.copy(result);
    }

    /**
     * Returns a standard move or {@code null} if the move would be invalid. A valid
     * pawn move is a normal move by one tile forward in the only direction the pawn
     * is allowed to move or a capture move of an enemy piece by the pawn. A white
     * pawn can move up the board and a black pawn can only move down.
     *
     * @param former the initial position.
     * @param start  the start coordinate.
     * @param end    the end coordinate.
     * @return a valid move or {@code null} if the move would be invalid.
     * @see #isValidCapture(Tile, Tile, Board)
     * @see #isValidMoveForward(Tile, Tile, Board)
     */
    public static Move perform(Tile start, Tile end, Board board) {
        return isValidMoveForward(start, end, board) || isValidCapture(start, end, board)
                ? new PawnMove(start, end, board)
                : null;
    }

    /**
     * Checks if the pawn move with the given game position is valid. A valid pawn
     * move is a normal move by one tile forward in the only direction the pawn is
     * allowed to move. A white pawn can move up the board and a black pawn can only
     * move down.
     *
     * @param end   the end coordinate.
     * @param start the start coordinate.
     * @param board the initial position.
     * @return {@code true} if the move is valid.
     */
    public static boolean isValidMoveForward(Tile start, Tile end, Board board) {
        // If there is no piece on start then the move is invalid. Also there must not
        // be a piece on the end position.
        if (board.containsKey(end)) {
            return false;
        }

        Piece pawn = board.get(start);
        if (Piece.isPawn(pawn)) {
            Color pawnColor = pawn.color();
            int dir = pawnColor == Color.WHITE ? 1 : -1;

            int deltaRow = end.row() - start.row();
            int deltaColumn = end.column() - start.column();

            return deltaRow * dir == 1 && deltaColumn == 0;
        } else {
            return false;
        }
    }

    /**
     * Checks if the pawn move with the given game position is valid.
     *
     * @param former the initial position.
     * @param start  the start coordinate.
     * @param end    the end coordinate.
     * @return {@code true} if the move is valid.
     */
    public static boolean isValidCapture(Tile start, Tile end, Board board) {
        // If there is no piece on start then the move is invalid. Also there must
        // be a piece on the end position.
        if (!board.containsKey(end)) {
            return false;
        }

        Piece pawn = board.get(start);
        Piece endPiece = board.get(end);

        if (Piece.isPawn(pawn)) {
            Color pawnColor = pawn.color();
            int dir = pawnColor == Color.WHITE ? 1 : -1;

            int deltaRow = end.row() - start.row();
            int deltaColumn = end.column() - start.column();

            return deltaRow * dir == 1 && Math.abs(deltaColumn) == 1 && Piece.isOpposite(pawn, endPiece);
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
