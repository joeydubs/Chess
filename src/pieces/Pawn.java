package pieces;

import game.GameBoard;

public class Pawn extends Piece {

	public Pawn (String color) {
		this.setImage(color + " pawn.png");
		this.setColor(color);
	}

	public boolean moveIsValid(int[] end) {
		boolean isValid = false;
		int[] start = getCurrentPos();

		// Black moves have a positive distance
		// White moves have a negative distance
		int mod = (this.getColor() == "black") ? 1 : -1;

		int colChange = end[0] - start[0];

		int[] current = new int[2];
		// Check if the pawn is staying on the same column
		if (colChange == 0) {
			current[0] = start[0];
			current[1] = start[1] + (1 * mod);
			if (GameBoard.getPiece(current) == null) {
				int rowChange = (end[1] - start[1]);

				if (rowChange == (1 * mod)) {
					isValid = true;
				}
				else {
					if (rowChange == (2 * mod) && !hasMoved()) {
						current[1] = start[1] + (1 * mod);
						if (GameBoard.getPiece(current) == null) {
							isValid = true;
						}
					}
				}
			}
		}
		else {
			int rowChange = (end[1] - start[1]);
			current[0] = end[0];
			current[1] = end[1];

			if ((colChange == 1 || colChange == -1) && rowChange == (1 * mod)) {
				if (GameBoard.getPiece(current) != null) {
					isValid = true;
				}
			}
		}

		return isValid;
	}
}