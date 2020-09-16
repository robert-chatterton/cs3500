package cs3500.excellence.controller.commands;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.view.IView;

/**
 * Control the toggle that controls whether the animation goes back to the beginning once its
 * finished.
 */
public class ToggleLoop implements AnimationControls {

  @Override
  public void executeControl(ExcellenceAnimator model, IView view) {
    view.toggleLoopback();
  }
}
