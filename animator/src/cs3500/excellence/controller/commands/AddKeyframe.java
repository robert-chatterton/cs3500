package cs3500.excellence.controller.commands;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.view.IView;
import java.util.ArrayList;

/**
 * Control to get the location of key frame from user and add it to the model.
 */
public class AddKeyframe implements AnimationControls {

  @Override
  public void executeControl(ExcellenceAnimator model, IView view) {
    try {
      String shapeName = view.getSelectedShape();
      int tickLocation = view.addKeyframe();
      if (model.returnViewModel().getKeyFrames().get(shapeName) == null ||
          !model.returnViewModel().getKeyFrames().get(shapeName).contains(tickLocation)) {
        model.addKeyframe(shapeName, tickLocation);
        view.showKeyframeControls(model.returnViewModel().getKeyFrames());

        view.setAnimation(model.returnViewModel().getAnimation());
        view.setListOfNames(new ArrayList<>(model.returnViewModel().getPresentShapes().keySet()));
        view.refresh();
      } else {
        view.showErrorMessage("Cannot add keyframe at existing location.");
      }
    } catch (IllegalArgumentException outOfBounds) {
      view.showErrorMessage(outOfBounds.getMessage());
    }
  }
}
