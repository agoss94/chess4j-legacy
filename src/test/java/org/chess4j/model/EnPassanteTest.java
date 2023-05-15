package org.chess4j.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.chess4j.Board;
import org.chess4j.Chronicle;
import org.chess4j.EnumMapBoard;
import org.chess4j.Tile;
import org.chess4j.moves.EnPassante;
import org.chess4j.moves.Move;
import org.chess4j.moves.PawnLeap;
import org.chess4j.moves.SimpleMove;
import org.chess4j.pieces.Bishop;
import org.chess4j.pieces.Pawn;
import org.junit.jupiter.api.Test;

class EnPassanteTest {

    @Test
    void enPassanteOnlyForPawns() {
        Board board = new EnumMapBoard();
        board.put(Tile.e2, Pawn.white());
        board.put(Tile.f4, Bishop.black());
        Chronicle game = new Chronicle(board);
        game.add(PawnLeap.perform(Tile.e2, Tile.e4, board));
        assertTrue(SimpleMove.isValid(Tile.f4, Tile.e3, board));
        assertFalse(EnPassante.isValid(Tile.f4, Tile.e3, game));
    }

    @Test
    void enPassanteOnlyAfterPawnLeap() {
        Board board = new EnumMapBoard();
        board.put(Tile.e4, Pawn.white());
        board.put(Tile.f4, Pawn.black());
        Chronicle game = new Chronicle(board);
        assertFalse(EnPassante.isValid(Tile.f4, Tile.e3, game));
    }

    @Test
    void validEnPassanteBlackPawn() {
        Board board = new EnumMapBoard();
        board.put(Tile.e2, Pawn.white());
        board.put(Tile.f4, Pawn.black());
        Chronicle game = new Chronicle(board);
        game.add(PawnLeap.perform(Tile.e2, Tile.e4, board));
        assertTrue(EnPassante.isValid(Tile.f4, Tile.e3, game));
    }

    @Test
    void validEnPassanteWhitePawn() {
        Board board = new EnumMapBoard();
        board.put(Tile.e7, Pawn.black());
        board.put(Tile.f5, Pawn.white());
        Chronicle game = new Chronicle(board);
        game.add(PawnLeap.perform(Tile.e7, Tile.e5, board));
        assertTrue(EnPassante.isValid(Tile.f5, Tile.e6, game));
    }

    @Test
    void performEnPassanteWhitePawn() {
        Board board = new EnumMapBoard();
        Pawn black = Pawn.black();
        board.put(Tile.e7, black);
        Pawn white = Pawn.white();
        board.put(Tile.f5, white);
        Chronicle game = new Chronicle(board);
        game.add(PawnLeap.perform(Tile.e7, Tile.e5, board));
        Move enPassante = EnPassante.perform(Tile.f5, Tile.e6, game);
        assertNotNull(enPassante);
        assertEquals(white, enPassante.moved());
        assertEquals(black, enPassante.captured().get());
    }

}
