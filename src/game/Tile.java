//package game;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//
//import pieces.King;
//import pieces.Piece;
//
//public class Tile extends JLabel {
//	private int x, y;
//	private boolean occupied;
//	private Piece piece;
//	private JLabel icon;
//	private JLabel coords;
//	
//	public Tile (int x, int y, Color color) {
//		this.setOpaque(true);
//		this.x = x;
//		this.y = y;
//		occupied = false;
//		piece = null;
//		this.setBackground(color);
//		this.setPreferredSize(new Dimension(75, 75));
//		this.setLayout(new BorderLayout());
//		coords = new JLabel(x + "," + y, JLabel.CENTER);
//		//this.add(icon, BorderLayout.CENTER);
//		//this.add(coords, BorderLayout.SOUTH);
//	}
//	
//	public void placePiece(Piece piece) {
//		occupied = true;
//		this.piece = piece;
//		this.setIcon(piece.getIcon());
//		piece.setTile(this);
//		
//		if (piece.getColor().equalsIgnoreCase("white")) {
//			GameEngine.board.whitePieces.add(piece);
//			System.out.println("White Pieces: " + GameEngine.board.whitePieces);
//		} else if (piece.getColor().equalsIgnoreCase("black")) {
//			GameEngine.board.blackPieces.add(piece);
//			System.out.println("Black Pieces: " + GameEngine.board.blackPieces);
//		}
//	}
//	
//	public Piece removePiece() {
//		Piece piece = this.piece;
//		occupied = false;
//		this.piece = null;
//		this.setIcon(null);
//		piece.setTile(null);
//		
//		if (piece.getColor().equalsIgnoreCase("white")) {
//			GameEngine.board.whitePieces.remove(piece);
//			System.out.println("White Pieces: " + GameEngine.board.whitePieces);
//		} else if (piece.getColor().equalsIgnoreCase("black")) {
//			GameEngine.board.blackPieces.remove(piece);
//			System.out.println("Black Pieces: " + GameEngine.board.blackPieces);
//		}
//		
//		return piece;
//	}
//		
//	public Piece getPiece() {
//		return piece;
//	}
//	
//	public String checkPieceColor() {
//		return piece.getColor();
//	}
//		
//	public boolean isOccupied() {
//		return occupied;
//	}
//		
//	public String getCoords() {
//		return (x + ", " + y);
//	}
//	public int x() {
//		return x;
//	}
//	
//	public int y() {
//		return y;
//	}
//}
