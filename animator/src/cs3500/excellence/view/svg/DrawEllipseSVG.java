package cs3500.excellence.view.svg;

import cs3500.excellence.model.IAnimatedShape;

class DrawEllipseSVG extends AbstractDrawShapeSVG {

  @Override
  public void declareShape(IAnimatedShape shape, StringBuilder file) {
    super.declareShape(shape, file);

    int centerX = (int) (initialPosition.getX() + (initialDimension.get("width") / 2));
    int centerY = (int) (initialPosition.getY() + (initialDimension.get("height")) / 2);

    file.append(String.format("<ellipse id=\"%s\" cx=\"%s\" cy=\"%s\" rx=\"%s\" ry=\"%s\" "
            + "fill=\"rgb(%s,%s,%s)\" visibility=\"hidden\" >\n",
        shape.getShapeName(), centerX, centerY,
        (initialDimension.get("width") / 2),
        (initialDimension.get("height") / 2), initialColor.getRedValue(),
        initialColor.getGreenValue(), initialColor.getBlueValue()));

  }

  @Override
  public void writeAnimate(IAnimatedShape initialShape, IAnimatedShape endShape,
      int startTime, double duration, StringBuilder file) {
    super.writeAnimate(initialShape, endShape, startTime, duration, file);

    double initialCenterX = (initialPosition.getX() + (initialDimension.get("width")) / 2);
    double initialCenterY = (initialPosition.getY() + (initialDimension.get("height")) / 2);

    double finalCenterX = (endPosition.getX() + (endDimension.get("width")) / 2);
    double finalCenterY = (endPosition.getY() + (endDimension.get("height")) / 2);

    String header = String.format("\t<animate attributeType=\"xml\" "
        + "begin=\"%sms\" dur=\"%sms\" ", startTime, duration);

    if (initialCenterX != finalCenterX) {
      file.append(String.format(header
          + "attributeName=\"cx\" from=\"%s\" to=\"%s\" "
          + "fill=\"freeze\" />\n", initialCenterX, finalCenterX));
    }

    if (initialCenterY != finalCenterY) {
      file.append(String.format(header
          + "attributeName=\"cy\" from=\"%s\" to=\"%s\" "
          + "fill=\"freeze\" />\n", initialCenterY, finalCenterY));
    }

    if (!initialDimension.get("width").equals(endDimension.get("width"))) {
      file.append(String.format(header
              + "attributeName=\"rx\" from=\"%s\" to=\"%s\" "
              + "fill=\"freeze\" />\n", (initialDimension.get("width") / 2),
          (endDimension.get("width") / 2)));
    }

    if (!initialDimension.get("height").equals(endDimension.get("height"))) {
      file.append(String.format(header
              + "attributeName=\"ry\" from=\"%s\" to=\"%s\" "
              + "fill=\"freeze\" />\n", (initialDimension.get("height") / 2),
          (endDimension.get("height") / 2)));
    }
  }
}
