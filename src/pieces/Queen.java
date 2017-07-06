package pieces;

import game.GameBoard;

public class Queen extends Piece {
	public Queen (String color) {
		this.setImage(color + " queen.png");
		this.setColor(color);
	}

	public boolean moveIsValid(int[] end) {
		boolean isValid = false;
		int[] start = getCurrentPos();

		int colDist = end[0] - start[0];
		int rowDist = end[1] - start[1];
		int colMod = colDist > 0 ? 1 : -1;
		int rowMod = rowDist > 0 ? 1 : -1;

		int[] current = {start[0], start[1]};

		if (colDist == 0 || rowDist == 0) {
			if (colDist == 0) {
				colMod = 0;
			}
			else {
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
		else if (Math.abs(colDist) == Math.abs(rowDist)) {
			current[0] = (start[0] + colMod);
			current[1] = (start[1] + rowMod);

			do {
				if (GameBoard.isEmpty(current)) {
					isValid = true;
					current[0] += colMod;
					current[1] += rowMod;
				}
				else if (!GameBoard.isEmpty(current)) {
					if (current[0] == end[0] && current[1] == end[1]) {
						isValid = true;
						current[0] += colMod;
						current[1] += rowMod;
					}
					else {
						isValid = false;
					}
				}
			} while (current[0] != (end[0] + colMod) && current[1] != (end[1] + rowMod) && isValid);
		}

		return isValid;
	}
}
