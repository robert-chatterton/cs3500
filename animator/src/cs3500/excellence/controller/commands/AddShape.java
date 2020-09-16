package cs3500.excellence.controller.commands;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.view.IView;
import java.util.ArrayList;
import java.util.Map;

/**
 * Control to get the ID and type of shape from user and add it to the model.
 */
public class AddShape implements AnimationControls {

  @Override
  public void executeControl(ExcellenceAnimator model, IView view) {
    Map<String, String> shapeInfo = view.getNewShapeInfo();
    try {
      model.addShape(shapeInfo.get("name"), shapeInfo.get("type").toLowerCase());
    } catch (IllegalArgumentException iae) {
      view.showErrorMessage(iae.getMessage());
    }
    view.setListOfNames(new ArrayList<>(model.returnViewModel().getPresentShapes().keySet()));
  }
}
