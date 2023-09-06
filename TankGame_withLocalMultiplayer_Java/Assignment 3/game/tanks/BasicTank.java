package game.tanks;

import base.Direction;
import base.Window;
import game.Player;
import game.weapons.Cannon;

/**
 * Implementation of a Tank that has a low firing speed and high damage per shot.
 *
 * @author John Gauci
 */
public class BasicTank extends Tank {

    /**
     * Constructor for a basic tank object.
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
    public BasicTank(int row, int column, int width, int height, int health, int color,
                     Direction direction, Player player) {
        super(row, column, width, height, health, color, new Cannon(25, 600, 600), direction,
                player, 10, 0.75);
    }

    /**
     * Overloaded basic tank constructor that initializes all values except for player to a default value.
     *
     * @param player the player that the tank belongs to.
     * @author John Gauci
     */
    public BasicTank(Player player) {
        this(70, 100, 44, 52, 100, Window.COLOR_BLUE, Direction.NORTH, player);
    }

}
