package cs3500.excellence.view.text;

import cs3500.excellence.model.IAnimatedShape;
import cs3500.excellence.model.IMoment;
import cs3500.excellence.view.AbstractView;
import cs3500.excellence.view.IViewModel;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A textual view of the model that contains information about the generated shapes and its motion
 * throughout the animation that is outputted either to a text file or {@code System.out}.
 */
public class TextView extends AbstractView {

  IViewModel model;
  int speed;
  String outputType;
  FileWriter outputFile;

  /**
   * A constructor for textual view of the model, where it uses an {@link IViewModel} to write the
   * animation's information into a text file or print it to the console.
   *
   * @param model      an {@link IViewModel} that is logged as a text file or sent to console
   * @param outputType the method of output (System.out or a text file)
   * @param speed      textual view doesn't use animation speed
   */
  public TextView(IViewModel model, String outputType, int speed) {
    this.model = model;
    this.speed = speed;
    this.outputType = outputType;

    try {
      if (!outputType.equals("") || outputType.equalsIgnoreCase("out")) {
        outputFile = new FileWriter(outputType);
      }
    } catch (IOException e) {
      System.out.print("an error occurred");
    }

  }

  @Override
  public void makeVisible() {
    if (outputFile != null) {
      try {
        outputFile.close();
      } catch (IOException e) {
        System.out.println("System occurred");
      }
    }
  }

  @Override
  public void writeToOutput() {
    Map<String, Integer> canvasDimensions = model.getCanvasDimensions();
    Map<String, Integer> canvasOffset = model.getCanvasOffset();

    StringBuilder animationLog = new StringBuilder();
    Map<String, StringBuilder> shapeMoments = new LinkedHashMap<>();
    List<IMoment> moments = model.getAnimation();

    animationLog.append(String.format("canvas %s %s %s %s\n",
        canvasOffset.get("xOffset"), canvasOffset.get("yOffset"),
        canvasDimensions.get("width"), canvasDimensions.get("height")));

    for (Map.Entry<String, List<Integer>> animationKeyframes
        : model.getKeyFrames().entrySet()) {
      String shapeName = animationKeyframes.getKey();
      List<Integer> shapeKeyframes = animationKeyframes.getValue();

      for (int i = 1; i < shapeKeyframes.size(); i += 2) {
        int startTick = shapeKeyframes.get(i - 1);
        int endTick = shapeKeyframes.get(i);

        IMoment startMoment = moments.get(startTick);
        IMoment endMoment = moments.get(endTick);

        IAnimatedShape startShapeValue = startMoment.getPresentShapes().get(shapeName);
        IAnimatedShape endShapeValue = endMoment.getPresentShapes().get(shapeName);

        StringBuilder shapeLog;
        if (!shapeMoments.containsKey(shapeName)) {
          shapeLog = new StringBuilder();
          shapeMoments.put(shapeName, shapeLog);
        }

        shapeLog = shapeMoments.get(shapeName);
        shapeLog.append("motion  ").append(startShapeValue.getShapeName()).append("  ")
            .append(startTick)
            .append("  ").append(startShapeValue.toString()).append("\t|\t");

        shapeLog.append(endTick).append("  ").append(endShapeValue.toString()).append("\n");
      }
    }

    for (Map.Entry<String, StringBuilder> shapeMoment
        : shapeMoments.entrySet()) {
      animationLog.append("shape " + shapeMoment.getKey() + "\n");
      animationLog.append(shapeMoment.getValue().toString());
    }

    try {
      if (outputFile == null) {
        System.out.println(animationLog.toString());
      } else {
        outputFile.write(animationLog.toString());
      }
    } catch (IOException e) {
      System.out.print("an error occurred");
    }
  }

}
