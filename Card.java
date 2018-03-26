public class Card {
	
	/*
	 * Class file for creating cards
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */
	

    private final String name;
    private final int playerNum;
    private final int envelope;
    private final int visible;
    
    // For type, 1 = Character, 2 = Weapon, and 3 = Room
    // For envelope, 0 = not in envelope, 1 = in envelope
    // For visible, 0 = not visible to all players, 1 = visible to all players

    Card(String name, int playerNum, int envelope, int visible) {
        this.name = name;
        this.playerNum = playerNum;
        this.envelope = envelope;
        this.visible = visible;
    }

    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.trim());
    }

    public String getName() {
        return name;
    }
    
    public int getPlayerNum() {
    	return playerNum;
    }
    
    public boolean inEnvelope() {
    	if(envelope == 1) {
    		return true;
    	}
    	
    	return false;
    }
    
    public boolean isVisible() {
    	if(visible == 1) {
    		return true;
    	}
    	
    	return false;
    }
}
