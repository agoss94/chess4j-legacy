package org.chess4j.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Set;

import org.chess4j.Tile;
import org.junit.jupiter.api.Test;

public class TileTest {

    @Test
    void pathUp() {
        assertEquals(Set.of(Tile.a2, Tile.a3, Tile.a4), Tile.path(Tile.a1, Tile.a5));
    }

    @Test
    void pathDown() {
        assertEquals(Set.of(Tile.a2, Tile.a3, Tile.a4), Tile.path(Tile.a5, Tile.a1));
    }

    @Test
    void pathLeft() {
        assertEquals(Set.of(Tile.b1, Tile.c1, Tile.d1), Tile.path(Tile.e1, Tile.a1));
    }

    @Test
    void pathRight() {
        assertEquals(Set.of(Tile.b1, Tile.c1, Tile.d1), Tile.path(Tile.a1, Tile.e1));
    }

    @Test
    void pathDiagonal() {
        assertEquals(Set.of(Tile.b2, Tile.c3, Tile.d4, Tile.e5), Tile.path(Tile.a1, Tile.f6));
    }

    @Test
    void pathEmptyForNonStraightPaths() {
        assertEquals(Collections.emptySet(), Tile.path(Tile.b1, Tile.c3));
    }
}
