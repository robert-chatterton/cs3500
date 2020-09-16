package cs3500.excellence.view.svg;

import cs3500.excellence.model.IAnimatedShape;

interface ShapeDrawSVG {

  void declareShape(IAnimatedShape shape, StringBuilder file);

  void writeAnimate(IAnimatedShape initialShape, IAnimatedShape endShape,
      int startTime, double duration, StringBuilder file);

}
