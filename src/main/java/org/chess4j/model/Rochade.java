package org.chess4j.model;

import java.util.Optional;

import org.chess4j.model.Player.Color;

public class Rochade implements Move {

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

    public static Move perform(Tile start, Tile end, Chronicle game) {
        return isValid(start, end, game) ? new Rochade(start, end, game) : null;
    }

    public static boolean isValid(Tile start, Tile end, Chronicle chronicle) {
        // Validity check for coordinates
        if (!validCoordinates(start, end)) {
            return false;
        }

        // Get the board and relevant pieces.
        Board board = chronicle.current();
        Piece king = board.get(start);
        Piece rook = board.get(getRookPosition(start, end));

        // Validity check for pieces
        if (!(Piece.isKing(king) && Piece.isRook(rook) && king.color() == rook.color())) {
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

    private static Tile getRookPosition(Tile start, Tile end) {
        return end.column() - start.column() < 0 ? Tile.valueOf(1, start.row()) : Tile.valueOf(8, start.row());
    }

    private static boolean validCoordinates(Tile start, Tile end) {
        return (start == Tile.e1 && (end == Tile.c1 || end == Tile.g1))
                || (start == Tile.e8 && (end == Tile.c8 || end == Tile.g8));
    }

    @Override
    public Tile start() {
        return start;
    }

    @Override
    public Tile end() {
        return end;
    }

    @Override
    public Board initial() {
        return Utils.unmodifiable(initial);
    }

    @Override
    public Board result() {
        return Utils.unmodifiable(result);
    }

    @Override
    public Piece moved() {
        return initial.get(start);
    }

    @Override
    public Optional<Piece> captured() {
        return Optional.empty();
    }

}
