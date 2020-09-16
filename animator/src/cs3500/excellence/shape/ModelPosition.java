package cs3500.excellence.shape;

import java.text.DecimalFormat;

/**
 * A representation of a position using Cartesian coordinates.
 */
public class ModelPosition implements IPosition {

  protected final double x;
  protected final double y;

  /**
   * A constructor that takes in the x- and y-coordinates for the position.
   *
   * @param x the x-coordinate
   * @param y the y-coordinate
   */
  public ModelPosition(double x, double y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public double getX() {
    return this.x;
  }

  @Override
  public double getY() {
    return this.y;
  }


  @Override
  public String toString() {
    DecimalFormat df = new DecimalFormat("#");
    return df.format(this.x) + "  " + df.format(this.y);
  }

  @Override
  public int hashCode() {
    return Double.hashCode(this.x * this.y);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    // this instance check
    if (this == obj) {
      return true;
    }

    // instanceof Check and actual value check
    if (obj instanceof ModelPosition) {
      ModelPosition compare = (ModelPosition) obj;
      return compare.x == this.x
          && compare.y == this.y;
    } else {
      return false;
    }
  }

  @Override
  public ModelPosition copy() {
    return new ModelPosition(this.x, this.y);
  }
}
