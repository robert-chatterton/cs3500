package cs3500.excellence.controller.commands;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.view.IView;

/**
 * Control to decrease how many ticks to be displayed per second in the view.
 */
public class DecreaseSpeed implements AnimationControls {

  @Override
  public void executeControl(ExcellenceAnimator model, IView view) {
    view.changeAnimationSpeed(-10);
  }
}
