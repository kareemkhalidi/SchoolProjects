package server.net.commands;

import base.Command;
import base.Communicator;
import base.Packet;
import server.game.ServerGameHandler;

/**
 * Command that shuts down the server
 *
 * @author John Gauci
 */
public class ShutdownServer extends Command {

    /**
     * Shuts down the server
     *
     * @param communicator the ServerGameHandler to operate upon
     * @param packet       unused
     * @author John Gauci
     */
    @Override
    public void execute(Communicator communicator, Packet packet) {
        ServerGameHandler serverGameHandler = (ServerGameHandler) communicator;
        serverGameHandler.getConnectionHandler().stop();
        serverGameHandler.stop();
    }
}
