package assignment1;

import javax.swing.JOptionPane;

public class GameSetup {

	public int playerNum() {
		
		String stringTemp = " ";
		
		stringTemp = JOptionPane.showInputDialog("Please enter the amount of players (1-6): ");
		int playerNum = Integer.parseInt(stringTemp);
		
		while(!(playerNum > 0 && playerNum < 7)) {
			stringTemp = JOptionPane.showInputDialog("Please enter a valid number of players (1-6): ");
			playerNum = Integer.parseInt(stringTemp);
		}
		
		return playerNum;
	}
	
	public int characterSelect() {
	
		
		Object[] characters = {"Miss Scarlett", "Professor Plum", "Mrs. Peacock", 
				"Reverend Green", "Colonel Mustard", "Mrs. White"};
		
		int charNum = JOptionPane.showOptionDialog(null, "Please select a character:", "Character Selection",
		        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
		        null, characters, characters[0]);
		
		return charNum + 1;
	}
}
