package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.cmd.Messages;

/**
 * Utility class controlling the win and ending the game
 *
 * @author Tomas Cap
 */
public final class Win {

    /**
     * Checks the lines and diagonals for a winning sequence
     *
     * @param board   the board to check
     * @param winSize the size of the required winning sequence
     * @return true if there is a winning sequence on the board
     */
    public static boolean checkWin(Board board, int winSize) {
        Board sameBoard = new Board(board.getSize());
        Board rotatedBoard = new Board(board.getSize());

        sameBoard.copy(board);
        rotatedBoard.copy(board);
        rotatedBoard.rotate();

        return checkRow(sameBoard, winSize) || checkRow(rotatedBoard, winSize) ||
                checkDia(sameBoard, winSize) || checkDia(rotatedBoard, winSize);
    }

    private static boolean checkRow(Board board, int winSize) {
        for (int i = 0; i < board.getSize(); i++) {
            StringBuilder series = new StringBuilder();

            for (int j = 0; j < board.getSize(); j++) {
                series = symbolsInLine(series, board, i, j);

                if (series.length() == winSize) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkDia(Board board, int winSize) {
        for (int k = 0; k < board.getSize() * 2 - 1; k++) {
            StringBuilder series = new StringBuilder();

            for (int j = 0; j <= k; j++) {
                int i = k - j;
                if (i < board.getSize() && j < board.getSize()) {
                    series = symbolsInLine(series, board, i, j);

                    if (series.length() == winSize) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static StringBuilder symbolsInLine(StringBuilder series, Board board, int i, int j) {
        if (series.length() == 0 || series.charAt(series.length() - 1) != board.getSymbol(i, j)) {
            series = new StringBuilder();
        }

        if (series.length() == 0 || series.charAt(series.length() - 1) != ' ') {
            series.append(board.getSymbol(i, j));
        }

        return series;
    }

    /**
     * Prints the winning format on standard output.
     *
     * @param board  the board to print
     * @param symbol the symbol of the winning player
     */
    public static void printWin(Board board, char symbol) {
        System.out.printf(Messages.GAME_OVER, board.getCounter() - 1);
        board.printBoard();
        System.out.printf(Messages.GAME_WINNER, symbol);
    }

    /**
     * Prints the game over format on standard output.
     *
     * @param board the board to print
     */
    public static void printGameOver(Board board) {
        System.out.printf(Messages.GAME_OVER, board.getCounter() - 1);
        board.printBoard();
    }
}

