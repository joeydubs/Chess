package pieces;

import game.GameBoard;

public class Rook extends Piece {
	
	public Rook (String color) {
		this.setImage(color + " rook.png");
		this.setColor(color);
	}

	public boolean moveIsValid(int[] end) {
		boolean isValid = false;
		int[] start = getCurrentPos();

		int colDist = end[0] - start[0];
		int rowDist = end[1] - start[1];

		int[] current = {start[0], start[1]};

		//TODO For Rook and Queen, change mod to equal if dist = 0, 0 else dist / abs(dist)

		if (colDist == 0 || rowDist == 0) {
			int colMod, rowMod;
			if (colDist == 0) {
				colMod = 0;
				rowMod = rowDist > 0 ? 1 : -1;
			}
			else {
				colMod = colDist > 0 ? 1 : -1;
				rowMod = 0;
			}

			current[0] += colMod;
			current[1] += rowMod;

			do {
				if (GameBoard.isEmpty(current)) {
					isValid = true;
					current[0] += colMod;
					current[1] += rowMod;
				}
				else {
					if (current[0] == end[0] && current[1] == end[1]) {
						isValid = true;
						current[0] += colMod;
						current[1] += rowMod;
					}
					else {
						isValid = false;
					}
				}
			} while ((current[0] != (end[0] + colMod) || current[1] != (end[1] + rowMod)) && isValid == true);
		}

		return isValid;
	}
}
