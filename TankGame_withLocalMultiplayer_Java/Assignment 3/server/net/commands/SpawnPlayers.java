package server.net.commands;

import base.Command;
import base.Communicator;
import base.Packet;
import server.game.ServerGameHandler;

/**
 * Command that spawns the players
 *
 * @author John Gauci
 */
public class SpawnPlayers extends Command {

    /**
     * Executes the command by calling spawn player on all players in the server game handler
     *
     * @param communicator the ServerGameHandler to operate upon
     * @param packet       unused
     * @author John Gauci
     */
    @Override
    public void execute(Communicator communicator, Packet packet) {
        ServerGameHandler serverGameHandler = (ServerGameHandler) communicator;
        serverGameHandler.getPlayers().forEach(player -> serverGameHandler.spawnPlayer(player, true));
    }

}
