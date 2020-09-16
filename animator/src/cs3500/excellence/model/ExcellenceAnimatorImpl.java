package cs3500.excellence.model;

import cs3500.excellence.shape.Ellipse;
import cs3500.excellence.shape.IColor;
import cs3500.excellence.shape.IPosition;
import cs3500.excellence.shape.ModelColor;
import cs3500.excellence.shape.ModelPosition;
import cs3500.excellence.shape.ModelShape;
import cs3500.excellence.shape.Rectangle;
import cs3500.excellence.view.IViewModel;
import cs3500.main.util.AnimationBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * An implementation of {@link ExcellenceAnimator} that supports the placing, moving, resizing, and
 * recoloring of multiple {@link ModelShape}s. The model is presented by a list of moments (ticks),
 * each with present {@link IAnimatedShape}.
 */
public class ExcellenceAnimatorImpl implements ExcellenceAnimator, IViewModel {

  private final Map<String, ModelShape> presentShapes;
  private final Map<String, List<Integer>> animationMoments;
  private final List<IMoment> ticks;
  private int canvasWidth;
  private int canvasHeight;
  private int canvasX;
  private int canvasY;
  private double maximumX;
  private double maximumY;
  private IAnimatedShape selectedShape;


  /**
   * A constructor that has {@code animationSpeed} as its constructor.
   */
  public ExcellenceAnimatorImpl() {
    presentShapes = new LinkedHashMap<>();
    animationMoments = new LinkedHashMap<>();
    ticks = new ArrayList<>();
    ticks.add(new Moment());
  }

  private void isShapePresentAtTick(String name, int tick) {
    if (ticks.get(tick).getPresentShapes().get(name) == null) {
      throw new IllegalArgumentException("Shape not present at tick");
    }
  }

  private void checkValidTicks(int initialTick, int endTick) {
    if (initialTick > endTick) {
      throw new IllegalArgumentException(initialTick + " is bigger than " + endTick);
    } else if (initialTick <= 0) {
      throw new IllegalArgumentException(initialTick + " cannot be less than zero");
    } else if (initialTick >= ticks.size()) {
      throw new IllegalArgumentException(initialTick + " cannot be larger than the size of the"
          + "animation: " + ticks.size());
    }
  }

  // makes sure the shape isn't instructed to remain put for the given duration
  private void checkForNothingActivity(String name, int initialTick) {

    for (int i = initialTick; i < ticks.size(); i++) {
      if (ticks.get(i).getPresentShapes().get(name) != null) {
        if (ticks.get(i).getPresentShapes().get(name).getMotionsExperienced()
            .contains(MotionType.STILL)) {
          throw new IllegalArgumentException("Cannot perform motion when shape has been "
              + "instructed to stay put");
        }
      }
    }
  }

  // makes sure that the given motion type isn't being made in the duration
  private void checkValidMotion(String name, int initialTick, int endTick) {
    checkValidTicks(initialTick, endTick);
  }

  private void checkMotionTypeActivity(String name, int initialTick, int endTick,
      MotionType motion) {
    for (int i = initialTick + 1; i < endTick; i++) {
      if (i < ticks.size()) {
        if (ticks.get(i).getPresentShapes().get(name) != null
            && ticks.get(i).getPresentShapes().get(name)
            .getMotionsExperienced().contains(motion)) {
          throw new IllegalArgumentException("Motions cannot overlap");
        }
      }
    }
  }


  private ModelShape getSelectedShape(IAnimatedShape backup, int tick) {
    if (tick >= ticks.size()
        || ticks.get(tick).getPresentShapes().get(backup.getShapeName()) == null) {
      selectedShape = ticks.get(tick - 1).getPresentShapes()
          .get(backup.getShapeName());

      return selectedShape.getModelShape().copy();
    } else {
      return null;
    }
  }

  private double intermediateValue(double initialValue, double endValue,
      double currentTick, double initialTick, double endTick) {
    double initialValueFactor = (endTick - currentTick) / (endTick - initialTick);
    double endValueFactor = (currentTick - initialTick) / (endTick - initialTick);

    return (initialValue * initialValueFactor) + (endValue * endValueFactor);
  }

