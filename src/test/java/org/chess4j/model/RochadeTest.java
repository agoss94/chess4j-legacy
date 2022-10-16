package org.chess4j.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RochadeTest {

    @Test
    void invalidCoordinates() {
        Board board = new EnumMapBoard();
        board.put(Tile.e1, King.white());
        board.put(Tile.h1, Rook.white());
        Chronicle chronicle = new Chronicle(board);
        assertFalse(Rochade.isValid(Tile.e1, Tile.h1, chronicle));
    }

    @Test
    void wrongColorPiece() {
        Board board = new EnumMapBoard();
        board.put(Tile.e1, King.white());
        board.put(Tile.h1, Rook.black());
        Chronicle chronicle = new Chronicle(board);
        assertFalse(Rochade.isValid(Tile.e1, Tile.g1, chronicle));
    }

    @Test
    void whiteShortRochadeIsValid() {
        Board board = new EnumMapBoard();
        board.put(Tile.e1, King.white());
        board.put(Tile.h1, Rook.white());
        Chronicle chronicle = new Chronicle(board);
        assertTrue(Rochade.isValid(Tile.e1, Tile.g1, chronicle));
    }

    @Test
    void blackShortRochadeIsValid() {
        Board board = new EnumMapBoard();
        board.put(Tile.e8, King.white());
        board.put(Tile.h8, Rook.white());
        Chronicle chronicle = new Chronicle(board);
        assertTrue(Rochade.isValid(Tile.e8, Tile.g8, chronicle));
    }

    @Test
    void whiteLongRochadeIsValid() {
        Board board = new EnumMapBoard();
        board.put(Tile.e1, King.white());
        board.put(Tile.a1, Rook.white());
        Chronicle chronicle = new Chronicle(board);
        assertTrue(Rochade.isValid(Tile.e1, Tile.c1, chronicle));
    }

    @Test
    void blackLongRochadeIsValid() {
        Board board = new EnumMapBoard();
        board.put(Tile.e8, King.white());
        board.put(Tile.a8, Rook.white());
        Chronicle chronicle = new Chronicle(board);
        assertTrue(Rochade.isValid(Tile.e8, Tile.c8, chronicle));
    }

    @Test
    void invalidWhiteLongRochadeWithoneTileInCheck() {
        Board board = new EnumMapBoard();
        board.put(Tile.e1, King.white());
        board.put(Tile.a1, Rook.white());
        board.put(Tile.d8, Rook.black());
        Chronicle chronicle = new Chronicle(board);
        assertFalse(Rochade.isValid(Tile.e1, Tile.c1, chronicle));
    }

    @Test
    void invalidWhiteRochadeWithMovedPiece() {
        Board board = new EnumMapBoard();
        board.put(Tile.e1, King.white());
        board.put(Tile.a1, Rook.white());
        Chronicle chronicle = new Chronicle(board);
        chronicle.add(SimpleMove.perform(Tile.a1, Tile.a3, chronicle.current()));
        chronicle.add(SimpleMove.perform(Tile.a3, Tile.a1, chronicle.current()));
        assertFalse(Rochade.isValid(Tile.e1, Tile.c1, chronicle));
    }

    @Test
    void invalidWhiteRochadeWithWhiteKnightInTheWay() {
        Board board = new EnumMapBoard();
        board.put(Tile.e1, King.white());
        board.put(Tile.a1, Rook.white());
        board.put(Tile.b1, Knight.white());
        Chronicle chronicle = new Chronicle(board);
        assertFalse(Rochade.isValid(Tile.e1, Tile.c1, chronicle));
    }
}
