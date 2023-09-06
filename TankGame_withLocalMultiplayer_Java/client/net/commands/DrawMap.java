package client.net.commands;

import base.Command;
import base.Communicator;
import base.Packet;
import client.game.ClientGameHandler;

/**
 * Command for drawing map
 *
 * @author John Gauci
 */

public class DrawMap extends Command {

    /**
     * Adds the map to the ClientGameHandler data to be drawn by observers
     *
     * @param communicator the ClientGameHandler to operate upon
     * @param packet       unused
     * @author John Gauci
     */
    @Override
    public void execute(Communicator communicator, Packet packet) {
        ClientGameHandler clientGameHandler = (ClientGameHandler) communicator;
        clientGameHandler.getMap().ifPresent(map -> clientGameHandler.getData().getEntities().add(map));
    }
}
