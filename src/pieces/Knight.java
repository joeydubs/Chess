package pieces;

import game.Tile;

public class Knight extends Piece {
	public Knight(String color) {
		this.setImage(color + " knight.png");
		this.setColor(color);

		mods = new int[][] { { -2, -1 }, { -1, -2 }, { -2, 1 }, { -1, 2 }, { 2, -1 }, { 1, -2 }, { 2, 1 }, { 1, 2 } };
	}

	@Override
	public void calcMoves() {
//		Util.debug("Calculating moves...");
		moves.clear();
		for (int[] mod : mods) {
//			Util.debug("Checking mod " + mod[0] + ", " + mod[1] + "...");
			Tile t = getCurrentPos().copy();
			t.increment(mod[0], mod[1]);

			if (t.isValid()) {
//				Util.debug("Checking generated point " + p + "...");
				Piece piece = t.getPiece();
				if (piece != null && !piece.getColor().equals(getColor())) {
//					Util.debug("Adding point " + p + " to moves list...");
					moves.add(t.copy());
					t.increment(mod[0], mod[1]);
				} else if (piece == null) {
//					Util.debug("Adding point " + p + " to moves list...");
					moves.add(t.copy());
					t.increment(mod[0], mod[1]);
				}
			}
		}
	}
}