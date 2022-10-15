package org.chess4j.model;

import java.util.Objects;

import org.chess4j.model.Player.Color;

/**
 * A pawn is the most common piece in any chess game. Pawns are initially set
 * either on the second row (white pawns) or seventh row (black pawns). Also a
 * pawn can only move in one direction. If a pawn reaches the last row in the
 * enemy camp it is promoted to another piece. In this case the pawn functions
 * essentially as a wrapper which forwards the {@link #isValid(Tile, Tile)},
 * {@link #toString()} and {@link #type()} method of the wrapped piece in order
 * to act as if this pawn is the promoted piece. As a pawn the
 * {@link #isValid(Tile, Tile)} method returns always {@code false} because each
 * pawn movement in principle requires additional information about other pieces
 * on the board.
 * <p>
 * The class offers two static factory methods {@link #white()} and
 * {@link #black()} for constructing a pawn. Once created a pawn is immutable
 * and stateless. Also note that no two pawn are equal as the class does not
 * overwrite {@link #hashCode()} nor {@link #equals(Object)}.
 */
public final class Pawn implements Piece {

	/**
	 * The color of the pawn
	 */
	private final Color color;

	/**
	 * The promoted piece which is forwarded in the case of a promotion.
	 */
	private Piece promotedPawn;

	// Private constructor
	private Pawn(Color color) {
		this.color = color;
	}

	/**
	 * Static factory that returns a new white Pawn
	 * 
	 * @return a new white pawn
	 */
	public static Pawn white() {
		return new Pawn(Color.WHITE);
	}

	/**
	 * Static factory that returns a new black Pawn
	 * 
	 * @return a new black pawn
	 */
	public static Pawn black() {
		return new Pawn(Color.BLACK);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Pawn move is always {@code false} with the exception, that the pawn has been
	 * promoted. In this case the pawn forwards the implementation of the promoted
	 * piece.
	 * 
	 * @see #promote(org.chess4j.model.Piece.Type)
	 */
	@Override
	public boolean isValid(Tile start, Tile end) {
		Objects.requireNonNull(start);
		Objects.requireNonNull(end);
		return promotedPawn == null ? false : promotedPawn.isValid(start, end);
	}

	/**
	 * Promotes this pawn to either a {@link Type#QUEEN}, {@link Type#ROOK},
	 * {@link Type#BISHOP} or {@link Type#KNIGHT}. In all other cases an
	 * {@link IllegalArgumentException} is thrown.
	 * 
	 * @param type the type the pawn is promoted to.
	 * @throws IllegalArgumentException if any other type is passed in than {@link Type#QUEEN},
	 *            {@link Type#ROOK}, {@link Type#BISHOP} or {@link Type#KNIGHT}
	 */
	public void promote(Type type) {
		Objects.requireNonNull(type);
		switch (type) {
		case BISHOP:
			promotedPawn = color == Color.WHITE ? Bishop.white() : Bishop.black();
			break;
		case KNIGHT:
			promotedPawn = color == Color.WHITE ? Knight.white() : Knight.black();
			break;
		case QUEEN:
			promotedPawn = color == Color.WHITE ? Queen.white() : Queen.black();
			break;
		case ROOK:
			promotedPawn = color == Color.WHITE ? Rook.white() : Rook.black();
			break;
		default:
			throw new IllegalArgumentException("Cannot promote the pawn to the given type " + type);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Type type() {
		return promotedPawn == null ? Type.PAWN : promotedPawn.type();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Color color() {
		return color;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return promotedPawn == null ? color == Color.WHITE ? "\u2659" : "\u265F" : promotedPawn.toString();
	}
}
