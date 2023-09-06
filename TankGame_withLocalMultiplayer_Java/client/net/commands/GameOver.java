package client.net.commands;

import base.Command;
import base.Communicator;
import base.Packet;
import client.game.ClientGameHandler;

/**
 * Command that handles game over on client
 *
 * @author John Gauci
 */

public class GameOver extends Command {
    /**
     * Handles the game over process for the client
     *
     * @param communicator the ClientGameHandler to operate upon
     * @param packet       unused
     * @author John Gauci
     */
    @Override
    public void execute(Communicator communicator, Packet packet) {
        ClientGameHandler clientGameHandler = (ClientGameHandler) communicator;
        clientGameHandler.notifyObservers();
        clientGameHandler.getController().handleGameOver();
    }
}
