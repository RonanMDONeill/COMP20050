public class Cluedo {
	
	/*
	 * Taken from Moodle page & edited
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */
	

    private static final int MAX_NUM_PLAYERS = 6;

    private final Tokens tokens = new Tokens();
    private Players players = new Players();
    private final Dice dice = new Dice();
    private final Map map = new Map();
    private final Weapons weapons = new Weapons(map);
    private final UI ui = new UI(tokens,weapons);
    private final Cards cards = new Cards();
    private final Notes notes = new Notes();
    private int playerNum;
    private Player currPlayer;

    private void inputPlayerNames() {
        int numPlayersSoFar = 0;
        do {
            ui.inputName(players);
            if (!ui.inputIsDone()) {
                ui.inputToken(tokens);
                Token token = tokens.get(ui.getTokenName());
                players.add(new Player(ui.getPlayerName(),token, numPlayersSoFar));
                token.setOwned();
                numPlayersSoFar++;
            }
        } while (!ui.inputIsDone() && numPlayersSoFar<MAX_NUM_PLAYERS);
        
        // Global variable used in other methods
        playerNum = numPlayersSoFar;
    }
    
    // Call dealCards method from Cards class
    private void setCards() {
    	cards.dealCards(playerNum, players);
    }
    
    // Method that determines who goes first based on dice rolls
    public void decideStarter () {
		Players inPlayers = new Players(players), selectedPlayers = new Players();
		boolean tie = false;
		do {
			int highestTotal = 0;
			for (Player p : inPlayers.get()) {
				dice.roll();
				ui.displayDice(p,dice);
				if (dice.getTotal() > highestTotal) {
					tie = false;
					highestTotal = dice.getTotal();
					selectedPlayers.clear();
					selectedPlayers.add(p);
				} else if (dice.getTotal() == highestTotal) {
					tie = true;
					selectedPlayers.add(p);
				}
			}
			if (tie) {
				ui.displayRollDraw();
				inPlayers = new Players(selectedPlayers);
				selectedPlayers.clear();
			}
		} while (tie);
		// Set the new starter
		players.setCurrentPlayer(selectedPlayers.get(0).getName(), playerNum);
		currPlayer = selectedPlayers.get(0);
		ui.displayRollWinner(currPlayer);
		ui.display();
		return;
	}

    private void takeTurns() {
        boolean moveOver, turnOver, gameOver = false;
        do {
            turnOver = false;
            moveOver = false;
            do {
            	Player currentPlayer = players.getCurrentPlayer();
                Token currentToken = currentPlayer.getToken();
                ui.inputCommand(currentPlayer);
                switch (ui.getCommand()) {
                    case "roll": {
                        if (!moveOver) {
                            dice.roll();
                            ui.displayDice(currentPlayer, dice);
                            int squaresMoved = 0;
                            if (currentToken.isInRoom()) {
                                if (currentToken.getRoom().getNumberOfDoors()>1) {
                                    boolean exitDone = false;
                                    do {
                                        ui.inputDoor(currentPlayer);
                                        if (ui.getDoor()>= 1 || ui.getDoor()<=currentToken.getRoom().getNumberOfDoors()) {
                                            currentToken.leaveRoom(ui.getDoor()-1);
                                            exitDone = true;
                                        } else {
                                            ui.displayErrorNotADoor();
                                        }
                                    } while (!exitDone);
                                } else {
                                    currentToken.leaveRoom();
                                }
                                ui.display();
                            }
                            do {
                                ui.inputMove(currentPlayer, squaresMoved+1, dice.getTotal());
                                Coordinates currentPosition = currentToken.getPosition();
                                Coordinates newPosition;
                                if (map.isValidMove(currentPosition, ui.getMove())) {
                                    newPosition = map.getNewPosition(currentPosition, ui.getMove());
                                    if (map.isDoor(currentPosition, newPosition)) {
                                        Room room = map.getRoom(newPosition);
                                        currentToken.enterRoom(room);
                                    } else {
                                        currentToken.setPosition(newPosition);
                                    }
                                    squaresMoved++;
                                    if (squaresMoved==dice.getTotal() || currentPlayer.getToken().isInRoom()) {
                                        moveOver = true;
                                    }
                                    ui.display();
                                } else {
                                    ui.displayErrorInvalidMove();
                                }
                            } while (!moveOver);
                        } else {
                            ui.displayErrorAlreadyMoved();
                        }
                        break;
                    }
                    case "passage": {
                        if (!moveOver) {
                            if (currentToken.isInRoom() && currentToken.getRoom().hasPassage()) {
                                Room destination = currentToken.getRoom().getPassageDestination();
                                currentToken.leaveRoom();
                                currentToken.enterRoom(destination);
                                moveOver = true;
                                ui.display();
                            } else {
                                ui.displayErrorNoPassage();
                            }
                        } else {
                            ui.displayErrorAlreadyMoved();
                        }
                        break;
                    }
                    // Notes command that displays all the cards
                    case "notes": {
                    	// Characters
                    	ui.displayString("Notes: " + notes.displayNotes(cards, players));
                    	break;
                    }
                    // Cheat command to show the cards in the envelope
                    case "cheat": {
                    	ui.displayString("Murder Envelope:\nPerson: " + cards.getPerson() + "\nWeapon: " + cards.getWeapon() + "\nRoom: " + cards.getRoom());
                    	break;
                    }
                    // Help command that displays help for the game
                    case "help": {
                    	ui.displayCommandHelp();
                    	break;
                    }
                    case "done": {
                        turnOver = true;
                        break;
                    }
                    case "quit": {
                        turnOver = true;
                        gameOver = true;
                        break;
                    }
                }
            } while (!turnOver);
            if (!gameOver) {
                players.turnOver();
            }
        } while (!gameOver);
    }

    public static void main(String[] args) {
        Cluedo game = new Cluedo();
        game.inputPlayerNames();
        game.decideStarter();
        game.setCards();
        game.takeTurns();
        System.exit(0);
    }
}
