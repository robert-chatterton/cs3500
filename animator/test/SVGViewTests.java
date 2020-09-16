import static org.junit.Assert.assertEquals;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.model.ExcellenceAnimatorImpl.Builder;
import cs3500.excellence.view.IView;
import cs3500.excellence.view.svg.SVGAnimationView;
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
 * Tests to confirm the {@link SVGAnimationView} is getting the right information and writing to the
 * SVG file correctly.
 */
public class SVGViewTests {

  BufferedReader br;
  File fileIn;
  AnimationBuilder<ExcellenceAnimator> builder;
  ExcellenceAnimator animation;
  IView visual;

  @Before
  public void init() throws FileNotFoundException {
    fileIn = new File(System.getProperty("user.dir") + "/test/smalldemo.txt");

    br = new BufferedReader(new FileReader(fileIn));
    builder = new Builder();

    try {
      animation = AnimationReader.parseFile(br, builder);
      visual = new SVGAnimationView(animation.returnViewModel(), "smalldemo.svg", 20);
      visual.writeToOutput();
      visual.makeVisible();
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
  public void testModelInformation() throws IOException {
    File logFile = new File(System.getProperty("user.dir") + "/smalldemo.svg");

    br = new BufferedReader(new FileReader(logFile));

    assertEquals("<svg width=\"360\" height=\"360\" viewBox=\"200 70 360 360\" version=\"1.1\" "
        + "xmlns=\"http://www.w3.org/2000/svg\" overflow=\"auto\">", br.readLine());

    StringBuilder motion = new StringBuilder();

    for (int i = 0; i < 8; i++) {
      motion.append(br.readLine()).append("\n");
    }

    assertEquals(
        "<rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"100.0\""
            + " fill=\"rgb(255,0,0)\" visibility=\"hidden\" >\n"
            + "\t<set attributeType=\"xml\" begin=\"50.0ms\" dur=\"1ms\" "
            + "attributeName=\"visibility\" to=\"visible\" fill=\"freeze\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"1450ms\" dur=\"2000.0ms\" "
            + "attributeName=\"x\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"1450ms\" dur=\"2000.0ms\" "
            + "attributeName=\"y\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"3500ms\" dur=\"950.0ms\""
            + " attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"4450ms\" dur=\"1500.0ms\" "
            + "attributeName=\"x\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"4450ms\" dur=\"1500.0ms\" "
            + "attributeName=\"y\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n"
            + "</rect>\n",
        motion.toString());

    motion = new StringBuilder();
    for (int i = 0; i < 7; i++) {
      motion.append(br.readLine()).append("\n");
    }

    assertEquals(
        "<ellipse id=\"C\" cx=\"500\" cy=\"100\" rx=\"60.0\" ry=\"30.0\" "
            + "fill=\"rgb(0,0,255)\" visibility=\"hidden\" >\n"
            + "\t<set attributeType=\"xml\" begin=\"300.0ms\" dur=\"1ms\" "
            + "attributeName=\"visibility\" to=\"visible\" fill=\"freeze\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"1700ms\" dur=\"1500.0ms\" "
            + "attributeName=\"cy\" from=\"100.0\" to=\"280.0\" fill=\"freeze\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"3200ms\" dur=\"1000.0ms\" "
            + "attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,170,85)\" "
            + "fill=\"freeze\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"3200ms\" dur=\"1000.0ms\" "
            + "attributeName=\"cy\" from=\"280.0\" to=\"400.0\" fill=\"freeze\" />\n"
            + "\t<animate attributeType=\"xml\" begin=\"4200ms\" dur=\"500.0ms\" "
            + "attributeName=\"fill\" from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" "
            + "fill=\"freeze\" />\n"
            + "</ellipse>\n",
        motion.toString());

    assertEquals("</svg>", br.readLine());

    br.close();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSVGViewRefresh() {
    visual.refresh();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSVGViewSetShapes() {
    visual.setShapes(null);
  }
}
