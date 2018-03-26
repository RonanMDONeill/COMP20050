public class Weapon {

	/*
	 * Taken from Moodle page
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */
	
    private final String name;
    private final Coordinates position;

    Weapon(String name, Room room) {
        this.name = name;
        position = room.addItem ();
    }

    public String getName() {
        return name;
    }

    public Coordinates getPosition() {
        return position;
    }

    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.toLowerCase().trim());
    }

}
