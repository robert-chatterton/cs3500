package cs3500.excellence.shape;

/**
 * Represents a position with coordinates within a space.
 */
public interface IPosition {

  /**
   * Returns the {@link ModelPosition}'s x-coordinate.
   *
   * @return the x-coordinate
   */
  double getX();


  /**
   * Returns the {@link ModelPosition}'s y-coordinate.
   *
   * @return the y-coordinate
   */
  double getY();

  /**
   * Returns a String representation of the position in the format of {@code x y}, where each of the
   * respective coordinates are replaced.
   *
   * @return a {@link String} with the {@link ModelPosition}'s coordinates
   */
  String toString();

  IPosition copy();

}
