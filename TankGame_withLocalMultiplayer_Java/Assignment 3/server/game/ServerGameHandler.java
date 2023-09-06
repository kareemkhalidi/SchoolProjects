package server.game;

import base.Communicator;
import base.Packet;
import base.Vector2;
import client.net.ClientGamePacket;
import game.Entity;
import game.Map;
import game.Player;
import game.PlayerStats;
import server.game.gamemodes.GameMode;
import server.net.ConnectionHandler;
import server.net.commands.CheckGameOver;
import server.net.commands.SpawnPlayers;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Server game handler class that handles all server side calculations, data, etc.
 *
 * @author John Gauci
 */
public class ServerGameHandler extends Communicator {

    private final ArrayList<Entity> entities;

    private final ArrayList<PlayerStats> playerStats;

    private final ArrayList<Entity> collideables;

    private final GameMode gameMode;

    private final Map map;

    private final String mapName;
    private final ArrayList<Player> players;
    private boolean gameOver;
    private ConnectionHandler connectionHandler;

    /**
     * ServerGameHandler constructor
     *
     * @param mapName  the name of the map for the game
     * @param gameMode the game mode for the game
     * @author John Gauci
     */
    public ServerGameHandler(String mapName, GameMode gameMode) {
        setName("ServerGameHandler");
        players = new ArrayList<>(20);
        entities = new ArrayList<>(10000);
        playerStats = new ArrayList<>(20);
        setData(new GameData(entities, mapName, playerStats));
        this.mapName = mapName;
        map = new Map(mapName);
        this.gameMode = gameMode;
        collideables = new ArrayList<>();
        gameOver = false;
    }

    /**
     * Returns the players in the game
     *
     * @return array list of players in the game
     * @author John Gauci
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Processes the current game tick
     *
     * @author John Gauci
     */
    @Override
    protected void processTick() {
        addCommand(new CheckGameOver(getGameMode()));
        addCommand(new SpawnPlayers());
        while (!getReceivedPackets().isEmpty()) {
            ClientGamePacket currentPacket = (ClientGamePacket) getReceivedPackets().poll();
            currentPacket.getCommand().ifPresent(command -> command.execute(this, currentPacket));
            getPlayer(currentPacket.getPlayer()).ifPresent(value -> value.getPlayerStats().setPing(currentPacket.getPing()));
        }
        executeAllCommands();
        sendAllSockets(new Packet(getData()));
    }

    /**
     * Gets the games data
     *
     * @return GameData the games data
     * @author John Gauci
     */
    @Override
    public GameData getData() {
        return (GameData) super.getData();
    }

    /**
     * Gets the players stats
     *
     * @return ArrayList of all player stats objects
     * @author John Gauci
     */
    public ArrayList<PlayerStats> getPlayerStats() {
        return playerStats;
    }

    /**
     * Gets all the entities
     *
     * @return ArrayList of all entities in the game
     * @author John Gauci
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Gets all the collideable entities
     *
     * @return ArrayList of all collideable entities
     * @author John Gauci
     */
    public ArrayList<Entity> getCollideables() {
        collideables.clear();
        collideables.addAll(entities);
        collideables.addAll(map.getWalls());
        return collideables;
    }

    /**
     * Gets the specified player from the list of serverPlayers
     *
     * @param player the player to find
     * @return Optional<Player> the player if found or empty if not
     * @author John Gauci
     */
    public Optional<Player> getPlayer(Player player) {
        for (Player serverPlayer : players) {
            if (serverPlayer.equals(player)) {
                return Optional.of(serverPlayer);
            }
        }
        return Optional.empty();
    }

    /**
     * Gets the specified player from the list of serverPlayers
     *
     * @param entity the player to find
     * @return Optional<Player> the player if found or empty if not
     * @author John Gauci
     */
    public Optional<Player> getPlayer(Entity entity) {
        for (Player serverPlayer : players) {
            if (serverPlayer.getTank().equals(entity)) {
                return Optional.of(serverPlayer);
            }
        }
        return Optional.empty();
    }

    /**
     * Spawns the player
     *
     * @param player the player to spawn
     * @param check  boolean representing whether to check that player is allowed to respawn
     *               or not.
     * @author John Gauci
     */
    public void spawnPlayer(Player player, boolean check) {
        if (check) {
            if (player.getTank().getHealth() <= 0 && player.getPlayerStats().getDeaths() < gameMode.getLives()) {
                Vector2 spawnPoint = getNextSpawnPoint();
                player.getTank().setRow(spawnPoint.y());
                player.getTank().setColumn(spawnPoint.x());
                player.getTank().setHealth(gameMode.getPlayerHealth());
                entities.add(player.getTank());
            }
        } else {
            Vector2 spawnPoint = getNextSpawnPoint();
            player.getTank().setRow(spawnPoint.y());
            player.getTank().setColumn(spawnPoint.x());
            player.getTank().setHealth(gameMode.getPlayerHealth());
            entities.add(player.getTank());
        }

    }

    /**
     * Gets the games map name
     *
     * @return String mapName
     * @author John Gauci
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Gets the next spawn point from the cycling queue of spawn points and adds it to the end of
     * the queue
     *
     * @return Vector2 representing the next spawn point
     * @author John Gauci
     */
    public Vector2 getNextSpawnPoint() {
        Vector2 spawnPoint = map.getSpawnPoints().removeFirst();
        map.getSpawnPoints().addLast(spawnPoint);
        return spawnPoint;
    }

    /**
     * Gets the game's game mode
     *
     * @return GameMode game mode
     * @author John Gauci
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Adds a new player to the game
     *
     * @param player the player to add
     * @author John Gauci
     */
    public void addPlayer(Player player) {
        players.add(player);
        playerStats.add(player.getPlayerStats());
        spawnPlayer(player, false);
    }

    /**
     * Gets the games connection handler
     *
     * @return the games connection handler
     * @author John Gauci
     */
    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    /**
     * Sets the connection handler to the specified connection handler
     *
     * @param connectionHandler the specified connection handler
     * @author John Gauci
     */
    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    /**
     * Gets the status of the game
     *
     * @return boolean representing if the game is over
     * @author John Gauci
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Sets the game over status to the specified stats
     *
     * @param gameOver the games status
     * @author John Gauci
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}






