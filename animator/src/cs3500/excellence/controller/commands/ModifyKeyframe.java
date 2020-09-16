package cs3500.excellence.controller.commands;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.model.IAnimatedShape;
import cs3500.excellence.view.IView;
import java.util.List;
import java.util.Map;

/**
 * Control to get the new information for the selected keyframe.
 */
public class ModifyKeyframe implements AnimationControls {

  @Override
  public void executeControl(ExcellenceAnimator model, IView view) {
    String nameModify = view.getSelectedShape();
    Map<String, Integer> keyframeToModify = view.getSelectedKeyframe();

    if (keyframeToModify.get(nameModify) == -1) {
      view.showErrorMessage("Please select keyframe before choosing action");
    } else {
      List<Integer> keyframesForShape =
          model.returnViewModel().getKeyFrames().get(nameModify);

      Integer tickAtKeyframe =
          keyframesForShape.get((keyframeToModify.get(nameModify) * 2) + 1);

      IAnimatedShape shapeToModify =
          model.returnViewModel().getShapeAtTick(nameModify, tickAtKeyframe);

      IAnimatedShape newShape = view.modifyKeyframe(shapeToModify);

      model.modifyKeyframe(nameModify, (keyframeToModify.get(nameModify) * 2) + 1);
    }
  }
}
