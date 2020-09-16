package cs3500.marblesolitaire.model.hw04;

import java.util.ArrayList;

import cs3500.marblesolitaire.model.hw02.GamePiece;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Represents a triangular board.
 */
public final class TriangleSolitaireModelImpl extends AbstractSolitaireModel
        implements MarbleSolitaireModel {

  /**
   * Constructor 1: takes in nothing, instantiates classic, simple board.
   */
  public TriangleSolitaireModelImpl() {
    this.armThickness = 5;
    this.totalThickness = armThickness;

    this.pieces = this.createBoard(0, 0);
  }

  /**
   * Constructor 2: takes in row/col of empty spot, arm thickness of 3.
   * @param sRow is the row coordinate of the empty spot.
   * @param sCol is the column coordinate of the empty spot.
   * @throws IllegalArgumentException if the position {@code sRow, sCol} is illegal.
   */
  public TriangleSolitaireModelImpl(int sRow, int sCol) {
    this.armThickness = 5;
    this.totalThickness = armThickness;
    int buffer = this.armThickness - 1;

    if (this.offBoard(sRow, sCol, buffer)) {
      throw new IllegalArgumentException(
              "Invalid empty cell position (" + sRow + "," + sCol + ")");
    }

    this.pieces = this.createBoard(sRow, sCol);

  }

  /**
   * Constructor 3: takes in a custom armThickness, empty spot goes in the middle.
   * @param armThickness is the armThickness for the game.
   * @throws IllegalArgumentException if the given armThickness is negative or even.
   */
  public TriangleSolitaireModelImpl(int armThickness) {
    if (armThickness < 0) {
      // if armThickness given is negative, throw a new IllegalArgumentException
      throw new IllegalArgumentException("Arm thickness must be a positive odd integer!");
    }

    this.armThickness = armThickness;
    this.totalThickness = armThickness;
    this.pieces = this.createBoard(0, 0);
  }

  /**
   * Constructor 4: takes in all 3 parameters to create a custom game board.
   * @param armThickness is the armThickness of the table.
   * @param sRow is the row coordinate of the empty space.
   * @param sCol is the column coordinate of the empty space.
   * @throws IllegalArgumentException if the given {@code armThickness} is negative or even.
   * @throws IllegalArgumentException if the position {@code sRow, sCol} is illegal.
   */
  public TriangleSolitaireModelImpl(int armThickness, int sRow, int sCol) {
    if (armThickness < 0) {
      // if armThickness given is negative or even, throw a new IllegalArgumentException
      throw new IllegalArgumentException("Arm thickness must be a positive odd integer!");
    }

    this.armThickness = armThickness;
    this.totalThickness = armThickness;

    int buffer = this.armThickness - 1;
    if (this.offBoard(sRow, sCol, buffer)) {
      throw new IllegalArgumentException(
              "Invalid empty cell position (" + sRow + "," + sCol + ")");
    }
    this.pieces = this.createBoard(sRow, sCol);
  }

  @Override
  protected int getBuffer(int row) {
    return armThickness - 1 - row;
  }

  @Override
  protected boolean offBoard(int row, int col, int buffer) {
    if (row < 0 || col < 0 || row >= totalThickness || col >= totalThickness) {
      // if the position is off the board on the top or left side
      return true;

    } else if ( col > row ) {
      // in either the top or bottom left squares
      return true;

    }

    // if it passes all these tests, it is a legal position
    return false;
  }

  @Override
  public boolean canMove(int row, int col) {
    if (pieces[row][col] != GamePiece.Piece) {
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

      if (col > 1) {
        y2 = col - 1;
        y3 = col - 2;
        if (pieces[x2][y2] == GamePiece.Piece) {
          if (pieces[x3][y3] == GamePiece.Empty) {
            return true;
          }
        }
      }

      if (col < (totalThickness - 2)) {
        y2 = col + 1;
        y3 = col + 2;
        if (pieces[x2][y2] == GamePiece.Piece) {
          if (pieces[x3][y3] == GamePiece.Empty) {
            return true;
          }
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

      if (col > 1) {
        y2 = col - 1;
        y3 = col - 2;
        if (pieces[x2][y2] == GamePiece.Piece) {
          if (pieces[x3][y3] == GamePiece.Empty) {
            return true;
          }
        }
      }

      if (col < totalThickness) {
        y2 = col + 1;
        y3 = col + 2;
        if (pieces[x2][y2] == GamePiece.Piece) {
          if (pieces[x3][y3] == GamePiece.Empty) {
            return true;
          }
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

    if (col < totalThickness) {
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

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) {
    // make sure game is not over
    if (this.isGameOver()) {
      throw new IllegalStateException("Game is over");
    }

    IllegalArgumentException e = new IllegalArgumentException(
            String.format("Invalid move from %d,%d to %d,%d", fromRow, fromCol, toRow, toCol));

    if (!this.canMove(fromRow, fromCol)) {
      throw e;
    }

    if (((toRow - fromRow) == 2 && (toCol - fromCol) == 2)
            || ((fromRow - toRow) == 2 && (fromCol - toCol) == 2)) {
      if ((toRow - fromRow > 0) && (toCol - fromCol > 0)) {
        pieces[fromRow + 1][fromCol + 1] = GamePiece.Empty;
      } else {
        pieces[fromRow - 1][fromCol - 1] = GamePiece.Empty;
      }
    } else if (Math.abs(toRow - fromRow) == 2 && toCol - fromCol == 0) {
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
  public String getGameState() {
    int usefulWidth;
    ArrayList<String> rows = new ArrayList<>();
    int r = 0;
    for (GamePiece[] row : getPieces()) {
      usefulWidth = armThickness + r;
      ArrayList<String> rowStrings = new ArrayList<>();
      int iterations = 0;
      for (GamePiece p : row) {
        if (p == null) {
          if (iterations < usefulWidth) {
            rowStrings.add(0, "");
          }
        } else {
          rowStrings.add(p.toString());
        }
        iterations++;
      }
      rows.add(String.join(" ", rowStrings));
      r++;
    }
    return String.join("\n", rows);
  }
}

