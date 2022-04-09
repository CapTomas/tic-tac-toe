package cz.muni.fi.pb162.hw01.impl;

import cz.muni.fi.pb162.hw01.cmd.Messages;

/**
 * Class determining the players and their play.
 *
 * @author Tomas Cap
 */
public class Player {
    /*............................ATTRIBUTES............................*/
    private final char symbol;
    private final int winSize;
    private final Board board;
    private Move move;

    /*...........................CONSTRUCTORS...........................*/
    /**
     * Instantiates a new Player.
     *
     * @param symbol  the symbol of the player
     * @param board   the board to play on
     * @param winSize sequence of identical characters needed to win
     */
    public Player(char symbol, Board board, int winSize) {
        this.symbol = symbol;
        this.winSize = winSize;
        this.board = board;
    }

    /* ........................GETTERS & SETTERS........................*/

    public Board getBoard() {
        return board;
    }

    /* ..........................OTHER METHODS..........................*/

    /**
     * The player receives a turn and plays it, checking for any termination conditions.
     *
     * @return true if another player can continue with his move
     */
    public boolean play() {
        move = new Move(symbol, board);
        if (move.getMove().length() != 1) {
            playMove(move.getMove());
            board.getHistory().createBackup(board);
        }

        if (checkEnd()) {
            return false;
        }

        System.out.printf(Messages.TURN_COUNTER, board.getCounter());
        if (move.getMove().length() == 1){
            board.getHistory().copyFromHistory(board, move.getRewind());
        }

        board.printBoard();
        return true;
    }

    private void playMove(String turn) {
        if (turn.length() == 3) {
            int[] turns = Move.strToIntArray(turn);
            board.setSymbol(turns[0], turns[1], symbol);
            move.setRewind(0);
        } else {
            move.setRewind(Integer.parseInt(turn));
        }
        System.out.println(Messages.TURN_DELIMITER);
    }

    private boolean checkEnd() {
        if (move.getMove().length() == 1 && Integer.parseInt(move.getMove()) == 0) {
            return true;
        }

        if (Win.checkWin(board, winSize)) {
            Win.printWin(board, symbol);
            return true;
        }

        if (board.checkFullness()) {
            Win.printGameOver(board);
            return true;
        }
        return false;
    }


}





