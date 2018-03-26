public class Player {

	/*
	 * Taken from Moodle page & edited
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */
	
	// Added number variable so players can be linked to cards
    private final String name;
    private final Token token;
    private final int number;

    Player(String name, Token token, int number) {
        this.name = name;
        this.token = token;
        this.number = number;
    }

    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.trim());
    }

    public String getName() {
        return name;
    }

    public Token getToken() {
        return token;
    }
    
    // Accessor for number variable
    public int getNumber() {
    	return number;
    }

    @Override
    public String toString() {
        return name + " (" + token.getName() + ")";
    }
}
