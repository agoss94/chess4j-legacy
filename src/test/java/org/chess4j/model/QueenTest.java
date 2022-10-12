package org.chess4j.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class QueenTest {

    @Test
    void queenLeft() {
        Piece queen = Queen.white();
        assertEquals(true, queen.isValid(Tile.d5, Tile.a5));
    }

    @Test
    void queenUp() {
        Piece queen = Queen.white();
        assertEquals(true, queen.isValid(Tile.d5, Tile.d7));
    }

    @Test
    void queenRight() {
        Piece queen = Queen.white();
        assertEquals(true, queen.isValid(Tile.d5, Tile.h5));
    }

    @Test
    void queenDown() {
        Piece queen = Queen.white();
        assertEquals(true, queen.isValid(Tile.d5, Tile.d2));
    }

    @Test
    void queenUpRight() {
        Piece queen = Queen.white();
        assertEquals(true , queen.isValid(Tile.d4, Tile.g7));
    }

    @Test
    void queenUpLeft() {
        Piece queen = Queen.white();
        assertEquals(true , queen.isValid(Tile.d4, Tile.b6));
    }

    @Test
    void queenDownRight() {
        Piece queen = Queen.white();
        assertEquals(true , queen.isValid(Tile.d4, Tile.g1));
    }

    @Test
    void queenDownLeft() {
        Piece queen = Queen.white();
        assertEquals(true , queen.isValid(Tile.d4, Tile.c3));
    }

    @Test
    void queenFalseMove() {
        Piece queen = Queen.white();
        assertEquals(false , queen.isValid(Tile.d4, Tile.e2));
    }
}
