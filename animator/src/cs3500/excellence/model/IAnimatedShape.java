package cs3500.excellence.model;

import cs3500.excellence.shape.IColor;
import cs3500.excellence.shape.IPosition;
import cs3500.excellence.shape.ModelShape;
import java.util.List;

/**
 * A representation of an shape in an animation, where the shape contains a name, a reference
 * geometric shape and its position.
 */
public interface IAnimatedShape {

  /**
   * Returns the shape's ID.
   *
   * @return ^
   */
  String getShapeName();

  /**
   * Returns the geometric shape representing this animated shape.
   *
   * @return a {@link ModelShape}
   */
  ModelShape getModelShape();

  /**
   * Returns the position this shape is a specified time in an animation.
   *
   * @return
   */
  IPosition getModelPosition();

  /**
   * Sets a new position for the shape, as to move the shape in the animation.
   *
   * @param newPosition the new coordinates of this shape
   */
  void setModelPosition(IPosition newPosition);

  /**
   * Returns what motions the shape experienced. at a given time, such as motion, resizing, or
   * recolor.
   *
   * @return
   */
  List<MotionType> getMotionsExperienced();

  /**
   * Adds a new type of supported motion to the animated shape.
   *
   * @param m a supported motion
   */
  void addMotionType(MotionType m);

  /**
   * Changes the color of the geometric shape representing this animated shape.
   *
   * @param color the new color to be set
   */
  void changeColor(IColor color);

  /**
   * Resizes the {@link ModelShape} associated with this shape.
   *
   * @param width  new width
   * @param height new height
   */
  void resize(double width, double height);

  /**
   * Creates a new {@link IAnimatedShape} with the exact same parameters as this one.
   *
   * @return a copy of an animated shape
   */
  IAnimatedShape copy();
}
