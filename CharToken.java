package assignment1;

import javax.swing.ImageIcon;

public class CharToken {

	private ImageIcon token;
	private int row, col, charNum;
	private boolean inRoom;
	
	// Constructor for Character Tokens
	public CharToken(ImageIcon t, int r, int c, int cN) {
		this.token = t;
		this.row = r;
		this.col = c;
		this.charNum = cN;
	}
	
	// Accessors
	public ImageIcon getToken() {
		return token;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getCharNum() {
		return charNum;
	}
	
	public boolean inRoom() {
		return this.inRoom;
	}
	
	// Mutators
	public void setRow(int r) {
		this.row = r;
	}
	
	public void setCol(int c) {
		this.col = c;
	}
	
	public void inRoom(boolean inRoom) {
		this.inRoom = inRoom;
	}
	
}
