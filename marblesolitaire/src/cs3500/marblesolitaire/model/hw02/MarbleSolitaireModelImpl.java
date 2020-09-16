package cs3500.marblesolitaire.model.hw02;

import cs3500.marblesolitaire.model.hw04.AbstractSolitaireModel;

/**
 * This class implements the MarbleSolitaireModel interface.
 */
public final class MarbleSolitaireModelImpl extends AbstractSolitaireModel
        implements MarbleSolitaireModel {

  /**
   * Constructor 1: takes in nothing, instantiates classic, simple board.
   */
  public MarbleSolitaireModelImpl() {
    this.armThickness = 3;
    this.totalThickness = (2 * this.armThickness - 2) + this.armThickness;
    this.pieces = this.createBoard(3, 3);
  }

  /**
   * Constructor 2: takes in row/col of empty spot, arm thickness of 3.
   * @param sRow is the row coordinate of the empty spot.
   * @param sCol is the column coordinate of the empty spot.
   * @throws IllegalArgumentException if the position {@code sRow, sCol} is illegal.
   */
  public MarbleSolitaireModelImpl(int sRow, int sCol) {
    this.armThickness = 3;
    this.totalThickness = (2 * this.armThickness - 2) + this.armThickness;
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
  public MarbleSolitaireModelImpl(int armThickness) {
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
  public MarbleSolitaireModelImpl(int armThickness, int sRow, int sCol) {
    if (armThickness < 0 || armThickness % 2 == 0) {
      // if armThickness given is negative or even, throw a new IllegalArgumentException
      throw new IllegalArgumentException("Arm thickness must be a positive odd integer!");
    }

    this.armThickness = armThickness;
    this.totalThickness = (2 * this.armThickness - 2) + this.armThickness;

    int buffer = this.armThickness - 1;
    if (this.offBoard(sRow, sCol, buffer)) {
      throw new IllegalArgumentException(
              "Invalid empty cell position (" + sRow + "," + sCol + ")");
    }
    this.pieces = this.createBoard(sRow, sCol);
  }

  @Override
  protected int getBuffer(int row) {
    return armThickness - 1;
  }

  @Override
  protected boolean offBoard(int row, int col, int buffer) {
    if (row < 0 || col < 0 || row >= totalThickness || col >= totalThickness) {
      // if the position is off the board on the top or left side
      return true;

    } else if (row < buffer && (col < buffer
            || col >= (armThickness + buffer))) {
      // in either the top or bottom left squares
      return true;

    } else if (row >= (armThickness + buffer) && (col < buffer
            || col >= (armThickness + buffer))) {
      // in either the top or bottom right squares
      return true;
    }

    // if it passes all these tests, it is a legal position
    return false;
  }
}
