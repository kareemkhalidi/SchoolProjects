package client.game;

import base.Controller;
import base.KeyInput;
import server.game.gamemodes.GameMode;

/**
 * Implements an abstract strategy class for communicating between a ClientGameHandler and a GameUI.
 *
 * @author John Gauci
 */

public class GameController extends Controller {
    private GameUI gameUI;
    private boolean paused;

    /**
     * Initializes a GameController with a subject to communicate to
     *
     * @param clientGameHandler the ClientGameHandler to communicate to
     * @author John Gauci
     */
    public GameController(ClientGameHandler clientGameHandler) {
        super(clientGameHandler);
        paused = false;
    }

    /**
     * Returns the ClientGameHandler to communicate to
     *
     * @return the ClientGameHandler to communicate to
     * @author John Gauci
     */
    @Override
    public ClientGameHandler getSubject() {
        return (ClientGameHandler) super.getSubject();
    }

    /**
     * Sets the GameUI for this controller
     *
     * @param gameUI GameUI for controller
     * @author John Gauci
     */
    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    /**
     * Processes the given keyInput from the GameUI
     *
     * @param keyInput the key input
     * @author John Gauci
     */
    @Override
    public void processKeyInput(KeyInput keyInput) {
        switch (keyInput.getCharacter()) {
            case 'w' -> getSubject().moveForward();
            case 'a' -> getSubject().moveLeft();
            case 's' -> getSubject().moveBackward();
            case 'd' -> getSubject().moveRight();
            case ' ' -> getSubject().shoot();
            case '\t' -> handlePauseResume();
        }
        switch (keyInput.getKeycode()) {
            case KeyInput.ARROW_UP -> getSubject().moveForward();
            case KeyInput.ARROW_LEFT -> getSubject().moveLeft();
            case KeyInput.ARROW_DOWN -> getSubject().moveBackward();
            case KeyInput.ARROW_RIGHT -> getSubject().moveRight();
            case KeyInput.ESCAPE -> handlePauseResume();
        }
    }

    /**
     * Handles the input to pause the game if not paused, or resume the game if paused
     *
     * @author John Gauci
     */
    public void handlePauseResume() {
        if (!paused) {
            paused = true;
            gameUI.stop();
            gameUI.clearPlaying();
            gameUI.setPauseMenu(false);
        } else {
            paused = false;
            gameUI.clearPauseMenu();
            gameUI.setPlaying();
            gameUI.start();
        }
    }

    /**
     * Ends the game
     *
     * @author John Gauci
     */
    public void handleGameOver() {
        gameUI.getDisplay().syncExec(() -> {
            gameUI.stop();
            gameUI.clearPlaying();
            gameUI.setPauseMenu(true);
        });
    }

    /**
     * Processes the input of hosting a game, passing the required map, game mode, player name,
     * and tank name to the ClientGameHandler
     *
     * @param mapName    the name of the map
     * @param gameMode   the game mode
     * @param playerName the name of the player
     * @param tankName   the name of the tank
     * @author John Gauci
     */
    public void processHostButton(String mapName, GameMode gameMode, String playerName, String tankName) {
        getSubject().host(mapName, gameMode, playerName, tankName);
    }

    /**
     * Processes the input of joining a game, passing the required host address, player name, and
     * tank name to the ClientGameHandler
     *
     * @param host       the host address
     * @param playerName the name of the player
     * @param tankName   the name of the tank
     * @author John Gauci
     */
    public void processJoinButton(String host, String playerName, String tankName) {
        getSubject().connect(host, playerName, tankName);
    }

    /**
     * Handles the input to disconnect and return to the main menu
     *
     * @author John Gauci
     */
    public void handleMainMenu() {
        gameUI.clearPauseMenu();
        getSubject().disconnect();
        gameUI.setMainMenu();
    }


}
