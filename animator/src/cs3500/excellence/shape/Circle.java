package cs3500.excellence.shape;

/**
 * Represents a geometric circle.
 */
public class Circle extends Ellipse {

  /**
   * A constructor for a circle that takes in values for radius and color.
   *
   * @param radius     the radius of the cirlce
   * @param modelColor the {@link ModelColor} of the rectangle
   */
  public Circle(int radius, ModelColor modelColor) {
    super(radius, radius, modelColor);
  }
}
