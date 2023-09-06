package game;

import base.Direction;
import base.Vector2;
import base.Window;
import client.game.GameUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Map object that loads and represents the games map.
 *
 * @author Kareem Khalidi
 */
public class Map extends Entity {

    private final ArrayList<Wall> walls;
    private final LinkedList<Vector2> spawnPoints;

    /**
     * Constructor for a map.
     *
     * @param mapFileName the name of the file containing the map.
     * @author Kareem Khalidi
     */
    public Map(String mapFileName) {
        super(0, 0, GameUI.CANVAS_DIMENSIONS, GameUI.CANVAS_DIMENSIONS, -1, -1,
                Direction.NORTH, false);
        walls = new ArrayList<>(200);
        spawnPoints = new LinkedList<>();
        loadMap("assets\\maps\\" + mapFileName);
    }

    /**
     * Gets all the walls of the map
     *
     * @return Array List of all the maps walls.
     * @author Kareem Khalidi
     */
    public ArrayList<Wall> getWalls() {
        return walls;
    }

    /**
     * Gets all the spawn points of the map
     *
     * @return Linked List of all map spawn points.
     * @author Kareem Khalidi
     */
    public LinkedList<Vector2> getSpawnPoints() {
        return spawnPoints;
    }

    /**
     * Loads the map based on the provided file.
     * 0 is open space, 1 is a wall, 2 is a spawn point.
     *
     * @param mapFileName the name of the file containing the map.
     * @author Kareem Khalidi
     */
    private void loadMap(String mapFileName) {

        //make scanner for file
        Scanner mapFileScanner;
        try {
            mapFileScanner = new Scanner(new File(mapFileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //load the file into a 2d array
        String[] curLine = mapFileScanner.nextLine().split("");
        int size = curLine.length;
        String[][] mapData = new String[size][size];
        mapData[0] = curLine;
        int curLineNum = 1;
        while (mapFileScanner.hasNextLine()) {
            mapData[curLineNum] = mapFileScanner.nextLine().split("");
            curLineNum++;
        }

        int wallSize = GameUI.CANVAS_DIMENSIONS / size;
        //for each 1 in the array, add a wall where it is located
        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {

                if (mapData[i][j].equals("1")) {
                    walls.add(new Wall(i * wallSize, j * wallSize, wallSize, wallSize));
                }
                if (mapData[i][j].equals("2")) {
                    spawnPoints.add(new Vector2(j * wallSize, i * wallSize));
                }

            }

        }

    }

    /**
     * Overridden draw method for a map that draws all the walls.
     *
     * @param window the window to draw the map in.
     * @author John Gauci
     */
    @Override
    public void draw(Window window) {
        walls.forEach(wall -> wall.draw(window));
    }

}
