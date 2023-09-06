package client.game;

import base.*;
import client.net.ClientGamePacket;
import client.net.commands.DrawMap;
import game.Map;
import game.Player;
import server.game.GameData;
import server.game.ServerGameHandler;
import server.game.gamemodes.GameMode;
import server.net.ConnectionHandler;
import server.net.commands.Connect;
import server.net.commands.Disconnect;
import server.net.commands.MoveTank;
import server.net.commands.Shoot;

import java.util.Optional;

/**
 * Implementation of a ClientGameHandler to handle game functionality for the client/player.
 *
 * @author John Gauci
 */

public class ClientGameHandler extends Communicator {

    private Player player;
    private GameController controller;
    private Map map;

    /**
     * Initializes a ClientGameHandler
     *
     * @author John Gauci
     */
    public ClientGameHandler() {
        setData(new GameData());
        setName("ClientGameHandler");
    }

    /**
     * Returns the GameData of the subject.
     *
     * @return the GameData of the subject
     * @author John Gauci
     */
    @Override
    public GameData getData() {
        return (GameData) super.getData();
    }

    /**
     * Starts a game server with the given map and game mode, and connects to it with the given
     * name and tank
     *
     * @param mapName    name of game map
     * @param gameMode   game mode
     * @param playerName name of player
     * @param tankName   name of tank
     * @author John Gauci
     */
    public void host(String mapName, GameMode gameMode, String playerName, String tankName) {
        player = new Player(playerName, tankName);
        player.getPlayerStats().setHost(true);
        ServerGameHandler serverGameHandler = new ServerGameHandler(mapName, gameMode);
        ConnectionHandler connectionHandler = new ConnectionHandler(18000, serverGameHandler);
        serverGameHandler.setConnectionHandler(connectionHandler);
        serverGameHandler.start();
        connectionHandler.start();
        connect("localhost", playerName, tankName);
    }

    /**
     * Connects to the game at the given host address with the given name and tank
     *
     * @param host       address of host
     * @param playerName name of player
     * @param tankName   name of tank
     * @author John Gauci
     */
    public void connect(String host, String playerName, String tankName) {
        if (player == null)
            player = new Player(playerName, tankName);
        RobustSocket listener = new RobustSocket(this, host, 18000);
        sendCommand(new Connect());
        listener.start();
    }

    /**
     * Disconnects from the currently connected game
     *
     * @author John Gauci
     */
    public void disconnect() {
        sendCommand(new Disconnect());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        stop();
    }

    /**
     * Sends a command to the server to move the tank forward
     *
     * @author John Gauci
     */
    public void moveForward() {
        sendCommand(new MoveTank(Direction.NORTH));
    }

    /**
     * Sends a command to the server to move the tank left
     *
     * @author John Gauci
     */
    public void moveLeft() {
        sendCommand(new MoveTank(Direction.WEST));
    }

    /**
     * Sends a command to the server to move the tank backward
     *
     * @author John Gauci
     */
    public void moveBackward() {
        sendCommand(new MoveTank(Direction.SOUTH));
    }

    /**
     * Sends a command to the server to move the tank right
     *
     * @author John Gauci
     */
    public void moveRight() {
        sendCommand(new MoveTank(Direction.EAST));
    }

    /**
     * Sends a command to the server to shoot
     *
     * @author John Gauci
     */
    public void shoot() {
        sendCommand(new Shoot());
    }

    /**
     * Wrapper for easily sending commands
     *
     * @param command command to be sent
     * @author John Gauci
     */
    private void sendCommand(Command command) {
        sendAllSockets(new ClientGamePacket(player, command));
    }

    /**
     * Processes the current game tick, including setting Subject data with the data received from
     * the server, executing any server commands, and notifying observers
     *
     * @author John Gauci
     */
    @Override
    protected void processTick() {
        while (!getReceivedPackets().isEmpty()) {
            addCommand(new DrawMap());
            Packet currentPacket = getReceivedPackets().poll();
            currentPacket.getData().ifPresent(this::setData);
            currentPacket.getCommand().ifPresent(command -> command.execute(this, currentPacket));
            executeAllCommands();
            notifyObservers();
        }
    }

    /**
     * Returns an optional map
     *
     * @return optional map
     * @author John Gauci
     */
    public Optional<Map> getMap() {
        return Optional.ofNullable(map);
    }

    /**
     * Sets the map to the given map
     *
     * @param map map
     * @author John Gauci
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Stops the ClientGameHandler thread, all of its sockets, and clears all queues and storage
     *
     * @author John Gauci
     */
    @Override
    public void stop() {
        super.stop();
        setData(new GameData());
        player = null;
        map = null;
    }

    /**
     * Gets the controller of this ClientGameHandler
     *
     * @return controller
     * @author John Gauci
     */
    public GameController getController() {
        return controller;
    }

    /**
     * Sets the controller of this ClientGameHandler to the given Controller
     *
     * @param controller controller for ClientGameHandler
     * @author John Gauci
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }

}
