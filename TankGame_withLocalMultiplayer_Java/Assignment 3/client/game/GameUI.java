package client.game;

import base.Observer;
import base.SWTKeyInput;
import base.SWTWindow;
import base.Window;
import game.PlayerStats;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import server.game.GameData;
import server.game.gamemodes.DeathMatch;
import server.game.gamemodes.GameMode;
import server.game.gamemodes.OneLife;

import java.util.ArrayList;

/**
 * Implementation of a GameUI observer that handles the UI for the game while in the menu, while
 * playing, or while paused.
 *
 * @author John Gauci
 * @author Kareem Khalidi
 */

public class GameUI extends Observer {
    public static final int CANVAS_DIMENSIONS = 800;
    private static final int FRAMERATE = 60;
    private static final long RENDER_DELAY = (long) ((1.0 / FRAMERATE) * 1000);
    public static int TEXT_COLOR;
    public static int BACKGROUND_COLOR;
    private final Display display;
    private final Shell shell;
    private final GameController gameController;
    private final ArrayList<Widget> widgets;
    private PaintListener paintListener;
    private KeyListener keyListener;
    private Canvas canvas;

    /**
     * Initializes an GameUI with the ClientGameHandler to observe and GameController.
     *
     * @param clientGameHandler the ClientGameHandler to observe
     * @param gameController    the GameController
     * @author John Gauci
     */
    public GameUI(ClientGameHandler clientGameHandler, GameController gameController) {
        super(clientGameHandler);
        this.gameController = gameController;
        display = Display.getDefault();
        shell = new Shell(display, SWT.SHELL_TRIM & ~(SWT.RESIZE | SWT.MIN | SWT.MAX | SWT.CLOSE));
        if (Display.isSystemDarkTheme()) {
            BACKGROUND_COLOR = Window.COLOR_BLACK;
            TEXT_COLOR = Window.COLOR_WHITE;
        } else {
            BACKGROUND_COLOR = Window.COLOR_WHITE;
            TEXT_COLOR = Window.COLOR_BLACK;
        }
        shell.setBackground(display.getSystemColor(BACKGROUND_COLOR));
        SWTWindow.addImage("assets\\graphics\\tankup.png");
        SWTWindow.addImage("assets\\graphics\\tankright.png");
        SWTWindow.addImage("assets\\graphics\\tankdown.png");
        SWTWindow.addImage("assets\\graphics\\tankleft.png");
        widgets = new ArrayList<>();

    }

    /**
     * Gets the GameData of the subject.
     *
     * @return the GameData of the subject.
     * @author John Gauci
     */
    @Override
    public GameData getData() {
        return (GameData) super.getData();
    }

    /**
     * Returns the display used by the GameUI
     *
     * @return display
     * @author John Gauci
     */
    public Display getDisplay() {
        return display;
    }

    /**
     * Clears the widgets on the main menu
     *
     * @author John Gauci
     */
    public void clearMainMenu() {
        widgets.forEach(Widget::dispose);
        widgets.clear();
    }

