package server.net.commands;

import base.Command;
import base.Communicator;
import base.Packet;
import client.net.ClientGamePacket;
import game.Player;
import server.game.ServerGameHandler;

/**
 * Command to shoot from a tank.
 *
 * @author John Gauci
 */
public class Shoot extends Command {

    /**
     * Handles the shooting of a player's tank
     *
     * @param communicator the ServerGameHandler to operate upon
     * @param packet       the ClientGamePacket that was sent to the ServerGameHandler
     * @author John Gauci
     */
    @Override
    public void execute(Communicator communicator, Packet packet) {
        ServerGameHandler serverGameHandler = (ServerGameHandler) communicator;
        ClientGamePacket clientGamePacket = (ClientGamePacket) packet;
        Player serverPlayer = serverGameHandler.getPlayer(clientGamePacket.getPlayer()).orElseThrow();
        serverPlayer.getTank().getWeapon().fire(serverPlayer, serverGameHandler);
    }
}
