package cs3500.excellence.controller.commands;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.view.IView;

/**
 * Control to increase how many ticks to be displayed per second in the view.
 */
public class IncreaseSpeed implements AnimationControls {

  @Override
  public void executeControl(ExcellenceAnimator model, IView view) {
    view.changeAnimationSpeed(10);
  }
}