  private void addNewTick(IAnimatedShape newAnimatedShape, int tick) {
    if (tick >= ticks.size()) {
      // If there are no more ticks, create a new moment with thew new AnimatedShape
      // and add to the list of ticks
      IMoment selectedMoment = new Moment();
      selectedMoment.addAnimatedShape(newAnimatedShape);
      ticks.add(selectedMoment);
    } else {
      // We know there is a tick, with just without this shape, so we just add a new AnimatedShape
      ticks.get(tick).addAnimatedShape(newAnimatedShape);
    }
  }

  @Override
  public void addShape(String name, String type) {
    if (this.presentShapes.containsKey(name)) {
      throw new IllegalArgumentException("Shape already present");
    } else {
      ModelShape newModelShape;
      switch (type) {
        case "rectangle":
          newModelShape = new Rectangle(5, 5, new ModelColor(0, 0, 0));
          break;
        case "ellipse":
          newModelShape = new Ellipse(5, 5, new ModelColor(0, 0, 0));
          break;
        default:
          throw new IllegalArgumentException("unknown shape");
      }

      presentShapes.put(name, newModelShape);
    }
  }

  @Override
  public void generateShape(String name, ModelShape newModelShape, IPosition modelPosition,
      int tick) {
    if (tick <= 0 || tick > ticks.size()) {
      throw new IllegalArgumentException("Tick does not exist");
    } else if (presentShapes.isEmpty() && tick != 1) {
      throw new IllegalArgumentException("No shape has been made yet. Please place a shape at"
          + "beginning of animation");
    } else if (tick < ticks.size() && ticks.get(tick).getPresentShapes().containsKey(name)) {
      throw new IllegalArgumentException("Shape already present in animation");
    }

    if (tick < ticks.size()) {
      ticks.get(tick).addAnimatedShape(
          new AnimatedShape(name, newModelShape, modelPosition));
    } else {
      IMoment selectedMoment = new Moment();
      selectedMoment.addAnimatedShape(
          new AnimatedShape(name, newModelShape, modelPosition));
      ticks.add(selectedMoment);
    }

    if (animationMoments.get(name) == null) {
      ArrayList<Integer> generateMarkers = new ArrayList<>();
      animationMoments.put(name, generateMarkers);
      presentShapes.put(name, newModelShape);
    }
  }


  @Override
  public void moveShape(String name, IPosition newModelPosition, int initialTick, int endTick) {
    checkValidMotion(name, initialTick, endTick);
    checkMotionTypeActivity(name, initialTick, endTick, MotionType.MOVE);

    maximumX = Math.max(maximumX, newModelPosition.getX());
    maximumY = Math.max(maximumY, newModelPosition.getY());

    IPosition initialModelPosition = ticks.get(initialTick).getPresentShapes()
        .get(name).getModelPosition();

    for (int i = initialTick + 1; i <= endTick; i++) {
      selectedShape = (ticks.get(i - 1).getPresentShapes()).get(name);

      double newXPosition = intermediateValue(initialModelPosition.getX(),
          newModelPosition.getX(), i, initialTick, endTick);
      double newYPosition = intermediateValue(initialModelPosition.getY(),
          newModelPosition.getY(), i, initialTick, endTick);

      ModelPosition endModelPosition =
          new ModelPosition(newXPosition, newYPosition);

      ModelShape currentModelShape = getSelectedShape(selectedShape, i);

      if (currentModelShape == null) {
        selectedShape = (ticks.get(i).getPresentShapes()).get(name);
        selectedShape.setModelPosition(endModelPosition);
        selectedShape.addMotionType(MotionType.MOVE);
      } else {
        AnimatedShape newAnimatedShape =
            new AnimatedShape(name, currentModelShape, endModelPosition, MotionType.MOVE);

        addNewTick(newAnimatedShape, i);

      }
    }


  }

