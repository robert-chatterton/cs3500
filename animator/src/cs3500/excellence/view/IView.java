package cs3500.excellence.view;

import cs3500.excellence.model.AnimatedShape;
import cs3500.excellence.model.IAnimatedShape;
import cs3500.excellence.model.IMoment;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**
 * Represent a view for the animation model that controls the visibility and how to display the
 * model.
 */
public interface IView {

  /**
   * Make the view visible. This is usually called after the view is constructed
   */
  void makeVisible();

  /**
   * Signal the view to draw itself.
   */
  void refresh();

  /**
   * Sets the shapes for a specific tick, primarily used for views that require all of the shapes
   * for a single tick.
   *
   * @param as the given list of {@link AnimatedShape} that is set to the view's needs
   */
  void setShapes(List<IAnimatedShape> as);


  void setListOfNames(List<String> names);

  /**
   * Provides the view a list of copies of the ticks from an animation model to allow the view to
   * read information from them.
   *
   * @param frames the ticks from an animation model
   */
  void setAnimation(List<IMoment> frames);

  /**
   * Writes the appropriate textual representation to the view, such as writing to a text file or
   * printing to the console.
   */
  void writeToOutput();

  /**
   * Provide the view with an action listener for the button that should cause the program to
   * process a command. This is so that when the button is pressed, control goes to the action
   * listener
   *
   * @param actionEvent the object that will be listening to whatever this actionEvent is added to
   */
  void setCommandButtonListener(ActionListener actionEvent);

  /**
   * Increases or decreases the animation speed.
   *
   * @param newSpeed the new number of ticks to be displayer per second
   */
  void changeAnimationSpeed(int newSpeed);

  /**
   * Pauses/resumes the animation at the ticks the method was called.
   */
  void resume();

  /**
   * Toggles the ability for the view to loop back to the beginning of the animation.
   */
  void toggleLoopback();

  /**
   * Manually brings the animation to the start, regardless of the loopback ability.
   */
  void restart();

  /**
   * Shows any error message that the view or model may provide, via any means necessary. The
   * default is an {@link javax.swing.JOptionPane} that displays the error message.
   *
   * @param error the message to display
   */
  void showErrorMessage(String error);

  /**
   * Returns the information about a shape that is added to the animation, which is gotten by any
   * methods the concrete class implements.
   *
   * @return a map containing the ID of the new shape, along with the type of shape
   */
  Map<String, String> getNewShapeInfo();

  /**
   * Selects and returns the shape that the user wishes to perform any modifications to, such as
   * completely removing or edit its keyframes.
   *
   * @return the ID of the shape that was selected
   */
  String getSelectedShape();

  /**
   * Performs the necessary actions to give the user a way of editing a shape's keyframes.
   *
   * @param keyFrames the keyframes for the selected shape.
   */
  void showKeyframeControls(Map<String, List<Integer>> keyFrames);

  /**
   * Selects and return the information about the key frame that the user wishes to modify or
   * delete.
   *
   * @return a map with the name of the shape and the location of the tick of the selected keyframe
   */
  Map<String, Integer> getSelectedKeyframe();

  /**
   * Returns the location of the key frame the user wishes to add to a selected shape.
   *
   * @return the tick location to mark as a key frame
   */
  int addKeyframe();

  /**
   * Returns an animated shape that contains all of the new parameters for the shape, which were
   * gotten from some sort of input.
   *
   * @param a the existing shape at the selected keyframe, which is used to show the current values
   *          for the shape
   * @return a modified shape with the user-inputted parameters
   */
  IAnimatedShape modifyKeyframe(IAnimatedShape a);
}
