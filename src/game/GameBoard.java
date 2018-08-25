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
	private static String turn = "white";
	private static Tile selectedTile = null;
	private static boolean whiteInCheck = false;
	private static boolean blackInCheck = false;
	private static LinkedList<Piece> whitePieces = new LinkedList<Piece>();
	private static LinkedList<Piece> blackPieces = new LinkedList<Piece>();
	private static Map<Tile, ArrayList<Piece>> moves = new HashMap<Tile, ArrayList<Piece>>();

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
				} else {
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
				// Util.debug("Tile is not null: " + (tile[col][row] != null));
				if (board[col][row].getPiece() != null) {
					// Util.debug("Drawing piece at " + col + ", " + row);
					// Util.debug("Image is not null: " + (tile[col][row].getImage() != null));
					Image img = board[col][row].getPiece().getImage();
					g.drawImage(img, (col * tileSize), (row * tileSize), this);
				}
			}
		}
	}

	private void setup() {
		Util.debug("Setting up pieces...");
		Piece[] blackPlayer = new Piece[16];
		Piece[] whitePlayer = new Piece[16];
		int toPlace = 0;

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
				Tile tile = board[col][row];
				Piece piece = blackPlayer[toPlace];
				placePiece(piece, tile);
				blackPieces.add(piece);
				toPlace++;
			}
		}
		
		toPlace = 0;
		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < 8; col++) {
				Tile tile = board[col][7 - row];
				Piece piece = whitePlayer[toPlace];
				placePiece(piece, tile);
				whitePieces.add(piece);
				toPlace++;
			}
		}
	}
	
	// Return value indicates if the piece can move there [0], and if it can keep moving [1]
	public static boolean[] canMoveHere(Piece p, int x, int y) {
		boolean[] answer = new boolean[2];
		
		if (x < 0 || x > 7 || y < 0 || y > 7) {
			answer[0] = false;
			answer[1] = false;
			
			return answer;
		}
		
		Tile t = board[x][y];
		
		if (!t.isOccupied()) {
//			System.out.println("Tile is not occupied...");
			answer[0] = true;
			answer[1] = true;
			
			if (moves.containsKey(t)) {
				moves.get(t).add(p);
			}
			else {
				moves.put(t, new ArrayList<Piece>(){{add(p);}});
			}

		}
		else {
//			System.out.println("Tile is occupied...");
			answer[1] = false;
			
			Piece occupying = t.getPiece();
			
			if (!occupying.getColor().equalsIgnoreCase(p.getColor())) {
//				System.out.println("Piece is a different color...");
				answer[0] = true;
				if (moves.containsKey(t)) {
					moves.get(t).add(p);
				}
				else {
					moves.put(t, new ArrayList<Piece>(){{add(p);}});
				}
			}
			else {
//				System.out.println("Piece is a the same color...");
				answer[0] = false;
//				System.out.println("Answer[0] = " + answer[0]);

			}
		}
		
		return answer;
	}

	private static void placePiece(Piece piece, Tile tile) {
		tile.placePiece(piece);
		piece.setCurrentPos(tile);
	}
	
	private static boolean isValidMove(Piece p, Tile t) {
		ArrayList<Piece> tile = moves.get(t);
		if (moves.containsKey(t) && moves.get(t).contains(p)) {
			return true;
		}
		else {
			return false;
		}
	}

	private static boolean movePiece(Tile start, Tile end) {
		Piece toMove = start.getPiece();
		Piece enemy = end.getPiece();

		if (toMove != null) {
			placePiece(toMove, end);
			start.removePiece();
			toMove.moved();
			
			if (enemy != null) {
				removePiece(enemy);
			}
			
			if (turn.equalsIgnoreCase("white")) {
				turn = "black";
			}
			else {
				turn = "white";
			}
			
			return true;
		}
		else {
			return false;
		}
	}

	// If a piece is captured it needs to be removed from the list of pieces
	private static void removePiece(Piece piece) {
		if (piece.getColor().equalsIgnoreCase("white")) {
			whitePieces.remove(piece);
		} else {
			blackPieces.remove(piece);
		}
	}
				
	public static void mapMoves() {
		Util.debug("Mapping moves...");
		moves.clear();
		for (Piece piece : whitePieces) {
			piece.calcMoves();
		}

		for (Piece piece : blackPieces) {
			piece.calcMoves();
		}
		
		System.out.println(moves);
	}

	@Override
	public void update(Graphics g) {
		repaint();
	}

	private class ClickListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				int x = e.getPoint().x / tileSize;
				int y = e.getPoint().y / tileSize;

				Tile t = board[x][y];
				Piece piece = t.getPiece();

				Util.debug("Click point: " + t.toString());

				if (x >= 8 || y >= 8) {
					Util.debug("Out of bounds");
				}
				else if (selectedTile == null) {
					Util.debug("Tile selected");
					
					if (piece == null) {
						System.out.println("Empty tile");
					}
					else if (piece.getColor().equalsIgnoreCase(turn)) {
						System.out.println("Piece selected");
						selectedTile = t;
					}
					else if (!piece.getColor().equalsIgnoreCase(turn)) {
						System.out.println("This is not one of your pieces: " + turn + " to move.");
					}
				}
				else if (t.equals(selectedTile)) {
					Util.debug("Tile deselected");
					selectedTile = null;
				}
				else {
					if (isValidMove(selectedTile.getPiece(), t)) {
						movePiece(selectedTile, t);
					}
					else {
						Util.debug("Invallid move...");
					}
//					if ((blackInCheck || whiteInCheck)) {
//						Util.debug("White king is in check: " + whiteInCheck + ", Black king is in check: " + blackInCheck);
//						if (GameEngine.isCheckmate()) {
//							if (whiteInCheck) {
//								Util.debug("White king has been checkmated.");
//							} else if (blackInCheck) {
//								Util.debug("Black king has been checkmated.");
//							} else {
//								Util.debug("Not actually checkmate - GameBoard.Class.ClickListener");
//							}
//						}
					update(getGraphics());
					selectedTile = null;
					mapMoves();
				}
			}
		}
	}
}