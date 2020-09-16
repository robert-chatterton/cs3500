package cs3500.excellence.controller.commands;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.view.IView;
import java.util.ArrayList;
import java.util.Map;

/**
 * Control to get the selected keyframe and remove it from the model.
 */
public class RemoveKeyframe implements AnimationControls {

  @Override
  public void executeControl(ExcellenceAnimator model, IView view) {
    String name = view.getSelectedShape();
    Map<String, Integer> keyframeToDelete = view.getSelectedKeyframe();
    if (keyframeToDelete.get(name) == -1) {
      view.showErrorMessage("Please select keyframe before choosing action");
    } else {
      model.removeKeyframe(name, keyframeToDelete.get(name));

      view.showKeyframeControls(model.returnViewModel().getKeyFrames());
      view.setAnimation(model.returnViewModel().getAnimation());
      view.setListOfNames(new ArrayList<>(model.returnViewModel().getPresentShapes().keySet()));
      view.refresh();
    }
  }
}
