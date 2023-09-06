package server.net.commands;

import base.Command;
import base.Communicator;
import base.Direction;
import base.Packet;
import client.net.ClientGamePacket;
import game.Player;
import server.game.ServerGameHandler;


/**
 * Command for moving player tank
 *
 * @author John Gauci
 */

public class MoveTank extends Command {

    private final Direction direction;

    /**
     * Initializes a new MoveTank command with the given direction
     *
     * @param direction direction that player tank moved
     */
    public MoveTank(Direction direction) {
        this.direction = direction;
    }

    /**
     * Handles the moving of a player
     *
     * @param communicator the ServerGameHandler to operate upon
     * @param packet       the ClientGamePacket that was sent to the ServerGameHandler
     * @author John Gauci
     * @author Kareem Khalidi
     */
    @Override
    public void execute(Communicator communicator, Packet packet) {
        ServerGameHandler serverGameHandler = (ServerGameHandler) communicator;
        ClientGamePacket clientGamePacket = (ClientGamePacket) packet;
        Player serverPlayer = serverGameHandler.getPlayer(clientGamePacket.getPlayer()).orElseThrow();
        serverPlayer.getTank().move(serverPlayer, serverGameHandler, direction);
    }
}
