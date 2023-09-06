package server.game.gamemodes;

import server.game.ServerGameHandler;

/**
 * Gets the number of points per kill of the game mode
 *
 * @author John Gauci
 */
public class OneLife extends GameMode {

    /**
     * Constructor for a new One Life game mode
     *
     * @param playerHealth the health players should have.
     * @param lives        the amount of lives each player should have.
     * @param killPoints   the number of points each kill awards.
     * @param killLimit    the number of kills that must be reached to win
     * @author John Gauci
     */
    public OneLife(int playerHealth, int lives, int killPoints, int killLimit) {
        super(playerHealth, lives, killPoints, killLimit);
    }

    /**
     * Overloaded constructor that sets the game mode up with default values.
     *
     * @author John Gauci
     */
    public OneLife() {
        this(100, 1, 100, -1);
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
        long alive = serverGameHandler.getPlayers().stream().filter(player -> player.getTank().getHealth() > 0).count();
        return serverGameHandler.getPlayers().size() > 1 && alive < 2;
    }
}
