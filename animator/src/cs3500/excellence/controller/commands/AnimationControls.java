package cs3500.excellence.controller.commands;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.view.IView;

/**
 * The set of all possible controls for an animation editor. The method to execute a control takes
 * in model and a view, and performs the necessary actions with those two arguments.
 */
public interface AnimationControls {

  /**
   * Executes whatever command is associated with an object implementing this interface.
   *
   * @param model the model that the control manipulates
   * @param view  the view that the control manipulates
   */
  void executeControl(ExcellenceAnimator model, IView view);
}