  @Override
  public void changeShapeColor(String name, IColor newIColor, int initialTick,
      int endTick) {
    checkValidMotion(name, initialTick, endTick);

    checkMotionTypeActivity(name, initialTick, endTick, MotionType.RECOLOR);

    selectedShape = ticks.get(initialTick).getPresentShapes().get(name);
    selectedShape.addMotionType(MotionType.RECOLOR);
    IColor initialIColor = selectedShape.getModelShape().getModelColor();

    for (int i = initialTick + 1; i <= endTick; i++) {

      selectedShape = (ticks.get(i - 1).getPresentShapes()).get(name);
      IPosition currentModelPosition = selectedShape.getModelPosition();
      ModelShape createdModelShape = getSelectedShape(selectedShape, i);

      double newRedValue = intermediateValue(initialIColor.getRedValue(),
          newIColor.getRedValue(), i, initialTick, endTick);
      double newGreenValue = intermediateValue(initialIColor.getGreenValue(),
          newIColor.getGreenValue(), i, initialTick, endTick);
      double newBlueValue = intermediateValue(initialIColor.getBlueValue(),
          newIColor.getBlueValue(), i, initialTick, endTick);

      IColor endIColor = new ModelColor(newRedValue, newGreenValue, newBlueValue);

      if (createdModelShape == null) {
        // Will try to update the already existing animated shape with the new position
        selectedShape = (ticks.get(i).getPresentShapes()).get(name);
        selectedShape.changeColor(endIColor);

        selectedShape.addMotionType(MotionType.RECOLOR);
      } else {
        createdModelShape.changeColor(endIColor);
        IAnimatedShape newAnimatedShape =
            new AnimatedShape(name, createdModelShape, currentModelPosition, MotionType.RECOLOR);

        addNewTick(newAnimatedShape, i);
      }
    }
  }

  @Override
  public void resizeShape(String name, int width, int height, int initialTick, int endTick) {
    checkValidMotion(name, initialTick, endTick);

    checkMotionTypeActivity(name, initialTick, endTick, MotionType.RESIZE);

    selectedShape = ticks.get(initialTick).getPresentShapes().get(name);
    selectedShape.addMotionType(MotionType.RESIZE);

    double initialWidth = selectedShape.getModelShape().getSize().get("width");
    double initialHeight = selectedShape.getModelShape().getSize().get("height");

    for (int i = initialTick + 1; i <= endTick; i++) {
      selectedShape = ticks.get(i - 1).getPresentShapes().get(name);
      ModelShape createdModelShape = getSelectedShape(selectedShape, i);

      double newWidth = intermediateValue(initialWidth, width, i, initialTick, endTick);
      double newHeight = intermediateValue(initialHeight, height, i, initialTick, endTick);

      if (createdModelShape == null) {
        // Will try to resize the already existing Shape
        selectedShape = ticks.get(i).getPresentShapes().get(name);
        selectedShape.resize(newWidth, newHeight);

        selectedShape.addMotionType(MotionType.RESIZE);
      } else {
        IPosition currentModelPosition = selectedShape.getModelPosition();
        createdModelShape.resize(newWidth, newHeight);
        IAnimatedShape newAnimatedShape =
            new AnimatedShape(name, createdModelShape, currentModelPosition, MotionType.RESIZE);

        addNewTick(newAnimatedShape, i);
      }
    }
  }

  @Override
  public void addKeyframe(String name, int keyframeLocation) {
    if (animationMoments.get(name) == null) {
      List<Integer> newKeyframes = new ArrayList<>();
      newKeyframes.add(1);
      newKeyframes.add(keyframeLocation);
      animationMoments.put(name, newKeyframes);
      generateShape(name, presentShapes.get(name), new ModelPosition(-20, -20), 1);

    } else {
      int originalSize = animationMoments.get(name).size();
      for (int i = 0; i < originalSize; i++) {
        if (keyframeLocation < animationMoments.get(name).get(i)) {
          animationMoments.get(name).add(i, keyframeLocation);
          animationMoments.get(name).add(i + 1, keyframeLocation);
        }
      }

      if (animationMoments.get(name).size() == originalSize) {
        int previousLast = animationMoments.get(name).get(originalSize - 1);
        fillInAddedKeyframeMotion(name, previousLast, keyframeLocation);
        animationMoments.get(name).add(originalSize,
            animationMoments.get(name).get(originalSize - 1));
        animationMoments.get(name).add(originalSize + 1, keyframeLocation);
      }
    }
  }

