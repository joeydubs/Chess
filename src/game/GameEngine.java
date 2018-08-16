//package game;
//
//import java.util.ArrayList;
//import java.util.Map;
//
//import pieces.Piece;
//
//public class GameEngine {
//	static private String turn = "white";
//	static private ArrayList<Piece> whitePCC = new ArrayList<Piece>();
//	static private ArrayList<Piece> blackPCC = new ArrayList<Piece>();
//
//	public static String getTurn() {
//		return turn;
//	}
//
//	public static boolean attemptMove(Piece piece, Tile end) {
//		boolean success = false;
//		Piece startPiece = piece;
//		Piece endPiece = null;
//		Tile start = piece.getCurrentPos();
//
//		if (end.x() < 0 || end.x() > 7 || end.y() < 0 || end.y() > 7) {
//			Util.debug("Position out of bounds - GameEngine.Class.attemptMove");
//			return success;
//		}
//
//		if (!GameBoard.isEmpty(end)) {
//			endPiece = end.getPiece();
//		}
//
//		if (startPiece.moveIsValid(end)) {
//			Util.debug("Valid Move");
//			GameBoard.movePiece(start, end);
//			GameBoard.removePiece(endPiece);
//
//			GameBoard.mapMoves();
//
//			checkStatus();
//
//			boolean wCheck = GameBoard.wCheck();
//			boolean bCheck = GameBoard.bCheck();
//
//			Util.debug("whiteCheck: " + wCheck + "; blackCheck: " + bCheck);
//
//			if (turn.equalsIgnoreCase("white")) {
//				if (!wCheck) {
//					turn = "black";
//					startPiece.moved();
//					success = true;
//				} else {
//					Util.debug("That move will put your king in check, please move somewhere else.");
//					undoMove(start, end, endPiece);
//				}
//			} else if (turn.equalsIgnoreCase("black")) {
//				if (!bCheck) {
//					turn = "white";
//					startPiece.moved();
//					success = true;
//				} else {
//					Util.debug("That move will put your king in check, please move somewhere else.");
//					undoMove(start, end, endPiece);
//				}
//			}
//		} else {
//			Util.debug("Invalid Move - GameEngine.Class.attemptMove");
//		}
//		return success;
//	}
//
//	private static void undoMove(Tile start, Tile end, Piece endPiece) {
//		GameBoard.movePiece(end, start);
//		if (endPiece != null) {
//			GameBoard.placePiece(endPiece, end);
//			GameBoard.addPiece(endPiece);
//		}
//	}
//
//	private static void checkStatus() {
//		GameBoard.wCheck(false);
//		GameBoard.bCheck(false);
//		Tile wKing = GameBoard.getKing("white").getCurrentPos();
//		Tile bKing = GameBoard.getKing("black").getCurrentPos();
//		Map<Tile, ArrayList<Piece>> blackThreats = GameBoard.getThreatsTo("white");
//		Map<Tile, ArrayList<Piece>> whiteThreats = GameBoard.getThreatsTo("black");
//		whitePCC.clear();
//		blackPCC.clear();
//
//		if (whiteThreats.containsKey(bKing)) {
//			GameBoard.bCheck(true);
//			for (Piece piece : whiteThreats.get(bKing)) {
//				whitePCC.add(piece);
//			}
//		}
//		if (blackThreats.containsKey(wKing)) {
//			GameBoard.wCheck(true);
//			for (Piece piece : blackThreats.get(wKing)) {
//				blackPCC.add(piece);
//			}
//		}
//	}
//
//	// private static boolean checkStatus(Point position, String color) {
//	// boolean isCheck = false;
//	// LinkedList<Piece> pieces;
//	//
//	// if (color.equalsIgnoreCase("white")) {
//	// pieces = GameBoard.getPieceSet("black");
//	// }
//	// else {
//	// pieces = GameBoard.getPieceSet("white");
//	// }
//	//
//	// for (Piece piece : pieces) {
//	// if (piece.moveIsValid(position)) {
//	// isCheck = true;
//	// }
//	// }
//	//
//	// return isCheck;
//	// }
//
//	public static boolean isCheckmate() {
//		boolean isCheckmate = true;
//
//		Piece king;
//		ArrayList<Piece> pCC;
//
//		if (GameBoard.wCheck()) {
//			king = GameBoard.getKing("white");
//			pCC = blackPCC;
//		} else {
//			king = GameBoard.getKing("black");
//			pCC = whitePCC;
//		}
//
//		isCheckmate = !kingCanEscape(king, pCC);
//
//		if (pCC.size() == 1 && isCheckmate) {
//			isCheckmate = !canBlockRoute(king, pCC.get(0));
//		}
//
//		return isCheckmate;
//	}
//
//	private static boolean kingCanEscape(Piece king, ArrayList<Piece> pCC) {
//		Util.debug("Checking if king can escape...");
//		boolean canEscape = false;
//		Tile kingStart = king.getCurrentPos();
//		ArrayList<Tile> kingMoves = (ArrayList<Tile>) king.getMoves().clone();
//
//		for (Tile p : kingMoves) {
//			Util.debug("Checking king's ability to move to " + p + "...");
//			if (GameBoard.getThreatsTo(king.getColor()).containsKey(p)) {
//				Util.debug("Position " + p + " is under threat, unable to move there...");
//			} else {
//				Piece endPiece = p.getPiece();
//				GameBoard.movePiece(king.getCurrentPos(), p);
//				GameBoard.removePiece(endPiece);
//				GameBoard.mapMoves();
//
//				if (!GameBoard.getThreatsTo(king.getColor()).containsKey(king.getCurrentPos())) {
//					Util.debug("King can escape to position " + p + "...");
//
//					canEscape = true;
//				}
//
//				undoMove(kingStart, p, endPiece);
//			}
//		}
//
//		return canEscape;
//	}
//
//	private static boolean canBlockRoute(Piece king, Piece piece) {
//		boolean canBlock = false;
//
//		ArrayList<Tile> path = piece.getMovesCopy();
//		Map<Tile, ArrayList<Piece>> kingsGuardians = GameBoard.getThreatsTo(piece.getColor());
//
//		for (Tile p : path) {
//			if (kingsGuardians.containsKey(p)) {
//				ArrayList<Piece> pieces = kingsGuardians.get(p);
//				for (Piece guardian : pieces) {
//					Tile guardianStart = guardian.getCurrentPos();
//					Piece endPiece = p.getPiece();
//					GameBoard.movePiece(guardian.getCurrentPos(), p);
//					GameBoard.mapMoves();
//
//					if (!GameBoard.getThreatsTo(king.getColor()).containsKey(king.getCurrentPos())) {
//						Util.debug("Piece " + guardian + " can block check at position " + p + "...");
//
//						canBlock = true;
//					}
//
//					undoMove(guardianStart, p, endPiece);
//				}
//			}
//		}
//
//		if (!canBlock) {
//			Tile attackerPos = piece.getCurrentPos();
//			if (kingsGuardians.containsKey(attackerPos)) {
//				ArrayList<Piece> pieces = kingsGuardians.get(attackerPos);
//				for (Piece guardian : pieces) {
//					Tile guardianStart = guardian.getCurrentPos();
//
//					GameBoard.movePiece(guardian.getCurrentPos(), attackerPos);
//					GameBoard.removePiece(piece);
//					GameBoard.mapMoves();
//
//					if (!GameBoard.getThreatsTo(king.getColor()).containsKey(king.getCurrentPos())) {
//						Util.debug("Piece " + guardian + " can block check at position " + attackerPos + "...");
//
//						canBlock = true;
//					}
//
//					undoMove(guardianStart, attackerPos, piece);
//				}
//			}
//		}
//
//		return canBlock;
//	}
//}
