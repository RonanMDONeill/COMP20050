public class Weapon {

	/*
	 * Taken from Moodle page & edited
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */
	
    private final String name;
    private Coordinates position;

    Weapon(String name, Room room) {
        this.name = name;
        position = room.addItem ();
    }

    public String getName() {
        return name;
    }

    // Checks to see if weapon with said name is valid
    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.trim());
    }
    
    public Coordinates getPosition() {
        return position;
    }
    
    // Mutator for position
    public void setPosition(Coordinates newPosition) {
    	this.position = newPosition;
    }
}
