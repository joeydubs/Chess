package pieces;

import game.GameBoard;
import game.Tile;
import game.Util;

public class Pawn extends Piece {
	public Pawn (String color) {
		this.setImage(color + " pawn.png");
		this.setColor(color);

		if (color.equals("white")) {
			mods = new int[][] {
				{ 0, -1},
				{-1, -1},
				{ 1, -1}
			};
		}
		else if (color.equals("black")) {
			mods = new int[][] {
				{ 0,  1},
				{-1,  1},
				{ 1,  1}
			};
		}
		else {
			Util.debug("Invalid color passed to Pawn constructor...");
		}
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
				if (mod == mods[0]) {
//					Util.debug("Pawn is moving straight...");
					if (t.getPiece() == null) {
//						Util.debug("Adding point " + p + " to moves list...");
						moves.add(t.copy());
						if (!hasMoved) {
							t.increment(mod[0], mod[1]);
							if (t.getPiece() == null) {
//								Util.debug("Adding point " + p + " to moves list...");
								moves.add(t.copy());
							}
						}
					}
				}
				else {
//					Util.debug("Pawn is moving diagonal...");
					Piece piece = t.getPiece();
					if (piece != null && !piece.getColor().equals(color)) {
//						Util.debug("Adding point " + p + " to moves list...");
						moves.add(t.copy());
					}
				}
			}
		}
	}
}