package cs3500.excellence.controller;

/**
 * The controller for the animator. The functions for this controller process the given command and
 * performs the appropriate action on the view and the model. It also provides a method to start the
 * animation.
 */
public interface IController {

  /**
   * Starts the animation for the given model, which is shown in whatever view the controller was
   * given.
   */
  void runAnimation();

  /**
   * Processes the given string to control the view and the model.
   *
   * @param command the action to be performed (e.g. play, pause, add shape, etc.)
   */
  void processCommand(String command);
}
