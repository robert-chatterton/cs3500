package cs3500.excellence.controller.commands;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.view.IView;

/**
 * Control to have the view restart the tick counter and bring the animation to the beginning.
 */
public class RestartAnimation implements AnimationControls {

  @Override
  public void executeControl(ExcellenceAnimator model, IView view) {
    view.restart();
  }
}
