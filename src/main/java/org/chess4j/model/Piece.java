package org.chess4j.model;

import java.util.Objects;
import java.util.function.Predicate;

import org.chess4j.model.Player.Color;

/**
 * A piece consists of a color, a type. Depending on the type different moves
 * are valid given a start and end position. As a chess game consists of many
 * distinct pieces of equal color and type no two pieces are equal. This is
 * reflected by the fact, that implementations of this class should <b>not</b> override
 * {@link #hashCode()} or {@link #equals(Object)}.
 */
public interface Piece {

	/**
	 * Get the type of the piece
	 *
	 * @return the type of the piece.
	 */
	Type type();

	/**
	 * Get the color of the piece.
	 *
	 * @return the color of the piece.
	 */
	Color color();

	/**
	 * Determines if the move is valid for the piece. Note that the piece has no
	 * knowledge of the surrounding pieces on a board and therefore can make no
	 * guarantee that the move would be valid in any given situation.
	 *
	 * @param start position of the piece at the beginning.
	 * @param end   position of the piece at the end.
	 * @return true is the move is valid false otherwise.
	 * @throws NullPointerException if start or end is {@code null}
	 */
	boolean isValid(Tile start, Tile end);

	/**
	 * Returns {@code true} if the given piece is of type {@link Type#ROOK}. This
	 * method returns false for null values.
	 *
	 * @return {@code true} if the piece is of type {@link Type#ROOK}.
	 */
	public static boolean isRook(Piece piece) {
		return piece == null ? false : piece.type() == Type.ROOK;
	}

	/**
	 * Returns {@code true} if the given piece is of type {@link Type#KNIGHT}. This
	 * method returns false for null values.
	 *
	 * @return {@code true} if the piece is of type {@link Type#KNIGHT}.
	 */
	public static boolean isKnight(Piece piece) {
		return piece == null ? false : piece.type() == Type.KNIGHT;
	}

	/**
	 * Returns {@code true} if the given piece is of type {@link Type#BISHOP}. This
	 * method returns false for null values.
	 *
	 * @return {@code true} if the piece is of type {@link Type#BISHOP}.
	 */
	public static boolean isBishop(Piece piece) {
		return piece == null ? false : piece.type() == Type.BISHOP;
	}

	/**
	 * Returns {@code true} if the given piece is of type {@link Type#QUEEN}. This
	 * method returns false for null values.
	 *
	 * @return {@code true} if the piece is of type {@link Type#QUEEN}.
	 */
	public static boolean isQueen(Piece piece) {
		return piece == null ? false : piece.type() == Type.QUEEN;
	}

	/**
	 * Returns {@code true} if the given piece is of type {@link Type#KING}. This
	 * method returns false for null values.
	 *
	 * @return {@code true} if the piece is of type {@link Type#KING}.
	 */
	public static boolean isKing(Piece piece) {
		return piece == null ? false : piece.type() == Type.KING;
	}

	/**
	 * Returns a predicate which tests a piece for the given color.
	 *
	 * @param color the color that the piece is tested for.
	 * @return a predicate that checks the color
	 */
	public static Predicate<Piece> isOfColor(Color color) {
		return p -> {
			return p == null ? false : p.color() == color;
		};
	}

	/**
	 * Returns {@code true} if the given piece is of type {@link Type#PAWN}. This
	 * method returns false for null values.
	 *
	 * @return {@code true} if the piece is of type {@link Type#PAWN}.
	 */
	public static boolean isPawn(Piece piece) {
		return piece == null ? false : piece.type() == Type.PAWN;
	}

	/**
	 * Returns true if both given pieces are of opposite colors.
	 *
	 * @param first  the first given piece.
	 * @param second the second given piece.
	 * @throws NullPointerException if any of the given parameters is {@code null}
	 * @return {@code true} if the color of the first piece does not match the color
	 *         of the second.
	 */
	public static boolean isOpposite(Piece first, Piece second) {
		Objects.requireNonNull(first);
		Objects.requireNonNull(second);
		return first.color() != second.color();
	}

	/**
	 * Returns the UTF-8 Unicode of the piece depending on the {@link #color()} and
	 * {@link #type()} of the piece.
	 */
	@Override
	String toString();

	/**
	 * All types of chess pieces that can be placed on a board.
	 */
	enum Type {

		/**
		 * Type PAWN
		 */
		PAWN,

		/**
		 * Type ROOK
		 */
		ROOK,

		/**
		 * Type KNIGHT
		 */
		KNIGHT,

		/**
		 * Type BISHOP
		 */
		BISHOP,

		/**
		 * Type QUEEN
		 */
		QUEEN,

		/**
		 * Type KING
		 */
		KING;
	}
}
