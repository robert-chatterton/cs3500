package cs3500.excellence.shape;

/**
 * Represents a color and its values depending on the color model.
 */
public interface IColor {

  int getRedValue();

  int getGreenValue();

  int getBlueValue();

  /**
   * Returns a formatted string with all of the color's RGB values.
   *
   * @return a string with the format <i>redValue  greenValue  blueValue</i>
   */
  String toString();
}
