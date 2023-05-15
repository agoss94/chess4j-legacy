package org.chess4j;

import java.util.Set;

import org.chess4j.Player.Color;
import org.chess4j.pieces.Piece;

/**
 * A chess game processes the player input and determines when the game is over.
 * The game class only processes the game logic therefore the input must be a
 * {@link Tile} object.
 */
public interface Game {

    /**
     * Returns the current board position.
     *
     * @return the current board position.
     */
    Board position();

    /**
     * Get the start input for the current move.
     *
     * @return the current start tile in focus.
     */
    Tile getStart();

    /**
     * Sets the given tile in focus for the start position of a move.
     *
     * @param start Sets the given start tile in focus.
     */
    void setStart(Tile start);

    /**
     * Get the start input for the current move.
     *
     * @return the current start tile in focus.
     */
    Tile getEnd();

    /**
     * Sets the given tile in focus for the end position of a move.
     *
     * @param end Sets the given start tile in focus.
     */
    void setEnd(Tile end);

    /**
     * Resets the input to {@code null}.
     */
    void reset();

    /**
     * Moves a piece from the given start to the end tile if the move is valid and
     * throws an {@link InvalidMoveException} or PawnNotPromotedException exception
     * otherwise.
     *
     * @throws InvalidMoveException     exception if the move is invalid.
     * @throws PawnNotPromotedException if a move is attemped although a pawn must
     *                                  be promoted first.
     */
    void move() throws InvalidMoveException, PawnNotPromotedException;

    /**
     * Returns the color of whose players turn it is.
     *
     * @return the color of the current players turn.
     */
    Color playersTurn();

    /**
     * Returns {@code true} if a pawn can be promoted in the game
     *
     * @return
     */
    boolean canBePromoted();

    /**
     * Has only
     *
     * @param piece
     */
    void promote(Piece.Type piece);

    /**
     * Returns a set of all reachable tile from the start position. If no start
     * position is currently in focus an empty set is returned.
     *
     * @return all reachable tiles.
     */
    Set<Tile> reachableTiles();

    /**
     * Returns {@code true} if the white player has won the game.
     *
     * @return {@code true} if the white player has won.
     */
    boolean hasWhitePlayerWon();

    /**
     * Returns {@code true} if the black player has won the game.
     *
     * @return {@code true} if the black player has won.
     */
    boolean hasBlackPlayerWon();

    /**
     * Returns {@code true} if the game ended in a stalemate.
     *
     * @return {@code true} if the game ended in a stalemate.
     */
    boolean isStalemate();

    /**
     * Returns {@code true} if the game ended by a threefold repetition.
     *
     * @return {@code true} if the game ended by a threefold repetition.
     */
    boolean isThreefoldRepetition();

    /**
     * Returns {@code true} if the fifty-move-rule applies.
     *
     * @return {@code true} if the fifty-move-rule applies.
     */
    boolean isFiftyMoveRule();

    /**
     * Returns {@code true} if the game is drawn by insufficient {@code false}
     * otherwise.
     *
     * @return {@code true} if the game is drawn by insufficient {@code false}
     *         otherwise.
     */
    boolean isDrawByInsufficientMaterial();

    /**
     * Returns the turn number
     *
     * @return the turn number.
     */
    int turnNumber();

    /**
     * Returns {@code true} if the game is over.
     *
     * @return {@code true} if the game is over.
     */
    default boolean gameOver() {
        return hasWhitePlayerWon() || hasBlackPlayerWon() || isStalemate() || isThreefoldRepetition()
                || isFiftyMoveRule() || isDrawByInsufficientMaterial();
    }

}
