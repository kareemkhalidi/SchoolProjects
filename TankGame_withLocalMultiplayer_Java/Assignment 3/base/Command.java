package base;

import java.io.Serializable;

/**
 * Abstract command class for any actions that may be sent and performed by server or client
 * communicators
 *
 * @author John Gauci
 */

public abstract class Command implements Serializable {

    /**
     * Executes the command
     *
     * @param communicator the Communicator to operate upon
     * @param packet       the Packet that was sent to the Communicator, if any
     * @author John Gauci
     */
    public abstract void execute(Communicator communicator, Packet packet);

}
