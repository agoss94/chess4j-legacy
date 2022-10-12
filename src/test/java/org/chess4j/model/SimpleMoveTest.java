package org.chess4j.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SimpleMoveTest {

    @Test
    void noPieceOnStart() {
        Board board = new EnumMapBoard();
        assertFalse(SimpleMove.isValid(Tile.b1, Tile.c3, board));
    }

    @Test
    void pieceOnStartMovedInvalid() {
        assertFalse(SimpleMove.isValid(Tile.b1, Tile.b3, Utils.newGame()));
    }

    @Test
    void pathNotClear() {
        Board board = new EnumMapBoard();
        board.put(Tile.a1, Rook.white());
        board.put(Tile.a3, Rook.white());
        assertFalse(SimpleMove.isValid(Tile.a1, Tile.a5, board));
    }

    @Test
    void capturedPieceOfWrongColor() {
        Board board = new EnumMapBoard();
        board.put(Tile.a1, Rook.white());
        board.put(Tile.a5, Rook.white());
        assertFalse(SimpleMove.isValid(Tile.a1, Tile.a5, board));
    }

    @Test
    void validMove() {
        assertTrue(SimpleMove.isValid(Tile.b1, Tile.a3, Utils.newGame()));
    }

    @Test
    void invalidPerformedMoveIsNull() {
        assertNull(SimpleMove.perform(Tile.b1, Tile.b4, Utils.newGame()));
    }

    @Test
    void validPerformedMoveIsNotNull() {
        assertNotNull(SimpleMove.perform(Tile.b1, Tile.c3, Utils.newGame()));
    }
}