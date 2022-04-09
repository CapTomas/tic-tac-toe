package cz.muni.fi.pb162.hw01.impl;

import java.util.Arrays;

/**
 * Class which edits the Board to the desired format and prints
 *
 * @author Tomas Cap
 */
public class ArrayBoard implements BoardFormatter {
    /*............................ATTRIBUTES............................*/
    private final char[][] boardArray;
    private final int arraySize;

    /*...........................CONSTRUCTORS...........................*/
    /**
     * Instantiates a new Array board.
     *
     * @param size the size of the original board
     */
    public ArrayBoard(int size) {
        arraySize = size * 2 + 1;
        boardArray = new char[arraySize][arraySize];
        setEmptyArrayBoard();
    }

    /* ........................GETTERS & SETTERS........................*/
    private void setBoardArray(int row, int jump, char symbol) {
        for (int i = row; i < arraySize; i = i + 2) {
            for (int j = 0; j < arraySize; j = j + 1 + jump) {
                boardArray[i][j] = symbol;
            }
        }
    }

    /* ..........................OTHER METHODS..........................*/
    /**
     * Print board to stdout in required format.
     *
     * @param board the original board
     */
    public void printBoard(Board board) {
        System.out.println(format(board));
    }

    @Override
    public String format(Board board) {
        String formattedBoard;
        boardToArrayBoard(board);

        String[] lines = new String[arraySize];
        for (int i = 0; i < arraySize; i++) {
            lines[i] = Arrays.toString(boardArray[i]).replace(", ", "").replace("[", "").replace("]", "");
        }

        formattedBoard = Arrays.toString(lines).replace(", ", "\n").replace("[", "").replace("]", "");

        return formattedBoard;
    }

    private void boardToArrayBoard(Board board) {
        int count1 = 0;
        for (int i = 1; i < arraySize; i = i + 2) {
            int count2 = 0;
            for (int j = 1; j < arraySize; j = j + 2) {
                boardArray[i][j] = board.getSymbol(count1, count2);
                count2++;
            }
            count1++;
        }
    }

    private void setEmptyArrayBoard() {
        setBoardArray(0, 0, '-');
        setBoardArray(1, 0, ' ');
        setBoardArray(1, 1, '|');
    }

}

