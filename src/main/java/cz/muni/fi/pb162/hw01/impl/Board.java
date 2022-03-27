package cz.muni.fi.pb162.hw01.impl;

/**
 * To create a game field and work with it.
 *
 * @author Tomas Cap
 */
public class Board {
    /*............................ATTRIBUTES............................*/
    private final int size;
    private final char[][] symbols;
    private int counter = 1;
    private History history;

    /*...........................CONSTRUCTORS...........................*/

    /**
     * Instantiates a new empty Board .
     *
     * @param size      the size of the board
     * @param maxRewind the maximum number by which a player can return
     */
    public Board(int size, int maxRewind) {
        this.size = size;
        this.symbols = new char[size][size];
        history = new History(size, maxRewind);
        setEmpty();
    }

    /**
     * Instantiates a new empty Board without rewind value.
     *
     * @param size the size of the board
     */
    public Board(int size) {
        this.size = size;
        this.symbols = new char[size][size];
        setEmpty();
    }

    /* ........................GETTERS & SETTERS........................*/
    public int getSize() {
        return size;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public History getHistory() {
        return history;
    }

    /**
     * Gets symbol from the specific coordinates.
     *
     * @param x the x coordinate of the board
     * @param y the y coordinate of the board
     * @return the character on given coordinates
     */
    public char getSymbol(int x, int y) {
        return symbols[x][y];
    }

    /**
     * Sets given symbol on specific position.
     *
     * @param x      the x coordinate of the board
     * @param y      the y coordinate of the board
     * @param symbol the character to be entered
     */
    public void setSymbol(int x, int y, char symbol) {
        symbols[x][y] = symbol;
    }

    /* ..........................OTHER METHODS..........................*/

    /**
     * Use the ArrayBoard to print the Board to the standard output.
     */
    public void printBoard() {
        new ArrayBoard(size).printBoard(this);
        counter++;
    }

    /**
     * Sets each Board field to a space character, i.e. blank.
     */
    public void setEmpty() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                symbols[i][j] = ' ';
            }
        }
    }

    /**
     * Checks if each Board field is filled with symbol.
     *
     * @return true if board is full
     */
    public boolean checkFullness() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (symbols[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Copies all characters from the Board in the argument to the current.
     *
     * @param origin Board from which it takes characters
     */
    public void copy(Board origin) {
        for (int i = 0; i < origin.getSize(); i++) {
            for (int j = 0; j < origin.getSize(); j++) {
                this.setSymbol(i, j, origin.getSymbol(i, j));
            }
        }
    }

    /**
     * Rotates the field by 90 degrees.
     */
    public void rotate() {
        int n = this.getSize() - 1;

        for (int i = 0; i < this.getSize() / 2; i++) {
            for (int j = i; j < n - i; j++) {

                char temp = this.getSymbol(i, j);
                this.setSymbol(i, j, this.getSymbol(n - j, i));
                this.setSymbol(n - j, i, this.getSymbol(n - i, n - j));
                this.setSymbol(n - i, n - j, this.getSymbol(j, n - i));
                this.setSymbol(j, n - i, temp);
            }
        }
    }

}
