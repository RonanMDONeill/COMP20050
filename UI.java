import javax.swing.*;
import java.awt.*;

public class UI {

	/*
	 * Taken from Moodle page & edited
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */
	
    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 800;

    private final BoardPanel boardPanel;
    private final InfoPanel infoPanel = new InfoPanel();
    private final CommandPanel commandPanel = new CommandPanel();
    private String input, playerName, tokenName, command, move;
    private int door;
    private boolean inputIsDone;

    UI(Tokens characters, Weapons weapons) {
        boardPanel = new BoardPanel(characters, weapons);
        JFrame frame = new JFrame();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Cluedo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(boardPanel, BorderLayout.LINE_START);
        frame.add(infoPanel, BorderLayout.LINE_END);
        frame.add(commandPanel,BorderLayout.PAGE_END);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /* Display Methods */
    
    public void displayRollWinner (Player player) {
		infoPanel.displayString(player + " wins the roll.");
		return;
	}
    public void displayRollDraw () {
		infoPanel.displayString("Draw");
		return;
    }
    
    public void displayCommandHelp () {
		infoPanel.displayString("Available commands: roll, done, cheat, passage, quit.\nTo move: u = up, d = down, l = left, r = right");
		return;
	}
    
    public void display() {
        boardPanel.refresh();
    }

    public void displayString(String string) {
        infoPanel.addText(string);
    }

    public void displayDice(Player player, Dice dice) {
        displayString(player + " rolls " + dice + ".");
    }

    /* Display Error Messages */

    private void displayError(String message) {
        displayString("Error: " + message + ".");
    }

    public void displayErrorNotADoor() {
        displayError("Not a door");
    }

    public void displayErrorInvalidMove() {
        displayError("Invalid move");
    }

    public void displayErrorAlreadyMoved() {
        displayError("Already moved this turn");
    }

    public void displayErrorNoPassage() {
        displayError("Not in a room with a passage");
    }

    /* User Input Methods */

    private void inputString() {
        input = commandPanel.getCommand();
    }

    public void inputName(Players playersSoFar) {
        boolean valid = false;
        inputIsDone = false;
        do {
            if (playersSoFar.size() < 2) {
                displayString("Enter new player name:");
            } else {
                displayString("Enter new player name or done:");
            }
            inputString();
            displayString("> " + input);
            playerName = input.trim();
            if (playerName.isEmpty()) {
                displayError("Name is blank");
            } else if (playersSoFar.contains(playerName)) {
                displayError("Same name used twice");
            } else if (playersSoFar.size() >= 2 && playerName.toLowerCase().equals("done")) {
                valid = true;
                inputIsDone = true;
            } else {
                valid = true;
            }
        } while (!valid);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void inputToken(Tokens tokens) {
        boolean valid = false;
        do {
            displayString("Enter your character name:");
            inputString();
            displayString("> " + input);
            tokenName = input.trim().toLowerCase();
            if (tokens.contains(tokenName)) {
                if (!tokens.get(tokenName).isOwned()) {
                    valid = true;
                } else {
                    displayError("Character name already in use");
                }
            } else {
                displayError("Not a valid character name");
            }
        } while (!valid);
    }

    public String getTokenName() {
        return tokenName;
    }

    public boolean inputIsDone() {
        return inputIsDone;
    }

    public void inputCommand(Player player) {
        boolean valid = false;
        do {
            displayString(player + " type your command:");
            inputString();
            displayString("> " + input);
            command = input.trim().toLowerCase().replaceAll("( )+", " ");
            if (command.equals("quit") || command.equals("done") || command.equals("roll") || command.equals("passage") || command.equals("notes") || command.equals("cheat") || command.equals("help")) {
                valid = true;
            } else {
                displayError("No such command");
            }
        } while (!valid);
    }

    public String getCommand() {
        return command;
    }

    public void inputMove(Player player, int moveNumber, int movesAvailable) {
        boolean valid = false;
        do {
            displayString(player + " enter move " + moveNumber + " of " + movesAvailable + ":");
            inputString();
            displayString("> " + input);
            move = input.trim().toLowerCase();
            if (move.matches("[udlr]")) {
                valid = true;
            } else {
                displayError("Move must be u, d, l or r");
            }
        } while (!valid);
    }

    public String getMove() {
        return move;
    }

    public void inputDoor(Player player) {
        boolean valid = false;
        do {
            displayString(player + " enter door number:");
            inputString();
            displayString("> " + input);
            input = input.trim();
            if (input.matches("[1234]")) {
                door = Integer.valueOf(input);
                valid = true;
            } else {
                displayError("Input must be a number");
            }
        } while (!valid);
    }

    public int getDoor() {
        return door;
    }

}
