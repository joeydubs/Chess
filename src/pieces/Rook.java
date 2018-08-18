package pieces;

public class Rook extends Piece {
	public Rook(String color) {
		this.setImage(color + " rook.png");
		this.setColor(color);
		this.continuous = true;

		mods = new int[][] { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
	}
}