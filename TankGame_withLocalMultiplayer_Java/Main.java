import client.game.ClientGameHandler;
import client.game.GameController;
import client.game.GameUI;

/**
 * Main class that initializes all necessary objects and starts the game and ui.
 *
 * @author John Gauci
 */
public class Main {
    public static void main(String[] args) {
        ClientGameHandler clientGameHandler = new ClientGameHandler();
        GameController gameController = new GameController(clientGameHandler);
        clientGameHandler.setController(gameController);
        GameUI gameUI = new GameUI(clientGameHandler, gameController);
        gameController.setGameUI(gameUI);
        gameUI.open();
    }

}
