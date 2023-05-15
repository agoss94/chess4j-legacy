package org.chess4j.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.chess4j.Board;
import org.chess4j.EnumMapBoard;
import org.chess4j.Tile;
import org.chess4j.moves.SimpleMove;
import org.chess4j.pieces.Rook;
import org.junit.jupiter.api.Test;

class SimpleMoveTest {

    @Test
    void noPieceOnStart() {
        Board board = new EnumMapBoard();
        assertFalse(SimpleMove.isValid(Tile.b1, Tile.c3, board));
    }

    @Test
    void pieceOnStartMovedInvalid() {
        assertFalse(SimpleMove.isValid(Tile.b1, Tile.b3, Board.newGame()));
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
        assertTrue(SimpleMove.isValid(Tile.b1, Tile.a3, Board.newGame()));
    }

    @Test
    void invalidPerformedMoveIsNull() {
        assertNull(SimpleMove.perform(Tile.b1, Tile.b4, Board.newGame()));
    }

    @Test
    void validPerformedMoveIsNotNull() {
        assertNotNull(SimpleMove.perform(Tile.b1, Tile.c3, Board.newGame()));
    }
}