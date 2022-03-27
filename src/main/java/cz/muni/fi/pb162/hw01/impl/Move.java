package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.Utils;
import cz.muni.fi.pb162.hw01.cmd.Messages;

/**
 * Class to get the move, verify it and work with it.
 *
 * @author Tomas Cap
 */
public class Move {
    /*............................ATTRIBUTES............................*/
    private final String move;
    private final char symbol;
    private final Board board;
    private int rewind = 0;

    /*...........................CONSTRUCTORS...........................*/
    /**
     * Instantiates a new Move.
     *
     * @param symbol symbol of the calling player
     * @param board  the board to play on
     */
    public Move(char symbol, Board board) {
        this.symbol = symbol;
        this.board = board;
        move = getInput();
    }

    /* ........................GETTERS & SETTERS........................*/
    public String getMove() {
        return move;
    }

    public int getRewind() {
        return rewind;
    }

    public void setRewind(int rewind) {
        this.rewind = rewind;
    }

    /* ..........................OTHER METHODS..........................*/
    private String getInput() {
        boolean resume = false;
        String turn = null;

        while (!resume) {
            System.out.printf(Messages.TURN_PROMPT, symbol);
            turn = Utils.readLineFromStdIn();

            if (checkQuit(turn)) {
                return Integer.toString(0);
            }

            if (!checkRewind(turn)) {
                resume = checkValidCommand(turn) && checkLegalPlay(turn);
            } else if (rewind == 0){
                System.out.println(Messages.ERROR_REWIND);
            } else {
                return Integer.toString(rewind);
            }
        }
        return turn;
    }

    /**
     * Creates an array of coordinates from a play-type move.
     *
     * @param turn move coordinates entered as String
     * @return move coordinates as an array[2]
     */
    public static int[] strToIntArray(String turn) {
        int[] turns = new int[2];
        turns[0] = Character.getNumericValue(turn.charAt(0));
        turns[1] = Character.getNumericValue(turn.charAt(2));
        return turns;
    }

    private boolean checkValidCommand(String turn) {
        if (turn.length() == 3 && turn.charAt(1) == ' ' &&
                Character.isDigit(turn.charAt(0)) && Character.isDigit(turn.charAt(2))) {
            return true;
        }
        System.out.println(Messages.ERROR_INVALID_COMMAND);
        return false;
    }

    private boolean checkLegalPlay(String turn) {
        int[] turns = strToIntArray(turn);
        if (Character.getNumericValue(turn.charAt(0)) < board.getSize() &&
                Character.getNumericValue(turn.charAt(2)) < board.getSize() &&
                board.getSymbol(turns[0], turns[1]) == ' ') {
            return true;
        }
        System.out.println(Messages.ERROR_ILLEGAL_PLAY);
        return false;
    }

    private boolean checkRewind(String turn) {

        if (turn.length() != 3 || turn.charAt(0) != '<' && turn.charAt(1) != '<'
                || !Character.isDigit(turn.charAt(2))){
            return false;
        }

        rewind = Character.getNumericValue(turn.charAt(2));

        if (rewind >= board.getHistory().getPointer() || rewind > board.getHistory().getMaxRewind()){
            rewind = 0;
        }

        return true;
    }

    private boolean checkQuit(String turn) {
        if (turn.length() != 2 || turn.charAt(0) != ':' || turn.charAt(1) != 'q') {
            return false;
        }
        System.out.println(Messages.TURN_DELIMITER);
        board.setCounter(board.getCounter() - 1);
        Win.printGameOver(board);
        return true;
    }

}

