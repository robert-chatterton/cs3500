import static org.junit.Assert.assertEquals;

import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.model.ExcellenceAnimatorImpl.Builder;
import cs3500.excellence.view.IView;
import cs3500.excellence.view.text.TextView;
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
 * Tests to confirm the contents of an output file of the textual view of an animation's demo.
 */
public class TextViewTests {

  BufferedReader br;
  ExcellenceAnimator animation;
  IView visual;
  AnimationBuilder<ExcellenceAnimator> builder;
  File fileIn;

  @Before
  public void init() throws FileNotFoundException {
    fileIn = new File(System.getProperty("user.dir") + "/test/smalldemo.txt");

    br = new BufferedReader(new FileReader(fileIn));
    builder = new Builder();

    try {
      animation = AnimationReader.parseFile(br, builder);
      visual = new TextView(animation.returnViewModel(), "smalldemo-log.txt", 2);
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
    File logFile = new File(System.getProperty("user.dir") + "/smalldemo-log.txt");

    br = new BufferedReader(new FileReader(logFile));

    assertEquals("canvas 200 70 360 360", br.readLine());

    assertEquals("shape R", br.readLine());

    StringBuilder motion = new StringBuilder();

    for (int i = 0; i < 5; i++) {
      motion.append(br.readLine() + "\n");
    }

    assertEquals("motion  R  1  200  200  50  100  255  0  0\t|\t10  200  200  50  100  255  0  0\n"
            + "motion  R  10  200  200  50  100  255  0  0\t|\t50  300  300  50  100  255  0  0\n"
            + "motion  R  50  300  300  50  100  255  0  0\t|\t51  300  300  50  100  255  0  0\n"
            + "motion  R  51  300  300  50  100  255  0  0\t|\t70  300  300  25  100  255  0  0\n"
            + "motion  R  70  300  300  25  100  255  0  0\t|\t100  200  200  25  100  255  0  0\n",
        motion.toString());

    assertEquals("shape C", br.readLine());

    motion = new StringBuilder();
    for (int i = 0; i < 5; i++) {
      motion.append(br.readLine() + "\n");
    }

    assertEquals("motion  C  6  440  70  120  60  0  0  255\t|\t20  440  70  120  60  0  0  255\n"
            + "motion  C  20  440  70  120  60  0  0  255\t|\t50  440  250  120  60  0  0  255\n"
            + "motion  C  50  440  250  120  60  0  0  255\t|\t70  440  370  120  60  0  170  85\n"
            + "motion  C  70  440  370  120  60  0  170  85\t|\t80  440  370  120  60  0  255  0\n"
            + "motion  C  80  440  370  120  60  0  255  0\t|\t100  440  370  120  60  0  255  0\n",
        motion.toString());

    br.close();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testTextViewRefresh() {
    visual.refresh();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testTextViewSetShapes() {
    visual.setShapes(null);
  }
}
