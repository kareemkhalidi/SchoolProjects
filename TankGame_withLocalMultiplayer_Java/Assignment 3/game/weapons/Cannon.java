package game.weapons;

import base.Window;
import game.Player;
import game.tanks.TankShell;
import server.game.ServerGameHandler;
import server.net.commands.MoveTankShell;

/**
 * Implementation of a weapon that shoots bullets in a straight line.
 *
 * @author John Gauci
 */
public class Cannon extends Weapon {
    private long nextShootTime;

    /**
     * Constructor for a cannon object
     *
     * @param damage          the damage of the cannon.
     * @param firingDelay     the firing speed of the cannon.
     * @param projectileSpeed the speed of the cannons projectiles.
     * @author John Gauci
     */
    public Cannon(int damage, int firingDelay, int projectileSpeed) {
        super(damage, firingDelay, projectileSpeed);
        nextShootTime = 0;
    }

    /**
     * Fires the cannon.
     *
     * @param shooter           the player that fired the cannon.
     * @param serverGameHandler the handler for the server.
     * @author Kareem Khalidi
     */
    @Override
    public void fire(Player shooter, ServerGameHandler serverGameHandler) {
        if (shooter.getTank().getHealth() <= 0 || System.currentTimeMillis() < nextShootTime)
            return;
        int shellRow = shooter.getTank().getRow() + shooter.getTank().getHeight() / 2;
        int shellColumn = shooter.getTank().getColumn() + shooter.getTank().getWidth() / 2;
        switch (shooter.getTank().getDirection()) {
            case NORTH -> shellRow -= 50;
            case EAST -> shellColumn += 50;
            case SOUTH -> shellRow += 50;
            case WEST -> shellColumn -= 50;
        }
        TankShell tankShell = new TankShell(shellRow, shellColumn, 10, 10, 0,
                Window.COLOR_YELLOW, shooter.getTank().getDirection(), shooter
        );
        serverGameHandler.getEntities().add(tankShell);
        nextShootTime = System.currentTimeMillis() + getFiringDelay();
        serverGameHandler.addCommand(new MoveTankShell(tankShell));

    }
}
