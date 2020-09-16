package cs3500.excellence.model;

import cs3500.excellence.shape.IColor;
import cs3500.excellence.shape.IPosition;
import cs3500.excellence.shape.ModelPosition;
import cs3500.excellence.shape.ModelShape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represent a shape with the required information for it to be used in animation. The class
 * contains the shape's name/ID, whatever motions the shape has experienced, the corresponding
 * {@link ModelShape}, and its current {@link ModelPosition}.
 */
public class AnimatedShape implements IAnimatedShape {

  protected final String name;
  protected final List<MotionType> motionsExperienced;
  protected ModelShape modelShape;
  protected IPosition modelPosition;


  AnimatedShape(String name, ModelShape modelShape, IPosition modelPosition, MotionType m) {
    this.name = name;
    this.modelShape = modelShape;
    this.modelPosition = modelPosition;
    motionsExperienced = new ArrayList<>();
    motionsExperienced.add(m);
  }

  AnimatedShape(String name, ModelShape newModelShape, IPosition modelPosition) {
    this.name = name;
    this.modelShape = newModelShape;
    this.modelPosition = modelPosition;
    motionsExperienced = new ArrayList<>();
  }


  @Override
  public String toString() {
    return String.format(modelPosition.toString() + "  " + modelShape.toString());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    // null check
    if (obj == null) {
      return false;
    }

    // this instance check
    if (this == obj) {
      return true;
    }

    // instanceof Check and actual value check
    if (obj instanceof AnimatedShape) {
      AnimatedShape compare = (AnimatedShape) obj;
      return this.name.equals(compare.name) && this.modelShape.equals(compare.modelShape);
    } else {
      return false;
    }
  }

  public String getShapeName() {
    return this.name;
  }

  public ModelShape getModelShape() {
    return this.modelShape.copy();
  }

  public IPosition getModelPosition() {
    return this.modelPosition.copy();
  }

  public void setModelPosition(IPosition newPosition) {
    this.modelPosition = newPosition;
  }

  public void changeColor(IColor color) {
    this.modelShape.changeColor(color);
  }

  @Override
  public void resize(double width, double height) {
    this.modelShape.resize(width, height);
  }

  public List<MotionType> getMotionsExperienced() {
    return Collections.unmodifiableList(this.motionsExperienced);
  }

  public void addMotionType(MotionType m) {
    this.motionsExperienced.add(m);
  }

  /**
   * Creates a copy of all the animated shape.
   *
   * @return
   */
  public IAnimatedShape copy() {
    return new AnimatedShape(name, modelShape.copy(), modelPosition.copy());
  }

}
