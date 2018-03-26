public class Dice {
	
	/*
	 * Taken from Moodle page
	 */
	
	/*
	 * TeamSynergy:
	 * Dáire Murphy - 15441458
	 * Rónan O'Neill - 16433656
	 * Lorcan Rooney - 16413092
	 */

    private static final int NUM_DICE = 2;

    private final int[] dice = new int [NUM_DICE];

    public void roll() {
        for (int i=0; i<NUM_DICE; i++) {
            dice[i] = 1 + (int)(Math.random() * 6);
        }
    }

    public int getTotal() {
        return (dice[0] + dice[1]);
    }

    public String toString() {
        return dice[0] + " " + dice[1];
    }

}
