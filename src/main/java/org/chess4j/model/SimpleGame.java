package org.chess4j.model;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.chess4j.model.Piece.Type;
import org.chess4j.model.Player.Color;

/**
 * Simple implementation of a standard chess game API.
 */
public final class SimpleGame implements Game {

    // The game history
    private final Chronicle chronicle;

    // The white player
    private final Player white;

    // The black player
    private final Player black;

    // The color of the player whose turn it is.
    private Color playersTurn;

    // The current start tile in focus.
    private Tile start;

    // The current end tile in focus.
    private Tile end;

    /**
     * Constructs a new Simple game.
     */
    public SimpleGame() {
        chronicle = new Chronicle(Board.newGame());
        white = Player.white(chronicle);
        black = Player.black(chronicle);
        playersTurn = Color.WHITE;
    }

    /**
     * {@inheritDoc}
     */
    private Player currentPlayer() {
        return playersTurn == Color.WHITE ? white : black;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board position() {
        return chronicle.current();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tile getStart() {
        return start;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStart(Tile start) {
        this.start = Objects.requireNonNull(start);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tile getEnd() {
        return end;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color playersTurn() {
        return playersTurn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEnd(Tile end) {
        this.end = Objects.requireNonNull(end);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        start = null;
        end = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() throws InvalidMoveException, PawnNotPromotedException {
        if (canBePromoted()) {
            throw new PawnNotPromotedException("The move cannot be performed. The pawn must be promoted first.");
        }
        if (Objects.isNull(start) || Objects.isNull(end)) {
            throw new InvalidMoveException("No valid start and end coordinates are provided.");
        }
        Player player = currentPlayer();
        player.move(start, end);
        playersTurn = playersTurn.swap();
        reset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canBePromoted() {
        for (Tile tile : Tile.values()) {
            if (tile.row() == 8 || tile.row() == 1) {
                Piece piece = position().get(tile);
                if (Piece.isPawn(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void promote(Type type) {
        for (Tile tile : Tile.values()) {
            if (tile.row() == 8 || tile.row() == 1) {
                Piece piece = position().get(tile);
                if (Piece.isPawn(piece)) {
                    Pawn pawn = (Pawn) piece;
                    pawn.promote(type);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tile> reachableTiles() {
        if (start == null) {
            return Collections.emptySet();
        }
        Set<Tile> reachableTiles = EnumSet.noneOf(Tile.class);
        for (Tile tile : Tile.values()) {
            if (currentPlayer().isValid(start, tile)) {
                reachableTiles.add(tile);
            }
        }
        return Collections.unmodifiableSet(reachableTiles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasWhitePlayerWon() {
        return black.isCheckmate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasBlackPlayerWon() {
        return white.isCheckmate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStalemate() {
        return white.isStalemate() || black.isStalemate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isThreefoldRepetition() {
        if (chronicle.size() < 8) {
            return false;
        } else {
            Board current = chronicle.current();
            Board before = chronicle.get(chronicle.size() - 4).initial();
            Board beforeBefore = chronicle.get(chronicle.size() - 8).initial();
            return current.equals(before) && before.equals(beforeBefore);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFiftyMoveRule() {
        if (chronicle.size() < 100) {
            return false;
        }
        for (int i = 0; i < 100; i++) {
            Move move = chronicle.get(chronicle.size() - (i + 1));
            if (move.captured().isPresent()) {
                return false;
            }
            if (move.moved().type() == Type.PAWN) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDrawByInsufficientMaterial() {
        // More tan four pieces on the board are sufficient for a checkmate.
        Board board = position();
        if (board.size() > 4) {
            return false;
        }
        // Any heavy piece is sufficient with a king for checkmate. Any pawn could be
        // promoted to a heavy piece.
        if (board.anyMatch(p -> Piece.isQueen(p) || Piece.isRook(p) || Piece.isPawn(p))) {
            return false;
        }
        if (board.size() == 4) {
            // Only two bishobs remain on the board.
            Optional<Tile> whiteBishopPos = board.filter(Piece.isOfColor(Color.WHITE).and(Piece::isBishop)).keySet()
                    .stream().findFirst();
            Optional<Tile> blackBishopPos = board.filter(Piece.isOfColor(Color.BLACK).and(Piece::isBishop)).keySet()
                    .stream().findFirst();
            if (whiteBishopPos.isPresent() && blackBishopPos.isPresent()) {
                // It is a draw is only two bishobs remain which are placed on the same color.
                return whiteBishopPos.get().parity() == blackBishopPos.get().parity();
            } else {
                return false;
            }
        }

        /*
         * At this point only three or fewer pieces remain on the board which can only
         * be two kings and one knight or bishop which is a draw by insufficient
         * material.
         */
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int turnNumber() {
        return chronicle.size();
    }
}
