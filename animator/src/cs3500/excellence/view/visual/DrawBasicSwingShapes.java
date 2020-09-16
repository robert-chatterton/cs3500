package cs3500.excellence.view.visual;

import cs3500.excellence.model.IAnimatedShape;
import cs3500.excellence.shape.Ellipse;
import cs3500.excellence.shape.Rectangle;
import java.awt.Graphics2D;

class DrawBasicSwingShapes implements ShapeDraw {


  private final ShapeDraw rectangle;
  private final ShapeDraw ellipse;


  DrawBasicSwingShapes() {
    rectangle = new DrawRectangle();
    ellipse = new DrawEllipse();
  }

  @Override
  public void drawShape(IAnimatedShape shape, Graphics2D graphics, int xOffset, int yOffset) {

    if (shape.getModelShape() instanceof Rectangle) {
      rectangle.drawShape(shape, graphics, xOffset, yOffset);
    } else if (shape.getModelShape() instanceof Ellipse) {
      ellipse.drawShape(shape, graphics, xOffset, yOffset);
    }
  }
}
