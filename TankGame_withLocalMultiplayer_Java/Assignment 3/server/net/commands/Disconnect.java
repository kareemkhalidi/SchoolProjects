package server.net.commands;

import base.Command;
import base.Communicator;
import base.Packet;
import client.net.ClientGamePacket;
import server.game.ServerGameHandler;

/**
 * Disconnect command that disconnects a player from the server
 *
 * @author John Gauci
 */
public class Disconnect extends Command {

    /**
     * Handles the disconnecting of a player from the server
     *
     * @param communicator the ServerGameHandler to operate upon
     * @param packet       the ClientGamePacket that was sent to the ServerGameHandler
     * @author John Gauci
     */
    @Override
    public void execute(Communicator communicator, Packet packet) {
        ServerGameHandler serverGameHandler = (ServerGameHandler) communicator;
        ClientGamePacket clientGamePacket = (ClientGamePacket) packet;
        var player = serverGameHandler.getPlayer(clientGamePacket.getPlayer());
        if (player.isPresent() && player.get().getPlayerStats().isHost()) {
            serverGameHandler.addCommand(new ShutdownServer());
        }
        serverGameHandler.getPlayers().remove(clientGamePacket.getPlayer());
        serverGameHandler.getPlayerStats().remove(clientGamePacket.getPlayer().getPlayerStats());
        serverGameHandler.getEntities().remove(clientGamePacket.getPlayer().getTank());
        serverGameHandler.closeSocket(packet.getReceiverID());
    }
}
