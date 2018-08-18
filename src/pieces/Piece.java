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
	protected Tile currentPos;
	protected boolean hasMoved = false;
	protected boolean continuous = false;
	protected int[][] mods;
	protected ArrayList<Tile> moves = new ArrayList<Tile>();

	public Image getImage() {
		return image;
	}

	protected void setImage(String img) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/images/" + img));
		} catch (IOException e) {
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

	public void calcMoves() {
//		Util.debug("Calculating moves...");
		moves.clear();
		
		for (int[] mod : mods) {
//			Util.debug("Checking mod " + mod[0] + ", " + mod[1] + "...");
			
			int currentX = currentPos.getX();
			int currentY = currentPos.getY();
			
			boolean moving = true;
			
			do {
				currentX += mod[0];
				currentY += mod[1];
				
				// Response indicates if the piece can move here [0], and if it can keep moving [1]
				boolean[] response = GameBoard.canMoveHere(this, currentX, currentY);
								
				if (response[0]) {
					moves.add(new Tile(currentX, currentY));
				}
				
				moving = response[1];
//				System.out.println(this.toString() + " " + moving);
			} while (moving && continuous);
		}
	}

	public void setCurrentPos(Tile position) {
		currentPos = position;
	}

	public void moved() {
		hasMoved = true;
	}

	@Override
	public String toString() {
		return color + " " + this.getClass().getSimpleName();
	}
}
