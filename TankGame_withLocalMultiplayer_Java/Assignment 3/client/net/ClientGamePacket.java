package client.net;

import base.Command;
import base.Packet;
import game.Player;

/**
 * Implements a ClientGamePacket used for sending a player alongside a packet.
 *
 * @author John Gauci
 */

public class ClientGamePacket extends Packet {

    private final Player player;

    /**
     * Initializes a ClientGamePacket with the given player and command
     *
     * @param player  the sending player
     * @param command the command
     * @author John Gauci
     */
    public ClientGamePacket(Player player, Command command) {
        super(command);
        this.player = player;

    }

    /**
     * Returns the player who sent this packet
     *
     * @return the sending player
     * @author John Gauci
     */
    public Player getPlayer() {
        return player;
    }
}
