package cs3500.marblesolitaire.model.hw02;

/**
 * Represents a game piece in Marble Solitaire.
 */
public enum GamePiece {
  Piece("O"), Empty("_");

  private final String disp;

  GamePiece(String disp) {
    this.disp = disp;
  }

  @Override
  public String toString() {
    return disp;
  }
}
