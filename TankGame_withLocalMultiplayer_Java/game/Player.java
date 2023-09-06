package game;

import game.tanks.BasicTank;
import game.tanks.BlitzTank;
import game.tanks.Tank;

import java.io.Serializable;
import java.util.Random;

/**
 * Represents a player in the game.
 *
 * @author John Gauci
 */
public class Player implements Serializable {

    private final String name;
    private final PlayerStats playerStats;
    private final long ID;
    private Tank tank;

    /**
     * Constructor for a new player object
     *
     * @param name     the players name
     * @param tankName the name of the tank type for the player.
     * @author John Gauci
     */
    public Player(String name, String tankName) {
        this.name = name;
        ID = new Random().nextLong();
        switch (tankName) {
            case "BasicTank" -> tank = new BasicTank(this);
            case "BlitzTank" -> tank = new BlitzTank(this);
        }
        playerStats = new PlayerStats(this);
    }

    /**
     * Returns the players stats to the user.
     *
     * @return the players stats.
     * @author John Gauci
     */
    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    /**
     * Returns the players id to the user.
     *
     * @return the player's id.
     * @author John Gauci
     */
    public long getID() {
        return ID;
    }

    /**
     * Returns the players name to the user.
     *
     * @return the players name.
     * @author John Gauci
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the players tank to the user.
     *
     * @return the players tank.
     * @author John Gauci
     */
    public Tank getTank() {
        return tank;
    }

    /**
     * Set the players tank to the specified tank.
     *
     * @param tank the tank to set the players tank to.
     * @author John Gauci
     */
    public void setTank(Tank tank) {
        this.tank = tank;
    }

    /**
     * Overridden equals method, checks if player == o
     *
     * @param o the object to compare the player to.
     * @return boolean representing player == o.
     * @author John Gauci
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;

        return getID() == player.getID();
    }

    /**
     * Returns a hash value for the player.
     *
     * @return integer hash for the player.
     * @author John Gauci
     */
    @Override
    public int hashCode() {
        return (int) (getID() ^ (getID() >>> 32));
    }
}
