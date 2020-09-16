import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.excellence.controller.AnimatorController;
import cs3500.excellence.controller.IController;
import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.model.ExcellenceAnimatorImpl.Builder;
import cs3500.excellence.view.visual.EditView;
import cs3500.main.util.AnimationBuilder;
import cs3500.main.util.AnimationReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for public methods in the controller and how the controllers affected
 * the view or the model.
 */
public class EditorTests {

  BufferedReader br;
  File fileIn;
  AnimationBuilder<ExcellenceAnimator> builder;
  ExcellenceAnimator model;
  EditView view;
  IController controller;

  @Before
  public void init() throws FileNotFoundException {
    fileIn = new File(System.getProperty("user.dir") + "/test/smalldemo.txt");

    br = new BufferedReader(new FileReader(fileIn));
    builder = new Builder();

    try {
      model = AnimationReader.parseFile(br, builder);
      view = new EditView(20);
      controller = new AnimatorController(model, view);
      br.close();
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(new JFrame(),
          e.getMessage(),
          "Illegal Argument Exception", JOptionPane.WARNING_MESSAGE);
    } catch (IOException e) {
      System.out.print("error occurred");
    }
  }

  @Test
  public void testRunAnimation() {
    assertFalse(view.isVisible());
    controller.runAnimation();
    assertTrue(view.isVisible());
  }

  @Test
  public void testProcessCommand() {
    java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
    System.setOut(new java.io.PrintStream(out));
    controller.processCommand("play");
    controller.runAnimation();

    System.out.flush();
    controller.processCommand("loop toggle");
    new java.io.ByteArrayOutputStream();
    System.setOut(new java.io.PrintStream(out));

    assertEquals("play\n"
        + "Current tick: 1\n"
        + "loop toggle\n", out.toString());
  }
}
