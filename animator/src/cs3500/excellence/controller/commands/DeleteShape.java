package cs3500.excellence.controller.commands;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.view.IView;
import java.util.ArrayList;

/**
 * Control to get the selected shape and remove it from the animation model.
 */
public class DeleteShape implements AnimationControls {

  @Override
  public void executeControl(ExcellenceAnimator model, IView view) {
    model.deleteShape(view.getSelectedShape());

    view.setAnimation(model.returnViewModel().getAnimation());
    view.setListOfNames(new ArrayList<>(model.returnViewModel().getPresentShapes().keySet()));
    view.refresh();
  }
}