  private void fillInAddedKeyframeMotion(String name, int startPoint, int endPoint) {
    // goes through the ticks in between and copies the shape at initial tick all the way through
    // the end tick
    for (int i = startPoint + 1; i <= endPoint; i++) {
      IAnimatedShape selectedShape = getShapeAtTick(name, i - 1).copy();
      try {
        ticks.get(i).addAnimatedShape(selectedShape);
      } catch (IndexOutOfBoundsException e) {
        IMoment newMoment = new Moment();
        newMoment.addAnimatedShape(selectedShape);
        ticks.add(newMoment);
      }
    }
  }

  private void fillInRemovedMotion(String name, int startPoint, int endPoint) {
    this.moveShape(name, getShapePosition(name, endPoint + 1),
        startPoint - 1, endPoint + 1);
    this.changeShapeColor(name, getShapeColor(name, endPoint + 1),
        startPoint - 1, endPoint + 1);

    this.resizeShape(name, getShapeSize(name, endPoint + 1).get("width").intValue(),
        getShapeSize(name, endPoint + 1).get("height").intValue(), startPoint - 1,
        endPoint + 1);
  }

  @Override
  public void removeKeyframe(String name, int keyframeNo) {
    if (animationMoments.containsKey(name)) {
      int startPoint = animationMoments.get(name).get(keyframeNo * 2);
      int endPoint = animationMoments.get(name).get((keyframeNo * 2) + 1);

      for (int i = startPoint + 1; i <= endPoint; i++) {
        ticks.get(i).removeAnimatedShape(name);
      }

      animationMoments.get(name).remove(keyframeNo * 2);
      animationMoments.get(name).remove(keyframeNo * 2);

      if (startPoint > 1 && endPoint < ticks.size() - 1) {
        fillInRemovedMotion(name, startPoint, endPoint);
      }

      if (animationMoments.get(name).isEmpty()) {
        animationMoments.remove(name);
      }
    }
  }

  @Override
  public void modifyKeyframe(String name, int keyframeNo) {
    if (animationMoments.get(name).get(keyframeNo)
        != animationMoments.get(name).get(keyframeNo - 1)) {
      int startPoint = animationMoments.get(name).get(keyframeNo - 1) + 1;
      int middlePoint = animationMoments.get(name).get(keyframeNo);

      for (int i = startPoint; i < middlePoint; i++) {
        ticks.get(i).removeAnimatedShape(name);
      }

      fillInRemovedMotion(name, startPoint, middlePoint - 1);

      if (animationMoments.get(name).size() > (keyframeNo + 2)) {
        int endPoint = animationMoments.get(name).get(keyframeNo + 2) - 1;

        for (int i = middlePoint + 1; i < endPoint; i++) {
          ticks.get(i).removeAnimatedShape(name);
        }

        fillInRemovedMotion(name, middlePoint + 1, endPoint - 1);
      }
    }
  }

  @Override
  public void deleteShape(String name) {
    for (int i = 0; i < ticks.size(); i++) {
      ticks.get(i).removeAnimatedShape(name);
    }

    presentShapes.remove(name);
    animationMoments.remove(name);
  }

  @Override
  public void doNothing(String name, int initialTick, int endTick) {
    checkValidTicks(initialTick, endTick);
    isShapePresentAtTick(name, initialTick);

    IAnimatedShape selectedShape;

    // goes through all the ticks and makes sure no motion is occurring in between ticks
    for (int i = initialTick + 1; i < ticks.size(); i++) {
      selectedShape = ticks.get(i).getPresentShapes().get(name);
      if (selectedShape != null) {
        if (!selectedShape.getMotionsExperienced().isEmpty()) {
          throw new IllegalArgumentException("Can't override motion when trying to do nothing");
        }
      }
    }

    // updates the start tick to be still
    selectedShape = (ticks.get(initialTick).getPresentShapes()).get(name);
    selectedShape.addMotionType(MotionType.STILL);

    // goes through the ticks in between and copies the shape at initial tick all the way through
    // the end tick
    for (int i = initialTick; i <= endTick; i++) {
      try {
        ticks.get(i).addAnimatedShape(selectedShape);
      } catch (IndexOutOfBoundsException e) {
        IMoment newMoment = new Moment();
        newMoment.addAnimatedShape(selectedShape);
        ticks.add(newMoment);
      }
    }

  }


