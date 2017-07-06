package pieces;

public class King extends Piece {
	boolean canCastle = true;
	
	public King (String color) {
		this.setImage(color + " king.png");
		this.setColor(color);
	}

	public boolean moveIsValid(int[] end) {
		boolean isValid = false;
		int[] start = getCurrentPos();

		int colDist = end[0] - start[0];
		int rowDist = end[1] - start[1];
		if ((colDist == 1 || colDist == 0 || colDist == -1)
				&& (rowDist == 1 || rowDist == 0 || rowDist == -1)) {
			isValid = true;
		}

		return isValid;
	}
	
	public void blockCastle() {
		canCastle = false;
	}
	
	public void unblockCastle() {
		canCastle = true;
	}
}
