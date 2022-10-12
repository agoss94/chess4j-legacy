package org.chess4j.model;

import java.util.Collections;
import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Enum class thats hold all possible tile coordinates of a 8x8 chess board. The
 * column is indicated by the first letter and the row by the number following
 * the letter. A tile is regularly called a coordinate and both terms are used
 * interchangeably.
 */
public enum Tile {

    a8(1, 8), b8(2, 8), c8(3, 8), d8(4, 8), e8(5, 8), f8(6, 8), g8(7, 8), h8(8, 8),

    a7(1, 7), b7(2, 7), c7(3, 7), d7(4, 7), e7(5, 7), f7(6, 7), g7(7, 7), h7(8, 7),

    a6(1, 6), b6(2, 6), c6(3, 6), d6(4, 6), e6(5, 6), f6(6, 6), g6(7, 6), h6(8, 6),

    a5(1, 5), b5(2, 5), c5(3, 5), d5(4, 5), e5(5, 5), f5(6, 5), g5(7, 5), h5(8, 5),

    a4(1, 4), b4(2, 4), c4(3, 4), d4(4, 4), e4(5, 4), f4(6, 4), g4(7, 4), h4(8, 4),

    a3(1, 3), b3(2, 3), c3(3, 3), d3(4, 3), e3(5, 3), f3(6, 3), g3(7, 3), h3(8, 3),

    a2(1, 2), b2(2, 2), c2(3, 2), d2(4, 2), e2(5, 2), f2(6, 2), g2(7, 2), h2(8, 2),

    a1(1, 1), b1(2, 1), c1(3, 1), d1(4, 1), e1(5, 1), f1(6, 1), g1(7, 1), h1(8, 1);

    // +------------------------------+
    // | class fields and definitions |
    // +------------------------------+

    /**
     * The column number of the position.,
     */
    private final int column;

    /**
     * The row number of the position
     */
    private final int row;

    /**
     * Private constructor
     *
     * @param column the column number
     * @param row    the row number
     */
    Tile(int column, int row) {
        this.column = column;
        this.row = row;
    }

    /**
     * Static factory returns a position with the specifies column and row number.
     *
     * @param column the column number
     * @param row    the row number
     * @return the position with the the specifies row and column number.
     * @throws NoSuchElementException if no position with row or column number
     *                                exists.
     */
    public static Tile valueOf(int column, int row) {
        String tileName = String.valueOf((char) (96 + column)) + row;
        return Tile.valueOf(tileName);
    }

    /**
     * Returns the row number of the position.
     *
     * @return the row number of the position.
     */
    public int row() {
        return row;
    }

    /**
     * Returns the column number of the position.
     *
     * @return the column number of the position.
     */
    public int column() {
        return column;
    }

    /**
     * Returns a set of all coordinates that lie between the start and end position
     * under the condition that both form a straight or diagonal line otherwise an
     * empty set is returned. Start and end coordinates are from the set excluded.
     *
     * @param start the start coordinate.
     * @param end   the end coordinate.
     * @return all the coordinates in between start and end.
     */
    public static Set<Tile> path(Tile start, Tile end) {
        int deltaColumn = end.column() - start.column();
        int deltaRow = end.row() - start.row();
        boolean isLine = Math.abs(deltaColumn) == Math.abs(deltaRow) || deltaColumn == 0 || deltaRow == 0;
        if (isLine) {
            int length = Math.max(Math.abs(deltaColumn), Math.abs(deltaRow));
            int dirColumn = Integer.signum(deltaColumn);
            int dirRow = Integer.signum(deltaRow);
            Set<Tile> path = EnumSet.noneOf(Tile.class);
            for (int i = 1; i < length; i++) {
                path.add(Tile.valueOf(start.column() + i * dirColumn, start.row() + i * dirRow));
            }
            return path;
        } else {
            return Collections.emptySet();
        }
    }
}
