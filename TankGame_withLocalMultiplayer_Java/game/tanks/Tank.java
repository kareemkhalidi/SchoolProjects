package game.tanks;

import base.Direction;
import base.Vector2;
import base.Window;
import client.game.GameUI;
import game.Entity;
import game.Player;
import game.weapons.Weapon;
import server.game.ServerGameHandler;

/**
 * Abstract base code for the different tank classes.
 *
 * @author John Gauci
 */
public abstract class Tank extends Entity {

    private final Weapon weapon;

    private final String playerName;

    private final long playerID;

    private final int movementSpeed;

    private final double armor;

    /**
     * Constructor for a tank object.
     *
     * @param row       the tank's row (y) location.
     * @param column    the tank's column (x) location.
     * @param width     the tank's width.
     * @param height    the tank's height.
     * @param health    the tank's health.
     * @param color     the tank's color.
     * @param direction the direction that the tank is facing.
     * @param player    the player that the tank belongs to.
     * @param armor     the damage reduction a tank has applied to it.
     * @author John Gauci
     */
    public Tank(int row, int column, int width, int height, int health, int color, Weapon weapon, Direction direction, Player player, int movementSpeed, double armor) {
        super(row, column, width, height, health, color, direction, true);
        this.weapon = weapon;
        this.playerName = player.getName();
        this.playerID = player.getID();
        this.movementSpeed = movementSpeed;
        this.armor = armor;
    }

    /**
     * Returns the tanks weapon
     *
     * @return the tank's weapon.
     * @author John Gauci
     */
    public Weapon getWeapon() {
        return (weapon);
    }

    /**
     * Draws the tank at the correct location and orientation.
     *
     * @param window the window for the tank to be drawn in
     * @author John Gauci
     */
    @Override
    public void draw(Window window) {
        if (getDirection() == Direction.NORTH || getDirection() == Direction.SOUTH)
            window.drawPicture(getRow() - 12, getColumn() - 10, getWidth(), getHeight(), getDirection().ordinal());
        if (getDirection() == Direction.EAST || getDirection() == Direction.WEST)
            window.drawPicture(getRow() - 12, getColumn() - 10, getHeight(), getWidth(), getDirection().ordinal());
        window.drawText(getRow() - 25, getColumn() + 5 - (getPlayerName().length() / 2),
                getPlayerName(), 10, 10, GameUI.TEXT_COLOR, GameUI.BACKGROUND_COLOR, 0, 0, "Verdana");
        window.drawText(getRow() + 55, getColumn() - (getPlayerName().length() / 2),
                "HP: " + getHealth(), 10, 10, GameUI.TEXT_COLOR, GameUI.BACKGROUND_COLOR, 0, 0,
                "Verdana");
        //window.drawText(getRow() - );
    }

    public Double getArmor() {
        return armor;
    }

    /**
     * Rotates the tanks hit box to match its model.
     *
     * @author Kareem Khalidi
     */
    public void rotate() {
        int tempHeight = getHeight();
        setHeight(getWidth());
        setWidth(tempHeight);
    }

    /**
     * Returns the tanks movement speed.
     *
     * @return the tanks movement speed.
     * @author John Gauci
     */
    public int getMovementSpeed() {
        return movementSpeed;
    }

    /**
     * Returns the tanks playerID
     *
     * @return the tanks playerID.
     * @author John Gauci
     */
    public long getPlayerID() {
        return playerID;
    }

    /**
     * Returns the tanks playerName
     *
     * @return the tanks playerName
     * @author John Gauci
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Checks if the object passed in is equal to this.
     *
     * @param o the object to compare this tank to.
     * @return a boolean representing their equality.
     * @author John Gauci
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tank tank = (Tank) o;
        if (getPlayerID() != tank.getPlayerID()) return false;
        return getPlayerName().equals(tank.getPlayerName());
    }

    /**
     * Returns a Hash for the tank.
     *
     * @return an integer hash for the tank.
     * @author John Gauci
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getPlayerName().hashCode();
        result = 31 * result + (int) (getPlayerID() ^ (getPlayerID() >>> 32));
        return result;
    }

    /**
     * Overridable movement method for the tank.
     *
     * @param player            the player whose tank is moving.
     * @param serverGameHandler the handler for the server that the player is on.
     * @param direction         the direction the tank is moving.
     * @author John Gauci
     */
    public void move(Player player, ServerGameHandler serverGameHandler, Direction direction) {
        if (player.getTank().getHealth() <= 0) return;
        int oldRow = player.getTank().getRow();
        int oldColumn = player.getTank().getColumn();
        Direction oldDir = player.getTank().getDirection();
        player.getTank().setDirection(direction);
        Vector2 vector2 = new Vector2(0, 0);
        switch (direction) {
            case NORTH -> {
                vector2.y(-1 * player.getTank().getMovementSpeed());
                if (oldDir == Direction.EAST || oldDir == Direction.WEST) {
                    player.getTank().rotate();
                }
            }
            case EAST -> {
                vector2.x(player.getTank().getMovementSpeed());
                if (oldDir == Direction.SOUTH || oldDir == Direction.NORTH) {
                    player.getTank().rotate();
                }
            }
            case SOUTH -> {
                vector2.y(player.getTank().getMovementSpeed());
                if (oldDir == Direction.EAST || oldDir == Direction.WEST) {
                    player.getTank().rotate();
                }
            }
            case WEST -> {
                vector2.x(-1 * player.getTank().getMovementSpeed());
                if (oldDir == Direction.SOUTH || oldDir == Direction.NORTH) {
                    player.getTank().rotate();
                }
            }
        }
        player.getTank().setRow(player.getTank().getRow() + vector2.y());
        player.getTank().setColumn(player.getTank().getColumn() + vector2.x());
        for (Entity collideable : serverGameHandler.getCollideables()) {
            if (!collideable.equals(player.getTank()) && player.getTank().contacted(collideable) && !(collideable instanceof TankShell)) {
                player.getTank().setRow(oldRow);
                player.getTank().setColumn(oldColumn);
                break;
            }
        }

    }
}
