public class Coordinates {

	/*
	 * Taken from Moodle page
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */
	
    private int col,row;

    Coordinates(int col, int row) {
        this.col = col;
        this.row = row;
    }

    Coordinates(Coordinates coordinates) {
        col = coordinates.getCol ();
        row = coordinates.getRow ();
    }

    public void add (Coordinates coordinates) {
        col = col + coordinates.getCol ();
        row = row + coordinates.getRow ();
    }

    public int getRow () {
        return row;
    }

    public int getCol () {
        return col;
    }

    public String toString () {
        return "(" + col + "," + row + ")";
    }
}
