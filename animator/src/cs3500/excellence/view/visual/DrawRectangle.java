package cs3500.excellence.view.visual;

import cs3500.excellence.model.IAnimatedShape;
import cs3500.excellence.shape.IPosition;
import java.awt.Graphics2D;
import java.util.Map;

class DrawRectangle implements ShapeDraw {

  @Override
  public void drawShape(IAnimatedShape shape, Graphics2D graphics, int xOffset, int yOffset) {
    Map<String, Double> dimensions = shape.getModelShape().getSize();
    IPosition position = shape.getModelPosition();

    graphics.fillRect((int) position.getX() - xOffset,
        (int) position.getY() - yOffset, dimensions.get("width").intValue(),
        dimensions.get("height").intValue());
  }
}
