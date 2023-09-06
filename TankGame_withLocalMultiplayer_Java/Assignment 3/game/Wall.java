package game;

import base.Direction;
import base.Window;

/**
 * A wall tile object.
 *
 * @author John Gauci
 */
public class Wall extends Entity {

    /**
     * Constructor for a wall object
     *
     * @param row    the walls row
     * @param column the walls column
     * @param width  the walls width
     * @param height the walls height
     * @author John Gauci
     */
    public Wall(int row, int column, int width, int height) {
        super(row, column, width, height, -1, Window.COLOR_BLUE, Direction.NORTH, false);
    }

    /**
     * Overridden draw method that draws a rectangle for the wall
     *
     * @param window the window to draw the tank in.
     * @author John Gauci
     */
    @Override
    public void draw(Window window) {
        window.drawRectangle(getRow(), getColumn(), getWidth(), getHeight(), getColor(), getColor());
    }


}
