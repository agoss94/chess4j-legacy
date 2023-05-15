package org.chess4j.moves;

import java.util.Objects;

import org.chess4j.Board;
import org.chess4j.EnumMapBoard;
import org.chess4j.Tile;
import org.chess4j.pieces.Piece;

/**
 * A Simple move is a normal move rook, knight, bishop, queen and king given
 * that the move is not a {@link Rochade}. All Pawn moves are either performed
 * by {@link PawnMove}, {@link PawnLeap} or {@link EnPassante}.
 */
public final class SimpleMove implements Move {

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
    private SimpleMove(Tile start, Tile end, Board initial) {
        this.start = start;
        this.end = end;
        this.initial = Board.copy(initial);
        Board result = new EnumMapBoard(initial);
        result.put(end, result.remove(start));
        this.result = Board.copy(result);
    }

    /**
     * Returns a standard move or {@code null} if the move would be invalid. A move
     * is valid if the piece can move from start to end legally as specified by
     * {@link Piece#isValid(Tile, Tile)}. Secondly the path of the piece must be
     * clear as specified by {@link Tile#path(Tile, Tile)} and lastly the end tile
     * must be either empty or host a piece of opposite color. If there is no Piece
     * associated with the start position then false is returned.
     *
     * @param initial the initial position.
     * @param start   the start coordinate.
     * @param end     the end coordinate.
     * @return a valid move or {@code null} if the move would be invalid.
     */
    public static Move perform(Tile start, Tile end, Board initial) {
        return isValid(start, end, initial) ? new SimpleMove(start, end, initial) : null;
    }

    /**
     * Checks if the standard move with the given position is valid. A move is valid
     * if the piece can move from start to end legally as specified by
     * {@link Piece#isValid(Tile, Tile)}. Secondly the path of the piece must be
     * clear as specified by {@link Tile#path(Tile, Tile)} and lastly the end tile
     * must be either empty or host a piece of opposite color. If there is no Piece
     * associated with the start position then false is returned.
     *
     * @param initial the initial position.
     * @param start   the start coordinate.
     * @param end     the end coordinate.
     * @return {@code true} if the move is valid.
     */
    public static boolean isValid(Tile start, Tile end, Board initial) {
        // If there is no piece on start then the move is invalid.
        if (!initial.containsKey(start)) {
            return false;
        }
        Piece startPiece = initial.get(start);

        // Check if the move in principle is possible
        if (!startPiece.isValid(start, end)) {
            return false;
        }

        // The path of the piece must be completely clear
        if (!Tile.path(start, end).stream().noneMatch(initial::containsKey)) {
            return false;
        }

        // The piece placed on the end Position must have a different color than the
        // start piece.
        Piece endPiece = initial.get(end);
        if (!(Objects.isNull(endPiece) || Piece.isOpposite(startPiece, endPiece))) {
            return false;
        }

        return true;
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
