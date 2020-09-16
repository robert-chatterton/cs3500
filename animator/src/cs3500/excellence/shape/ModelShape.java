package cs3500.excellence.shape;

import java.util.Map;

/**
 * An interface to represent a geometric shape, such as a rectangle or triangle, along with
 * modification methods.
 */
public interface ModelShape {

  /**
   * Changes the {@link ModelShape}'s color.
   *
   * @param newModelColor the new color the shape will have
   */
  void changeColor(IColor newModelColor);

  /**
   * Resizes the {@link ModelShape} to the given width and height.
   *
   * @param newWidth  the {@link ModelShape}'s new width
   * @param newHeight the {@link ModelShape}'s new height
   */
  void resize(double newWidth, double newHeight);

  /**
   * Returns the {@link ModelShape}'s dimensions.
   *
   * @return a {@link Map} with the width/height as keys and their respective values
   */
  Map<String, Double> getSize();

  /**
   * Returns the {@link ModelShape}'s color.
   *
   * @return the shape's color
   */
  IColor getModelColor();


  /**
   * Returns a string representation of the shape, where it displays the shape's width and height
   * first, followed with a string representation of its {@link ModelColor}.
   *
   * @return the {@link ModelShape}'s string representation
   */
  String toString();

  /**
   * Returns the {@link ModelShape}'s area.
   *
   * @return a integer representing the shape's area
   */
  double area();

  /**
   * Creates a new copy of this shape.
   *
   * @return a new {@link ModelShape} with the same information as this one
   */
  ModelShape copy();

  int hashCode();

  boolean equals(Object obj);
}
