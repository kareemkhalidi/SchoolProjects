package base;

import java.io.Serializable;

/**
 * Implementation of a two-integer vector.
 *
 * @author John Gauci
 */

public class Vector2 implements Serializable {
    private int x, y;

    /**
     * Constructs a vector with the given x and y values.
     *
     * @param x x value
     * @param y y value
     * @author John Gauci
     */
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x value of the vector
     *
     * @return x value
     * @author John Gauci
     */
    public int x() {
        return x;
    }

    /**
     * Sets the x value of the vector to the given value
     *
     * @param x x value to be set to
     * @author John Gauci
     */
    public void x(int x) {
        this.x = x;
    }

    /**
     * Gets the y value of the vector
     *
     * @return y value
     * @author John Gauci
     */
    public int y() {
        return y;
    }

    /**
     * Sets the y value of the vector to the given value
     *
     * @param y y value to be set to
     * @author John Gauci
     */
    public void y(int y) {
        this.y = y;
    }


}
