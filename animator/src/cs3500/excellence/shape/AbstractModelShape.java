package cs3500.excellence.shape;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractModelShape implements ModelShape {

  protected IColor modelColor;
  protected double width;
  protected double height;

  AbstractModelShape(double width, double height, IColor modelColor) {

    if (width < 0 || height < 0 || modelColor == null) {
      throw new IllegalArgumentException("Cannot have a shape with negative dimensions");
    }

    this.width = width;
    this.height = height;
    this.modelColor = modelColor;
  }

  @Override
  public void changeColor(IColor newModelColor) {
    this.modelColor = newModelColor;
  }

  @Override
  public IColor getModelColor() {
    return this.modelColor;
  }

  @Override
  public void resize(double newWidth, double newHeight) {
    this.width = newWidth;
    this.height = newHeight;
  }

  @Override
  public Map<String, Double> getSize() {
    Map<String, Double> dimension = new HashMap<>();
    dimension.put("width", width);
    dimension.put("height", height);

    return dimension;
  }

  @Override
  public String toString() {
    return String
        .format("%d  %d  " + this.modelColor.toString(), (int) this.width, (int) this.height);
  }

  @Override
  public int hashCode() {
    return Double.hashCode(this.area());
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
    if (obj instanceof ModelShape) {
      ModelShape compare = (ModelShape) obj;
      return this.getSize().equals(compare.getSize());
    } else {
      return false;
    }
  }
}
