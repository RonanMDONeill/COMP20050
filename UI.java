import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    private final InfoPanel infoPanel;
    private final CommandPanel commandPanel;
    private String input, playerName, tokenName, weaponName, command, move, disproveCard;
    private int door;
    private boolean inputIsDone;

    UI(Tokens characters, Weapons weapons) {
        JFrame frame = new JFrame();
        infoPanel = new InfoPanel();
        commandPanel = new CommandPanel();
        boardPanel = new BoardPanel(characters, weapons);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Cluedo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(boardPanel, BorderLayout.LINE_START);
        frame.add(infoPanel, BorderLayout.LINE_END);
        frame.add(commandPanel,BorderLayout.PAGE_END);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    
    // Clear the infoPanel
    public void clear() {
    	infoPanel.setText(" ");
    }
    
    /* Display Methods */

    public void display() {
        boardPanel.refresh();
    }
    
    private void displayString(String string) {
        infoPanel.addText(string);
    }

    public void displayMurderAnnouncement() {
        displayString("WELCOME TO CLUEDO");
        displayString("A murder has been committed.");
        displayString("You must solve the case.");
    }

    public void displayCardsDealt() {
        displayString("The cards have been dealt.");
    }

    public void displayDice(Player player, Dice dice) {
        displayString(player + " rolls " + dice + ".");
    }

    public void displayRollDraw() {
        displayString("Draw.");
    }

    public void displayRollWinner(Player player) {
        displayString(player + " wins the roll.");
    }

    private void displayNote(Player player, Deck deck, String cardName) {
        String displayName = String.format("%-18s",cardName);
        if (player.hasCard(cardName)) {
            displayString(displayName + "X");
        } else if (deck.isSharedCard(cardName)) {
            displayString(displayName + "A");
        } else {
            displayString(displayName + ".");
        }
    }

    public void displayNotes(Player player, Deck deck) {
        displayString("SUSPECTS");
        for (String cardName : Names.SUSPECT_NAMES) {
            displayNote(player, deck, cardName);
        }
        displayString("WEAPONS");
        for (String cardName : Names.WEAPON_NAMES) {
            displayNote(player, deck, cardName);
        }
        displayString("ROOMS");
        for (String cardName : Names.ROOM_CARD_NAMES) {
            displayNote(player, deck, cardName);
        }
    }

    public void displaySolution(Cards cards) {
        displayString("The solutions is: " + cards);
    }

    public void displayHelp() {
        displayString("Commands:");
        displayString("roll = roll the dice and move your token.");
        displayString("   u = up");
        displayString("   d = down");
        displayString("   l = left");
        displayString("   r = right");
        displayString("passage = move to another room via the passage.");
        displayString("notes = see a record of your cards.");
        displayString("done = end your turn.");
        displayString("quit = end the game.");
        displayString("question = ask a question when in a room");
        displayString("log = full history of all questions and their answers");
        displayString("accuse = make an accusation (can only be done from inside the cellar)");
    }
    
    // Shows the player being asked what the question was
    public void displayGuess(Player player, Token token, Weapon weapon, Room room) {
    	String playerName = player.getName();
    	String tokenName = token.getName();
    	String weaponName = weapon.getName();
    	String roomName = room.toString();
    	
    	displayString(playerName + " has made a guess!");
    	displayString("Their guess is as follows:");
    	displayString("Character: " + tokenName);
    	displayString("Weapon: " + weaponName);
    	displayString("Room: " + roomName);
    	displayString("Can you disprove them?");
    }
    
    // Shows the asker how their question was disproven
    public void displayDisprove(Player player, String disprove) {
    	displayString(player.getName() + " had the card " + disprove);
    	displayString("Your guess has been disproven");
    }
    
    // Tells the asker that no one could disprove their question
    public void displayNoDisprove() {
    	displayString("Your guess could not be disproven");
    }
    
    // Displays the log of questions and their answers
    public void displayLog(ArrayList<Token> token, ArrayList<Weapon> weapon, ArrayList<Room> room, ArrayList<String> responses) {
    	int length = token.size();
    	
    	for(int x = 0; x < length; x++) {
    		displayString("Guess " + (x + 1) + ":");
    		displayString("Character: " + token.get(x).getName());
    		displayString("Weapon: " + weapon.get(x).getName());
    		displayString("Room: " + room.get(x).toString());
    		if(responses.get(x).equals("fail")){
    			displayString("This guess was could not be disproven");
    		}
    		
    		else {
    			displayString(responses.get(x));
    		}
    		
    	}
    }
    
    // Informing the accuser about the situation
    public void displayAccusation() {
    	displayString("You are about to make an accusation");
    	displayString("To do so, you must enter three cards");
    	displayString("A character card, a weapon card, and a room card");
    	displayString("If it is correct, you win the game");
    	displayString("However, if it is not you will be eliminated");
    }
    
    // Displays the winner of the game
    public void displayGameWinner(Player player) {
    	displayString(player.getName() + " has won the game!!!");
    }
    
    // Tells the user they have been eliminated
    public void displayElimination() {
    	displayString("That accusation is incorrect, you have been eliminated");
    }
    
    // Displays eliminated players
    public void displayEliminatedPlayers(Players players, boolean eliminated) {
    	if(eliminated) {
    		displayString("Eliminated player(s)");
    		for(Player player : players) {
    			displayString(player.getName());
    		}
    	}
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
    
    // Must be in a room to ask a question
    public void displayErrorNotInRoom() {
    	displayError("You must be in a room");
    }
    
    // Can only guess once per turn
    public void displayErrorHasGuessed() {
    	displayError("You only make one guess per turn");
    }
    
    // Can't ask questions in the cellar
    public void displayErrorCellarGuess() {
    	displayError("You can only make accusations in the cellar (accuse)");
    }
    
    // Must be in the cellar to accuse
    public void displayErrorNotInCellar(){
    	displayError("You must be in the cellar");
    }

    /* User Input Methods */

    private void inputString() {
        input = commandPanel.getCommand();
    }

    public void inputName(Players playersSoFar) {
        boolean valid = false;
        inputIsDone = false;
        do {
            if (playersSoFar.getNumberOfPlayers() < 2) {
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
            } else if (playersSoFar.getNumberOfPlayers() >= 2 && playerName.toLowerCase().equals("done")) {
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
    
    // For naming suspect when questioning
    public void guessToken(Tokens tokens) {
    	boolean valid = false;
    	do {
    		displayString("Please name your suspect:");
    		inputString();
    		displayString("> " + input);
    		tokenName = input.trim().toLowerCase();
    		if(tokens.contains(tokenName)) {
    			valid = true;
    		}
    		else {
    			displayError("Not a valid character name");
    		}
    	} while (!valid);
    }
    
    // For naming weapon when questioning
    public void guessWeapon(Weapons weapons) {
    	boolean valid = false;
    	do {
    		displayString("Please choose your suspected weapon:");
    		inputString();
    		displayString("> " + input);
    		weaponName = input.trim().toLowerCase();
    		if(weapons.contains(weaponName)) {
    			valid = true;
    		}
    		else {
    			displayError("Not a valid weapon");
    		}
    	} while (!valid);
    }
    
    // Asks the player if they can disprove the question
    public boolean disproveGuess(Token token, Weapon weapon, Room room, boolean boolToken, boolean boolWeapon, boolean boolRoom) {
    	boolean valid = false, disproved = false;
    	String formatToken = token.getName().trim().toLowerCase();
    	String formatWeapon = weapon.getName().trim().toLowerCase();
    	String formatRoom = room.toString().trim().toLowerCase();
    	
    	do {
    		displayString("If you have one of the guessed cards in your notes, let the guesser know by entering one now:");
    		displayString("Otherwise, type 'done'");
    		inputString();
    		displayString("> " + input);
    		disproveCard = input.trim().toLowerCase();
    		
    		// done command
    		if(disproveCard.equals("done")) {
    			// If player enters done, but has card
    			if(boolToken || boolWeapon || boolRoom) {
    				displayError("You have at least one of the cards, you must disprove the guess");
    				valid = false;
    			}
    			// Unsuccessfully disproved
    			else {
    				displayString("You cannot disprove this guess");
    				disproved = false;
    				valid = true;
    			}
    		}
    		
    		// token
    		else if(disproveCard.equals(formatToken)) {
    			// If player enters the token card and has it in their deck, it is successfully disproved
    			if(boolToken) {
    				displayString("You have successfully disproved this guess!");
    				disproved = true;
    				valid = true;
    			}
    			// If not in deck, it is an error
    			else {
    				displayError("You do not have this card");
    				valid = false;
    			}
    		}
    		
    		// weapon
    		else if(disproveCard.equals(formatWeapon)) {
    			if(boolWeapon) {
    				displayString("You have successfully disproved this guess!");
    				disproved = true;
    				valid = true;
    			}
    			else {
    				displayError("You do not have this card");
    				valid = false;
    			}
    		}
    		
    		// room
    		else if(disproveCard.equals(formatRoom)) {
    			if(boolRoom) {
    				displayString("You have successfully disproved this guess!");
    				disproved = true;
    				valid = true;
    			}
    			else {
    				displayError("You do not have this card");
    				valid = false;
    			}
    		}
    		
    		// Invalid entry
    		else {
    			displayError("Invalid input");
    			valid = false;
    		}
    	
    	} while(!valid);
    	
    	return disproved;
    }
    
    // Confirm that the game has been handed to the player being asked
    public void confirmSwitch(Player player) {
    	boolean valid = false;
    	do {
    		displayString("Type 'ready' when " + player.getName() + " is in control.");
    		inputString();
    		displayString("> " + input);
    		String ready = input.trim().toLowerCase();
    		if(ready.equals("ready")) {
    			valid = true;
    		}
    		else {
    			displayError("Not a valid input");
    		}
    	} while(!valid);
    }

    // Get the suspected token
    public String getTokenName() {
        return tokenName;
    }
    
    // Get the suspected weapon
    public String getWeaponName() {
    	return weaponName;
    }
    
    // Get card that disproved the question
    public String getDisproveCard() {
    	return disproveCard;
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
            if (command.matches("quit|done|roll|passage|notes|cheat|help|question|log|accuse")) {
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
    
    // Get the accused character the user
    public String accuseToken(Tokens tokens) {
    	boolean valid = false;
    	String accusedToken = "";
    	
    	do {
    		displayString("Please enter which character you wish to accuse:");
    		inputString();
            displayString("> " + input);
            accusedToken = input.trim().toLowerCase();
            if (tokens.contains(accusedToken)) {
                valid = true;
            } else {
                displayError("Not a valid character name");
            }
        } while (!valid);
    	
    	return accusedToken;
    }
    
    // Get the accused weapon from the user
    public String accuseWeapon(Weapons weapons) {
    	boolean valid = false;
    	String accusedWeapon = "";
    	
    	do {
    		displayString("With what weapon?:");
    		inputString();
            displayString("> " + input);
            accusedWeapon = input.trim().toLowerCase();
            if (weapons.contains(accusedWeapon)) {
                valid = true;
            } else {
                displayError("Not a valid weapon name");
            }
        } while (!valid);
    	
    	return accusedWeapon;
    }
    
    // Get the accused room from the user
    public String accuseRoom() {
    	boolean valid = false,  found = false;
    	String accusedRoom = "";
    	
    	do {
    		displayString("In which room?:");
    		inputString();
            displayString("> " + input);
            accusedRoom = input.trim().toLowerCase();
            for(int x = 0; x < Names.ROOM_CARD_NAMES.length; x++) {
            	if(accusedRoom.equals(Names.ROOM_CARD_NAMES[x].trim().toLowerCase())) {
            		found = true;
            	}
            }
            
            if(found) {
            	valid = true;
            }
            else {
            	displayError("Not a valid room name");
            }
        } while (!valid);
    	
    	return accusedRoom;
    }

    public int getDoor() {
        return door;
    }

}
