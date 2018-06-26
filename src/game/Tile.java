package game;

import java.util.ArrayList;

import pieces.Piece;

public class Tile {
	private int x;
	private int y;
	
	private boolean black;
	private ArrayList<Piece> blackThreats = new ArrayList<Piece>();
	private boolean white;
	private ArrayList<Piece> whiteThreats = new ArrayList<Piece>();
	
	private Piece piece;
	
	public void placePiece(Piece p) {
		piece = p;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public Piece removePiece() {
		Piece p = piece;
		piece = null;
		
		return p;
	}
	
	public boolean isThreatenedBy(String color) {
		if (color.equals("white")) {
			return white;
		}
		else {
			return black;
		}
	}
	
	public void addThreatBy(Piece p) {
		if (p.getColor().equals("white")) {
			white = true;
			whiteThreats.add(p);
		}
		else {
			black = true;
			blackThreats.add(p);
		}
	}

	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object p) {
		Tile point = p.getClass().equals(Tile.class) ? (Tile) p : null;
		if (point == null) {
			Util.debug("Object p is not of class Point - Point.Class.equals");
			return false;
		}
		
		if (point.x() == x && point.y() == y) {
			return true;
		}
		else {
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
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}

	public void increment(int colMod, int rowMod) {
		x += colMod;
		y += rowMod;
		
		GameBoard.getTile
	}
	
	public boolean isValid() {		
		if (x < 0  || x > 7 || y < 0 || y > 7) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public Tile copy() {
		return new Tile(x, y);
	}
	
	public String toString() {
		return (x + ", " + y);
	}
}
