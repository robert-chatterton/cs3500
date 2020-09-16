package cs3500.excellence.view.svg;

import cs3500.excellence.model.IAnimatedShape;
import cs3500.excellence.shape.Ellipse;
import cs3500.excellence.shape.Rectangle;
import cs3500.excellence.view.AbstractView;
import cs3500.excellence.view.IViewModel;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A view that converts the respective model into an SVG file containing all of the information
 * about a shape and its motion in XML format.
 */
public class SVGAnimationView extends AbstractView {

  private final List<String> declaredShapes;
  private final IViewModel model;
  private final int animationSpeed;
  private FileWriter svgFile;

  /**
   * A constructor for the SVG view that uses a model to convert the animation into a valid SVG
   * file, with the appropriate animation speed.
   *
   * @param model          an {@link IViewModel} that is converted to SVG
   * @param filename       the method of output (System.out or an SVG file)
   * @param animationSpeed uses the speed to determine the duration of motions
   */
  public SVGAnimationView(IViewModel model, String filename, int animationSpeed) {
    this.declaredShapes = new ArrayList<>();
    this.model = model;
    this.animationSpeed = animationSpeed;

    try {
      if (!filename.equals("") || filename.equals("out")) {
        svgFile = new FileWriter(filename);
      }
    } catch (IOException e) {
      System.out.print("an error occurred");
    }

  }


  @Override
  public void makeVisible() {
    if (svgFile != null) {
      try {
        svgFile.close();
      } catch (IOException e) {
        System.out.println("System occurred");
      }
    }
  }

  @Override
  public void writeToOutput() {
    Map<String, Integer> canvasDimensions = model.getCanvasDimensions();
    Map<String, Integer> canvasOffset = model.getCanvasOffset();
    StringBuilder shapeTransform = new StringBuilder();

    shapeTransform.append(String.format("<svg width=\"%s\" height=\"%s\" "
            + "viewBox=\"%s %s %s %s\" version=\"1.1\" "
            + "xmlns=\"http://www.w3.org/2000/svg\" overflow=\"auto\">\n",
        canvasDimensions.get("width"), canvasDimensions.get("height"),
        canvasOffset.get("xOffset"), canvasOffset.get("yOffset"),
        canvasDimensions.get("width"), canvasDimensions.get("height")));

    ShapeDrawSVG draw = new DrawBasicSVGShapes();

    for (Map.Entry<String, List<Integer>> keyFrames : model.getKeyFrames().entrySet()) {
      int currentMillisecond = 1000;
      String shapeName = keyFrames.getKey();
      int i = 1;

      int shapeGenerationKeyFrame = keyFrames.getValue().get(0);

      this.declaredShapes.add(shapeName);
      draw.declareShape(model.getShapeAtTick(shapeName, shapeGenerationKeyFrame),
          shapeTransform);

      shapeTransform.append(String.format("\t<set attributeType=\"xml\" "
              + "begin=\"%sms\" dur=\"1ms\" "
              + "attributeName=\"visibility\" to=\"visible\" "
              + "fill=\"freeze\" />\n",
          ((double) shapeGenerationKeyFrame / (double) animationSpeed) * 1000));

      while (i < keyFrames.getValue().size()) {
        int initialTick = keyFrames.getValue().get(i - 1);
        int endTick = keyFrames.getValue().get(i);

        double duration = ((double) (endTick - initialTick) / animationSpeed) * 1000;

        if (duration > 0) {
          IAnimatedShape initialShape = model.getShapeAtTick(shapeName, initialTick);
          IAnimatedShape finalShape = model.getShapeAtTick(shapeName, endTick);

          draw.writeAnimate(initialShape, finalShape, currentMillisecond, duration, shapeTransform
          );
        }

        currentMillisecond += duration;
        i += 2;
      }

      if (model.getPresentShapes().get(shapeName) instanceof Rectangle) {
        shapeTransform.append("</rect>\n");

      } else if (model.getPresentShapes().get(shapeName) instanceof Ellipse) {
        shapeTransform.append("</ellipse>\n");
      }
    }

    shapeTransform.append("</svg>");

    try {
      if (svgFile == null) {
        System.out.println(shapeTransform.toString());
      } else {
        svgFile.write(shapeTransform.toString());
      }
    } catch (IOException e) {
      System.out.print("an error occurred");
    }
  }

}
