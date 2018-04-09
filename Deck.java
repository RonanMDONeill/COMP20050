

public class Deck {
	
	/*
	 * Taken from Moodle page
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */

    private final Cards suspectCards = new Cards();
    private final Cards weaponCards  = new Cards();
    private final Cards roomCards  = new Cards();
    private final Cards allCards = new Cards();
    private final Cards murderCards = new Cards();
    private Cards sharedCards = new Cards();
    private int numberOfCardsEach;
    public Cards allCardsCopy = new Cards();

    Deck() {
        for (String cardName : Names.SUSPECT_NAMES) {
            suspectCards.add(new Card(cardName));
        }
        for (String cardName : Names.WEAPON_NAMES) {
            weaponCards.add(new Card(cardName));
        }
        for (String cardName : Names.ROOM_CARD_NAMES) {
            roomCards.add(new Card(cardName));
        }
    }

    public void selectMurderCards () {
        suspectCards.shuffle();
        murderCards.add(suspectCards.take());
        weaponCards.shuffle();
        murderCards.add(weaponCards.take());
        roomCards.shuffle();
        murderCards.add(roomCards.take());
    }

    public Cards getMurderCards () {
        return murderCards;
    }

    public void prepareToDeal(int numberOfPlayers) {
        allCards.addAll(suspectCards);
        allCards.addAll(weaponCards);
        allCards.addAll(roomCards);
        allCards.shuffle();
        numberOfCardsEach = allCards.count() / numberOfPlayers;
        int numberOfCardsShared = allCards.count() - numberOfCardsEach*numberOfPlayers;
        sharedCards = new Cards ();
        for (int i=0; i<numberOfCardsShared; i++) {
            sharedCards.add(allCards.take());
        }
        
        allCardsCopy.addAll(allCards);
    }

    public boolean isSharedCard(String name) {
        return sharedCards.contains(name);
    }

    public Cards dealCards() {
        Cards hand = new Cards();
        for (int i = 0; i<numberOfCardsEach; i++) {
            hand.add(allCards.take());
        }
        return hand;
    }

}
