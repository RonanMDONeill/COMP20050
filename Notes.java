
public class Notes {

	/*
	 * Class file that contains the displayNotes function. Displays the cards to the users and marks the ones they have - (X) - and the ones visible to all players -(A)
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */
	
	public String displayNotes(Cards cards, Players players) {
		String notes = "";
		int x;
		
		for(x = 0; x < 21; x++) {
			notes += cards.get(x).getName();
			// If the player has this card, mark with (X) in infoPanel
			if(cards.get(x).getPlayerNum() == players.getCurrentPlayer().getNumber()) {
				notes += "(X)";
			}
			// If this card is visible to all players, mark with (A) in infoPanel
			if(cards.get(x).isVisible()) {
				notes += "(A)";
			}
			
			notes += "\n";
		}
		
		return notes;
	}
}
