package pieces;

public class Queen extends Piece {
	public Queen(String color) {
		this.setImage(color + " queen.png");
		this.setColor(color);
		this.continuous = true;

		mods = new int[][] { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
	}
}
