package base;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * Implementation of a RobustSocket class to work with sending and receiving of packets over a
 * network socket.
 */

public class RobustSocket extends RobustThread {

    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;
    private final Communicator communicator;
    private final long ID;

    /**
     * Initializes a RobustSocket with the given communicator and remote host and port
     *
     * @param communicator the communicator to add to
     * @param host         remote address
     * @param port         remote port
     * @author John Gauci
     */
    public RobustSocket(Communicator communicator, String host, int port) {
        setName("RobustClientSocket");
        ID = new Random().nextLong();
        this.communicator = communicator;
        try {
            this.socket = new Socket(host, port);
            output = new ObjectOutputStream(this.socket.getOutputStream());
            input = new ObjectInputStream(this.socket.getInputStream());
            this.socket.setTcpNoDelay(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        communicator.addSocket(this);
    }

    /**
     * Initializes a RobustSocket with the given communicator and socket
     *
     * @param communicator the communicator to add to
     * @param socket       the socket to enclose the RobustSocket around
     */
    public RobustSocket(Communicator communicator, Socket socket) {
        Thread.currentThread().setName("RobustServerSocket");
        setName("RobustServerSocket");
        ID = new Random().nextLong();
        this.communicator = communicator;
        try {
            this.socket = socket;
            output = new ObjectOutputStream(this.socket.getOutputStream());
            input = new ObjectInputStream(this.socket.getInputStream());
            this.socket.setTcpNoDelay(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        communicator.addSocket(this);
    }

    /**
     * Sends the given packet to remote
     *
     * @param packet the packet to send
     * @author John Gauci
     */
    public void sendPacket(Packet packet) {
        try {
            output.reset();
            packet.stampOutgoing(ID);
            output.writeObject(packet);
            output.flush();
        } catch (IOException ignored) {
        }
    }

    /**
     * Listens for incoming packets in a loop and adds them to the communicator.
     *
     * @author John Gauci
     */
    @Override
    public void loopExecute() {
        try {
            Packet packet = (Packet) input.readObject();
            packet.stampIncoming(ID);
            communicator.addPacket(packet);
        } catch (IOException | ClassNotFoundException ignored) {
        }
    }

    /**
     * Stops the listening loop and closes the underlying socket.
     *
     * @author John Gauci
     */
    @Override
    public void stop() {
        super.stop();
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Returns the socket ID
     *
     * @return socket ID
     * @author John Gauci
     */
    public long getID() {
        return ID;
    }

    /**
     * Returns whether the two sockets are equal
     *
     * @param o object to be checked for equality
     * @return whether two sockets are equal
     * @author John Gauci (IDE generated)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobustSocket socket1 = (RobustSocket) o;
        if (!socket.equals(socket1.socket)) return false;
        if (!output.equals(socket1.output)) return false;
        return input.equals(socket1.input);
    }

    /**
     * Returns the hashcode for a RobustSocket object
     *
     * @return hashcode
     * @author John Gauci (IDE generated)
     */
    @Override
    public int hashCode() {
        int result = socket.hashCode();
        result = 31 * result + output.hashCode();
        result = 31 * result + input.hashCode();
        return result;
    }


}
