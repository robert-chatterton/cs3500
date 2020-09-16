package cs3500.excellence.model;

import java.util.Map;

/**
 * Represent a single moment (tick) in an animation, where the ticks contains a list of {@link
 * IAnimatedShape}s.
 */
public interface IMoment {

  /**
   * Returns the list of {@link IAnimatedShape} that are being animated at a given tick.
   *
   * @return
   */
  Map<String, IAnimatedShape> getPresentShapes();

  /**
   * Adds a new {@link IAnimatedShape} to the list of present shapes being animated.
   *
   * @param m the new shape to be added
   */
  void addAnimatedShape(IAnimatedShape m);

  /**
   * Removes a {@link IAnimatedShape} from the current tick.
   *
   * @param name the ID of the shape to be removed
   */
  void removeAnimatedShape(String name);
}
