package base;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * Implementation of a network packet used to send data and (a) command.
 *
 * @author John Gauci
 */

public class Packet implements Serializable {
    private final Data data;
    private final Command command;
    private InetAddress senderIP;
    private long senderID;
    private long senderTime;
    private InetAddress receiverIP;
    private long receiverID;
    private long receiverTime;
    private long ping;


    /**
     * Initializes a Packet with the given data and command
     *
     * @param data    the data for the packet
     * @param command the command for the packet
     * @author John Gauci
     */
    public Packet(Data data, Command command) {
        this.data = data;
        this.command = command;
        ping = Long.MIN_VALUE;
        senderTime = Long.MIN_VALUE;
        receiverTime = Long.MIN_VALUE;
    }

    /**
     * Initializes a Packet with the given data
     *
     * @param data the data for the packet
     * @author John Gauci
     */
    public Packet(Data data) {
        this(data, null);
    }

    /**
     * Initializes a Packet with the given command
     *
     * @param command the command for the packet
     * @author John Gauci
     */
    public Packet(Command command) {
        this(null, command);
    }

    /**
     * Stamps the outgoing packet with the sender's time, IP, and given socket ID
     *
     * @param senderID sender socket ID
     * @author John Gauci
     */
    public void stampOutgoing(long senderID) {
        this.senderID = senderID;
        try {
            senderIP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        senderTime = System.currentTimeMillis();
    }

    /**
     * Stamps the received packet with the receiver's time, IP, and given socket ID
     *
     * @param receiverID receiver socket ID
     * @author John Gauci
     */
    public void stampIncoming(long receiverID) {
        receiverTime = System.currentTimeMillis();
        this.receiverID = receiverID;
        try {
            receiverIP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        ping = receiverTime - senderTime;
    }

    /**
     * Returns the optional data
     *
     * @return optional data
     * @author John Gauci
     */
    public Optional<Data> getData() {
        return Optional.ofNullable(data);
    }

    /**
     * Returns the optional command
     *
     * @return optional command
     * @author John Gauci
     */
    public Optional<Command> getCommand() {
        return Optional.ofNullable(command);
    }

    /**
     * Returns the ping, or packet latency, between sender and receiver
     *
     * @return packet latency
     * @author John Gauci
     */
    public long getPing() {
        return ping;
    }

    /**
     * Returns the receiver's IP
     *
     * @return receiver's IP
     * @author John Gauci
     */
    public InetAddress getReceiverIP() {
        return receiverIP;
    }

    /**
     * Returns the sender's IP
     *
     * @return sender's IP
     * @author John Gauci
     */
    public InetAddress getSenderIP() {
        return senderIP;
    }

    /**
     * Return's the sender's socket ID
     *
     * @return sender's socket ID
     * @author John Gauci
     */
    public long getSenderID() {
        return senderID;
    }

    /**
     * Return's the receiver's socket ID
     *
     * @return receiver's socket ID
     * @author John Gauci
     */
    public long getReceiverID() {
        return receiverID;
    }
}
