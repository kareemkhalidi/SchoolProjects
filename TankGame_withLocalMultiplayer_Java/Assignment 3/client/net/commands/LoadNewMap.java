package client.net.commands;

import base.Command;
import base.Communicator;
import base.Packet;
import client.game.ClientGameHandler;
import game.Map;
import server.game.GameData;

/**
 * Command that loads a new map to the client when executed
 *
 * @author John Gauci
 */
public class LoadNewMap extends Command {

    /**
     * Loads and sets the given map on the client
     *
     * @param communicator the ClientGameHandler to operate upon
     * @param packet       unused
     * @author John Gauci
     */
    @Override
    public void execute(Communicator communicator, Packet packet) {
        ClientGameHandler clientGameHandler = (ClientGameHandler) communicator;
        if (packet.getData().isPresent()) {
            GameData gameData = (GameData) packet.getData().get();
            Map map = new Map(gameData.getMapName());
            clientGameHandler.setMap(map);
        }
    }
}
