import javax.swing.JPanel;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class Tile extends JPanel {

	//Global variables used throughout the class
	private String imgName;
	private JLabel img;
	
	/**
	 * The Constructor for the Tile object. A tile is a JPanel
	 * that has a certain image. The image starts hidden.
	 */
	public Tile(String imgName) throws IOException {
		
		
		this.imgName = imgName;
		setUpTile();

	}
	
	//sets up the tile settings as well as the image.
	private void setUpTile() throws IOException {
		
		//setting panel specs
		setLayout(null);
		setBounds(0, 0, 124, 124);
		setBackground(Color.GRAY);
		
		//setting label specs (label displays img)
		img = new JLabel("");
		img.setIcon(new ImageIcon(ImageIO.read(new File(imgName))));
		img.setBounds(12, 12, 100, 100);
		img.setVisible(false);
		add(img);
		
	}

	//method to unhide the tiles image
	public void setImgVisible() {
		
		img.setVisible(true);
		
	}
	
	//method to hide the tiles image
	public void setImgInvisible() {
		
		img.setVisible(false);
		
	}
	
	//method that returns the name of the tiles image
	public String getImgName() {
		
		return(this.imgName);
		
	}
	
}
