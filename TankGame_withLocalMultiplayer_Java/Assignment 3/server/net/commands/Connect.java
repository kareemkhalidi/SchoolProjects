package server.net.commands;


import base.Command;
import base.Communicator;
import base.Packet;
import client.net.ClientGamePacket;
import client.net.commands.LoadNewMap;
import server.game.GameData;
import server.game.ServerGameHandler;

/**
 * Command for connecting to server
 *
 * @author John Gauci
 */

public class Connect extends Command {

    /**
     * Handles the connecting of a player to the server
     *
     * @param communicator the ServerGameHandler to operate upon
     * @param packet       the ClientGamePacket that was sent to the ServerGameHandler
     * @author John Gauci
     */
    @Override
    public void execute(Communicator communicator, Packet packet) {
        ServerGameHandler serverGameHandler = (ServerGameHandler) communicator;
        ClientGamePacket clientGamePacket = (ClientGamePacket) packet;
        serverGameHandler.addPlayer(clientGamePacket.getPlayer());
        GameData mapData = new GameData(serverGameHandler.getMapName());
        Packet mapPacket = new Packet(mapData, new LoadNewMap());
        serverGameHandler.sendUnique(clientGamePacket.getReceiverID(), mapPacket);
    }

}
