package cs3500.excellence.view.svg;

import cs3500.excellence.model.IAnimatedShape;

class DrawRectangleSVG extends AbstractDrawShapeSVG {

  @Override
  public void declareShape(IAnimatedShape shape, StringBuilder file) {
    super.declareShape(shape, file);

    file.append(String.format("<rect id=\"%s\" x=\"%s\" y=\"%s\" width=\"%s\" height=\"%s\" "
            + "fill=\"rgb(%s,%s,%s)\" visibility=\"hidden\" >\n",
        shape.getShapeName(), initialPosition.getX(), initialPosition.getY(),
        initialDimension.get("width"),
        initialDimension.get("height"), initialColor.getRedValue(),
        initialColor.getGreenValue(), initialColor.getBlueValue()));
  }

  @Override
  public void writeAnimate(IAnimatedShape initialShape, IAnimatedShape endShape,
      int startTime, double duration, StringBuilder file) {
    super.writeAnimate(initialShape, endShape, startTime, duration, file);

    String header = String.format("\t<animate attributeType=\"xml\" "
        + "begin=\"%sms\" dur=\"%sms\" ", startTime, duration);

    if (initialPosition.getX() != endPosition.getX()) {
      file.append(String.format(header
              + "attributeName=\"x\" from=\"%s\" to=\"%s\" "
              + "fill=\"freeze\" />\n",
          initialPosition.getX(), endPosition.getX()));
    }

    if (initialPosition.getY() != endPosition.getY()) {
      file.append(String.format(header
              + "attributeName=\"y\" from=\"%s\" to=\"%s\" "
              + "fill=\"freeze\" />\n",
          initialPosition.getY(), endPosition.getY()));
    }

    if (!initialDimension.get("width").equals(endDimension.get("width"))) {
      file.append(String.format(header
          + "attributeName=\"width\" from=\"%s\" to=\"%s\" "
          + "fill=\"freeze\" />\n", initialDimension.get("width"), endDimension.get("width")));
    }

    if (!initialDimension.get("height").equals(endDimension.get("height"))) {
      file.append(String.format(header
          + "attributeName=\"height\" from=\"%s\" to=\"%s\" "
          + "fill=\"freeze\" />\n", initialDimension.get("height"), endDimension.get("height")));
    }
  }
}
