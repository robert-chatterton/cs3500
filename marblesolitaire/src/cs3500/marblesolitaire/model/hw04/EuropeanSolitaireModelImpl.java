package cs3500.marblesolitaire.model.hw04;

import java.util.ArrayList;

import cs3500.marblesolitaire.model.hw02.GamePiece;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Represents a "European" type board.
 */
public final class EuropeanSolitaireModelImpl extends AbstractSolitaireModel
        implements MarbleSolitaireModel {

  /**
   * Constructor 1: takes in nothing, instantiates classic, simple board.
   */
  public EuropeanSolitaireModelImpl() {
    this.armThickness = 3;
    this.totalThickness = (2 * (armThickness - 1)) + armThickness;

    this.pieces = this.createBoard(3, 3);
  }

  /**
   * Constructor 2: takes in row/col of empty spot, arm thickness of 3.
   * @param sRow is the row coordinate of the empty spot.
   * @param sCol is the column coordinate of the empty spot.
   * @throws IllegalArgumentException if the position {@code sRow, sCol} is illegal.
   */
  public EuropeanSolitaireModelImpl(int sRow, int sCol) {
    this.armThickness = 3;
    this.totalThickness = (2 * this.armThickness - 2) + this.armThickness;
    int buffer = this.getBuffer(sRow);

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
  public EuropeanSolitaireModelImpl(int armThickness) {
    if (armThickness < 0 || armThickness % 2 == 0) {
      // if armThickness given is negative or even, throw a new IllegalArgumentException
      throw new IllegalArgumentException("Arm thickness must be a positive odd integer!");
    }

    this.armThickness = armThickness;
    this.totalThickness = (2 * this.armThickness - 2) + this.armThickness;
    this.pieces = this.createBoard((armThickness - 1) + armThickness / 2,
            (armThickness - 1) + armThickness / 2);
  }

  /**
   * Constructor 4: takes in all 3 parameters to create a custom game board.
   * @param armThickness is the armThickness of the table.
   * @param sRow is the row coordinate of the empty space.
   * @param sCol is the column coordinate of the empty space.
   * @throws IllegalArgumentException if the given {@code armThickness} is negative or even.
   * @throws IllegalArgumentException if the position {@code sRow, sCol} is illegal.
   */
  public EuropeanSolitaireModelImpl(int armThickness, int sRow, int sCol) {
    if (armThickness < 0 || armThickness % 2 == 0) {
      // if armThickness given is negative or even, throw a new IllegalArgumentException
      throw new IllegalArgumentException("Arm thickness must be a positive odd integer!");
    }

    this.armThickness = armThickness;
    this.totalThickness = (2 * this.armThickness - 2) + this.armThickness;

    int buffer = this.getBuffer(sRow);
    if (this.offBoard(sRow, sCol, buffer)) {
      throw new IllegalArgumentException(
              "Invalid empty cell position (" + sRow + "," + sCol + ")");
    }
    this.pieces = this.createBoard(sRow, sCol);
  }

  @Override
  protected int getBuffer(int row) {
    if (row < armThickness - 1) {
      return armThickness - 1 - row;
    } else if (row >= armThickness - 1 && row <= (2 * armThickness) - 2) {
      return armThickness - 1;
    } else {
      return row - ((2 * armThickness) - 2);
    }
  }

  @Override
  protected boolean offBoard(int row, int col, int buffer) {
    int hBuffer = armThickness - 1;

    if (row < 0 || col < 0 || row >= totalThickness || col >= totalThickness) {
      // if the position is off the board on the top or left side
      return true;

    } else if (row < hBuffer && (col < buffer
            || col > (totalThickness - buffer - 1))) {
      // in either the top or bottom left squares
      return true;

    } else if (row >= (armThickness + hBuffer) && (col < buffer
            || col > (totalThickness - buffer - 1))) {
      // in either the top or bottom right squares
      return true;

    }

    // if it passes all these tests, it is a legal position
    return false;
  }

  @Override
  public String getGameState() {
    int usefulWidth;
    ArrayList<String> rows = new ArrayList<>();
    int r = 0;
    for (GamePiece[] row : getPieces()) {
      usefulWidth = getBuffer(r);
      ArrayList<String> rowStrings = new ArrayList<String>();
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
      r++;
    }
    return String.join("\n", rows);
  }
}
