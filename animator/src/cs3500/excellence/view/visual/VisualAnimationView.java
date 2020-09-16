package cs3500.excellence.view.visual;

import cs3500.excellence.model.IAnimatedShape;
import cs3500.excellence.model.IMoment;
import cs3500.excellence.view.AbstractView;
import cs3500.excellence.view.IViewModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 * The visual view of the animation model, which extends the {@link JFrame} and animates the model
 * within a window.
 */
public class VisualAnimationView extends AbstractView implements ActionListener {

  private final AnimationCanvas animationCanvas;
  private final int totalFrames;
  private final List<IMoment> frames;
  private int currentFrame = 1;


  /**
   * A constructor to create a view that uses an {@link IViewModel} to animate the model at the
   * given speed.
   *
   * @param model          the model containing the animation information
   * @param ticksPerSecond the speed that is used to determine the {@link Timer}'s pause
   */
  public VisualAnimationView(IViewModel model, int ticksPerSecond) {
    super();
    setTitle("Animation");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    int canvasWidth = model.getCanvasDimensions().get("width");
    int canvasHeight = model.getCanvasDimensions().get("height");

    this.setPreferredSize(new Dimension(canvasWidth, canvasHeight));

    animationCanvas = new AnimationCanvas(
        model.getCanvasOffset().get("xOffset"),
        model.getCanvasOffset().get("yOffset"));

    animationCanvas.setPreferredSize(new Dimension(model.getMaximumX(), model.getMaximumY()));

    this.frames = model.getAnimation();
    this.totalFrames = frames.size();

    JScrollPane scrollPane = new JScrollPane(animationCanvas);

    this.add(scrollPane);

    animationCanvas.revalidate();

    Timer timer = new Timer(1000 / ticksPerSecond, this);
    timer.setInitialDelay(0);

    timer.start();

    this.pack();
  }

  @Override
  public void refresh() {
    animationCanvas.repaint();
  }

  @Override
  public void setShapes(List<IAnimatedShape> as) {
    this.animationCanvas.setShapes(as);
  }

  @Override
  public void writeToOutput() {
    System.out.println("Showing animation on window");
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    if (currentFrame == totalFrames) {
      currentFrame = totalFrames;
    }

    List<IAnimatedShape> listToBeSet = new ArrayList<>();
    listToBeSet.addAll(frames.get(currentFrame).getPresentShapes().values());
    this.setShapes(listToBeSet);

    refresh();

    currentFrame++;
  }


}
