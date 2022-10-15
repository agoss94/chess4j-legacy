package org.chess4j.model;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

import org.chess4j.model.Piece.Type;
import org.chess4j.model.Player.Color;

public final class SimpleGame implements Game {

    // The game history
    private final Chronicle chronicle;

    // The white player
    private final Player white;

    // The black player
    private final Player black;

    private Color playersTurn;

    // The current start tile in focus.
    private Tile start;

    // The current end tile in focus.
    private Tile end;

    public SimpleGame() {
        chronicle = new Chronicle(Board.newGame());
        white = Player.white(chronicle);
        black = Player.black(chronicle);
        playersTurn = Color.WHITE;
    }

    private Player currentPlayer() {
        return playersTurn == Color.WHITE ? white : black;

    }

    @Override
    public Board position() {
        return chronicle.current();
    }

    @Override
    public Tile getStart() {
        return start;
    }

    @Override
    public void setStart(Tile start) {
        this.start = Objects.requireNonNull(start);
    }

    @Override
    public Tile getEnd() {
        return end;
    }

    @Override
    public Color playersTurn() {
        return playersTurn;
    }

    @Override
    public void setEnd(Tile end) {
        this.end = Objects.requireNonNull(end);
    }

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
    }

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

    @Override
    public boolean hasWhitePlayerWon() {
        return black.isCheckmate();
    }

    @Override
    public boolean hasBlackPlayerWon() {
        return white.isCheckmate();
    }

    @Override
    public boolean isStalemate() {
        return white.isStalemate() || black.isStalemate();
    }

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

    @Override
    public boolean isFiftyMoveRule() {
        if (chronicle.size() < 100) {
            return false;
        } else {
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
    }

    @Override
    public int turnNumber() {
        return chronicle.size();
    }
}
