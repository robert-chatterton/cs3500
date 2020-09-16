package cs3500.excellence.view;

import cs3500.excellence.model.IAnimatedShape;
import cs3500.excellence.model.IMoment;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A class that holds most of the unsupported operations for the majority of the extending views.
 * Most of the views have no need for or cannot use the methods in this class.
 */
public abstract class AbstractView extends JFrame implements IView {

  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    throw new UnsupportedOperationException("Not supported by this view");
  }

  @Override
  public void changeAnimationSpeed(int newSpeed) {
    throw new UnsupportedOperationException("Not supported by this view");
  }

  @Override
  public void resume() {
    throw new UnsupportedOperationException("Not supported by this view");
  }

  @Override
  public void toggleLoopback() {
    throw new UnsupportedOperationException("Not supported by this view");
  }

  @Override
  public void restart() {
    throw new UnsupportedOperationException("Not supported by this view");
  }


  @Override
  public Map<String, String> getNewShapeInfo() {

    throw new UnsupportedOperationException("Not supported by this view");
  }

  @Override
  public String getSelectedShape() {
    throw new UnsupportedOperationException("Not supported by this view");
  }

  @Override
  public void showKeyframeControls(
      Map<String, List<Integer>> keyFrames) {
    throw new UnsupportedOperationException("Not supported by this view");
  }

  @Override
  public Map<String, Integer> getSelectedKeyframe() {

    throw new UnsupportedOperationException("Not supported by this view");
  }

  @Override
  public int addKeyframe() {
    throw new UnsupportedOperationException("Not supported by this view");
  }


  @Override
  public void refresh() {
    throw new UnsupportedOperationException("Do not need to refresh this view");
  }

  @Override
  public void setShapes(List<IAnimatedShape> as) {
    throw new UnsupportedOperationException("No component of this view requires to set shapes");
  }

  @Override
  public void setListOfNames(List<String> names) {
    throw new UnsupportedOperationException("Not supported by this view");
  }

  @Override
  public void setAnimation(List<IMoment> frames) {
    throw new UnsupportedOperationException("Not supported by this view");
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
        JOptionPane.ERROR_MESSAGE);

  }

  @Override
  public IAnimatedShape modifyKeyframe(IAnimatedShape a) {
    throw new UnsupportedOperationException("Not supported by this view");
  }
}
