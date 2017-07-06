package pieces;

public class Knight extends Piece {	
	public Knight (String color) {
		this.setImage(color + " knight.png");
		this.setColor(color);
	}

	public boolean moveIsValid(int[] end) {
		boolean isValid = false;
		int[] start = getCurrentPos();
		
		int colChange = end[0] - start[0];
		int rowChange = end[1] - start[1];
		
		if ((colChange == 1 || colChange == -1) && (rowChange == 2 || rowChange == -2)) {
			isValid = true;
		}
		
		else if ((colChange == 2 || colChange == -2) && (rowChange == 1 || rowChange == -1)) {
			isValid = true;
		}

		return isValid;
	}
}
