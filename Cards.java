import java.util.ArrayList;
import java.util.Random;

public class Cards {
	
	/*
	 * Class file for dealing and shuffling cards and setting up the envelope
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */
	
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	int randChar, randWeap, randRoom;
	
	public Card get(int index) {
		return cards.get(index);
	}
	
	public void dealCards(int playerNum, Players players) {
		
		int remainingCardsMod;
		int x, y, z;
		int divisor = 0;
		String character = " ";
		String room = " ";
		String weapon = " ";
		
		// Set envelope first
		
		// Assign character card
		randChar = (int)(Math.random() * 6);
		character = Names.SUSPECT_NAMES[randChar];
		cards.add(new Card(character, -1, 1, 0));
		
		// Assign weapon card
		randWeap = (int)(Math.random() * 6);
		weapon = Names.WEAPON_NAMES[randWeap];
		cards.add(new Card(weapon, -1, 1, 0));
		
		// Assign room card
		randRoom = (int)(Math.random() * 6);
		room = Names.ROOM_NAMES[randRoom];
		cards.add(new Card(room, -1, 1, 0));
		
		// Create array of remaining cards
		String[] remainingCards = new String[18];
		
		for(x = 0; x < randChar; x++) {
			remainingCards[x] = Names.SUSPECT_NAMES[x];
		}
		y = randChar;
		for(x = randChar + 1; x < 6; x++) {
			remainingCards[y] = Names.SUSPECT_NAMES[x];
			y++;
		}
		
		y = 0;
		for(x = 5; x < randWeap + 5; x++) {
			remainingCards[x] = Names.WEAPON_NAMES[y];
			y++;
		}
		y += 5;
		for(x = randWeap + 1; x < 6; x++) {
			remainingCards[y] = Names.WEAPON_NAMES[x];
			y++;
		}
		
		y = 0;
		for(x = 10; x < randRoom + 10; x++) {
			remainingCards[x] = Names.ROOM_NAMES[y];
			y++;
		}
		y += 10;
		for(x = randRoom + 1; x < 9; x++) {
			remainingCards[y] = Names.ROOM_NAMES[x];
			y++;
		}
		
		// Deal remaining cards
		remainingCardsMod = 18 % playerNum;
		if(remainingCardsMod == 0) {
			divisor = 18 / playerNum;
		}
		
		// Shuffle cards
		remainingCards = shuffleCards(remainingCards);
		
		// If 5 players
		if(remainingCardsMod == 3) {
			z = 0;
			// Deal each player 3 cards
			for(x = 0; x < 5; x++) {
				for(y = 0; y < 3; y++) {
					cards.add(new Card(remainingCards[z], players.get(x).getNumber(), 0, 0));
					z++;
				}
			}
			// Make remaining 3 cards visible
			for(x = 15; x < 18; x++) {
				cards.add(new Card(remainingCards[x], -1, 0, 1));
			}
		}
		
		// If 4 players
		else if(remainingCardsMod == 2) {
			z = 0;
			// Deal each player 4 cards
			for(x = 0; x < 4; x++) {
				for(y = 0; y < 4; y++) {
					cards.add(new Card(remainingCards[z], players.get(x).getNumber(), 0, 0));
					z++;
				}
			}
			// Make remaining 2 cards visible
			for(x = 16; x < 18; x++) {
				cards.add(new Card(remainingCards[x], -1, 0, 1));
			}
		}
		
		else if(remainingCardsMod == 0) {
			// If 6 players
			if(divisor == 3) {
				z = 0;
				// Deal each player 3 cards
				for(x = 0; x < 6; x++) {
					for(y = 0; y < 3; y++) {
						cards.add(new Card(remainingCards[z], players.get(x).getNumber(), 0, 0));
						z++;
					}
				}
			}
			// If 3 players
			else if(divisor == 6) {
				z = 0;
				// Deal each player 6 cards
				for(x = 0; x < 3; x++) {
					for(y = 0; y < 6; y++) {
						cards.add(new Card(remainingCards[z], players.get(x).getNumber(), 0, 0));
						z++;
					}
				}
			}
			// If 2 players
			else if(divisor == 9) {
				z = 0;
				// Deal each player 9 cards
				for(x = 0; x < 2; x++) {
					for(y = 0; y < 9; y++) {
						cards.add(new Card(remainingCards[z], players.get(x).getNumber(), 0, 0));
						z++;
					}
				}
			}
		}
		
	}
	
	public String getPerson() {
		String person = Names.SUSPECT_NAMES[randChar];
		return person;
	}
	
	public String getWeapon() {
		String weapon = Names.WEAPON_NAMES[randWeap];
		return weapon;
	}
	
	public String getRoom() {
		String room = Names.ROOM_NAMES[randRoom];
		return room;
	}
	
	private String[] shuffleCards(String[] deck) {
		
		// Fisher-Yates Algorithm
		int n = deck.length;
		Random random = new Random();
		// Loop over array.
		for (int i = 0; i < deck.length; i++) {
			// Get a random index of the array past the current index.
			// ... The argument is an exclusive bound.
			//     It will not go past the array's end.
			int randomValue = i + random.nextInt(n - i);
			// Swap the random element with the present element.
			String randomElement = deck[randomValue];
			deck[randomValue] = deck[i];
			deck[i] = randomElement;
		}
		
		return deck;
	}

}
