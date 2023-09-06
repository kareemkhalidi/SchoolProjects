package server.net.commands;

import base.Command;
import base.Communicator;
import base.Packet;
import client.net.commands.GameOver;
import server.game.ServerGameHandler;
import server.game.gamemodes.GameMode;

/**
 * Check game over command that checks if the game is over when executed
 *
 * @author John Gauci
 */
public class CheckGameOver extends Command {

    private final GameMode gameMode;

    /**
     * CheckGameOver command constructor
     *
     * @param gameMode the Game rules to check if the game is over
     * @author John Gauci
     */
    public CheckGameOver(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Checks to see if the game is over and ends it if so
     *
     * @param communicator the ServerGameHandler to operate upon
     * @param packet       unused
     * @author John Gauci
     */
    @Override
    public void execute(Communicator communicator, Packet packet) {
        ServerGameHandler serverGameHandler = (ServerGameHandler) communicator;
        if (!serverGameHandler.isGameOver() && gameMode.isOver((ServerGameHandler) communicator)) {
            serverGameHandler.setGameOver(true);
            serverGameHandler.sendAllSockets(new Packet(serverGameHandler.getData(), new GameOver()));
        }
    }
}
