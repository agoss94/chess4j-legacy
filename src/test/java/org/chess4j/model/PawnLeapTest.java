package org.chess4j.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PawnLeapTest {

    @Test
    void noPieceOnStart() {
        Board board = new EnumMapBoard();
        assertFalse(PawnLeap.isValid(Tile.e2, Tile.e4, board));
    }

    @Test
    void noPawnOnStart() {
        Board board = new EnumMapBoard();
        board.put(Tile.e2, Rook.white());
        assertFalse(PawnLeap.isValid(Tile.e2, Tile.e4, board));
    }

    @Test
    void pawnMovesInTheWrongDirection() {
        Board board = new EnumMapBoard();
        board.put(Tile.e3, Rook.white());
        assertFalse(PawnLeap.isValid(Tile.e3, Tile.e1, board));
    }

    @Test
    void pawnCannotCaptureInLeap() {
        Board board = new EnumMapBoard();
        board.put(Tile.e2, Pawn.white());
        board.put(Tile.e4, Rook.black());
        assertFalse(PawnLeap.isValid(Tile.e2, Tile.e4, board));
    }

    @Test
    void pawnLeapIsValid() {
        assertTrue(PawnLeap.isValid(Tile.e2, Tile.e4, Utils.newGame()));
    }

    @Test
    void invalidPerformedMoveIsNull() {
        assertNull(PawnLeap.perform(Tile.e2, Tile.e3, Utils.newGame()));
    }

    @Test
    void validPerformedMoveIsNotNull() {
        assertNotNull(PawnLeap.perform(Tile.e2, Tile.e4, Utils.newGame()));
    }
}
