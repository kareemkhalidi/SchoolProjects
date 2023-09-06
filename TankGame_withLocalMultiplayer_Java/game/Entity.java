package game;

import base.Direction;
import base.Vector2;
import base.Window;

import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract entity class that all entity's (tanks, walls, bullets, etc) are based on.
 *
 * @author John Gauci
 */
public abstract class Entity implements Serializable {

    private final boolean destroyable;
    private int row;
    private int column;
    private int width;
    private int height;
    private int health;
    private int color;
    private Direction direction;

    /**
     * Entity object constructor.
     *
     * @param row         the entities row
     * @param column      the entities column
     * @param width       the entities width
     * @param height      the entities height
     * @param health      the entities health
     * @param color       the entities color
     * @param direction   the entities direction
     * @param destroyable a boolean representing if the entity is destroyable
     * @author John Gauci
     */
    public Entity(int row, int column, int width, int height, int health, int color,
                  Direction direction, boolean destroyable) {
        this.row = row;
        this.column = column;
        this.width = width;
        this.height = height;
        this.health = health;
        this.color = color;
        this.direction = direction;
        this.destroyable = destroyable;
    }

    /**
     * Returns the entity's row to the user.
     *
     * @return the entity's row.
     * @author John Gauci
     */
    public int getRow() {
        return row;
    }

    /**
     * Set the entity's row to the specified value.
     *
     * @param row the row to set the entity's row to.
     * @author John Gauci
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Returns the entity's column to the user.
     *
     * @return the entity's column.
     * @author John Gauci
     */
    public int getColumn() {
        return column;
    }

    /**
     * Set the entity's column to the specified value.
     *
     * @param column the column to set the entity's column to.
     * @author John Gauci
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Returns the entity's width to the user.
     *
     * @return the entity's width.
     * @author John Gauci
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the entity's width to the specified value.
     *
     * @param width the width to set the entity's width to.
     * @author John Gauci
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the entity's height to the user.
     *
     * @return the entity's height.
     * @author John Gauci
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the entity's height to the specified value.
     *
     * @param height the height to set the entity's height to.
     * @author John Gauci
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the entity's health to the user.
     *
     * @return the entity's health.
     * @author John Gauci
     */
    public int getHealth() {
        return health;
    }

    /**
     * Set the entity's health to the specified value.
     *
     * @param health the health to set the entity's health to.
     * @author John Gauci
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Returns the entity's color to the user.
     *
     * @return the entity's color.
     * @author John Gauci
     */
    public int getColor() {
        return color;
    }

    /**
     * Set the entity's color to the specified value.
     *
     * @param color the color to set the entity's color to.
     * @author John Gauci
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Returns the entity's direction to the user.
     *
     * @return the entitys direction.
     * @author John Gauci
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Set the entity's direction to the specified value.
     *
     * @param direction the direction to set the entity's direction to.
     * @author John Gauci
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Abstract draw method that all entities must implement.
     *
     * @param window the window to draw the entity in
     * @author John Gauci
     */
    public abstract void draw(Window window);

    /**
     * Returns a boolean whether the given entity has contacted this entity.
     *
     * @param other the entity that may have contacted
     * @return boolean whether contacted
     * @author Kareem Khalidi
     */
    public boolean contacted(Entity other) {
        var upperLeft = new Vector2(getColumn(), getRow());
        var lowerRight = new Vector2(getColumn() + getWidth(), getRow() + getHeight());

        var otherUpperLeft = new Vector2(other.getColumn(), other.getRow());
        var otherLowerRight = new Vector2(other.getColumn() + other.getWidth(), other.getRow() + other.getHeight());

        return (upperLeft.x() < otherLowerRight.x() && lowerRight.x() > otherUpperLeft.x() + 4 &&
                upperLeft.y() < otherLowerRight.y() && lowerRight.y() > otherUpperLeft.y() + 2);

    }

    /**
     * Equals method for an entity (checks if entity == Object o)
     *
     * @param o the object to compare this entity to.
     * @author John Gauci
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity entity)) return false;
        return getRow() == entity.getRow() && getColumn() == entity.getColumn() && getWidth() == entity.getWidth() && getHeight() == entity.getHeight() && getHealth() == entity.getHealth() && getColor() == entity.getColor() && getDirection() == entity.getDirection();
    }

    /**
     * Gets a hash for the entity
     *
     * @return integer hash for this entity
     * @author John Gauci
     */
    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn(), getWidth(), getHeight(), getHealth(), getColor(), getDirection());
    }

    /**
     * Set the entities color to the specified value.
     *
     * @return if the entity is destroyable.
     * @author John Gauci
     */
    public boolean isDestroyable() {
        return destroyable;
    }
}
