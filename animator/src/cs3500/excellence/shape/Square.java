package cs3500.excellence.shape;

/**
 * Represents a geometric square.
 */
public class Square extends Rectangle {

  /**
   * A constructor for a squre that takes in values for its side length and color.
   *
   * @param sideLength the side length of the square
   * @param modelColor the {@link ModelColor} of the square
   */
  public Square(int sideLength, ModelColor modelColor) {
    super(sideLength, sideLength, modelColor);
  }
}
