package org.chess4j.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.chess4j.Board;
import org.chess4j.Chronicle;
import org.chess4j.EnumMapBoard;
import org.chess4j.Player;
import org.chess4j.Tile;
import org.chess4j.exceptions.InvalidMoveException;
import org.chess4j.pieces.Bishop;
import org.chess4j.pieces.King;
import org.chess4j.pieces.Knight;
import org.chess4j.pieces.Pawn;
import org.chess4j.pieces.Queen;
import org.chess4j.pieces.Rook;
import org.junit.jupiter.api.Test;

class PlayerTest {

	@Test
	void isValidPlayer() {
		Chronicle game = new Chronicle(Board.newGame());
		Player white = Player.white(game);
		assertTrue(white.isValid(Tile.e2, Tile.e3));
		assertTrue(white.isValid(Tile.b1, Tile.c3));
	}

	@Test
	void movePlayer() {
		Chronicle game = new Chronicle(Board.newGame());
		Player white = Player.white(game);
		assertTrue(white.isValid(Tile.e2, Tile.e3));
		assertTrue(white.isValid(Tile.b1, Tile.c3));
		white.move(Tile.e2, Tile.e3);
		assertEquals(1, game.size());
	}

	@Test
	void inCheck() {
		Chronicle game = new Chronicle(Board.newGame());
		Player white = Player.white(game);
		Player black = Player.black(game);
		white.move(Tile.e2, Tile.e3);
		black.move(Tile.b8, Tile.c6);
		white.move(Tile.f2, Tile.f3);
		black.move(Tile.c6, Tile.b4);
		white.move(Tile.c2, Tile.c3);
		black.move(Tile.b4, Tile.d3);
		assertTrue(white.inCheck());
		assertFalse(black.inCheck());
	}

	@Test
	void checkmate() {
		Board board = new EnumMapBoard();
		board.put(Tile.a2, Pawn.white());
		board.put(Tile.c4, Pawn.white());
		board.put(Tile.d2, Pawn.white());
		board.put(Tile.e2, Pawn.white());
		board.put(Tile.f2, Pawn.white());
		board.put(Tile.g2, Pawn.white());
		board.put(Tile.h2, Pawn.white());

		board.put(Tile.a1, Rook.white());
		board.put(Tile.b1, Knight.white());
		board.put(Tile.c1, Bishop.white());
		board.put(Tile.d1, Queen.white());
		board.put(Tile.e1, King.white());
		board.put(Tile.f1, Bishop.white());
		board.put(Tile.h1, Rook.white());

		// Put black Pieces on Board
		// First Row
		board.put(Tile.a8, Rook.black());
		board.put(Tile.d3, Knight.black());
		board.put(Tile.c8, Bishop.black());
		board.put(Tile.e8, King.black());
		board.put(Tile.e7, Queen.black());
		board.put(Tile.f8, Bishop.black());
		board.put(Tile.g8, Knight.black());
		board.put(Tile.h8, Rook.black());

		// Second Row of Pawns
		board.put(Tile.a7, Pawn.black());
		board.put(Tile.b7, Pawn.black());
		board.put(Tile.c7, Pawn.black());
		board.put(Tile.d7, Pawn.black());
		board.put(Tile.f7, Pawn.black());
		board.put(Tile.g7, Pawn.black());
		board.put(Tile.h7, Pawn.black());

		Chronicle game = new Chronicle(board);
		Player white = Player.white(game);
		Player black = Player.black(game);
		assertTrue(white.isCheckmate());
		assertFalse(black.isCheckmate());
	}

	@Test
	void stalemate() {
		Board board = new EnumMapBoard();
		board.put(Tile.a3, Knight.black());
		board.put(Tile.b3, King.black());
		board.put(Tile.a1, King.white());

		Chronicle game = new Chronicle(board);
		Player white = Player.white(game);
		Player black = Player.black(game);
		assertFalse(white.isCheckmate());
		assertFalse(black.isCheckmate());
		assertTrue(white.isStalemate());
	}

	@Test
	void mustResolveCheck() {
		Chronicle chronicle = new Chronicle(Board.newGame());
		Player white = Player.white(chronicle);
		Player black = Player.black(chronicle);
		white.move(Tile.e2, Tile.e4);
		black.move(Tile.e7, Tile.e5);
		white.move(Tile.b1, Tile.c3);
		black.move(Tile.d7, Tile.d5);
		white.move(Tile.e4, Tile.d5);
		black.move(Tile.d8, Tile.d5);
		white.move(Tile.c3, Tile.d5);
		black.move(Tile.c8, Tile.g4);
		white.move(Tile.d5, Tile.c7);
		assertTrue(black.inCheck());
		assertFalse(black.isValid(Tile.g4, Tile.d1));
		assertThrows(InvalidMoveException.class, () -> black.move(Tile.g4, Tile.d1));
	}

	@Test
	void enPassanteMove() {
		Chronicle chronicle = new Chronicle(Board.newGame());
		Player white = Player.white(chronicle);
		Player black = Player.black(chronicle);
		white.move(Tile.f2, Tile.f4);
		black.move(Tile.e7, Tile.e5);
		white.move(Tile.f4, Tile.e5);
		black.move(Tile.d7, Tile.d5);
		assertTrue(white.isValid(Tile.e5, Tile.d6));
	}
}
