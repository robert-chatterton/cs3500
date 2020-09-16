package cs3500.excellence.view.visual;

import cs3500.excellence.model.IAnimatedShape;
import java.awt.Graphics2D;

interface ShapeDraw {

  void drawShape(IAnimatedShape shape, Graphics2D graphics, int xOffset, int yOffset);
}
