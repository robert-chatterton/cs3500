import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.model.ExcellenceAnimatorImpl;
import cs3500.excellence.shape.Ellipse;
import cs3500.excellence.shape.IPosition;
import cs3500.excellence.shape.ModelColor;
import cs3500.excellence.shape.ModelPosition;
import cs3500.excellence.shape.ModelShape;
import cs3500.excellence.shape.Rectangle;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests to check {@link ExcellenceAnimatorImpl} functionality and its correctness.
 */
public class AnimatorTests {

  ExcellenceAnimator animator;
  ModelShape rectangle;
  ModelShape ellipse;
  ModelColor black = new ModelColor(0, 0, 0);
  ModelColor red = new ModelColor(255, 0, 0);
  ModelColor white = new ModelColor(255, 255, 255);

  @Before
  public void init() {
    animator = new ExcellenceAnimatorImpl();
    rectangle = new Rectangle(50, 100, black);
    ellipse = new Ellipse(120, 60, red);
    animator.generateShape("R", rectangle, new ModelPosition(200, 200), 1);
  }

  @Test
  public void testConstructor() {
    ExcellenceAnimator animator = new ExcellenceAnimatorImpl();

    assertNotEquals(null, animator);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDoubleShapeGeneration() {
    init();
    animator.generateShape("R", rectangle, new ModelPosition(50, 50), 1);
  }

  @Test
  public void testValidShapeMove() {
    init();

    animator.moveShape("R", new ModelPosition(100, 100), 1, 10);

    assertEquals(new ModelPosition(100, 100), animator.getShapePosition("R", 10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOverlap() {
    init();

    animator.moveShape("R", new ModelPosition(100, 100), 1, 10);
    animator.moveShape("R", new ModelPosition(300, 300), 5, 10);
  }

  @Test
  public void testValidRecolor() {
    init();

    animator.changeShapeColor("R", white, 1, 10);

    assertEquals(white, animator.getShapeColor("R", 10));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testRecolorOverlap() {
    init();

    animator.changeShapeColor("R", white, 1, 10);
    animator.changeShapeColor("R", red, 2, 5);
  }

  @Test
  public void testValidResize() {
    init();

    animator.resizeShape("R", 25, 100, 1, 20);

    Map<String, Double> newDimensions = new HashMap<>();
    newDimensions.put("width", 25.0);
    newDimensions.put("height", 100.0);

    assertEquals(newDimensions, animator.getShapeSize("R", 20));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testResizeOverlap() {
    init();

    animator.resizeShape("R", 25, 200, 1, 20);
    animator.resizeShape("R", 75, 200, 5, 10);
  }

  @Test
  public void testShapeGenerationInBetweenAnimation() {
    init();

    animator.moveShape("R", new ModelPosition(100, 100), 1, 10);

    animator.generateShape("C", ellipse, new ModelPosition(440, 70), 6);
    animator.moveShape("C", new ModelPosition(500, 500), 6, 20);

    assertEquals(new ModelPosition(500, 500), animator.getShapePosition("C", 20));
    assertEquals(new ModelPosition(440, 70), animator.getShapePosition("C", 6));
    assertEquals(new ModelPosition(100, 100), animator.getShapePosition("R", 10));
  }

  @Test
  public void testMoveAndResizeOverlap() {
    init();

    animator.moveShape("R", new ModelPosition(100, 100), 1, 10);
    animator.resizeShape("R", 400, 400, 4, 9);

    Map<String, Double> newDimensions = new HashMap<>();
    newDimensions.put("width", 400.0);
    newDimensions.put("height", 400.0);

    assertEquals(new ModelPosition(100, 100), animator.getShapePosition("R", 10));
    assertEquals(newDimensions, animator.getShapeSize("R", 9));
  }

  @Test
  public void testMoveAndRecolorOverlap() {
    init();

    animator.moveShape("R", new ModelPosition(100, 100), 1, 10);
    animator.changeShapeColor("R", red, 4, 6);

    assertEquals(new ModelPosition(100, 100), animator.getShapePosition("R", 10));
    assertEquals(red, animator.getShapeColor("R", 6));
  }

  @Test
  public void testDoNothing() {
    init();
    animator.doNothing("R", 1, 5);

    Map<String, Double> newDimensions = new HashMap<>();
    newDimensions.put("width", 50.0);
    newDimensions.put("height", 100.0);

    assertEquals(black, animator.getShapeColor("R", 5));
    assertEquals(new ModelPosition(200, 200), animator.getShapePosition("R", 5));
    assertEquals(newDimensions, animator.getShapeSize("R", 5));
  }

  @Test
  public void testGetShapePosition() {
    init();

    IPosition testModelPosition = animator.getShapePosition("R", 1);

    assertEquals(new ModelPosition(200, 200), testModelPosition);
  }

  @Test
  public void testGetShapeColor() {
    init();

    assertEquals(black, animator.getShapeColor("R", 1));
  }

  @Test
  public void testGetShapeSize() {
    init();

    Map<String, Double> dimensions = new HashMap<>();
    dimensions.put("width", 50.0);
    dimensions.put("height", 100.0);

    assertEquals(dimensions, animator.getShapeSize("R", 1));
  }

  @Test
  public void testAssignmentSampleOutput() {
    init();

    animator.doNothing("R", 1, 10);
    animator.moveShape("R", new ModelPosition(300, 300), 10, 50);
    animator.doNothing("R", 50, 51);
    animator.resizeShape("R", 25, 100, 51, 70);
    animator.moveShape("R", new ModelPosition(200, 200), 70, 100);

    animator.generateShape("C", ellipse, new ModelPosition(440, 70), 6);
    animator.doNothing("C", 6, 20);
    animator.moveShape("C", new ModelPosition(440, 250), 20, 50);
    animator.changeShapeColor("C",
        new ModelColor(0, 170, 85), 50, 78);

    Map<String, Double> newRectangleDimensions = new HashMap<>();
    newRectangleDimensions.put("width", 25.0);
    newRectangleDimensions.put("height", 100.0);

    Map<String, Double> newEllipseDimensions = new HashMap<>();
    newEllipseDimensions.put("width", 120.0);
    newEllipseDimensions.put("height", 60.0);

    assertEquals(black, animator.getShapeColor("R", 5));
    assertEquals(new ModelPosition(200, 200), animator.getShapePosition("R", 100));
    assertEquals(newRectangleDimensions, animator.getShapeSize("R", 100));

    assertEquals(new ModelColor(0, 170, 85),
        animator.getShapeColor("C", 78));
    assertEquals(new ModelPosition(440, 250), animator.getShapePosition("C", 78));
    assertEquals(newEllipseDimensions, animator.getShapeSize("C", 78));
  }

}
