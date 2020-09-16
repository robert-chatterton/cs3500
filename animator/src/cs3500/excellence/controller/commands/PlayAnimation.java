package cs3500.excellence.controller.commands;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.view.IView;

/**
 * Control to either start, pause or resume the animation.
 */
public class PlayAnimation implements AnimationControls {

  @Override
  public void executeControl(ExcellenceAnimator model, IView view) {
    view.resume();
  }
}
