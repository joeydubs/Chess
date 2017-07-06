package game;

import pieces.King;
import pieces.Piece;

public class GameEngine {
	static private String turn = "white";
	static private boolean whiteCheck;
	static private boolean blackCheck;

	public static String getTurn() {
		return turn;
	}

	public static void attemptMove(Piece piece, int[] end) {
		Piece startPiece = piece;
		Piece endPiece = null;
		int[] start = piece.getCurrentPos();

		int colDist = end[0] - start[0];
		int rowDist = end[1] - start[1];

		if (start[0] + colDist >= 8 || start[1] + rowDist >= 8) {
			Util.debug("Invalid move");
			return;
		}

		if (!GameBoard.isEmpty(end)) {
			endPiece = GameBoard.getPiece(end);
		}

		if (endPiece != null && endPiece.getColor().equalsIgnoreCase(turn)) {
			Util.debug("Invalid move");
			return;
		}

		if (startPiece.moveIsValid(end)) {
			Util.debug("Valid Move");
			GameBoard.placePiece(startPiece, end);
			GameBoard.removePiece(start);

			if (startPiece.getClass().equals(King.class)) {
				Util.debug("King moved");
				if (!GameBoard.updateKingPos(startPiece, end)) {
					Util.debug("An error occurred updating the kings position");
					undoMove(startPiece, endPiece, start, end);
				}
			}

			checkStatus();
			Util.debug("whiteCheck: " + whiteCheck + "; blackCheck: " + blackCheck);

			if (turn.equalsIgnoreCase("white")) {
				if (!whiteCheck) {
					turn = "black";
				} else {
					Util.debug("That move will put your king in check, please move somewhere else.");
					undoMove(startPiece, endPiece, start, end);
				}
			} else if (turn.equalsIgnoreCase("black")) {
				if (!blackCheck) {
					turn = "white";
				} else {
					Util.debug("That move will put your king in check, please move somewhere else.");
					undoMove(startPiece, endPiece, start, end);
				}
			}
		} else {
			Util.debug("Invalid Move");
		}

		//		checkStatus();
	}

	private static void undoMove(Piece startPiece, Piece endPiece, int[] start, int[] end) {
		GameBoard.placePiece(startPiece, start);
		GameBoard.placePiece(endPiece, end);

		if (startPiece.getClass().equals(King.class)) {
			GameBoard.updateKingPos(startPiece, start);
		}
	}

	private static void checkStatus() {
		int[] whiteKing = GameBoard.getKingPosition("white");
		int[] blackKing = GameBoard.getKingPosition("black");
		for (int col = 0; col < 8; col++) {
			for (int row = 0; row < 8; row++) {
				int[] current = {col, row};

				if (!GameBoard.isEmpty(current)) {
					Piece piece = GameBoard.getPiece(current);
					if (piece.getColor().equalsIgnoreCase("black")) {
						//Util.debug("Checking move from " + col + ", " + row + " to " + whiteKing[0] + ", " + whiteKing[1]);
						if (piece.moveIsValid(whiteKing)) {
							whiteCheck = true;
							return;
						}
						else {
							whiteCheck = false;
						}
					}
					else if (piece.getColor().equalsIgnoreCase("white")) {
//						Util.debug("Checking move from " + col + ", " + row + " to " + blackKing[0] + ", " + blackKing[1]);
						if (piece.moveIsValid(blackKing)) {
							blackCheck = true;
							return;
						}
						else {
							blackCheck = false;
						}
					}
				}
			}
		}
	}
}
