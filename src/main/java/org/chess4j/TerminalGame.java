package org.chess4j;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.chess4j.exceptions.InvalidMoveException;
import org.chess4j.exceptions.PawnNotPromotedException;
import org.chess4j.pieces.Piece.Type;

/**
 * Runner class for starting a Terminal chess game.
 */
public final class TerminalGame {

    /**
     * Pattern for user input
     */
    private static final Pattern ALGEBRAIC_NOTATION = Pattern.compile("[a-h]\\d-[a-h]\\d");

    /**
     * Separator for user input
     */
    private static final Pattern SEPARATOR = Pattern.compile("-");

    /**
     * Main method for starting a terminal game.
     */
    public static void main(String[] args) {
    	System.out.println("file.encoding=" + System.getProperty("file.encoding"));

        Game game = new SimpleGame();
        Scanner in = new Scanner(System.in);
        printBanner();
        System.out.println(game.position());
        System.out.println(String.format("%nMake a move! %n"));
        while (!game.gameOver()) {
            printTurnNumber(game);
            playTurn(game, in);
        }
        in.close();

        printGameEndedMessage(game);
    }

    /**
     * Prints the turn number to the console
     */
    private static void printTurnNumber(Game game) {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println(String.format("\t\t\t\tTurn %d", game.turnNumber() + 1));
        System.out.println("--------------------------------------------------------------------------");
    }

    /**
     * Prints an appropriate message at the end of the game.
     */
    private static void printGameEndedMessage(Game game) {
        if (game.hasWhitePlayerWon()) {
            System.out.println("Checkmate! The white player has won.");
        } else if (game.hasBlackPlayerWon()) {
            System.out.println("Checkmate! The black player has won.");
        } else if (game.isStalemate()) {
            System.out.println("Stalemate! Neither player wins.");
        } else if (game.isThreefoldRepetition()) {
            System.out.println("Draw by threefold repetition! Neither player wins.");
        } else if (game.isFiftyMoveRule()) {
            System.out.println("Draw by fifty-move-rule! Neither player wins.");
        } else if (game.isDrawByInsufficientMaterial()) {
            System.out.println("Draw! The material on the board is insuffiecient to checkmate a player.");
        }
    }

    /**
     * Processes standard input for a complete turn
     *
     * @param game the current game
     * @param in   standard input scanner.
     */
    private static void playTurn(Game game, Scanner in) {
        String input = in.nextLine();
        Matcher matcher = ALGEBRAIC_NOTATION.matcher(input);
        if (matcher.find()) {
            String matchedResult = matcher.group();
            String[] tilesByName = SEPARATOR.split(matchedResult);
            game.setStart(Tile.valueOf(tilesByName[0]));
            game.setEnd(Tile.valueOf(tilesByName[1]));
            try {
                game.move();

                promotePawn(game, in);
                System.out.println(game.position());
            } catch (InvalidMoveException | PawnNotPromotedException e) {
                System.out.println(e.getMessage());
                System.out.println("The input is invalid try again.");
            }
        } else {
            System.out.println(
                    "Could not understand input. The input must conform to a specific format (for example e2-e4)");
        }
    }

    /**
     * Promts the player for input in case of a pawn promotion.
     *
     * @param game the current game.
     * @param in   input scanner.
     */
    private static void promotePawn(Game game, Scanner in) {
        // Promote Pawn if possible
        while (game.canBePromoted()) {
            System.out.println("Promote Pawn (Q = Queen, B = Bishop, N = Knight, R = Rook):");
            String promotionType = in.nextLine();
            if (promotionType.equals("Q")) {
                game.promote(Type.QUEEN);
            } else if (promotionType.equals("B")) {
                game.promote(Type.BISHOP);
            } else if (promotionType.equals("N")) {
                game.promote(Type.KNIGHT);
            } else if (promotionType.equals("R")) {
                game.promote(Type.ROOK);
            } else {
                System.out.println("Illegal input.");
            }
        }
    }

    /**
     * Prints a banner at the start of a new game.
     */
    private static void printBanner() {
        System.out.println("\t\t\t+------------------------+");
        System.out.println("\t\t\t|        Chess4j         |");
        System.out.println("\t\t\t+------------------------+");
        System.out.println();
    }

}
