package server.game.gamemodes;

import server.game.ServerGameHandler;

/**
 * Class representing the Death Match game mode
 *
 * @author John Gauci
 */
public class DeathMatch extends GameMode {

    /**
     * Constructor for a new Death Match game mode
     *
     * @param playerHealth the health players should have.
     * @param lives        the amount of lives each player should have.
     * @param killPoints   the number of points each kill awards.
     * @param killLimit    the number of kills that must be reached to win
     * @author John Gauci
     */
    public DeathMatch(int playerHealth, int lives, int killPoints, int killLimit) {
        super(playerHealth, lives, killPoints, killLimit);
    }

    /**
     * Overloaded constructor that sets the game mode up with default values.
     *
     * @author John Gauci
     */
    public DeathMatch() {
        this(100, Integer.MAX_VALUE, 100, 10);
    }

    /**
     * Returns the status of the game (over/not over)
     *
     * @param serverGameHandler the handler for the games server
     * @return boolean representation of whether the game is over or not.
     * @author John Gauci
     */
    @Override
    public boolean isOver(ServerGameHandler serverGameHandler) {
        int highestKills = serverGameHandler.getPlayers().stream().mapToInt(player -> player.getPlayerStats().getKills()).filter(player -> player >= 0).max().orElse(0);
        return highestKills >= getKillLimit();
    }
}
