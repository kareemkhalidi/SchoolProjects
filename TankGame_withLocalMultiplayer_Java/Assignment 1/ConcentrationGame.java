import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ConcentrationGame {

	//Global variables
	public static HomeUI h;
	protected static GameUI g;
	public static ArrayList<String> animalImgNames;
	public static ArrayList<String> plantImgNames;
	public static ArrayList<String> objectImgNames;
	
	/**
	 * Main method that gets executed when the program is first ran.
	 * Initializes the arrays with the img names for the different tile sets.
	 * In order to add a new tile set, create a new global arraylist object and
	 * initialize and randomize it exactly how the already implemented tile sets are initialized.
	 * 
	 * After the tile sets are initialized, a new HomeUI is created and set visible. This causes
	 * the home page window to open for the user, where they can then choose the number of 
	 * players, the image set they want to use, and the rule set they want to play with.
	 * */
	public static void main(String args[]) throws IOException {
		
		//setting the ImgNames arrays to have all of the img names for the specified tileset
		//and then creating two of each image by adding the array to itself
		//and finally shuffling the array.
		animalImgNames = new ArrayList<String>(Arrays.asList("bear.jpg", "cat.jpg", "dog.jpg",
				"elephant.jpg", "fish.jpg", "lion.jpg", "snake.jpg", "swan.jpg", "toucan.jpg", "zebra.jpg"));
		animalImgNames.addAll(animalImgNames);
		Collections.shuffle(animalImgNames);
		
		plantImgNames = new ArrayList<String>(Arrays.asList("cactus.jpg", "daisy.jpg", "evergreen.jpg",
				"fern.jpg", "oak.jpg", "palm tree.jpg", "sunflower.jpg", "tulip.jpg", "vine.jpg", "wheat.jpg"));
		plantImgNames.addAll(plantImgNames);
		Collections.shuffle(plantImgNames);
		
		objectImgNames = new ArrayList<String>(Arrays.asList("bike.jpg", "controller.jpg", "hairbrush.jpg",
				"lego.jpg", "pencil.jpg", "pot.jpg", "shoe.jpg", "skateboard.jpg", "toothbrush.jpg", "towels.jpg"));
		objectImgNames.addAll(objectImgNames);
		Collections.shuffle(objectImgNames);
		
		//initializing the Home page UI and making it visible so the user can interact with it
		h = new HomeUI();
		h.setVisible(true);
		
	}
	
}
