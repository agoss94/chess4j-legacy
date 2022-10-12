package org.chess4j.model;

import java.util.Objects;

import org.chess4j.model.Player.Color;

public final class Pawn implements Piece {

	private final Color color;

	private Piece promotedPawn;

	/**
	 * @param color
	 */
	private Pawn(Color color) {
		this.color = color;
	}

	public static Pawn white() {
		return new Pawn(Color.WHITE);
	}

	public static Pawn black() {
		return new Pawn(Color.BLACK);
	}

	@Override
	public boolean isValid(Tile start, Tile end) {
		return promotedPawn == null ? false : promotedPawn.isValid(start, end);
	}

	public void promote(Type type) {
		Objects.requireNonNull(type);
		if (type == Type.PAWN || type == Type.KING) {
			throw new IllegalArgumentException("Cannot promote the pawn to the given type " + type);
		} else if (type == Type.QUEEN) {
			promotedPawn = color == Color.WHITE ? Queen.white() : Queen.black();
		} else if (type == Type.ROOK) {
			promotedPawn = color == Color.WHITE ? Rook.white() : Rook.black();
		} else if (type == Type.KNIGHT) {
			promotedPawn = color == Color.WHITE ? Knight.white() : Knight.black();
		} else if (type == Type.BISHOP) {
			promotedPawn = color == Color.WHITE ? Bishop.white() : Bishop.black();
		}
	}

	@Override
	public Type type() {
		return promotedPawn == null ? Type.PAWN : promotedPawn.type();
	}

	@Override
	public Color color() {
		return color;
	}

	@Override
	public String toString() {
		return promotedPawn == null ? color == Color.WHITE ? "\u2659" : "\u265F" : promotedPawn.toString();
	}
}