  @Override
  public List<IMoment> getAnimation() {
    return Collections.unmodifiableList(ticks);
  }

  @Override
  public Map<String, Integer> getCanvasDimensions() {
    Map<String, Integer> dimensions = new HashMap<>();
    dimensions.put("width", this.canvasWidth);
    dimensions.put("height", this.canvasHeight);

    return dimensions;
  }

  @Override
  public Map<String, Integer> getCanvasOffset() {
    Map<String, Integer> dimensions = new HashMap<>();
    dimensions.put("xOffset", this.canvasX);
    dimensions.put("yOffset", this.canvasY);

    return dimensions;
  }

  @Override
  public int getMaximumX() {
    return (int) this.maximumX;
  }

  @Override
  public int getMaximumY() {
    return (int) this.maximumY;
  }

  @Override
  public Map<String, List<Integer>> getKeyFrames() {
    return this.animationMoments;
  }

  @Override
  public Map<String, ModelShape> getPresentShapes() {
    return this.presentShapes;
  }

  @Override
  public IAnimatedShape getShapeAtTick(String name, int tick) {
    return this.ticks.get(tick).getPresentShapes().get(name);
  }

  @Override
  public IPosition getShapePosition(String name, int tick) {
    isShapePresentAtTick(name, tick);
    return ticks.get(tick).getPresentShapes().get(name).getModelPosition();
  }

  @Override
  public IColor getShapeColor(String name, int tick) {
    isShapePresentAtTick(name, tick);

    return ticks.get(tick).getPresentShapes().get(name).getModelShape().getModelColor();
  }

  @Override
  public Map<String, Double> getShapeSize(String name, int tick) {
    isShapePresentAtTick(name, tick);

    return ticks.get(tick).getPresentShapes().get(name).getModelShape().getSize();
  }


  public IViewModel returnViewModel() {
    return this;
  }


  /**
   * A builder that creates an {@link ExcellenceAnimatorImpl} using the model's methods to generate
   * shapes and add motion.
   */
  public static final class Builder implements AnimationBuilder<ExcellenceAnimator> {

    ExcellenceAnimatorImpl currentAnimation;

    public Builder() {
      this.currentAnimation = new ExcellenceAnimatorImpl();
    }

    @Override
    public ExcellenceAnimator build() {
      return currentAnimation;
    }

    @Override
    public AnimationBuilder<ExcellenceAnimator> setBounds(int x, int y, int width, int height) {
      currentAnimation.canvasHeight = height;
      currentAnimation.canvasWidth = width;
      currentAnimation.canvasX = x;
      currentAnimation.canvasY = y;

      return this;
    }

    @Override
    public AnimationBuilder<ExcellenceAnimator> declareShape(String name, String type) {
      currentAnimation.addShape(name, type);

      return this;
    }

    @Override
    public AnimationBuilder<ExcellenceAnimator> addMotion(String name, int t1, int x1, int y1,
        int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2,
        int g2, int b2) {

      if (t1 >= currentAnimation.ticks.size() || !currentAnimation.ticks.get(t1).getPresentShapes()
          .containsKey(name)
          || t1 == t2) {
        IColor oldIColor = new ModelColor(r1, g1, b1);
        ModelPosition oldModelPosition = new ModelPosition(x1, y1);
        currentAnimation.presentShapes.get(name).resize(w1, h1);
        currentAnimation.presentShapes.get(name).changeColor(oldIColor);

        currentAnimation.generateShape(name, currentAnimation.presentShapes.get(name),
            oldModelPosition, t1);
      }

      IColor newIColor = new ModelColor(r2, g2, b2);
      ModelPosition newModelPosition = new ModelPosition(x2, y2);

      currentAnimation.moveShape(name, newModelPosition, t1, t2);
      currentAnimation.changeShapeColor(name, newIColor, t1, t2);
      currentAnimation.resizeShape(name, w2, h2, t1, t2);

      currentAnimation.animationMoments.get(name).add(t1);
      currentAnimation.animationMoments.get(name).add(t2);

      return this;
    }

    @Override
    public AnimationBuilder<ExcellenceAnimator> addKeyframe(String name, int t, int x, int y, int w,
        int h, int r, int g, int b) {
      return this;
    }
  }
}
