package server.net.commands;

import base.Command;
import base.Communicator;
import base.Packet;
import game.tanks.TankShell;
import server.game.ServerGameHandler;

/**
 * Command that Moves a Tank Shell
 *
 * @author John Gauci
 */
public class MoveTankShell extends Command {

    private final TankShell tankShell;

    /**
     * Constructor for a new MoveTankShell command
     *
     * @param tankShell the tank shell to move
     * @author John Gauci
     */
    public MoveTankShell(TankShell tankShell) {
        this.tankShell = tankShell;
    }

    /**
     * Moves the tank shell that was provided upon construction of the command
     *
     * @param communicator the ServerGameHandler to operate upon
     * @param packet       unused
     * @author John Gauci
     * @author Kareem Khalidi
     */
    @Override
    public void execute(Communicator communicator, Packet packet) {
        ServerGameHandler serverGameHandler = (ServerGameHandler) communicator;
        tankShell.move(serverGameHandler);
    }
}
