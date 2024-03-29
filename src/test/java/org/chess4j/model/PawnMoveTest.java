package org.chess4j.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.chess4j.Board;
import org.chess4j.Chronicle;
import org.chess4j.EnumMapBoard;
import org.chess4j.Tile;
import org.chess4j.moves.PawnMove;
import org.chess4j.pieces.Pawn;
import org.chess4j.pieces.Rook;
import org.junit.jupiter.api.Test;

class PawnMoveTest {

    @Test
    void noPieceOnStart() {
        Board board = new EnumMapBoard();
        assertFalse(PawnMove.isValidMoveForward(Tile.e2, Tile.e3, board));
    }

    @Test
    void noPawnOnStart() {
        Board board = new EnumMapBoard();
        board.put(Tile.e2, Rook.white());
        assertFalse(PawnMove.isValidMoveForward(Tile.e2, Tile.e3, board));
    }

    @Test
    void pawnMovesInTheWrongDirection() {
        Board board = new EnumMapBoard();
        board.put(Tile.e2, Rook.white());
        assertFalse(PawnMove.isValidMoveForward(Tile.e2, Tile.e1, board));
    }

    @Test
    void pawnCannotCaptureInNormalMoves() {
        Board board = new EnumMapBoard();
        board.put(Tile.e2, Pawn.white());
        board.put(Tile.e3, Rook.black());
        assertFalse(PawnMove.isValidMoveForward(Tile.e2, Tile.e3, board));
    }

    @Test
    void validWhiteMove() {
        assertTrue(PawnMove.isValidMoveForward(Tile.e2, Tile.e3, Board.newGame()));
    }

    @Test
    void validBlackMove() {
        assertTrue(PawnMove.isValidMoveForward(Tile.e7, Tile.e6, Board.newGame()));
    }

    @Test
    void invalidPerformedMoveIsNull() {
        assertNull(PawnMove.perform(Tile.e2, Tile.e4, Board.newGame()));
    }

    @Test
    void validPerformedMoveIsNotNull() {
        assertNotNull(PawnMove.perform(Tile.e2, Tile.e3, Board.newGame()));
    }

    @Test
    void pawnMustCapture() {
        Board board = new EnumMapBoard();
        board.put(Tile.e2, Pawn.white());
        Chronicle game = new Chronicle(board);
        assertFalse(PawnMove.isValidCapture(Tile.e2, Tile.d3, game.current()));
    }

    @Test
    void validWhiteMoveCapture() {
        Board board = new EnumMapBoard();
        board.put(Tile.e2, Pawn.white());
        board.put(Tile.d3, Rook.black());
        Chronicle game = new Chronicle(board);
        assertTrue(PawnMove.isValidCapture(Tile.e2, Tile.d3, game.current()));
    }

    @Test
    void validBlackMoveCapture() {
        Board board = new EnumMapBoard();
        board.put(Tile.e7, Pawn.black());
        board.put(Tile.d6, Rook.white());
        Chronicle game = new Chronicle(board);
        assertTrue(PawnMove.isValidCapture(Tile.e7, Tile.d6, game.current()));
    }
}