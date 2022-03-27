package cz.muni.fi.pb162.hw01.impl;

/**
 * For saving to history and calling from its backup
 *
 * @author Tomas Cap
 */
public class History {
    /*............................ATTRIBUTES............................*/
    private final int maxLength;
    private final int maxRewind;
    private int pointer = 0;
    private final Board[] history;


    /*...........................CONSTRUCTORS...........................*/
    /**
     * Instantiates a new History.
     *
     * @param size      the size of the original board
     * @param maxRewind the maximum number by which a player can return
     */
    public History(int size, int maxRewind) {
        maxLength = size * size + 1;
        this.maxRewind = maxRewind;
        history = new Board[maxLength];
        setEmpty(size);
    }

    /* ........................GETTERS & SETTERS........................*/
    public int getPointer() {
        return pointer;
    }

    public int getMaxRewind() {
        return maxRewind;
    }

    /* ..........................OTHER METHODS..........................*/
    /**
     * Creates a backup of the Board in history, at the location pointed to by the history pointer.
     *
     * @param board the original board at the moment
     */
    public void createBackup(Board board) {
        history[pointer].copy(board);
        pointer++;
    }

    /**
     * Copies the contents of the history backup to the Board and modifies the pointer by rewind.
     *
     * @param board  the Board to be overwritten
     * @param rewind the number by which the game moves into history
     */
    public void copyFromHistory(Board board, int rewind){
        pointer = pointer - rewind - 1;
        board.copy(history[pointer]);
        pointer++;
    }

    private void setEmpty(int size) {
        for (int i = 0; i < maxLength; i++) {
            history[i] = new Board(size);
        }
    }


}
