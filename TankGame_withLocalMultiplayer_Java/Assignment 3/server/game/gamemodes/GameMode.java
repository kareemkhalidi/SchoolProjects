package server.game.gamemodes;

import server.game.ServerGameHandler;

/**
 * Abstract game mode class
 *
 * @author John Gauci
 */
public abstract class GameMode {

    private final int playerHealth;
    private final int lives;
    private final int killPoints;
    private final int killLimit;

    /**
     * Constructor for a game mode.
     *
     * @param playerHealth the health of players
     * @param lives        the number of lives for players
     * @param killPoints   the number of points each kill awards
     * @param killLimit    the number of kills that must be reached to win.
     * @author John Gauci
     */
    public GameMode(int playerHealth, int lives, int killPoints, int killLimit) {
        this.playerHealth = playerHealth;
        this.lives = lives;
        this.killPoints = killPoints;
        this.killLimit = killLimit;
    }

    /**
     * Gets the kill limit of the game mode
     *
     * @return the kill limit
     * @author John Gauci
     */
    public int getKillLimit() {
        return killLimit;
    }

    /**
     * Gets the players health of the game mode
     *
     * @return the player health
     * @author John Gauci
     */
    public int getPlayerHealth() {
        return playerHealth;
    }

    /**
     * Gets the number of lives of the game mode
     *
     * @return the game modes lives.
     * @author John Gauci
     */
    public int getLives() {
        return lives;
    }

    /**
     * Gets the number of points per kill of the game mode
     *
     * @return the game modes kill points.
     * @author John Gauci
     */
    public int getKillPoints() {
        return killPoints;
    }

    /**
     * Abstract method that all game modes must implement that checks if the game is over
     *
     * @return boolean representing if the game is over.
     * @author John Gauci
     */
    public abstract boolean isOver(ServerGameHandler serverGameHandler);
}
