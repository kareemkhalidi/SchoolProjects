package game;

import java.io.Serializable;

/**
 * Stores the stats for a player.
 *
 * @author John Gauci
 */
public class PlayerStats implements Serializable {

    private final String name;
    private final long playerID;
    private int score;
    private int kills;
    private int deaths;
    private long ping;
    private boolean host;

    /**
     * Constructor for a player stats object
     *
     * @param player the player that the stats belong to.
     * @author John Gauci
     */
    public PlayerStats(Player player) {
        score = 0;
        kills = 0;
        deaths = 0;
        ping = 0;
        host = false;
        name = player.getName();
        playerID = player.getID();
    }

    /**
     * Resets the players stats to 0
     *
     * @author John Gauci
     */
    public void reset() {
        score = 0;
        kills = 0;
        deaths = 0;
    }

    /**
     * Increases the players score by the specified score
     *
     * @param score the score to increase the players score by.
     * @author John Gauci
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Increases the players kills by 1
     *
     * @author John Gauci
     */
    public void addKill() {
        kills++;
    }

    /**
     * Increases the players deaths by 1
     *
     * @author John Gauci
     */
    public void addDeath() {
        deaths++;
    }

    /**
     * Tells the user if this player is the host
     *
     * @return boolean representing if the player is the host
     * @author John Gauci
     */
    public boolean isHost() {
        return host;
    }

    /**
     * Sets the host status of the player
     *
     * @param host the status to set host to.
     * @author John Gauci
     */
    public void setHost(boolean host) {
        this.host = host;
    }

    /**
     * overridden equals method that checks if the playerStats == o
     *
     * @param o the object to compare playerStats to.
     * @return boolean representation of the == status
     * @author John Gauci
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerStats that)) return false;
        if (playerID != that.playerID) return false;
        return name.equals(that.name);
    }

    /**
     * Returns a hash value for the player stats
     *
     * @author John Gauci
     */
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (int) (playerID ^ (playerID >>> 32));
        return result;
    }

    /**
     * Gets the number of deaths of the player
     *
     * @return the players deaths.
     * @author John Gauci
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * Gets the number of kills of the player
     *
     * @return the players kills.
     * @author John Gauci
     */
    public int getKills() {
        return kills;
    }

    /**
     * Gets the player id of the player
     *
     * @return the player's id.
     * @author John Gauci
     */
    public long getPlayerID() {
        return playerID;
    }

    /**
     * Gets the score of the player
     *
     * @return the players score.
     * @author John Gauci
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the name of the player
     *
     * @return the players name.
     * @author John Gauci
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the ping of the player
     *
     * @return the players ping.
     * @author John Gauci
     */
    public long getPing() {
        return ping;
    }

    /**
     * Sets the players ping to the specified ping.
     *
     * @param ping the ping to set the players ping to.
     * @author John Gauci
     */
    public void setPing(long ping) {
        this.ping = ping;
    }

}
