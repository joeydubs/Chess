package game;

import pieces.Piece;

public class Tile {
	private int x;
	private int y;

	private boolean isOccupied;
	private Piece piece;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void placePiece(Piece p) {
		piece = p;
	}

	public boolean isOccupied() {
		return isOccupied;
	}
	
	public String getColor() {
		if (piece.getColor().equalsIgnoreCase("white")) {
			return "white";
		}
		else {
			return "black";
		}
	}
	
	public Piece getPiece() {
		return piece;
	}

	public void removePiece() {
		piece = null;
	}
	
/*
	@Override
	public boolean equals(Object p) {
		Tile point = p.getClass().equals(Tile.class) ? (Tile) p : null;
		if (point == null) {
			Util.debug("Object p is not of class Point - Point.Class.equals");
			return false;
		}

		if (point.x == x && point.y == y) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int result = 10;
		result = 25 * result + x;
		result = 25 * result + y;

		return result;

	}
*/

	@Override
	public String toString() {
		return (x + ", " + y);
	}
}
