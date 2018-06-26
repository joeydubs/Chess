package pieces;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.GameBoard;
import game.Tile;
import game.Util;

public class Piece {
	private Image image;
	protected String color;
	private Tile currentPos;
	protected Tile possibleMoves;
	protected boolean hasMoved = false;
	protected int[][] mods;
	protected ArrayList<Tile> moves = new ArrayList<Tile>();

	public Image getImage() {
		return image;
	}
	
	protected void setImage(String img) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/images/" + img));
		}
		catch (IOException e) {
			Util.debug("Unable to load image for file name " + img);
		}
		this.image = image;
		
	}
	
	protected void setColor(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}

	public boolean moveIsValid(Tile end) {
		if (moves.contains(end)) {
			Util.debug("Move is valid");
			return true;
		}
		else {
			Util.debug("Moves does not contain point " + end);
			return false;
		}
	}

	public void calcMoves() {
//		Util.debug("Calculating moves...");
		moves.clear();
		for (int[] mod : mods) {
//			Util.debug("Checking mod " + mod[0] + ", " + mod[1] + "...");
			Tile t = getCurrentPos().copy();
			t.increment(mod[0], mod[1]);

			boolean occupied = false;
			
			while (!occupied && t.isValid()) {
//				Util.debug("Checking generated point " + p + "...");
				Piece piece = t.getPiece();
				if (piece != null) {
					occupied = true;
					if (!piece.getColor().equals(color)) {
//						Util.debug("Adding point " + p + " to moves list...");
						moves.add(t.copy());
					}
				}
				else {
//					Util.debug("Adding point " + p + " to moves list...");
					moves.add(t.copy());
					t.increment(mod[0], mod[1]);
				}
			}
		}
	}
	
	public ArrayList<Tile> getMoves() {
		return moves;
	}
	
	public ArrayList<Tile> getMovesCopy() {
		ArrayList<Tile> copy = new ArrayList<Tile>();
		for (Tile p : moves) {
			copy.add(p);
		}
		return copy;
	}

	
	public void setCurrentPos(Tile position) {
		currentPos = position;
	}
	
	public Tile getCurrentPos() {
		return currentPos;
	}
	
	protected int getDist(int x, int y) {
		return (x - y);
	}
		
	public boolean hasMoved() {
		return hasMoved;
	}
	
	public void moved() {
		hasMoved = true;
	}
	
	@Override
	public String toString() {
		return color + " " + this.getClass().getSimpleName();
	}
}
