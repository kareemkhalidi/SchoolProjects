package base;

import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Abstract communicator class for tick-based processing of packets and commands and managing
 * sockets
 *
 * @author John Gauci
 */

public abstract class Communicator extends Subject {

    private final long tickFrequency;
    private final int tickRate;

    private final LinkedBlockingQueue<Packet> receivedPackets;

    private final LinkedBlockingQueue<Command> commands;

    private final Vector<RobustSocket> sockets;

    /**
     * Initializes a communicator with the given tick rate
     *
     * @param tickRate the given tick rate for the communicator to process at
     * @author John Gauci
     */
    public Communicator(int tickRate) {
        this.tickFrequency = (long) (1.0 / (tickRate / 1000.0));
        this.tickRate = tickRate;
        receivedPackets = new LinkedBlockingQueue<>();
        commands = new LinkedBlockingQueue<>();
        sockets = new Vector<>();
    }

    /**
     * Initializes a communicator with a default tick rate of 120
     *
     * @author John Gauci
     */
    public Communicator() {
        this(120);
    }

    /**
     * Returns the tick rate of the Communicator
     *
     * @return tick rate
     * @author John Gauci
     */
    public int getTickRate() {
        return tickRate;
    }

    /**
     * Returns the received packets sent by the Communicator's sockets
     *
     * @return received packets
     * @author John Gauci
     */
    public LinkedBlockingQueue<Packet> getReceivedPackets() {
        return receivedPackets;
    }

    /**
     * Adds a socket that can be used to send and receive packets from
     *
     * @param socket socket to be added
     * @author John Gauci
     */
    public void addSocket(RobustSocket socket) {
        sockets.add(socket);
    }

    /**
     * Adds the given packet to the queue of received packets
     *
     * @param packet received packet
     * @author John Gauci
     */
    public void addPacket(Packet packet) {
        receivedPackets.add(packet);
    }

    /**
     * Calls process tick at the specified tick rate in a loop
     *
     * @author John Gauci
     */
    @Override
    public final void loopExecute() {
        try {
            long timeStart = System.currentTimeMillis();
            processTick();
            long timeEnd = System.currentTimeMillis();
            long sleepTime = tickFrequency - (timeEnd - timeStart);
            if (sleepTime >= 0) Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends the given packet to all added sockets
     *
     * @param packet packet to be sent
     * @author John Gauci
     */
    public void sendAllSockets(Packet packet) {
        sockets.forEach(socket -> socket.sendPacket(packet));
    }

    /**
     * Sends the given packet to the socket with the given ID
     *
     * @param ID     socket ID
     * @param packet packet to be sent
     * @author John Gauci
     */
    public void sendUnique(long ID, Packet packet) {
        for (RobustSocket socket : sockets) {
            if (socket.getID() == ID) {
                socket.sendPacket(packet);
                break;
            }
        }
    }

    /**
     * Stops and removes management of the socket with the given ID
     *
     * @param ID socket ID
     * @author John Gauci
     */
    public void closeSocket(long ID) {
        for (int i = 0; i < sockets.size(); i++) {
            if (sockets.get(i).getID() == ID) {
                sockets.remove(i).stop();
                break;
            }
        }

    }

    /**
     * Adds the given command to the queue of commands
     *
     * @param command the command to be added
     * @author John Gauci
     */
    public void addCommand(Command command) {
        commands.add(command);
    }

    /**
     * Executes all the commands found in the queue when called
     *
     * @author John Gauci
     */
    public void executeAllCommands() {
        commands.forEach(command -> commands.poll().execute(this, null));
    }

    /**
     * Stops the communicator thread, all of its sockets, and clears all queues and storage
     *
     * @author John Gauci
     */
    @Override
    public void stop() {
        super.stop();
        receivedPackets.clear();
        commands.clear();
        sockets.forEach(RobustSocket::stop);
        sockets.clear();
    }

    /**
     * Code to be executed each tick
     *
     * @author John Gauci
     */
    protected abstract void processTick();

}
