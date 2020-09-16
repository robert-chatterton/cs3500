package cs3500.excellence.shape;

import java.util.HashMap;
import java.util.Map;

/**
 * A class of representing an ellipse.
 */
public class Ellipse extends AbstractModelShape {

  /**
   * A constructor for a ellipse that takes in values for its width, height, and color.
   *
   * @param width      the width of the ellipse
   * @param height     the height of the ellipse
   * @param modelColor the {@link ModelColor} of the rectangle
   */
  public Ellipse(double width, double height, IColor modelColor) {
    super(width, height, modelColor);
  }

  @Override
  public Map<String, Double> getSize() {
    Map<String, Double> dimensions = new HashMap<>();

    dimensions.put("width", this.width);
    dimensions.put("height", this.height);

    return dimensions;
  }

  @Override
  public double area() {
    return Math.PI * (this.height / 2) * (this.width / 2);
  }

  @Override
  public ModelShape copy() {
    return new Ellipse(this.width, this.height, this.modelColor);
  }
}
