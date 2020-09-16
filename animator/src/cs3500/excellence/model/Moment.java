package cs3500.excellence.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a tick of motion within the animator, which contains all the present shapes that are
 * being being animated.
 */
public class Moment implements IMoment {

  protected final Map<String, IAnimatedShape> presentShapes;

  Moment() {
    this.presentShapes = new LinkedHashMap<>();
  }


  public Map<String, IAnimatedShape> getPresentShapes() {
    return Collections.unmodifiableMap(presentShapes);
  }

  public void addAnimatedShape(IAnimatedShape m) {
    presentShapes.put(m.getShapeName(), m);
  }

  @Override
  public void removeAnimatedShape(String name) {
    this.presentShapes.remove(name);
  }
}
