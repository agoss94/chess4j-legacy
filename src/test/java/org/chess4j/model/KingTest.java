package org.chess4j.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class KingTest {

    @Test
    void kingUp() {
        Piece king = King.black();
        assertEquals(true, king.isValid(Tile.d3, Tile.d4));
    }

    @Test
    void kingUpRight() {
        Piece king = King.black();
        assertEquals(true, king.isValid(Tile.d3, Tile.e4));
    }

    @Test
    void kingRight() {
        Piece king = King.black();
        assertEquals(true, king.isValid(Tile.d3, Tile.c3));
    }

    @Test
    void kingRightDown() {
        Piece king = King.black();
        assertEquals(true, king.isValid(Tile.d3, Tile.c2));
    }

    @Test
    void kingDown() {
        Piece king = King.black();
        assertEquals(true, king.isValid(Tile.d3, Tile.d2));
    }

    @Test
    void kingDownLeft() {
        Piece king = King.black();
        assertEquals(true, king.isValid(Tile.d3, Tile.c2));
    }

    @Test
    void kingLeft() {
        Piece king = King.black();
        assertEquals(true, king.isValid(Tile.d3, Tile.c3));
    }

    @Test
    void kingLeftUp() {
        Piece king = King.black();
        assertEquals(true, king.isValid(Tile.d3, Tile.c4));
    }

    @Test
    void kingFalseMove() {
        Piece king = King.black();
        assertEquals(false, king.isValid(Tile.d3, Tile.d5));
    }
}
