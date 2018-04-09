import java.util.ArrayList;
import java.util.Iterator;

public class Weapons implements Iterable<Weapon>, Iterator<Weapon> {
	
	/*
	 * Taken from Moodle page & edited
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */

    private final ArrayList<Weapon> weapons = new ArrayList<>();
    private Iterator<Weapon> iterator;

    Weapons (Map map) {
        weapons.add(new Weapon(Names.WEAPON_NAMES[0],map.getRoom(Names.ROOM_NAMES[0])));
        weapons.add(new Weapon(Names.WEAPON_NAMES[1],map.getRoom(Names.ROOM_NAMES[1])));
        weapons.add(new Weapon(Names.WEAPON_NAMES[2],map.getRoom(Names.ROOM_NAMES[2])));
        weapons.add(new Weapon(Names.WEAPON_NAMES[3],map.getRoom(Names.ROOM_NAMES[3])));
        weapons.add(new Weapon(Names.WEAPON_NAMES[4],map.getRoom(Names.ROOM_NAMES[4])));
        weapons.add(new Weapon(Names.WEAPON_NAMES[5],map.getRoom(Names.ROOM_NAMES[5])));
    }

    // Checks to see if there is a weapon with said name
    public boolean contains(String name) {
        for (Weapon weapon : weapons) {
            if (weapon.hasName(name)) {
                return true;
            }
        }
        return false;
    }
    
    // Gets weapon of said name, if available
    public Weapon get(String name) {
        for (Weapon weapon : weapons) {
            if (weapon.hasName(name)) {
                return weapon;
            }
        }
        return null;
    }
    
    public boolean hasNext() {
        return iterator.hasNext ();
    }

    public Weapon next() {
        return iterator.next ();
    }

    public Iterator<Weapon> iterator() {
        iterator = weapons.iterator ();
        return iterator;
    }

}
