package cs3500.marblesolitaire.model.hw04;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.marblesolitaire.model.hw02.GamePiece;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * The base abstract class that all of the model implementations extend from.
 */
public abstract class AbstractSolitaireModel implements MarbleSolitaireModel {
  protected int armThickness;
  protected int totalThickness;
  protected GamePiece[][] pieces;

  /**
   * Determines if a piece at the given coordinates can make a move or not.
   * @param row the row of the piece.
   * @param col the column of the piece
   * @return true if the piece can make a move, false otherwise
   */
  public boolean canMove(int row, int col) {
    if (pieces[row][col] != GamePiece.Piece) {
      return false;
    }

    if (row < 0 || col < 0) {
      return false;
    }

    int x2;
    int x3;
    int y2;
    int y3;

    if (row > 1) {
      x2 = row - 1;
      x3 = row - 2;
      if (pieces[x2][col] == GamePiece.Piece) {
        if (pieces[x3][col] == GamePiece.Empty) {
          return true;
        }
      }
    }

    if (row < (totalThickness - 2)) {
      x2 = row + 1;
      x3 = row + 2;
      if (pieces[x2][col] == GamePiece.Piece) {
        if (pieces[x3][col] == GamePiece.Empty) {
          return true;
        }
      }
    }

    if (col > 1) {
      y2 = col - 1;
      y3 = col - 2;
      if (pieces[row][y2] == GamePiece.Piece) {
        if (pieces[row][y3] == GamePiece.Empty) {
          return true;
        }
      }
    }

    if (col < (totalThickness - 2)) {
      y2 = col + 1;
      y3 = col + 2;
      if (pieces[row][y2] == GamePiece.Piece) {
        if (pieces[row][y3] == GamePiece.Empty) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Creates the game board.
   * @param sRow row of the empty spot
   * @param sCol column of the empty spot
   * @return the game board as GamePiece[][] array.
   */
  protected GamePiece[][] createBoard(int sRow, int sCol) {
    GamePiece[][] newBoard = new GamePiece[totalThickness][totalThickness];
    int buffer;

    // put pieces everywhere
    for (int i = 0; i < totalThickness; i++) {
      for (int j = 0; j < totalThickness; j++) {
        buffer = this.getBuffer(i);
        if (!this.offBoard(i, j, buffer)) {
          newBoard[i][j] = GamePiece.Piece;
        }
      }
    }

    if (newBoard[sRow][sCol] == GamePiece.Piece) {
      newBoard[sRow][sCol] = GamePiece.Empty;
    }

    return newBoard;
  }

  /**
   * Determines if the given position is off the playing board.
   * @param row the row of the position
   * @param col the column of the position
   * @param buffer the buffer, for determining if the piece is in play
   * @return true if this spot is off the board
   */
  protected abstract boolean offBoard(int row, int col, int buffer);

  /**
   * Gets the buffer for each model implementation.
   * @param row the row to be checked
   * @return the buffer
   */
  protected abstract int getBuffer(int row);

  /**
   * Gets the board as a 2D array of GamePiece.
   * @return the current game board
   */
  public GamePiece[][] getPieces() {
    GamePiece[][] returnable = new GamePiece[totalThickness][totalThickness];
    for (int r = 0; r < pieces.length; r++) {
      returnable[r] = Arrays.copyOf(pieces[r], pieces[r].length);
    }
    return returnable;
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) {
    // make sure game is not over
    if (this.isGameOver()) {
      throw new IllegalStateException("Game is over");
    }

    IllegalArgumentException e = new IllegalArgumentException(String.format(
            "Invalid move from %d,%d to %d,%d", fromRow, fromCol, toRow, toCol));

    if (!this.canMove(fromRow, fromCol)) {
      throw e;
    }

    if (Math.abs(toRow - fromRow) == 2 && toCol - fromCol == 0) {
      if (toRow - fromRow > 0) {
        pieces[fromRow + 1][fromCol] = GamePiece.Empty;
      } else {
        pieces[fromRow - 1][fromCol] = GamePiece.Empty;
      }
    } else if (Math.abs(toCol - fromCol) == 2 && toRow - fromRow == 0) {
      if (toCol - fromCol > 0) {
        pieces[fromRow][fromCol + 1] = GamePiece.Empty;
      } else {
        pieces[fromRow][fromCol - 1] = GamePiece.Empty;
      }
    } else {
      throw e;
    }

    pieces[fromRow][fromCol] = GamePiece.Empty;
    pieces[toRow][toCol] = GamePiece.Piece;
  }

  @Override
  public boolean isGameOver() {
    int legalMoves = 0;
    // check if there are any more available moves
    for (int r = 0; r < totalThickness; r++) {
      for (int c = 0; c < totalThickness; c++) {
        if (pieces[r][c] == GamePiece.Piece) {
          if (this.canMove(r, c)) {
            legalMoves++;
          }
        }
        if (legalMoves > 0) {
          break;
        }
      }
      if (legalMoves > 0) {
        break;
      }
    }

    return legalMoves == 0;
  }

  @Override
  public String getGameState() {
    int usefulWidth = (armThickness * 2) - 1;
    ArrayList<String> rows = new ArrayList<>();
    for (GamePiece[] row : getPieces()) {
      ArrayList<String> rowStrings = new ArrayList<>();
      int iterations = 0;
      for (GamePiece p : row) {
        if (p == null) {
          if (iterations < usefulWidth) {
            rowStrings.add(" ");
          }
        } else {
          rowStrings.add(p.toString());
        }
        iterations++;
      }
      rows.add(String.join(" ", rowStrings));
    }
    return String.join("\n", rows);
  }

  @Override
  public int getScore() {
    int score = 0;
    for (int r = 0; r < totalThickness; r++) {
      for (int c = 0; c < totalThickness; c++) {
        if (this.pieces[r][c] == GamePiece.Piece) {
          score++;
        }
      }
    }
    return score;
  }
}
