package cz.muni.fi.pb162.hw01.impl;

import com.beust.jcommander.Parameter;
import cz.muni.fi.pb162.hw01.cmd.CommandLine;
import cz.muni.fi.pb162.hw01.cmd.Messages;

/**
 * Application class represents the command line interface of this application.
 * <p>
 * You are expected to implement the {@link Application#run()} method
 *
 * @author jcechace
 */
public class Application {
    @Parameter(names = {"--size", "-s"})
    private int size = 3;

    @Parameter(names = {"--win", "-w"})
    private int win = 3;

    @Parameter(names = {"--history", "-h"})
    private int history = 1;

    @Parameter(names = {"--players", "-p"})
    private String players = "xo";

    @Parameter(names = "--help", help = true)
    private boolean showUsage = false;

    /**
     * Application entry point
     *
     * @param args command line arguments of the application
     */
    public static void main(String[] args) {
        Application app = new Application();

        CommandLine cli = new CommandLine(app);
        cli.parseArguments(args);

        if (app.showUsage) {
            cli.showUsage();
        } else {
            app.run();
        }
    }

    /**
     * Application runtime logic
     */
    private void run() {
        checkArgs();
//        Creates a playing field
        Board board = new Board(size, history);
        System.out.printf(Messages.TURN_COUNTER, board.getCounter());
        board.printBoard();

//        Creates players
        Player[] allPlayers = new Player[players.length()];
        for (int i = 0; i < players.length(); i++) {
            allPlayers[i] = new Player(players.charAt(i), board, win);
        }

//        Starts the game
        boolean playing = true;
        while (playing) {
            for (int j = 0; j < players.length(); j++) {
                playing = allPlayers[j].play();

                if (!playing) {
                    break;
                }
            }
        }
    }

    private void checkArgs() {
        if (size < 3) {
            size = 3;
        }
        if (win < 3 || win > size) {
            win = 3;
        }
        if (history <= 0 || history >= size * size) {
            history = 1;
        }
        if (players.length() <= 1) {
            players = "xo";
        }
    }
}
