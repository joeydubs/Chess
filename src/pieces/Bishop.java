package pieces;

public class Bishop extends Piece {
	public Bishop(String color) {
		this.setImage(color + " bishop.png");
		this.setColor(color);
		this.continuous = true;

		mods = new int[][] { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };
	}
}