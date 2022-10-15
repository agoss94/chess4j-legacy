package org.chess4j.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.chess4j.model.Player.Color;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    void unmodifiableBoard() {
        Board board = new EnumMapBoard();
        board.put(Tile.e2, Pawn.white());
        Board unmodifiableBoard = Board.unmodifiable(board);
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableBoard.put(Tile.d1, Pawn.white()));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableBoard.remove(Tile.e2));
        assertThrows(UnsupportedOperationException.class, unmodifiableBoard::clear);
        assertEquals(1, unmodifiableBoard.size());
        board.clear();
        assertEquals(0, unmodifiableBoard.size());
    }

    @Test
    void subBoard() {
        Board board = Board.newGame();
        Board subBoardBoard = board.filter(Piece.isOfColor(Color.WHITE));
        assertEquals(16, subBoardBoard.size());

        assertThrows(UnsupportedOperationException.class, () -> subBoardBoard.put(Tile.d1, Pawn.white()));
        assertThrows(UnsupportedOperationException.class, () -> subBoardBoard.remove(Tile.e2));
        assertThrows(UnsupportedOperationException.class, subBoardBoard::clear);
        //Removal of a black piece does not affect the subBoard
        board.remove(Tile.e7);
        assertEquals(16, subBoardBoard.size());
        board.remove(Tile.e2);
        assertEquals(15, subBoardBoard.size());
    }

}
