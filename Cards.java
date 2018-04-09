import java.util.ArrayList;
import java.util.Collections;


public class Cards {
	
	/*
	 * Taken from Moodle page & edited
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */

    private ArrayList<Card> cards =  new ArrayList<Card>();

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void add(Card card) {
        cards.add(card);
    }

    public boolean contains(String name) {
        for (Card card : cards) {
            if (card.hasName(name)) {
                return true;
            }
        }
        return false;
    }
    
    
    // Method that returns the card of the corresponding string name
    public Card getCard(String name) {
    	Card nullCard = null;
    	for (Card card : cards) {
            if (card.hasName(name)) {
                nullCard = card;
            }
        }
    	
    	return nullCard;
    }

    private ArrayList<Card> asList() {
        return cards;
    }

    public void addAll(Cards cards) {
        this.cards.addAll(cards.asList());
    }

    public Card take() {
        Card card = cards.get(0);
        cards.remove(0);
        return card;
    }

    public int count() {
        return cards.size();
    }

    public String toString() {
        StringBuilder cardNames = new StringBuilder();
        boolean firstCard = true;
        for (Card card : cards) {
            if (firstCard) {
                cardNames = new StringBuilder("" + card);
                firstCard = false;
            } else {
                cardNames.append(", ").append(card);
            }
        }
        return cardNames.toString();
    }
}
