package org.chess4j.model;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.chess4j.model.Piece.isKing;
import static org.chess4j.model.Piece.isPawn;

/**
 * A Player moves pieces throughout a game of chess. The player can move all the
 * pieces through legal moves given that he is not {@link #inCheck()}
 * {@link #isStalemate()} or {@link #isCheckmate()}. Additionally the Player
 * cannot move a piece to a position such that the player is {@link #inCheck()}
 * as a result of the move.
 */
public final class Player {

	/**
	 * The color of the chess player determines which pieces he can move.
	 */
	private final Color color;

	/**
	 * The current game the player plays.
	 */
	private final Chronicle chronicle;

	/*
	 * Private Constructor.
	 */
	private Player(Color color, Chronicle chronicle) {
		this.color = color;
		this.chronicle = chronicle;
	}

	/**
	 * Returns a white player that moves pieces in the given game.
	 *
	 * @param chronicle the game the player is supposed to play.
	 * @return a white player
	 */
	public static Player white(Chronicle chronicle) {
		return new Player(Color.WHITE, chronicle);
	}

	/**
	 * Returns a black player that moves pieces in the given game.
	 *
	 * @param chronicle the game the player is supposed to play.
	 * @return a black player
	 */
	public static Player black(Chronicle chronicle) {
		return new Player(Color.BLACK, chronicle);
	}

	/**
	 * Returns the player color
	 *
	 * @return the color of the player
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns {@code true} if the player can successfully move a piece from the
	 * start coordinate to the end coordinate.
	 *
	 * @param start the start coordinate.
	 * @param end   the end coordinate.
	 * @return {@code true} if the move is valid.
	 */
	public boolean isValid(Tile start, Tile end) {
		Move move = createMove(chronicle, start, end);
		return nonNull(move) && !inCheck(move.result(), color);
	}

	/**
	 * Moves the piece from start to end if the given move is valid as indicated by
	 * {@link #isValid(Tile, Tile)}.
	 *
	 * @throws InvalidMoveException if there is no piece on the start position, if the piece
	 *                     has the wrong color if the move is invalid in principle
	 *                     for the piece on the board or if the player is in check
	 *                     as a result of the move.
	 */
	public void move(Tile start, Tile end) throws InvalidMoveException {
		Board current = chronicle.current();
		Piece piece = current.get(start);
		if (isNull(piece)) {
			throw new InvalidMoveException("There is no piece on start.");
		}
		if (piece.color() != color) {
			throw new InvalidMoveException("The player cannot move a piece of the opposite color.");
		}
		Move move = createMove(chronicle, start, end);
		if (isNull(move)) {
			throw new InvalidMoveException("The move is invalid for the given piece.");
		}
		if (inCheck(move.result(), color)) {
			throw new InvalidMoveException("Cannot move the piece. The player is in check!");
		}
		// If no prior exception occurred add the move to the chronicle
		chronicle.add(move);
	}

	/**
	 * Creates a move only if there can be a valid move from start to end. Otherwise
	 * {@code null} is returned.
	 *
	 * @param chronicle the current game position on whic
	 * @param start
	 * @param end
	 * @return
	 */
	private static Move createMove(Chronicle chronicle, Tile start, Tile end) {
		Board current = chronicle.current();
		Piece piece = current.get(start);

		// First try a simple move.
		Move move = SimpleMove.perform(start, end, current);

		if (isPawn(piece)) {
			// The move itself is null if the piece is a pawn.
			move = PawnMove.perform(start, end, current);

			if (isNull(move)) {
				move = PawnLeap.perform(start, end, current);
			}
			if (isNull(move)) {
				// An EnPassante move requires knowelege about the history of the game.
				move = EnPassante.perform(start, end, chronicle);
			}
		}
		if (isNull(move) && isKing(piece)) {
			// Try Rochade as last.
			move = Rochade.perform(start, end, chronicle);
		}
		return move;
	}

	/**
	 * Returns {@code true} if the king of the given player can be captured by enemy
	 * pieces on the next move.
	 *
	 * @return {@code true} if the king of the player is in check.
	 */
	public boolean inCheck() {
		return inCheck(chronicle.current(), color);
	}

	/**
	 * Returns true if the player of the given color is in check on the given Board.
	 * That is that an enemy piece can capture the king on the next move.
	 *
	 * @param board the current board position.
	 * @param color the color of the player
	 * @return true if the player of the given color is in check.
	 */
	public static boolean inCheck(Board board, Color color) {
		Board enemyPieces = board.filter(Piece.isOfColor(color.swap()));
		Board kingMapping = board.filter(Piece.isOfColor(color).and(Piece::isKing));
		Tile kingPosition = kingMapping.keySet().stream().findFirst().orElse(null);

		if (kingPosition == null) {
			return false;
		}
		for (Tile enemyPosition : enemyPieces.keySet()) {
			if (SimpleMove.isValid(enemyPosition, kingPosition, board)
					|| PawnMove.isValidCapture(enemyPosition, kingPosition, board)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the player cannot make a valid move that does not result with
	 * the king in check or the material on the board is insufficient to checkmate
	 * the other king.
	 *
	 * @return {@code true} if the player is unable to make a move that does not
	 *         result in check.
	 */
	public boolean isStalemate() {
		return !inCheck() && isMate();
	}

	/**
	 * A Player is mate if he is unable to make a valid move.
	 *
	 * @return true if the player cannot make any valid move false otherwise.
	 */
	private boolean isMate() {
		Board pieces = chronicle.current().filter(Piece.isOfColor(color));
		for (Tile piecePosition : pieces.keySet()) {
			if (canMove(piecePosition)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns a true if any given piece on the start coordinate can make at least
	 * one valid move.
	 *
	 * @param start the given start tile.
	 * @return true if any valid move from start is possible.
	 */
	private boolean canMove(Tile start) {
		for (Tile tile : Tile.values()) {
			if (isValid(start, tile)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the given player is checkmate. That means the capture of the
	 * king in the next move is inevitable.
	 *
	 * @return {@code true} if he player is checkmate.
	 */
	public boolean isCheckmate() {
		return inCheck() && isMate();
	}

	/**
	 * The two different chess colors black and white in an enum class.
	 */
	public enum Color {

		/**
		 * The color white.
		 */
		WHITE,

		/**
		 * The color black.
		 */
		BLACK;

		/**
		 * Switch between colors.
		 *
		 * @return the other color.
		 */
		public Color swap() {
			switch (this) {
			case BLACK:
				return WHITE;
			case WHITE:
				return BLACK;
			default:
				throw new RuntimeException();
			}
		}
	}

}