    /**
     * Sets the shell with the main menu widgets
     *
     * @author John Gauci
     * @author Kareem Khalidi
     */
    public void setMainMenu() {
        shell.setText("Tank Game");
        shell.setSize(400, 425);
        shell.setLayout(new GridLayout(1, false));
        shell.requestLayout();
        GridData entryFieldData = new GridData(SWT.BEGINNING, SWT.LEFT, false, false);
        entryFieldData.widthHint = 150;
        Label nameEntryLabel = new Label(shell, SWT.NULL);
        nameEntryLabel.setText("Name: ");
        Text nameEntryField = new Text(shell, SWT.BORDER);
        nameEntryField.setSize(100, 10);
        nameEntryField.setLayoutData(entryFieldData);
        Label ipEntryLabel = new Label(shell, SWT.NULL);
        ipEntryLabel.setText("IP: ");
        Text ipEntryField = new Text(shell, SWT.BORDER);
        ipEntryField.setSize(100, 10);
        ipEntryField.setLayoutData(entryFieldData);
        Combo tankCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
        tankCombo.setItems("Basic tank", "Blitz tank");
        tankCombo.select(0);

        Combo gameModeCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
        gameModeCombo.setItems("One Life", "Death Match");
        gameModeCombo.select(0);

        Combo mapCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
        mapCombo.setItems("Map A", "Map B");
        mapCombo.select(0);

        Button joinButton = new Button(shell, SWT.PUSH);
        joinButton.setText("Join Game");
        joinButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String playerName = nameEntryField.getText();
                if (playerName.isBlank()) playerName = "Default";
                String tankName = "BasicTank";
                if (tankCombo.getSelectionIndex() == 0) tankName = "BasicTank";
                if (tankCombo.getSelectionIndex() == 1) tankName = "BlitzTank";
                String ipText = ipEntryField.getText();
                if (ipText.isBlank()) ipText = "127.0.0.1";
                try {
                    shell.setText("Tank Game - Player: " + playerName + " Server: " + ipText);
                    getSubject().start();
                    clearMainMenu();
                    setPlaying();
                    start();
                    gameController.processJoinButton(ipText, playerName, tankName);
                } catch (Exception ex) {
                    getSubject().stop();
                    stop();
                    clearPlaying();
                    setMainMenu();
                    MessageBox ipErrorBox = new MessageBox(shell);
                    ipErrorBox.setText("IP ERROR");
                    ipErrorBox.setMessage("Invalid IP was entered, please try again with a " +
                            "valid IP.");
                    ipErrorBox.open();
                }

            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        Button hostButton = new Button(shell, SWT.PUSH);
        hostButton.setText("Host Game");
        hostButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                String playerName = nameEntryField.getText();
                if (playerName.isBlank()) playerName = "Default";
                String tankName = "BasicTank";
                if (tankCombo.getSelectionIndex() == 0) tankName = "BasicTank";
                if (tankCombo.getSelectionIndex() == 1) tankName = "BlitzTank";
                String mapName = "mapA.tnk";
                if (mapCombo.getSelectionIndex() == 0) mapName = "mapA.tnk";
                if (mapCombo.getSelectionIndex() == 1) mapName = "mapB.tnk";
                GameMode gameMode = new OneLife();
                if (gameModeCombo.getSelectionIndex() == 0) gameMode = new OneLife();
                if (gameModeCombo.getSelectionIndex() == 1) gameMode = new DeathMatch();
                gameController.processHostButton(mapName, gameMode, playerName, tankName);
                shell.setText("Tank Game - Player: " + playerName + " Server: " + "Host");
                clearMainMenu();
                getSubject().start();
                setPlaying();
                start();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        Button exitButton = new Button(shell, SWT.PUSH);
        exitButton.setText("Exit");
        exitButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                clearMainMenu();
                System.exit(0);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });

        Button debugButton = new Button(shell, SWT.PUSH);
        debugButton.setText("Debug");
        debugButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                System.out.println("DEBUG: " + Thread.activeCount() + " active Java threads: ");
                Thread[] threads = new Thread[Thread.activeCount()];
                Thread.enumerate(threads);
                for (Thread thread : threads) {
                    System.out.println("Thread name: " + thread.getName());
                }
                System.out.println();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });
        debugButton.setVisible(false);

        widgets.add(nameEntryLabel);
        widgets.add(nameEntryField);
        widgets.add(ipEntryLabel);
        widgets.add(ipEntryField);
        widgets.add(tankCombo);
        widgets.add(gameModeCombo);
        widgets.add(mapCombo);
        widgets.add(joinButton);
        widgets.add(hostButton);
        widgets.add(exitButton);
        widgets.add(debugButton);
    }

    /**
     * Renders what is to be drawn to the canvas
     *
     * @author John Gauci
     */
    public void render() {
        if (!canvas.isDisposed()) canvas.redraw();
    }

    /**
     * Clears the widgets used for playing
     *
     * @author John Gauci
     */
    public void clearPlaying() {
        if (!canvas.isDisposed()) {
            canvas.removePaintListener(paintListener);
            canvas.removeKeyListener(keyListener);
            canvas.dispose();
        }
        widgets.clear();
    }

    /**
     * Sets the widgets used for playing
     *
     * @author John Gauci
     */
    public void setPlaying() {
        shell.setSize(CANVAS_DIMENSIONS + 14, CANVAS_DIMENSIONS + 38);
        canvas = new Canvas(shell, SWT.DOUBLE_BUFFERED);
        canvas.setFocus();
        canvas.setBackground(display.getSystemColor(BACKGROUND_COLOR));
        canvas.setSize(CANVAS_DIMENSIONS, CANVAS_DIMENSIONS);
        paintListener = paintEvent -> getData().draw(new SWTWindow(paintEvent));

        keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                gameController.processKeyInput(new SWTKeyInput(keyEvent));
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }
        };
        canvas.addPaintListener(paintListener);
        canvas.addKeyListener(keyListener);
        widgets.add(canvas);
    }

    /**
     * Renders the UI in a loop
     *
     * @author John Gauci
     */
    @Override
    public void loopExecute() {
        try {
            Thread.sleep(RENDER_DELAY);
            Display.getDefault().syncExec(this::render);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the widgets used for the pause menu
     *
     * @author John Gauci
     */
    public void clearPauseMenu() {
        widgets.forEach(Widget::dispose);
        widgets.clear();
        shell.removeKeyListener(keyListener);
    }

    /**
     * Sets the widgets for the pause menu/game over based on the given condition
     *
     * @param gameOver whether the game is over
     * @author Kareem Khalidi
     * @author John Gauci
     */
    public void setPauseMenu(boolean gameOver) {
        shell.setSize(800, 800);
        shell.setFocus();
        shell.setLayout(new GridLayout(1, false));
        Label pauseTitle = new Label(shell, SWT.NONE);
        if (gameOver) {
            pauseTitle.setText("Game Over");
        } else {
            pauseTitle.setText("Pause");
        }

        Table scoreBoardTable = new Table(shell, SWT.BORDER | SWT.V_SCROLL);
        scoreBoardTable.setSize(400, 500);
        scoreBoardTable.setHeaderVisible(true);
        scoreBoardTable.setBackground(display.getSystemColor(BACKGROUND_COLOR));
        TableColumn nameColumn = new TableColumn(scoreBoardTable, SWT.NULL);
        nameColumn.setText("NAME");
        TableColumn scoreColumn = new TableColumn(scoreBoardTable, SWT.NULL);
        scoreColumn.setText("SCORE");
        TableColumn killsColumn = new TableColumn(scoreBoardTable, SWT.NULL);
        killsColumn.setText("KILLS");
        TableColumn deathsColumn = new TableColumn(scoreBoardTable, SWT.NULL);
        deathsColumn.setText("DEATHS");
        TableColumn pingColumn = new TableColumn(scoreBoardTable, SWT.NULL);
        pingColumn.setText("PING");
        int i = 0;
        for (PlayerStats stats : getData().getPlayerStats()) {
            TableItem item = new TableItem(scoreBoardTable, SWT.NULL);
            item.setText(stats.getName());
            item.setText(0, stats.getName());
            item.setText(1, Integer.toString(stats.getScore()));
            item.setText(2, Integer.toString(stats.getKills()));
            item.setText(3, Integer.toString(stats.getDeaths()));
            item.setText(4, Long.toString(stats.getPing()));
            if (i % 2 == 0) {
                item.setBackground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
            } else {
                item.setBackground(display.getSystemColor(SWT.COLOR_GREEN));
            }
            i++;
        }
        for (TableColumn column : scoreBoardTable.getColumns()) {
            column.pack();
        }
        scoreBoardTable.addListener(SWT.EraseItem, event -> scoreBoardTable.deselectAll());
        if (!gameOver) {
            Button resumeButton = new Button(shell, SWT.PUSH);
            resumeButton.setText("Resume");
            resumeButton.addSelectionListener(new SelectionListener() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    gameController.handlePauseResume();
                }

                @Override
                public void widgetDefaultSelected(SelectionEvent e) {
                }
            });
            widgets.add(resumeButton);
        }

        Button mainMenuButton = new Button(shell, SWT.PUSH);
        mainMenuButton.setText("Main Menu");
        mainMenuButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                gameController.handleMainMenu();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {

            }
        });
        keyListener = new KeyListener() {

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                gameController.processKeyInput(new SWTKeyInput(keyEvent));
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        };
        shell.addKeyListener(keyListener);
        shell.layout(true, true);
        widgets.add(pauseTitle);
        widgets.add(scoreBoardTable);
        widgets.add(mainMenuButton);
    }

    /**
     * Opens the GameUI, setting the main menu and waiting for events
     *
     * @author John Gauci
     */
    public void open() {
        setMainMenu();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }


}
