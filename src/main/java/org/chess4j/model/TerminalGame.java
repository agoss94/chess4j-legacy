package org.chess4j.model;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.chess4j.model.Piece.Type;

public final class TerminalGame {

    private static final Pattern ALGEBRAIC_NOTATION = Pattern.compile("[a-h]\\d-[a-h]\\d");

    private static final Pattern SEPARATOR = Pattern.compile("-");

    public static void main(String[] args) {
        Game game = new SimpleGame();
        Scanner in = new Scanner(System.in);
        printBanner();
        System.out.println(game.position());
        System.out.println(String.format("%nMake a move! %n"));
        while (!game.gameOver()) {
            System.out.println("--------------------------------------------------------------------------");
            System.out.println(String.format("\t\t\t\tTurn %d", game.turnNumber() + 1));
            System.out.println("--------------------------------------------------------------------------");
            String input = in.nextLine();
            Matcher matcher = ALGEBRAIC_NOTATION.matcher(input);
            if (matcher.find()) {
                String matchedResult = matcher.group();
                String[] tilesByName = SEPARATOR.split(matchedResult);
                game.setStart(Tile.valueOf(tilesByName[0]));
                game.setEnd(Tile.valueOf(tilesByName[1]));
                try {
                    game.move();

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
        in.close();

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
        }
    }

    private static void printBanner() {
        System.out.println("\t\t\t+------------------------+");
        System.out.println("\t\t\t|        Chess4j         |");
        System.out.println("\t\t\t+------------------------+");
        System.out.println();
    }

}
