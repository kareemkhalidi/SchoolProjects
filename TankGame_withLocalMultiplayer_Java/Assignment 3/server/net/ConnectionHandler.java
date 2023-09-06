package server.net;

import base.Communicator;
import base.RobustSocket;
import base.RobustThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class that handles game connections
 *
 * @author John Gauci
 */
public class ConnectionHandler extends RobustThread {

    private final ServerSocket serverSocket;
    private final Communicator communicator;
    private final ExecutorService pool;

    /**
     * Constructor for connection handler
     *
     * @param port         the port to connect to
     * @param communicator the communicator for the connection handler
     * @author John Gauci
     */
    public ConnectionHandler(int port, Communicator communicator) {
        setName("ConnectionHandler");
        this.communicator = communicator;
        try {
            serverSocket = new ServerSocket(port);
            pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Run code in loop.
     *
     * @author John Gauci
     */
    @Override
    public void loopExecute() {
        try {
            pool.execute(new RobustSocket(communicator, serverSocket.accept()));
        } catch (IOException ignored) {
        }
    }

    /**
     * Stops the connection handler
     *
     * @author John Gauci
     */
    @Override
    public void stop() {
        pool.shutdown();
        super.forceStop();
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


