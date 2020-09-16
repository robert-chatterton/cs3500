package cs3500.excellence.view.visual;

import cs3500.excellence.model.AnimatedShape;
import cs3500.excellence.model.IAnimatedShape;
import cs3500.excellence.shape.ModelShape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * A panel that animates all the {@link AnimatedShape} in a single tick of the given {@link
 * cs3500.excellence.model.ExcellenceAnimator}.
 */
class AnimationCanvas extends JPanel {

  private final int xOffset;
  private final int yOffset;
  private List<IAnimatedShape> shapes;


  protected AnimationCanvas(int xOffset, int yOffset) {
    super();
    shapes = new ArrayList<>();
    this.xOffset = xOffset;
    this.yOffset = yOffset;

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    for (IAnimatedShape as : this.shapes) {
      ModelShape modelShape = as.getModelShape();

      DrawBasicSwingShapes draw = new DrawBasicSwingShapes();

      g2d.setColor(new Color(modelShape.getModelColor().getRedValue(),
          modelShape.getModelColor().getGreenValue(),
          modelShape.getModelColor().getBlueValue()));

      draw.drawShape(as, g2d, xOffset, yOffset);

    }
  }

  public void setShapes(List<IAnimatedShape> as) {
    this.shapes = as;
  }

}
