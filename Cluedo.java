import java.util.ArrayList;

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
    private final Players players = new Players();
    private final Dice dice = new Dice();
    private final Map map = new Map();
    private final Weapons weapons = new Weapons(map);
    private final UI ui = new UI(tokens,weapons);
    private boolean moveOver;
    private Player currentPlayer;
    private Token currentToken;
    private final Deck deck = new Deck();
    private Cards cards = new Cards();
    private ArrayList<Token> guessedTokens = new ArrayList<>();
    private ArrayList<Weapon> guessedWeapons = new ArrayList<>();
    private ArrayList<Room> guessedRooms = new ArrayList<>();
    private ArrayList<String> responses = new ArrayList<>();
    private Players removed = new Players();
    private boolean eliminated = false;

    private void announceTheGame() {
        ui.displayMurderAnnouncement();
    }

    private void inputPlayerNames() {
        int numPlayersSoFar = 0;
        do {
            ui.inputName(players);
            if (!ui.inputIsDone()) {
                ui.inputToken(tokens);
                Token token = tokens.get(ui.getTokenName());
                players.add(new Player(ui.getPlayerName(),token));
                token.setOwned();
                numPlayersSoFar++;
            }
        } while (!ui.inputIsDone() && numPlayersSoFar<MAX_NUM_PLAYERS);
    }

    private void rollToStart() {
        Players playersToRoll = new Players(players), playersWithHighRoll = new Players();
        boolean tie = false;
        do {
            int highRoll = 0;
            for (Player player : playersToRoll) {
                dice.roll();
                ui.displayDice(player,dice);
                if (dice.getTotal() > highRoll) {
                    tie = false;
                    highRoll = dice.getTotal();
                    playersWithHighRoll.clear();
                    playersWithHighRoll.add(player);
                } else if (dice.getTotal() == highRoll) {
                    tie = true;
                    playersWithHighRoll.add(player);
                }
            }
            if (tie) {
                ui.displayRollDraw();
                playersToRoll = new Players(playersWithHighRoll);
                playersWithHighRoll.clear();
            }
        } while (tie);
        players.setCurrentPlayer(playersWithHighRoll.get(0).getName());
        ui.displayRollWinner(players.getCurrentPlayer());
        ui.display();
    }

    private void dealCards() {
        deck.selectMurderCards();
        deck.prepareToDeal(players.getNumberOfPlayers());
        for (Player player : players) {
            player.addCards(deck.dealCards());
        }
        ui.displayCardsDealt();
        cards = deck.allCardsCopy;
    }

    private void roll() {
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
    }

    private void passage() {
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
    }
    
    // Method for questioning
    private void question(boolean hasGuessed) {
    	int count = players.getNumberOfPlayers() - 1;
    	boolean disproved = false;
    	boolean hasToken = false, hasWeapon = false, hasRoom = false;
    	Players guessList = new Players(players);
    	Player questionedPlayer = null;
    	Room room = null;
    	Token token = null;
    	Weapon weapon = null;
    	String disproveCard = "", inCellar = "cellar";

    	
    	if(currentToken.isInRoom() && !hasGuessed && !inCellar.equals(currentToken.getRoom().toString().trim().toLowerCase())) {
    		// Get room coordinates and guess
    		room = currentToken.getRoom();
    		guessedRooms.add(room);
    		ui.guessToken(tokens);
    		token = tokens.get(ui.getTokenName());
    		guessedTokens.add(token);
    		ui.guessWeapon(weapons);
    		weapon = weapons.get(ui.getWeaponName());
    		guessedWeapons.add(weapon);
    		
    		// Move token and weapon to guess room
    		Coordinates addWeapon = room.addItem();
    		token.enterRoom(room);
    		weapon.setPosition(addWeapon);
    		ui.display();
    	
    		guessList.setCurrentPlayer(currentPlayer.getName());
    	
    		while(!disproved && count > 0) {
    			// Next player
    			guessList.turnOver();
    			questionedPlayer = guessList.getCurrentPlayer();
    		
    			// Clear infoPanel
    			ui.clear();
    		
    			// Confirm switch
    			ui.confirmSwitch(questionedPlayer);
    		
    			// Display guess
    			ui.displayGuess(currentPlayer, token, weapon, room);
    		
    			// Check if player has a card from the guess
    			hasToken = questionedPlayer.hasCard(token.getName());
    			hasWeapon = questionedPlayer.hasCard(weapon.getName());
    			hasRoom = questionedPlayer.hasCard(room.toString());
    		
    			// Show player their notes
    			ui.displayNotes(questionedPlayer, deck);
    		
    			// Get response from player
    			disproved = ui.disproveGuess(token, weapon, room, hasToken, hasWeapon, hasRoom);
    			disproveCard = ui.getDisproveCard();
    			
    			count--;
    		}
    	
    		// Clear infoPanel
    		ui.clear();
    	
    		// If disproved, mark in notes
    		if(disproved) {
    			Card card = cards.getCard(disproveCard);
    			Cards updated = new Cards();
    			updated = currentPlayer.getCards();
    			updated.add(card);
    			currentPlayer.addCards(updated);
    			responses.add(questionedPlayer.getName() + " disproved this with the " + disproveCard + " card");
    		
    			ui.displayDisprove(questionedPlayer, disproveCard);
    		}
    	
    		// If not disproved
    		if(count == 0 && !disproved) {
    			responses.add("fail");
    			ui.displayNoDisprove();
    		}
    	}
    	// If player is not in room
    	else if(!currentToken.isInRoom()){
    		ui.displayErrorNotInRoom();
    	}
    	// If already guessed this turn
    	else if(hasGuessed){
    		ui.displayErrorHasGuessed();
    	}
    	// If player is in cellar
    	else {
    		ui.displayErrorCellarGuess();
    	}
    }
    
    // Method that displays all previous questions and their responses
    private void log() {
    	ui.displayLog(guessedTokens, guessedWeapons, guessedRooms, responses);
    }
    
    // Method for accusing
    private boolean accuse() {
    	String token, weapon, room;
    	boolean tokenBool, weaponBool, roomBool;
    	Cards murderCards = new Cards();
    	String inCellar = "cellar";
    	
    	boolean correct = false;
    	
    	if(currentToken.isInRoom() && inCellar.equals(currentToken.getRoom().toString().trim().toLowerCase())) {
    		// Get accusation
    		ui.clear();
    		ui.displayAccusation();
    		token = ui.accuseToken(tokens);
    		weapon = ui.accuseWeapon(weapons);
    		room = ui.accuseRoom();
    		
    		// Get murder cards
    		murderCards = deck.getMurderCards();
    		
    		// Check if accusation is correct
    		tokenBool = murderCards.contains(token);
    		weaponBool = murderCards.contains(weapon);
    		roomBool = murderCards.contains(room);
    		
    		// Player was correct
    		if(tokenBool && weaponBool && roomBool) {
    			ui.displayGameWinner(currentPlayer);
    			ui.displaySolution(deck.getMurderCards());
    			correct = true;
    		}
    		
    		// Player was incorrect
    		else {
    			ui.displayElimination();
    			correct = false;
    			
    			// Remove player from game and add to removed players list
        		int index = players.getIndex();
        		removed.add(players.removePlayer(index));
        		eliminated = true;
    		}
    		
    	}
    	
    	// Must be in cellar to accuse
    	else {
    		ui.displayErrorNotInCellar();
    	}
    	
    	return correct;
    }

    private void takeTurns() {
        boolean turnOver, gameOver = false, hasGuessed = false, correct = false;
        do {
            turnOver = false;
            moveOver = false;
            do {
                currentPlayer = players.getCurrentPlayer();
                currentToken = currentPlayer.getToken();
                ui.displayEliminatedPlayers(removed, eliminated);
                ui.inputCommand(currentPlayer);
                switch (ui.getCommand()) {
                    case "roll": {
                        roll();
                        break;
                    }
                    case "passage": {
                        passage();
                        break;
                    }
                    case "question":{
                    	question(hasGuessed);
                    	hasGuessed = true;
                    	break;
                    }
                    case "log":{
                    	log();
                    	break;
                    }
                    case "accuse":{
                    	correct = accuse();
                    	if(correct) {
                    		turnOver = true;
                    		gameOver = true;
                    	}
                    	else {
                    		turnOver = true;
                    	}
                    	// If only one player left, they win
                    	if(players.getNumberOfPlayers() == 1) {
                    		ui.displayGameWinner(players.get(0));
                    		ui.displaySolution(deck.getMurderCards());
                    		turnOver = true;
                    		gameOver = true;
                    	}
                    	
                    	break;
                    }
                    case "notes": {
                        ui.displayNotes(currentPlayer, deck);
                        break;
                    }
                    case "cheat" : {
                        ui.displaySolution(deck.getMurderCards());
                        break;
                    }
                    case "help" : {
                        ui.displayHelp();
                        break;
                    }
                    case "done": {
                        turnOver = true;
                        break;
                    }
                    case "quit": {
                        turnOver = true;
                        gameOver = true;
                        System.exit(0);
                        break;
                    }
                }
            } while (!turnOver);
            if (!gameOver) {
                players.turnOver();
            	ui.clear();
            	hasGuessed = false;
            }
        } while (!gameOver);
    }

    public static void main(String[] args) {
        Cluedo game = new Cluedo();
        game.announceTheGame();
        game.inputPlayerNames();
        game.dealCards();
        game.rollToStart();
        game.takeTurns();
    }
}
