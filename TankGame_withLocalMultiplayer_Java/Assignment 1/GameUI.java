import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

//The UI class for the game page
public class GameUI extends JFrame {

	//Global variables used throughout the class
	private JPanel contentPane;
	private int numPlayers;
	private int curPlayer;
	private String rules;
	private ArrayList<String> imgNames;
	private Tile curTile1;
	private Tile curTile2;
	private Tile[][] tiles;
	private int[] playerScores;
	private int width;
	private int height;
	private JLabel[] lblPlayerScores;
	
	/**
	 * Constructer for the GameUI class. Takes parameters for the number of players, the rules
	 * and an arraylist of the names of all the images, and then initializes all the variables.
	 * Finally, it calls the setUpGameUI method which sets up all of the components that actually
	 * make up the UI.
	 */
	public GameUI(int numPlayers, String rules, ArrayList<String> imgNames) throws IOException {
		
		this.numPlayers = numPlayers;
		this.curPlayer = 1;
		this.rules = rules;
		this.imgNames = imgNames;
		this.width = 5;
		this.height = 4;
		this.tiles = new Tile[this.height][this.width];
		this.playerScores = new int[numPlayers];
		for(int i = 0; i < numPlayers; i++) {
			
			playerScores[i] = 0;
			
		}
		setUpGameUI();
		
	}

	/**
	 * Sets up all components for the Game UI and adds them to the content pane.
	 * */
	private void setUpGameUI() throws IOException {
		
		//settings for the JFrame itself
		setResizable(false);
		setTitle("Concentration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		//settings for the Content Pane
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//creating and setting up the JLabel that displays the current player
		JLabel lblCurPlayer = new JLabel("CURRENT PLAYER: 1");
		lblCurPlayer.setFont(new Font("DialogInput", Font.BOLD, 18));
		lblCurPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurPlayer.setBounds((134 * width) / 2 - 100, (134 * height), 200, 50);
		contentPane.add(lblCurPlayer);
		
		//Creating and setting up array of JLabels to display all 4 players scores.
		lblPlayerScores = new JLabel[4];
		
		lblPlayerScores[0] = new JLabel("PLAYER 1 SCORE: 0");
		lblPlayerScores[0].setFont(new Font("DialogInput", Font.BOLD, 18));
		lblPlayerScores[0].setBounds(20, 134 * height + 50, 210, 40);
		contentPane.add(lblPlayerScores[0]);
		
		lblPlayerScores[1] = new JLabel("PLAYER 2 SCORE: 0");
		lblPlayerScores[1].setFont(new Font("DialogInput", Font.BOLD, 18));
		lblPlayerScores[1].setBounds(20, 134 * height + 100, 210, 40);
		contentPane.add(lblPlayerScores[1]);
		//if player 2 isnt playing in this game (aka theres only 1 player), make the label invisible
		if(numPlayers <= 1) {
			
			lblPlayerScores[1].setVisible(false);
			
		}
		
		lblPlayerScores[2] = new JLabel("PLAYER 3 SCORE: 0");
		lblPlayerScores[2].setFont(new Font("DialogInput", Font.BOLD, 18));
		lblPlayerScores[2].setBounds(134 * width - 210, 134 * height + 50, 210, 40);
		contentPane.add(lblPlayerScores[2]);
		//if player 3 isnt playing in this game (aka theres only 1 or 2 players), make the label invisible
		if(numPlayers <= 2) {
			
			lblPlayerScores[2].setVisible(false);
			
		}
		
		lblPlayerScores[3] = new JLabel("PLAYER 4 SCORE: 0");
		lblPlayerScores[3].setFont(new Font("DialogInput", Font.BOLD, 18));
		lblPlayerScores[3].setBounds(134 * width - 210, 134 * height + 100, 210, 40);
		contentPane.add(lblPlayerScores[3]);
		//if player 4 isnt playing in this game (aka theres only 1, 2, or 3 players), make the label invisible
		if(numPlayers <= 3) {
			
			lblPlayerScores[3].setVisible(false);
			
		}
		
		//Setting up the 2d array of tiles.
		int counter = 0;
		for(int i = 0; i < height; i++) {
			
			for(int j = 0; j < width; j++) {
				
				//creating and setting up the current tile.
				Tile t = new Tile(imgNames.get(counter));
				t.setBounds((134 * j) + 10, (134 * i) + 10, 124, 124);
				t.setVisible(true);
				//The brains of the game. This code is executed whenever a tile is clicked.
				t.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseClicked(MouseEvent e) {
						
						//if there are currently no tiles flipped, flip the tile and save it under curTile1
						if(curTile1 == null) {
							
							curTile1 = (Tile)e.getSource();
							curTile1.setImgVisible();
							
						}
						//if there is 1 tile flipped AND the tile clicked is not the same as the first one,
						//then flip the clicked tile and save it under curTile2
						else if(curTile2 == null && !curTile1.equals((Tile)e.getSource())) {
							
							curTile2 = (Tile)e.getSource();
							curTile2.setImgVisible();
							
						}
						//If there are 2 tiles flipped, this code will run.
						else if(curTile1 != null && curTile2 != null){
							
							//if the 2 flipped tiles have the same image
							if(curTile1.getImgName().equals(curTile2.getImgName())) {
								
								//remove both tiles
								curTile1.setVisible(false);
								curTile2.setVisible(false);
								//then increment the current players score and update the label displaying their score
								playerScores[curPlayer - 1]++;
								lblPlayerScores[curPlayer - 1].setText("PLAYER " + (curPlayer) + " SCORE: " + playerScores[curPlayer - 1]);
								//then check if the Standard rules are being used. If they are, subtract 1 from curPlayer.
								//Since we will be incrementing curPlayer by 1 later, this effectively gives the current player another turn
								//since they got a match.
								if(rules.equals("Standard")) {
									
									curPlayer--;
									
								}
								
							}
							//if the 2 tiles flipped do not have the same image, flip them back over.
							else {
								
								curTile1.setImgInvisible();
								curTile2.setImgInvisible();
								
							}
							//unsave the flipped tiles because they are no longer flipped over
							curTile1 = null;
							curTile2 = null;
							//call the nextPlayer() method to change curPlayer to the actual current player and update the cur player label
							nextPlayer();
							lblCurPlayer.setText("CURRENT PLAYER: " + curPlayer);
							
						}
						
					}
				});
				//saves the tile that was just created and set to the correct location in the array of Tiles.
				tiles[i][j] = t;
				add(tiles[i][j]);
				counter++;
				
			}
			
		}
		
