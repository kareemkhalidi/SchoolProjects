package server.game;

import base.Data;
import base.Window;
import game.Entity;
import game.PlayerStats;

import java.util.ArrayList;

/**
 * Class that stores the data for the game
 *
 * @author John Gauci
 */
public class GameData extends Data {

    private final ArrayList<Entity> entities;
    private final String mapName;


    private final ArrayList<PlayerStats> playerStats;


    /**
     * Constructor for a new game data object
     *
     * @param entities    the entities in the game
     * @param mapName     the name of the map
     * @param playerStats the stats of all the players
     * @author John Gauci
     */
    public GameData(ArrayList<Entity> entities, String mapName, ArrayList<PlayerStats> playerStats) {
        this.entities = entities;
        this.mapName = mapName;
        this.playerStats = playerStats;
    }

    /**
     * Overloaded constructor that initializes all values to default values
     *
     * @author John Gauci
     */
    public GameData() {
        this(new ArrayList<>(0), "", new ArrayList<>(0));
    }

    /**
     * Overloaded constructor that initializes all values except for the map name to default values
     *
     * @param mapName the name of the map
     * @author John Gauci
     */
    public GameData(String mapName) {
        this(new ArrayList<>(0), mapName, new ArrayList<>(0));
    }

    /**
     * Returns the stats of all the players in the game
     *
     * @return arraylist of all players player stats objects
     * @author John Gauci
     */
    public ArrayList<PlayerStats> getPlayerStats() {
        return playerStats;
    }

    /**
     * Returns all entities in the game currently
     *
     * @return arraylist of all entities in the game
     * @author John Gauci
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Returns the name of the map
     *
     * @return String map name
     * @author John Gauci
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Draws the GameData entities to the given window
     *
     * @param window the window to be drawn to
     * @author John Gauci
     */
    public void draw(Window window) {
        entities.forEach(entity -> entity.draw(window));
    }
}
