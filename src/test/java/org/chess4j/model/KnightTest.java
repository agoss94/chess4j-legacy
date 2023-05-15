package org.chess4j.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.chess4j.Tile;
import org.chess4j.pieces.Knight;
import org.chess4j.pieces.Piece;
import org.junit.jupiter.api.Test;

class KnightTest {


    @Test
    void knightUpRight() {
        Piece knight = Knight.black();
        assertEquals(true, knight.isValid(Tile.d4, Tile.e6));
    }

    @Test
    void knightRightUp() {
        Piece knight = Knight.black();
        assertEquals(true, knight.isValid(Tile.d4, Tile.f5));
    }

    @Test
    void knightRightDown() {
        Piece knight = Knight.black();
        assertEquals(true, knight.isValid(Tile.d4, Tile.f3));
    }

    @Test
    void knightDownRight() {
        Piece knight = Knight.black();
        assertEquals(true, knight.isValid(Tile.d4, Tile.e2));
    }

    @Test
    void knightDownLeft() {
        Piece knight = Knight.black();
        assertEquals(true, knight.isValid(Tile.d4, Tile.c2));
    }

    @Test
    void knightLeftDown() {
        Piece knight = Knight.black();
        assertEquals(true, knight.isValid(Tile.d4, Tile.b3));
    }

    @Test
    void knightLeftUp() {
        Piece knight = Knight.black();
        assertEquals(true, knight.isValid(Tile.d4, Tile.b5));
    }

    @Test
    void knightUpLeft() {
        Piece knight = Knight.black();
        assertEquals(true, knight.isValid(Tile.d4, Tile.c6));
    }

    @Test
    void knightFalseMove() {
        Piece knight = Knight.black();
        assertEquals(false, knight.isValid(Tile.d4, Tile.d6));
    }

}
