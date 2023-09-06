import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class HomeUI extends JFrame {

	//Global variables used throughout the class
	private JPanel contentPane;
	private JComboBox<Integer> cbPlayers;
	private JComboBox<String> cbCardSet;
	private JComboBox<String> cbRules;

	/**
	 * Constructor for the HomeUI class. Creates and sets up all of the components for the Home UI.
	 */
	public HomeUI() {
		
		//settings for the JFrame
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 237, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		//settings for the content pane
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Creating and setting up the title label
		JLabel Title = new JLabel("CONCENTRATION");
		Title.setFont(new Font("DialogInput", Font.BOLD, 24));
		Title.setHorizontalTextPosition(SwingConstants.CENTER);
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		Title.setBounds(10, 11, 200, 30);
		contentPane.add(Title);
		
		//Initializing and setting up the JComboBox where the user selects a number of players and the label to
		//communicate this to the users
		cbPlayers = new JComboBox<Integer>();
		cbPlayers.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1, 2, 3, 4}));
		cbPlayers.setToolTipText("");
		cbPlayers.setFont(new Font("DialogInput", Font.BOLD, 18));
		cbPlayers.setBounds(87, 84, 40, 22);
		contentPane.add(cbPlayers);
		
		JLabel lblPlayers = new JLabel("PLAYERS:");
		lblPlayers.setFont(new Font("DialogInput", Font.BOLD, 18));
		lblPlayers.setBounds(67, 52, 94, 22);
		contentPane.add(lblPlayers);
		
		//Initializing and setting up the JComboBox where the user selects a card set and the label to
		//communicate this to the users
		JLabel lblCardSet = new JLabel("CARD SET:");
		lblCardSet.setFont(new Font("DialogInput", Font.BOLD, 18));
		lblCardSet.setBounds(59, 117, 109, 22);
		contentPane.add(lblCardSet);
		
		cbCardSet = new JComboBox<String>();
		cbCardSet.setModel(new DefaultComboBoxModel<String>(new String[] {"Animals", "Plants", "Objects"}));
		cbCardSet.setToolTipText("PLAYERS");
		cbCardSet.setFont(new Font("DialogInput", Font.BOLD, 18));
		cbCardSet.setBounds(51, 150, 114, 22);
		contentPane.add(cbCardSet);
		
		//Initializing and setting up the JComboBox where the user selects the rules and the label to
		//communicate this to the users
		JLabel lblRules = new JLabel("RULES:");
		lblRules.setHorizontalAlignment(SwingConstants.CENTER);
		lblRules.setFont(new Font("DialogInput", Font.BOLD, 18));
		lblRules.setBounds(56, 184, 109, 22);
		contentPane.add(lblRules);
		
		cbRules = new JComboBox<String>();
		cbRules.setModel(new DefaultComboBoxModel<String>(new String[] {"Standard", "One Flip"}));
		cbRules.setToolTipText("");
		cbRules.setFont(new Font("DialogInput", Font.BOLD, 18));
		cbRules.setBounds(51, 217, 114, 22);
		contentPane.add(cbRules);
		
		//Creating and setting up the start button. When clicked, the button creates a new GameUI object and gets
		//the data from the 3 JComboBoxes to pass in as parameters, then hides the home page and displays the game page
		JButton btnStart = new JButton("START");
		btnStart.setFont(new Font("DialogInput", Font.BOLD, 18));
		btnStart.setBounds(63, 255, 89, 23);
		contentPane.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(((String)cbCardSet.getSelectedItem()).equals("Animals")) {
					
					try {
						ConcentrationGame.g = new GameUI((int)cbPlayers.getSelectedItem(), (String)cbRules.getSelectedItem(), ConcentrationGame.animalImgNames);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else if(((String)cbCardSet.getSelectedItem()).equals("Plants")) {
					
					try {
						ConcentrationGame.g = new GameUI((int)cbPlayers.getSelectedItem(), (String)cbRules.getSelectedItem(), ConcentrationGame.plantImgNames);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else if(((String)cbCardSet.getSelectedItem()).equals("Objects")) {
					
					try {
						ConcentrationGame.g = new GameUI((int)cbPlayers.getSelectedItem(), (String)cbRules.getSelectedItem(), ConcentrationGame.objectImgNames);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				ConcentrationGame.h.setVisible(false);
				ConcentrationGame.g.setVisible(true);
				ConcentrationGame.g.setLocation(ConcentrationGame.h.getX(), ConcentrationGame.h.getY());
				
			}
		});
		
	}
	
}