		//sets the size of the frame relative to the width and height (width and height in num tiles)
		setSize((134 * width) + 25, (134 * height) + 195);
		
		//Creates and sets up the restart button. When the button is clicked, the image name arrays are
		//reshuffled, and a new homepage is created and set visible, and the game page is set invisible,
		//effectively restarting the game.
		JButton btnRestart = new JButton("RESTART");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ConcentrationGame.h = new HomeUI();
				ConcentrationGame.h.setLocation(ConcentrationGame.g.getX(), ConcentrationGame.g.getY());
				ConcentrationGame.h.setVisible(true);
				ConcentrationGame.g.setVisible(false);
				Collections.shuffle(ConcentrationGame.animalImgNames);
				Collections.shuffle(ConcentrationGame.plantImgNames);
				Collections.shuffle(ConcentrationGame.objectImgNames);
				
			}
		});
		btnRestart.setFont(new Font("DialogInput", Font.BOLD, 18));
		btnRestart.setBounds((134 * width) / 2 - 57, (134 * height) + 75, 124, 50);
		contentPane.add(btnRestart);
		
	}
	
	//method that increments curPlayer and then resets it to 1 if it overflowed past the number of
	//players in the game.
	private void nextPlayer() {
		
		curPlayer++;
		if(curPlayer > numPlayers) {
			
			curPlayer = 1;
			
		}
		
	}
	
}
