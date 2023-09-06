package game.tanks;

import base.Direction;
import base.Window;
import game.Player;
import game.weapons.Cannon;

/**
 * Implementation of a Tank that has a fast firing speed and low damage per shot.
 *
 * @author John Gauci
 */
public class BlitzTank extends Tank {

    /**
     * Constructor for a blitz tank object.
     *
     * @param row       the tank's row (y) location.
     * @param column    the tank's column (x) location.
     * @param width     the tank's width.
     * @param height    the tank's height.
     * @param health    the tank's health.
     * @param color     the tank's color.
     * @param direction the direction that the tank is facing.
     * @param player    the player that the tank belongs to.
     * @author John Gauci
     */
    public BlitzTank(int row, int column, int width, int height, int health, int color,
                     Direction direction, Player player) {
        super(row, column, width, height, health, color, new Cannon(5, 50, 900), direction,
                player, 10, 0.95);
    }

    /**
     * Overloaded blitz tank constructor that initializes all values except for player to a default value.
     *
     * @param player the player that the tank belongs to.
     * @author John Gauci
     */
    public BlitzTank(Player player) {
        this(70, 100, 44, 52, 100, Window.COLOR_BLUE, Direction.NORTH, player);
    }

}
