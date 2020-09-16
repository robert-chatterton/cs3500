package cs3500.excellence.view.svg;

import cs3500.excellence.model.IAnimatedShape;
import cs3500.excellence.shape.IColor;
import cs3500.excellence.shape.IPosition;
import cs3500.excellence.shape.ModelShape;
import java.util.Map;

class AbstractDrawShapeSVG implements ShapeDrawSVG {

  IPosition initialPosition;
  IColor initialColor;
  Map<String, Double> initialDimension;

  IPosition endPosition;
  IColor endColor;
  Map<String, Double> endDimension;

  @Override
  public void declareShape(IAnimatedShape shape, StringBuilder file) {
    setInitialConditions(shape);

  }

  @Override
  public void writeAnimate(IAnimatedShape initialShape, IAnimatedShape endShape,
      int startTime, double duration, StringBuilder file) {
    setInitialConditions(initialShape);

    endPosition = endShape.getModelPosition();
    endColor = endShape.getModelShape().getModelColor();
    endDimension = endShape.getModelShape().getSize();

    String header = String.format("\t<animate attributeType=\"xml\" "
        + "begin=\"%sms\" dur=\"%sms\" ", startTime, duration);

    if (!initialColor.equals(endColor)) {
      file.append(String.format(header
              + "attributeName=\"fill\" from=\"rgb(%s,%s,%s)\" to=\"rgb(%s,%s,%s)\" "
              + "fill=\"freeze\" />\n",
          initialColor.getRedValue(), initialColor.getGreenValue(), initialColor.getBlueValue(),
          endColor.getRedValue(), endColor.getGreenValue(), endColor.getBlueValue()));
    }
  }

  private void setInitialConditions(IAnimatedShape shape) {
    ModelShape ms = shape.getModelShape();
    initialPosition = shape.getModelPosition();
    initialColor = ms.getModelColor();
    initialDimension = ms.getSize();
  }
}
