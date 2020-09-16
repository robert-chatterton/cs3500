package cs3500.excellence.shape;

/**
 * A class for representing the classic rectangle.
 */
public class Rectangle extends AbstractModelShape {

  /**
   * A constructor for a rectangle that takes in values for its width, height, and color.
   *
   * @param width      the width of the rectangle
   * @param height     the height of the rectangle
   * @param modelColor the {@link ModelColor} of the rectangle
   */
  public Rectangle(double width, double height, IColor modelColor) {
    super(width, height, modelColor);
  }


  @Override
  public double area() {
    return this.width * this.height;
  }

  @Override
  public ModelShape copy() {
    return new Rectangle(this.width, this.height, this.modelColor);
  }
}
