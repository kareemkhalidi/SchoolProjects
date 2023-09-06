package game.weapons;

import game.Player;
import server.game.ServerGameHandler;

import java.io.Serializable;

/**
 * Abstract base class for weapons to be built off of.
 *
 * @author John Gauci
 */
public abstract class Weapon implements Serializable {

    private final int damage;
    private final int firingDelay;
    private final int projectileSpeed;

    /**
     * Constructor for a weapon object
     *
     * @param damage          the damage of the cannon.
     * @param firingDelay     the firing delay of the cannon.
     * @param projectileSpeed the speed of the cannons projectiles.
     * @author John Gauci
     */
    public Weapon(int damage, int firingDelay, int projectileSpeed) {
        this.damage = damage;
        this.firingDelay = firingDelay;
        this.projectileSpeed = projectileSpeed;
    }

    /**
     * returns the weapons damage.
     *
     * @return weapons damage.
     * @author John Gauci
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Returns the weapons firing delay
     *
     * @return weapons firing delay.
     * @author John Gauci
     */
    public int getFiringDelay() {
        return firingDelay;
    }

    /**
     * Returns the weapons projectile speed.
     *
     * @return weapons projectile speed.
     * @author John Gauci
     */
    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    /**
     * Abstract method to fire the weapon
     *
     * @param shooter           the player that fired the cannon.
     * @param serverGameHandler the handler for the server.
     * @author Kareem Khalidi
     */
    public abstract void fire(Player shooter, ServerGameHandler serverGameHandler);

}
