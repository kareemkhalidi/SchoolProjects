package game.tanks;

import base.Direction;
import game.Entity;
import game.Player;
import server.game.ServerGameHandler;

/**
 * Class for the tank shells that are fired when a tank shoots.
 *
 * @author John Gauci
 */
abstract class Projectile extends Entity {

    private final Player firedFrom;

    private final int damage;
    private final int projectileSpeed;

    /**
     * Constructor for a new tank shell
     *
     * @param row       the projectile row
     * @param column    the projectile column
     * @param width     the projectile width
     * @param height    the projectile height
     * @param health    the projectile health
     * @param color     the projectile color
     * @param direction the projectile direction
     * @param firedFrom the player that shot the projectile
     * @author John Gauci
     */
    public Projectile(int row, int column, int width, int height, int health, int color,
             Direction direction, Player firedFrom) {
        super(row, column, width, height, health, color, direction, false);
        this.firedFrom = firedFrom;
        this.damage = firedFrom.getTank().getWeapon().getDamage();
        this.projectileSpeed = firedFrom.getTank().getWeapon().getProjectileSpeed();
    }

    public abstract void move(ServerGameHandler serverGameHandler);

    /**
     * Gets the projectile damage value
     *
     * @return int damage
     * @author John Gauci
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Gets the tank shells speed
     *
     * @return int speed
     * @author John Gauci
     */
    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    /**
     * Gets the player that the tank shell was fired from
     *
     * @return the player that fired the tank shell
     * @author John Gauci
     */
    public Player getFiredFrom() {
        return firedFrom;
    }

}
