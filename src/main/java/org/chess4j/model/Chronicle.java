package org.chess4j.model;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A chronicle is an ordered sequence of all moves in a chess game. All moved
 * must progressive logically linked. Meaning that for each new move added to
 * the chronicle the board position given through {@link Move#initial()} must
 * equal the result {@link Move#result()} of the previous move or else an
 * exception is thrown. Therefore each chronicle is created with an initial
 * position from which onwards the game logically progresses.
 */
public final class Chronicle extends AbstractList<Move> implements List<Move> {

	/**
	 * Internal list that holds all added moves that is forwarded.
	 */
	private final List<Move> game;

	/**
	 * The initial position at the start of the game.
	 */
	private final Board initial;

	/**
	 * Constructs a new chronicle with the given initial position.
	 *
	 * @param initial the initial position of the board.
	 */
	public Chronicle(Board initial) {
		game = new ArrayList<>();
		this.initial = Board.copy(Objects.requireNonNull(initial));
	}

	/**
	 * Constructs a new chronicle with the the moves contained in the given list. If
	 * the list is empty a chronicle with {@link Board#newGame()} is constructed.
	 *
	 * @param initial the initial position of the board.
	 */
	public Chronicle(List<? extends Move> chronicle) {
		Objects.requireNonNull(chronicle);
		if (chronicle.isEmpty()) {
			game = new ArrayList<>();
			this.initial = Board.copy(Objects.requireNonNull(Board.newGame()));
		} else {
			game = new ArrayList<>(chronicle);
			this.initial = Board.copy(chronicle.get(0).initial());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Move set(int index, Move element) {
		return game.set(index, element);
	}

	/**
	 * Adds a move to the game. An {@link IllegalStateException} is thrown if the
	 * initial position of the board does not match the end position of the previous
	 * move.
	 *
	 * @throws IllegalStateException if the initial position of the board does not
	 *                               match the end position of the previous move.
	 */
	@Override
	public void add(int index, Move element) {
		Objects.requireNonNull(element);
		if (current().equals(element.initial())) {
			game.add(index, element);
		} else {
			throw new IllegalArgumentException(
					"The initial board position of the move does not match the current board position of the game.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Move remove(int index) {
		return game.remove(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Move get(int index) {
		return game.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return game.size();
	}

	/**
	 * Returns the last entry of the list. This method must not return {@code null}
	 *
	 * @return the current position of the game.
	 */
	public Board current() {
		return isEmpty() ? initial : get(size() - 1).result();
	}

	/**
	 * Returns {@code true} if the piece has been moved {@code false} otherwise.
	 *
	 * @param piece the given piece.
	 * @return {@code true} if the piece has been moved {@code false} otherwise.
	 */
	public boolean hasBeenMoved(Piece piece) {
		Objects.requireNonNull(piece);
		return stream().anyMatch(m -> m.moved().equals(piece));
	}

	/**
	 * Reverts the last move of the game. Returns the reverted Move or null if none
	 * is revertable.
	 */
	public Move revert() {
		return isEmpty() ? null : remove(size() - 1);
	}
}
