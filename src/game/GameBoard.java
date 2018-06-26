package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
	private static Tile[][] board = new Tile[8][8];
	private static Tile selectedTile = null;
	private static Piece whiteKing;
	private static Piece blackKing;
	private static boolean wCheck = false;
	private static boolean bCheck = false;
	private static LinkedList<Piece> whitePieces = new LinkedList<Piece>();
	private static LinkedList<Piece> blackPieces = new LinkedList<Piece>();
	private static Map<Tile, ArrayList<Piece>> threatsToWhite = new HashMap<Tile, ArrayList<Piece>>();
	private static Map<Tile, ArrayList<Piece>> threatsToBlack = new HashMap<Tile, ArrayList<Piece>>();

	public GameBoard(int tileSize) {
		this.tileSize = tileSize;
		this.setSize(new Dimension(tileSize * 8, tileSize * 8));
		this.addMouseListener(new ClickListener());
		
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				board[col][row] = new Tile(col, row);
			}
		}

		setup();
		mapMoves();
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
				if (board[col][row].getPiece() != null) {
					//					Util.debug("Drawing piece at " + col + ", " + row);
					//					Util.debug("Image is not null: " + (tile[col][row].getImage() != null));
					Image img = board[col][row].getPiece().getImage();
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

		whiteKing = whitePlayer[4];

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

		blackKing = blackPlayer[4];

		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < 8; col++) {
				Tile tile = board[col][row];
				Piece piece = blackPlayer[col + (8 * row)];
				placePiece(piece, tile);
				blackPieces.add(piece);
			}
		}

		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < 8; col++) {
				Tile tile = board[col][7-row];
				Piece piece = whitePlayer[col + (8 * row)];
				placePiece(piece, tile);
				whitePieces.add(piece);
			}
		}
	}
	
	public static void placePiece(Piece piece, Tile tile) {
		tile.placePiece(piece);
		piece.setCurrentPos(tile);
	}

	public static void movePiece(Tile start, Tile end) {		
		Piece piece = start.getPiece();

		if (piece != null) {
			placePiece(piece, end);
			start.removePiece();
		}		
	}

//	// Remove a piece from a position
//	public static void removePiece(Tile t) {
//		t.removePiece();
//	}

	public static void addPiece(Piece endPiece) {
		if (endPiece.getColor().equals("white")) {
			whitePieces.add(endPiece);
		}
		else {
			blackPieces.add(endPiece);
		}
	}

	// If a piece is captured it needs to be removed from the list of pieces
	public static void removePiece(Piece piece) {
		if (piece != null) {
			if (piece.getColor().equalsIgnoreCase("white")) {
				whitePieces.remove(piece);
			}
			else {
				blackPieces.remove(piece);
			}
		}
	}

	public static LinkedList<Piece> getPieceSet(String color) {
		if (color.equalsIgnoreCase("white")) {
			return whitePieces;
		}
		else {
			return blackPieces;
		}
	}

	public static boolean isEmpty(Tile p) {
		boolean isEmpty = false;

		if (board[p.x()][p.y()] == null) {
			isEmpty = true;
		}

		return isEmpty;
	}
	
	public static Map<Tile, ArrayList<Piece>> getThreatsTo(String color) {
		if (color.equals("white")) {
			return threatsToWhite;
		}
		else if (color.equals("black")) {
			return threatsToBlack;
		}
		else {
			Util.debug("Invalid color passed - GameBoard.class.getThreat");
			return null;
		}
	}

	public static Piece getKing(String color) {
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

	public static boolean wCheck() {
		return wCheck;
	}

	public static void wCheck(boolean b) {
		wCheck = b;
	}

	public static boolean bCheck() {
		return bCheck;
	}

	public static void bCheck(boolean b) {
		bCheck = b;
	}

	public static void mapMoves() {
		Util.debug("Mapping moves...");
		threatsToWhite.clear();
		threatsToBlack.clear();
		for (Piece piece : whitePieces) {
			piece.calcMoves();
			ArrayList<Tile> moves = piece.getMoves();
			for (Tile move : moves) {
				if (threatsToBlack.containsKey(move)) {
					ArrayList<Piece> pieces = threatsToBlack.get(move);
					pieces.add(piece);
				}
				else {
					ArrayList<Piece> pieces = new ArrayList<Piece>();
					pieces.add(piece);
					threatsToBlack.put(move, pieces);
				}
			}
		}
		
		for (Piece piece : blackPieces) {
			piece.calcMoves();
			ArrayList<Tile> moves = piece.getMoves();
			for (Tile move : moves) {
				if (threatsToWhite.containsKey(move)) {
					ArrayList<Piece> pieces = threatsToWhite.get(move);
					pieces.add(piece);
				}
				else {
					ArrayList<Piece> pieces = new ArrayList<Piece>();
					pieces.add(piece);
					threatsToWhite.put(move, pieces);
				}
			}
		}
		
		System.out.println(threatsToWhite);
		System.out.println(threatsToBlack);
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

				Tile t = board[x][y];
				Util.debug("Click point: " + t.x() + ", " + t.y());

				if (x >= 8 || y >= 8) {
					Util.debug("Out of bounds");
				}
				else if (selectedTile == null) {
					Util.debug("Tile selected");

					Piece piece = t.getPiece();
					if (piece == null) {
						System.out.println("Empty tile");
					}
					else if (piece.getColor().equalsIgnoreCase(GameEngine.getTurn())) {
						System.out.println("Piece selected");
						selectedTile = t;
					}
					else if (!piece.getColor().equalsIgnoreCase(GameEngine.getTurn())) {
						System.out.println("This is not one of your pieces: " + GameEngine.getTurn() + " to move.");
					}
				}
				else if (t.equals(selectedTile)) {
					Util.debug("Tile deselected");
					selectedTile = null;
				}
				else {
					GameEngine.attemptMove(selectedTile.getPiece(), t);
					if ((bCheck || wCheck)) {
						Util.debug("White king is in check: " + wCheck + ", Black king is in check: " + bCheck);
						if (GameEngine.isCheckmate()) {
							if (wCheck) {
								Util.debug("White king has been checkmated.");
							}
							else if (bCheck) {
								Util.debug("Black king has been checkmated.");
							}
							else {
								Util.debug("Not actually checkmate - GameBoard.Class.ClickListener");
							}
						}
					}
					update(getGraphics());
					selectedTile = null;
					mapMoves();
				}
			}
		}
	}
}