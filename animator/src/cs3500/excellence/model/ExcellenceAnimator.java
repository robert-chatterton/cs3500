package cs3500.excellence.model;

import cs3500.excellence.shape.IColor;
import cs3500.excellence.shape.IPosition;
import cs3500.excellence.shape.ModelPosition;
import cs3500.excellence.shape.ModelShape;
import cs3500.excellence.view.IViewModel;
import java.util.Map;

/**
 * An interface for an animation application that performs basic animation, such as moving,
 * resizing, recoloring, etc shapes. The model allows shapes to be generated at a specified tick,
 * given a valid name, shape and position. Transformation can then be applied to the generated
 * shape.
 */
public interface ExcellenceAnimator {

  /**
   * Places a named shape unto the canvas at a given {@link ModelPosition}.
   *
   * @param name          the {@link String} id given to the newly created shape
   * @param newModelShape an {@link ModelShape} object that will be added
   * @param modelPosition the {@link ModelPosition} that the {@code newShape} will be placed at
   * @param tick          the tick at which the users wishes to places the shape
   * @throws IllegalArgumentException if the shape's name is already present, the tick is outside
   *                                  the range of present ticks (1 - # of ticks) or if user tries
   *                                  to generate shape at a tick greater than 1 when the animation
   *                                  is empty
   */
  void generateShape(String name, ModelShape newModelShape, IPosition modelPosition, int tick);

  /**
   * Moves a {@link ModelShape} to the newly given {@link ModelPosition}.
   *
   * @param name             the id what will be used to get selected {@link ModelShape}
   * @param newModelPosition the new {@link ModelPosition} that the selected shape will be moved to
   * @param initialTick      the beginning tick where the shape will start moving
   * @param endTick          the tick at which the shape will finish its movement
   * @throws IllegalArgumentException if the difference between the initial tick and the end tick is
   *                                  zero, if the initial tick is less than 0, the end tick is less
   *                                  than the initial tick, or if the tick indicated by {@code
   *                                  initialTick} does not contain the referenced {@code Shape}
   */
  void moveShape(String name, IPosition newModelPosition, int initialTick, int endTick);

  /**
   * Changes the {@link IColor} of the selected {@link ModelShape}.
   *
   * @param name        the id what will be used to get selected {@link ModelShape}
   * @param newIColor   the new {@link IColor} that will be applied to the selected shape
   * @param initialTick the tick where the shape will begin transitioning to the new color
   * @param endTick     the tick where the shape would finally reach the given color
   */
  void changeShapeColor(String name, IColor newIColor, int initialTick, int endTick);

  /**
   * Resizes the selected {@link ModelShape}.
   *
   * @param name        the id what will be used to get selected {@link ModelShape}
   * @param width       the new width of the selected shape
   * @param height      the new height of the selected shape
   * @param initialTick the beginning tick where the shape will begin growing or shrinking
   * @param endTick     the tick at which the shape will have finished resizing
   */
  void resizeShape(String name, int width, int height, int initialTick, int endTick);

  /**
   * Modifies an existing keyframe for a shape with the given name. The keyframe is selected by the
   * position in the list of existing keyframes. The tick that is modified is the endpoint of that
   * keyframe (e.g. keyframe 1 - 10 will modify the shape values at tick 10)
   *
   * @param name       the name of the selected shape
   * @param keyframeNo the index of the keyframe for the shape
   */
  void modifyKeyframe(String name, int keyframeNo);

  /**
   * Deletes a shape that is present in the animation, along with any information in the animation
   * that might be related, such as key frames.
   *
   * @param name the name of the shape to be removed
   */
  void deleteShape(String name);

  /**
   * Makes the given shape the instruction to remain put, not changing anything from it.
   *
   * @param name        the name to reference the appropraite {@link ModelShape}
   * @param initialTick the tick where the shape should start to remain put
   * @param endTick     the tick where the shape can finally perform motion
   */
  void doNothing(String name, int initialTick, int endTick);

  /**
   * Returns the latest {@link ModelPosition} of the selected {@link ModelShape}.
   *
   * @param name the id what will be used to get selected {@link ModelShape}
   * @return the {@link ModelPosition} associated with the selected shape
   * @throws IllegalArgumentException if the shape is not present at the given tick or if the name
   *                                  is null
   */
  IPosition getShapePosition(String name, int tick);

  /**
   * Returns the {@link IColor} of the selected {@link ModelShape}.
   *
   * @param name the id what will be used to get selected {@link ModelShape}
   * @return the {@link IColor} associated with the selected shape
   * @throws IllegalArgumentException if the shape is not present at the given tick or if the name
   *                                  is null
   */
  IColor getShapeColor(String name, int tick);

  /**
   * Returns the dimensions (width, height) of the selected {@link ModelShape}.
   *
   * @param name the id what will be used to get selected {@link ModelShape}
   * @return a {@link Map}, which holds the width/height as a key and it associated value
   * @throws IllegalArgumentException if the shape is not present at the given tick or if the name
   *                                  is null
   */
  Map<String, Double> getShapeSize(String name, int tick);


  /**
   * Returns a ViewModel of this {@link ExcellenceAnimator}, which contain purely methods pertaining
   * to the access of necessary information of the model, such as the tick's or {@link
   * IAnimatedShape}'s information.
   *
   * @return a {@link IViewModel} to provide to a view for access to the model's information
   */
  IViewModel returnViewModel();


  /**
   * Inserts a new keyframe for the given shape, whether it be the very first key frame or the tick
   * already exists within the animation.
   *
   * @param name             the name of the shape whose key frame's list is going to be added to
   * @param keyframeLocation the tick number that is going to be marked as a key frame
   */
  void addKeyframe(String name, int keyframeLocation);

  /**
   * Removes a key frame from a shape, therefore removing motion regarding that shape in between the
   * previous and next key frames from the removed key frame. When a key frame that is in between
   * two other, the "tweened" values will be modified to show motion from the previous key frame to
   * the next available key frame.
   *
   * @param name       the name of the shape
   * @param keyframeNo the tick number whose key frame status is removed
   */
  void removeKeyframe(String name, int keyframeNo);


  /**
   * Adds a new shape to the list of shapes present in the animation, but does nothing in regards to
   * animation.
   *
   * @param name the ID that is given to the shape to be added
   * @param type the type of shape that is added (rectangle or ellipse)
   */
  void addShape(String name, String type);
}
