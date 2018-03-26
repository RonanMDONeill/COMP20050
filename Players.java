import java.util.ArrayList;
import java.util.Iterator;

public class Players implements Iterable<Player>, Iterator<Player> {

	/*
	 * Taken from Moodle page & edited
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */
	
    private final ArrayList<Player> players = new ArrayList<>();
    private int currentPlayerIndex;
    private Iterator<Player> iterator;

    public void add(Player player) {
        players.add(player);
    }

    Players () {
		return;
	}
	
	Players (Players players) {
		for (Player p : players.get()) {
			this.players.add(p);
		}
		return;
	}
	
	public ArrayList<Player> get () {
		return players;
	}
    
    public void clear () {
		players.clear();
		return;
	}
    
    public boolean contains(String name) {
        for (Player player : players) {
            if (player.hasName(name)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return players.size();
    }

    public Player get(int index) {
        return players.get(index);
    }

    public void setCurrentPlayer(String name, int playerNum) {
        for(int x = 0; x < playerNum; x++) {
        	if(name.equals(players.get(x).getName())) {
        		currentPlayerIndex = x;
        	}
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void turnOver() {
        if (currentPlayerIndex < players.size()-1) {
            currentPlayerIndex++;
        } else {
            currentPlayerIndex = 0;
        }
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Player next() {
        return iterator.next();
    }

    @Override
    public Iterator<Player> iterator() {
        iterator = players.iterator();
        return iterator;
    }

}
