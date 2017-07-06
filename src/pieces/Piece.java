package pieces;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Util;

public class Piece {
	private Image image;
	private String color;
	private int[] currentPos;
	private boolean hasMoved = false;

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
		
	public boolean moveIsValid(int[] end) {
		return false;
	}
	
	public void setCurrentPos(int[] position) {
		currentPos = position;
	}
	
	public int[] getCurrentPos() {
		return currentPos;
	}
	
	public boolean hasMoved() {
		return hasMoved;
	}
	
	public void moved() {
		hasMoved = true;
	}
}
