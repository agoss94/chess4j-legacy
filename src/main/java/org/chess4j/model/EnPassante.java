package org.chess4j.model;

import java.util.Objects;
import java.util.Optional;

import org.chess4j.model.Player.Color;

/**
 * An EnPassante move can only occur after a PawnLeap which would result in an
 * enemy pawn losing the chance to capture the leaped pawn. The EnPassante move
 * offers this pawn the immediate chance to capture the leaped pawn as if it
 * only had moved one tile in the last move. As such the EnPassante move is the
 * only move where a piece captures another piece without moving to the tile on
 * which the captured piece resides.
 */
public class EnPassante implements Move {

	/**
	 * The start position of the move
	 */
	private final Tile start;

	/**
	 * The end position of the move
	 */
	private final Tile end;

	/**
	 * The former board position.
	 */
	private final Board initial;

	/**
	 * The later board position.
	 */
	private final Board result;

	/**
	 * the captured piece.
	 */
	private final Optional<Piece> captured;

	// Private constructor
	private EnPassante(Tile start, Tile end, Chronicle chronical) {
		this.start = Objects.requireNonNull(start);
		this.end = Objects.requireNonNull(end);
		Board initial = chronical.current();
		this.initial = Objects.requireNonNull(new EnumMapBoard(initial));
		Board board = setUpBoard(chronical.get(chronical.size() - 1));
		this.captured = Optional.of(board.get(end));
		board.put(end, board.remove(start));
		this.result = Objects.requireNonNull(board);
	}

	/**
	 * Static factory that returns a valid EnPassante move or {@code null} if none
	 * can be constructed on the given board from the given start to end tile.
	 *
	 * @param start     the start tile of the move.
	 * @param end       the end tile of the move.
	 * @param chronicle the given chronicle of the game.
	 * @return a valid constructed move or {@code null}.
	 */
	public static Move perform(Tile start, Tile end, Chronicle chronicle) {
		return isValid(start, end, chronicle) ? new EnPassante(start, end, chronicle) : null;
	}

	/**
	 * Checks if the EnPassante move with the given position is valid. A EnPassante
	 * can only occur directly after a PawnLeap of an enemy piece. If th
	 *
	 * @param chronicle the current gaming position.
	 * @param start     the start coordinate.
	 * @param end       the end coordinate.
	 * @return {@code true} if the move is valid.
	 */
	public static boolean isValid(Tile start, Tile end, Chronicle chronicle) {
		if (chronicle.isEmpty()) {
			return false;
		}
		Move lastMove = chronicle.get(chronicle.size() - 1);
		if (lastMove instanceof PawnLeap) {
			Board board = setUpBoard(lastMove);
			return PawnMove.isValidCapture(start, end, board);
		}
		return false;
	}

	private static Board setUpBoard(Move lastMove) {
		int dir = lastMove.moved().color() == Color.WHITE ? 1 : -1;
		int column = lastMove.end().column();
		int row = lastMove.end().row() - dir;
		Tile pawnLoc = Tile.valueOf(column, row);
		Board board = new EnumMapBoard(lastMove.result());
		board.put(pawnLoc, board.remove(lastMove.end()));
		return board;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tile start() {
		return start;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tile end() {
		return end;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Board initial() {
		return Utils.unmodifiable(initial);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Board result() {
		return Utils.unmodifiable(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Piece> captured() {
		return captured;
	}
}
