package cs3500.excellence.view.svg;

import cs3500.excellence.model.IAnimatedShape;
import cs3500.excellence.shape.Ellipse;
import cs3500.excellence.shape.Rectangle;

class DrawBasicSVGShapes implements ShapeDrawSVG {

  private final ShapeDrawSVG rectangle;
  private final ShapeDrawSVG ellipse;

  public DrawBasicSVGShapes() {
    rectangle = new DrawRectangleSVG();
    ellipse = new DrawEllipseSVG();
  }

  @Override
  public void declareShape(IAnimatedShape shape, StringBuilder file) {
    if (shape.getModelShape() instanceof Rectangle) {
      rectangle.declareShape(shape, file);
    } else if (shape.getModelShape() instanceof Ellipse) {
      ellipse.declareShape(shape, file);
    }
  }

  @Override
  public void writeAnimate(IAnimatedShape initialShape, IAnimatedShape endShape,
      int startTime, double duration, StringBuilder file) {
    if (initialShape.getModelShape() instanceof Rectangle) {
      rectangle.writeAnimate(initialShape, endShape, startTime, duration, file);
    } else if (initialShape.getModelShape() instanceof Ellipse) {
      ellipse.writeAnimate(initialShape, endShape, startTime, duration, file);
    }
  }
}
