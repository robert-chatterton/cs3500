package cs3500.main;

import cs3500.excellence.controller.AnimatorController;
import cs3500.excellence.controller.IController;
import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.model.ExcellenceAnimatorImpl.Builder;
import cs3500.excellence.view.IView;
import cs3500.excellence.view.svg.SVGAnimationView;
import cs3500.excellence.view.text.TextView;
import cs3500.excellence.view.visual.EditView;
import cs3500.excellence.view.visual.VisualAnimationView;
import cs3500.main.util.AnimationBuilder;
import cs3500.main.util.AnimationReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The main entry point of the animation program, which takes in a text file containing the motion.
 * The text file is then built into an {@link ExcellenceAnimator}, which is then displayed with the
 * according {@link IView}.
 */
public final class Excellence {

  /**
   * Main program to run the animator. The program also includes command line arguments to control
   * the input text file that reads the animation, the output destination, the view type, and the
   * animation speed.
   *
   * @param args <ul>
   *             <li>{@code in} - the input text file that contains the animation</li>
   *             <li>{@code out} - the output destination, default is {@code System.out}</li>
   *             <li>{@code view} - the view type (visual, SVG, or text)</li>
   *             <li>{@code speed} - the animation speed, default speed is 1 tick/second</li>
   *             </ul>
   */
  public static void main(String[] args) {
    StringBuilder filePath = new StringBuilder();
    String viewType = "";
    int animationSpeed = 1;
    String fileName = "";
    String out = "";

    for (int i = 0; i < args.length; i++) {
      if (args[i].contains("-")) {
        switch (args[i]) {
          case "-in":
            fileName = args[i + 1];
            filePath.append(System.getProperty("user.dir"));
            if (!filePath.toString().contains("/resources")) {
              filePath.append("/resources/source_animations/" + fileName);
            } else {
              filePath.append("/source_animations/" + fileName);
            }
            break;
          case "-view":
            viewType = args[i + 1];
            break;
          case "-out":
            out = args[i + 1];
            break;
          case "-speed":
            animationSpeed = Integer.parseInt(args[i + 1]);
            break;
          default:
            break;
        }
      }
    }

    if (fileName.equals("") || viewType.equals("")) {
      JOptionPane.showMessageDialog(new JFrame(),
          "Must enter a valid view type and an input file",
          "Invalid Arguments", JOptionPane.ERROR_MESSAGE);

      throw new IllegalArgumentException("invalid arguments");
    }

    System.out.println(filePath);

    File fileIn = new File(filePath.toString());

    try {
      BufferedReader br = new BufferedReader(new FileReader(fileIn));
      AnimationBuilder<ExcellenceAnimator> builder = new Builder();
      ExcellenceAnimator animation = AnimationReader.parseFile(br, builder);
      IController controller = createController(viewType, animation, animationSpeed, out);
      controller.runAnimation();
      br.close();
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(new JFrame(),
          e.getMessage(),
          "Illegal Argument Exception", JOptionPane.WARNING_MESSAGE);
    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(new JFrame(),
          e.getMessage(),
          "FILE NOT FOUND", JOptionPane.ERROR_MESSAGE);
    } catch (IOException e) {
      System.out.print("error occurred");
    }
  }

  private static IController createController(String viewType, ExcellenceAnimator model,
      int animationSpeed,
      String outputFileName) {
    IView view;
    switch (viewType) {
      case "visual":
        view = new VisualAnimationView(model.returnViewModel(), animationSpeed);
        return new AnimatorController(model.returnViewModel(), view);
      case "text":
        view = new TextView(model.returnViewModel(), outputFileName, animationSpeed);
        return new AnimatorController(model.returnViewModel(), view);
      case "svg":
        view = new SVGAnimationView(model.returnViewModel(), outputFileName, animationSpeed);
        return new AnimatorController(model.returnViewModel(), view);
      case "edit":
        view = new EditView(animationSpeed);
        return new AnimatorController(model, view);
      default:
        JOptionPane.showMessageDialog(new JFrame(),
            "Unknown view type",
            "Invalid View Type", JOptionPane.WARNING_MESSAGE);

        throw new IllegalArgumentException("unknown view type");
    }
  }
}
