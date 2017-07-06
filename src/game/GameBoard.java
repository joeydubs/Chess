package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class GameBoard extends JPanel {
	int tileSize;
	private static Piece[][] tile = new Piece[8][8];
	private static int[] selectedTile = null;
	private static int[] whiteKing = {4, 7};
	private static int[] blackKing = {4, 0};

	public GameBoard(int tileSize) {
		this.tileSize = tileSize;
		this.setSize(new Dimension(tileSize * 8, tileSize * 8));
		this.addMouseListener(new ClickListener());

		setup();
	}

	@Override
	public void paintComponent(Graphics g) {
		Util.debug("Painting Game Board...");
		super.paintComponent(g);

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if ((row + col) % 2 == 0) {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
				}
				else {
					g.setColor(Color.GRAY);
					g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
				}
			}
		}

		paintPieces(g);
	}

	public void paintPieces(Graphics g) {
		Util.debug("Painting pieces...");

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
//				Util.debug("Tile is not null: " + (tile[col][row] != null));
				if (tile[col][row] != null) {
//					Util.debug("Drawing piece at " + col + ", " + row);
//					Util.debug("Image is not null: " + (tile[col][row].getImage() != null));
					Image img = tile[col][row].getImage();
					g.drawImage(img, (col * tileSize), (row * tileSize), this);
				}
			}
		}
	}

	void setup () {
		Util.debug("Setting up pieces...");
		Piece[] blackPlayer = new Piece[16];
		Piece[] whitePlayer = new Piece[16];

		whitePlayer[0] = new Rook("white");
		whitePlayer[1] = new Knight("white");
		whitePlayer[2] = new Bishop("white");
		whitePlayer[3] = new Queen("white");
		whitePlayer[4] = new King("white");
		whitePlayer[5] = new Bishop("white");
		whitePlayer[6] = new Knight("white");
		whitePlayer[7] = new Rook("white");

		for (int i = 0; i < 8; i++) {
			whitePlayer[8 + i] = new Pawn("white");
		}

		blackPlayer[0] = new Rook("black");
		blackPlayer[1] = new Knight("black");
		blackPlayer[2] = new Bishop("black");
		blackPlayer[3] = new Queen("black");
		blackPlayer[4] = new King("black");
		blackPlayer[5] = new Bishop("black");
		blackPlayer[6] = new Knight("black");
		blackPlayer[7] = new Rook("black");

		for (int i = 0; i < 8; i++) {
			blackPlayer[8 + i] = new Pawn("black");
		}

		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = blackPlayer[col + (8 * row)];
				placePiece(piece, new int[]{col, row});
			}
		}

		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = whitePlayer[col + (8 * row)];
				placePiece(piece, new int[]{col, 7 - row});
			}
		}		
	}

	public static void placePiece(Piece piece, int[] position) {
		tile[position[0]][position[1]] = piece;
		piece.setCurrentPos(position);
	}

	public static void removePiece(int[] position) {
		tile[position[0]][position[1]] = null;
	}
	
	public static Piece getPiece(int[] position) {
		return tile[position[0]][position[1]];
	}
	
	public static boolean isEmpty(int[] position) {
		boolean isEmpty = false;
		
		if (tile[position[0]][position[1]] == null) {
			isEmpty = true;
		}
		
		return isEmpty;
	}
	
	public static int[] getKingPosition(String color) {
		if (color.equalsIgnoreCase("white")) {
			return whiteKing;
		}
		else if (color.equalsIgnoreCase("black")) {
			return blackKing;
		}
		else {
			Util.debug("Invalid color " + color + " passed to getKingPosition");
			return null;
		}
	}
	
	public static boolean updateKingPos(Piece piece, int[] newPos) {
		boolean isSuccessful = false;
		
		if (piece.getColor().equalsIgnoreCase("white")) {
			whiteKing = newPos;
			isSuccessful = true;
		}
		else if (piece.getColor().equalsIgnoreCase("black")) {
			blackKing = newPos;
			isSuccessful = true;
		}
		
		return isSuccessful;
	}

	@Override
	public void update(Graphics g) {
		repaint();
	}

	private class ClickListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				int x = e.getPoint().x / tileSize;
				int y = e.getPoint().y / tileSize;

				int[] tile = {x, y};
				Util.debug("Click point: " + tile[0] + ", " + tile[1]);
				
				if (x >= 8 || y >= 8) {
					Util.debug("Out of bounds");
				}
				else if (selectedTile == null) {
					Util.debug("Tile selected");

					Piece piece = GameBoard.tile[tile[0]][tile[1]];
					if (piece == null) {
						System.out.println("Empty tile");
					}
					else if (piece.getColor().equalsIgnoreCase(GameEngine.getTurn())) {
						System.out.println("Piece selected");
						selectedTile = tile;
					}
					else if (!piece.getColor().equalsIgnoreCase(GameEngine.getTurn())) {
						System.out.println("This is not one of your pieces: " + GameEngine.getTurn() + " to move.");
					}
				}
				else if (tile[0] == selectedTile[0] && tile[1] == selectedTile[1]) {
					Util.debug("Tile deselected");
					selectedTile = null;
				}
				else {
					GameEngine.attemptMove(getPiece(selectedTile), tile);
					update(getGraphics());
					selectedTile = null;
				}
			}
		}
	}
}