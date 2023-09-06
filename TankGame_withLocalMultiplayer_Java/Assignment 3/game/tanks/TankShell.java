package game.tanks;

import base.Direction;
import base.Window;
import game.Entity;
import game.Player;
import server.game.ServerGameHandler;
import server.net.commands.MoveTankShell;

import java.util.Objects;


/**
 * Class for the tank shells that are fired when a tank shoots.
 *
 * @author John Gauci
 */
public class TankShell extends Projectile {

    /**
     * Constructor for a new tank shell
     *
     * @param row       the tank shells row
     * @param column    the tank shells column
     * @param width     the tank shells width
     * @param height    the tank shells height
     * @param health    the tank shells health
     * @param color     the tank shells color
     * @param direction the tank shells direction
     * @param firedFrom the player that shot the tank shell
     * @author John Gauci
     */
    public TankShell(int row, int column, int width, int height, int health, int color, Direction direction, Player firedFrom) {
        super(row, column, width, height, health, color, direction, firedFrom);
    }



    /**
     * Overridden draw method that draws the tank shell
     *
     * @author John Gauci
     */
    @Override
    public void draw(Window window) {
        window.drawRectangle(getRow(), getColumn(), getWidth(), getHeight(), getColor(), getColor());
    }

    /**
     * overridden equals method that checks if the tank shell is equal to the specified object
     *
     * @param o the object to compare the tank shell to
     * @return boolean value representing if the tank shell and object are the same
     * @author John Gauci
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TankShell tankShell)) return false;
        return getFiredFrom().equals(tankShell.getFiredFrom());
    }


    /**
     * Overridden hash method that generates hash for the tank shell
     *
     * @return int tank shell hash code
     * @author John Gauci
     */
    @Override
    public int hashCode() {
        return Objects.hash(getFiredFrom());
    }



    /**
     * Handles the tank shells movement
     *
     * @param serverGameHandler the handler for the game
     * @author John Gauci
     */
    public void move(ServerGameHandler serverGameHandler) {
        boolean destroyed = false;
        for (Entity collideable : serverGameHandler.getCollideables()) {
            if (!collideable.equals(this) && this.contacted(collideable)) {
                if (collideable.isDestroyable()) {
                    collideable.setHealth((int) (collideable.getHealth() - (this.getDamage() * ((Tank) collideable).getArmor())));
                    if (collideable.getHealth() <= 0) {
                        serverGameHandler.getPlayer(collideable).orElseThrow().getPlayerStats().addDeath();
                        Player killer = serverGameHandler.getPlayer(this.getFiredFrom()).orElseThrow();
                        killer.getPlayerStats().addKill();
                        killer.getPlayerStats().addScore(serverGameHandler.getGameMode().getKillPoints());
                        serverGameHandler.getEntities().remove(collideable);
                    }
                }
                destroyed = true;
                break;
            }
        }

        if (!destroyed) {
            int shellDelta = this.getProjectileSpeed() / serverGameHandler.getTickRate();
            switch (this.getDirection()) {
                case NORTH -> this.setRow(this.getRow() - shellDelta);
                case EAST -> this.setColumn(this.getColumn() + shellDelta);
                case SOUTH -> this.setRow(this.getRow() + shellDelta);
                case WEST -> this.setColumn(this.getColumn() - shellDelta);
            }
            serverGameHandler.addCommand(new MoveTankShell(this));
        } else {
            serverGameHandler.getEntities().remove(this);
        }
    }
}
