package assignment1;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CluedoBoardUI extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JFrame gui;
	private JPanel board;
	
	private int playerNum;
	
	private ArrayList<CharToken> charArray = new ArrayList<CharToken>();
	
	GameSetup setup = new GameSetup();
	
	// Board Squares
	private JButton[][] boardGrid = new JButton[25][25];
	
	// Background Image 
	private BufferedImage boardImage;
	
	// Character Icon
	private ImageIcon scarlett;
	private ImageIcon plum;
	private ImageIcon peacock;
	private ImageIcon green;
	private ImageIcon mustard;
	private ImageIcon white;
	
	@SuppressWarnings("unlikely-arg-type")
	public CluedoBoardUI() {
		
		board = new JPanel(new GridLayout(25, 25));
		
		findCharacterIcons();
		
		try {
			boardImage = ImageIO.read(getClass().getResourceAsStream("board.jpg"));
		}catch (IOException e) {
			System.out.println("Could not find the image file " + e.toString());
		}
		
		// Setting up player tokens
		playerNum = setup.playerNum();
		int[] selectedChar = new int[6];
		int temp;
		
		for(int x = 0; x < playerNum; x++) {
			temp = setup.characterSelect();
			// Check to see if player is already selected
			while(Arrays.asList(selectedChar).contains(temp)) {
				JOptionPane.showMessageDialog(null, "That character is already selected, please select another.");
				temp = setup.characterSelect();
			}
			
			selectedChar[x] = temp;
		}
		
		// Event Handler
		MoveHandler moveHandler = new MoveHandler();
		
		// Add components to board
		for(int x = 0; x < 25; x++) {
			for(int y = 0; y < 25; y++) {
				boardGrid[x][y] = new JButton();
				boardGrid[x][y].addActionListener(moveHandler);
				boardGrid[x][y].setOpaque(false);
				boardGrid[x][y].setContentAreaFilled(false);
				board.add(boardGrid[x][y]);
			}
		}
		
		// Adding Player tokens to board
		ImageIcon charImage = new ImageIcon("white.png");
		int charNum, charRow, charCol;
		for(int x = 0; x < playerNum; x++) {
			charNum = selectedChar[x];
			charImage = setCharacterToken(charNum);
					
			charRow = setStartingRow(charNum);
			charCol = setStartingCol(charNum);
			
			boardGrid[charRow][charCol].setIcon(charImage);
			System.out.println(charNum);
			// Add new character to character array
			charArray.add(new CharToken(charImage, charRow, charCol, charNum));
		}
		
	
		gui = new JFrame("Cluedo");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setResizable(false);
		gui.setSize(600, 600);
		gui.getContentPane().setLayout(new BorderLayout());
		gui.getContentPane().add(board, BorderLayout.CENTER);
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(boardImage, 0, 0, 600, 600, this);
	}
	
	private ImageIcon setCharacterToken(int charNum) {
		
		ImageIcon imageFile = new ImageIcon("white.png");
		
		switch(charNum) {
			case 1:
				imageFile = scarlett;
				break;
			case 2:
				imageFile = plum;
				break;
			case 3:
				imageFile = peacock;
				break;
			case 4:
				imageFile = green;
				break;
			case 5:
				imageFile = mustard;
				break;
			case 6:
				imageFile = white;
				break;
		}
		
		return imageFile;
	}
	
	private int setStartingRow(int charNum) {
		
		int row = 0;
		
		switch(charNum) {
			case 1:
				row = 24;
				break;
			case 2:
				row = 19;
				break;
			case 3:
				row = 6;
				break;
			case 4:
				row = 0;
				break;
			case 5:
				row = 17;
				break;
			case 6:
				row = 0;
				break;
		}
		
		return row;
	}
	
	private int setStartingCol(int charNum) {
		
		int col = 0;
		
		switch(charNum) {
			case 1:
				col = 7;
				break;
			case 2:
				col = 24;
				break;
			case 3:
				col = 24;
				break;
			case 4:
				col = 15;
				break;
			case 5:
				col = 0;
				break;
			case 6:
				col = 9;
				break;
		}
		
		return col;
		
	}
	
	private class MoveHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			for(int x = 0; x < 25; x++) {
				for(int y = 0; y < 25; y++) {
					if(source == boardGrid[x][y]) {
						makeMove(x, y, 6, 1, charArray);
						return;
					}
				}
			}
		}
		
	}

	private void makeMove(int x, int y, int dice, int playerNum, ArrayList<CharToken> ctArray) {
		
		int charRow = ctArray.get(playerNum).getRow();
		int charCol = ctArray.get(playerNum).getCol();
		ImageIcon moveImage = ctArray.get(playerNum).getToken();
		
		if(isMovableTo(x, y, dice, playerNum, ctArray) == false) {
			return;
		}
		else {
			boardGrid[charRow][charCol].setIcon(null);
			boardGrid[x][y].setIcon(moveImage);
			ctArray.get(playerNum).setRow(x);
			ctArray.get(playerNum).setCol(y);
		}
	}

	private boolean isMovableTo(int x, int y, int dice, int playerNum, ArrayList<CharToken> ctArray) {
		
		int charRow = ctArray.get(playerNum).getRow();
		int charCol = ctArray.get(playerNum).getCol();
		
		int rowDist = Math.abs(x - charRow);
		int colDist = Math.abs(y - charCol);
		int distance = rowDist + colDist;
		
		// if grid is in a no-go zone, return false
		// for(int a = 0; a < 25; a++){
		//		for(int b = 0; b < 25; b++){
		//			if(boardGrid[x][y] = outOfBounds[a][b]){
		//				return false;
		//			}
		//		}
		//	}
		
		if(distance <= dice) {
			return true;
		}
		
		else {
			return false;
		}
	
	}
	
	private void findCharacterIcons() {
		
		try {
			scarlett = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("red.png")));
		}catch (IOException e) {
			System.out.println("Could not find the image file " + e.toString());
		}
		
		try {
			plum = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("purple.png")));
		}catch (IOException e) {
				System.out.println("Could not find the image file " + e.toString());
			}
		
		try {
			peacock = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("blue.png")));
		}catch (IOException e) {
				System.out.println("Could not find the image file " + e.toString());
			}
		
		try {
			green = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("green.png")));
		}catch (IOException e) {
				System.out.println("Could not find the image file " + e.toString());
			}
		
		try {
			mustard = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("yellow.png")));
		}catch (IOException e) {
				System.out.println("Could not find the image file " + e.toString());
			}
		
		try {
			white = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("white.png")));
		}catch (IOException e) {
				System.out.println("Could not find the image file " + e.toString());
			}
	
	}
}