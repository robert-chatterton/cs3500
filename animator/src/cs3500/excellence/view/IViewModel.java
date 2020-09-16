package cs3500.excellence.view;

import cs3500.excellence.model.AnimatedShape;
import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.model.IAnimatedShape;
import cs3500.excellence.model.IMoment;
import cs3500.excellence.shape.ModelShape;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A ViewModel for {@link ExcellenceAnimator}, which only contains getter methods as to avoid the
 * View to have access to any modifier methods or direct access to variables within the model.
 */
public interface IViewModel {

  /**
   * Returns a list of all the moments from all the placed shapes.
   *
   * @return an {@link ArrayList} containing all of the moments' information for all the shapes in
   *         the model
   */
  List<IMoment> getAnimation();

  /**
   * Returns a map of the canvas' width and height.
   *
   * @return
   */
  Map<String, Integer> getCanvasDimensions();

  /**
   * Returns a map of the canvas' x-offset and y-offset.
   *
   * @return
   */
  Map<String, Integer> getCanvasOffset();

  /**
   * Returns the maximum-x value reached within the animation.
   *
   * @return
   */
  int getMaximumX();

  /**
   * Returns the maximum-y value reached within the animation.
   *
   * @return
   */
  int getMaximumY();

  /**
   * Returns a map containing every ID that exists in the animation, along with the important
   * keyframes for that shape (such as when the shape was created, when it started/stopped moving,
   * etc).
   *
   * @return
   */
  Map<String, List<Integer>> getKeyFrames();

  /**
   * Returns a map containing all of the present shape's ID, along with the shape associated with
   * the ID.
   *
   * @return
   */
  Map<String, ModelShape> getPresentShapes();

  /**
   * Returns the {@link AnimatedShape} at the specified tick with the given ID.
   *
   * @param name the ID of the shape
   * @param tick desired tick to look at
   * @return
   */
  IAnimatedShape getShapeAtTick(String name, int tick);

}
