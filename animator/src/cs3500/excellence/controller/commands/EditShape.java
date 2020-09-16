package cs3500.excellence.controller.commands;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.view.IView;

/**
 * Control to show the new controls to edit a shape's keyframes.
 */
public class EditShape implements AnimationControls {

  @Override
  public void executeControl(ExcellenceAnimator model, IView view) {
    view.showKeyframeControls(model.returnViewModel().getKeyFrames());
  }
}
