package pieces;

import game.Tile;

public class King extends Piece {
	boolean canCastle = true;

	public King(String color) {
		this.setImage(color + " king.png");
		this.setColor(color);

		mods = new int[][] { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
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
