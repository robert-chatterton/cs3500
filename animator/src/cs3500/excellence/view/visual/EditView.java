package cs3500.excellence.view.visual;

import cs3500.excellence.model.IAnimatedShape;
import cs3500.excellence.model.IMoment;
import cs3500.excellence.shape.IColor;
import cs3500.excellence.shape.IPosition;
import cs3500.excellence.shape.ModelColor;
import cs3500.excellence.shape.ModelPosition;
import cs3500.excellence.view.AbstractView;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.Timer;

/**
 * The EditView displays the animation model in a JFrame with editing tools playback controls.
 */
public class EditView extends AbstractView implements ActionListener {

  private final AnimationCanvas animationCanvas;
  private final Map<String, JButton> playbackButtons;
  private final JLabel animationSpeedLabel;
  private final JLabel isLoopingLabel;
  private final Map<String, JButton> editButtons;
  private final JPanel editorPreview;
  private final ArrayList<String> presentShapes;
  private final JComboBox<String> presentShapesBox;
  private final JComboBox<String> addShapeOptions;
  private ActionListener listener;
  private List<IMoment> frames;
  private JButton lastPressedButton;
  private JList<String> keyframesList;
  private JPanel editKeyframesPane;
  private int animationSpeed;
  private int totalFrames;
  private int currentFrame = 1;
  private boolean isPlaying;
  private boolean isLoopbackOn;
  private Timer timer;


  /**
   * This constructor creates the view that uses the {@link cs3500.excellence.view.IViewModel} to
   * animate the model at the given speed.
   *
   * @param animationSpeed the speed with which to run the animation at.
   */
  public EditView(int animationSpeed) {
    setTitle("editor");
    isLoopbackOn = false;
    isPlaying = false;
    this.animationSpeed = animationSpeed;
    presentShapes = new ArrayList<>();
    editKeyframesPane = new JPanel();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.setExtendedState(Frame.MAXIMIZED_BOTH);

    animationCanvas = new AnimationCanvas(0, 0);
    animationCanvas.setPreferredSize(new Dimension(800, 800));

    // GUI FOR PLAYBACK CONTROLS
    JToolBar playbackControls = new JToolBar();

    playbackButtons = new LinkedHashMap<>();
    playbackButtons.put("play", new JButton("Play"));
    playbackButtons.put("restart", new JButton("Restart"));
    playbackButtons.put("loop toggle", new JButton("Toggle loop"));
    playbackButtons.put("increase speed", new JButton("+"));
    playbackButtons.put("decrease speed", new JButton("-"));

    animationSpeedLabel = new JLabel("\t\tspeed: " + animationSpeed);
    isLoopingLabel = new JLabel("\t\tlooping: OFF");

    lastPressedButton = playbackButtons.get("play");

    for (Map.Entry<String, JButton> button : playbackButtons.entrySet()) {
      playbackControls.add(button.getValue());
    }

    playbackControls.add(animationSpeedLabel);
    playbackControls.add(isLoopingLabel);

    // GUI FOR EDIT CONTROLS
    JToolBar editControls = new JToolBar();

    editButtons = new LinkedHashMap<>();
    JButton addShape = new JButton("Add Shape");
    addShapeOptions = new JComboBox<String>();
    addShapeOptions.addItem("Rectangle");
    addShapeOptions.addItem("Ellipse");
    editControls.add(addShapeOptions);
    editControls.add(addShape);

    presentShapesBox = new JComboBox();
    editControls.add(presentShapesBox);
    JButton removeShape = new JButton("Remove Shape");
    editControls.add(removeShape);
    JButton editShape = new JButton("Edit Keyframes");
    editControls.add(editShape);

    editButtons.put("add", addShape);
    editButtons.put("remove", removeShape);
    editButtons.put("edit", editShape);

    JPanel toolBar = new JPanel();
    toolBar.setLayout(new FlowLayout());
    toolBar.add(playbackControls);
    toolBar.add(editControls);
    JScrollPane toolBarScroll = new JScrollPane(toolBar);

    this.add(toolBarScroll, BorderLayout.SOUTH);

    timer = new Timer(1000 / animationSpeed, this);
    timer.setInitialDelay(0);
    timer.setActionCommand("timer");
    timer.setRepeats(false);

    JScrollPane scrollPane = new JScrollPane(animationCanvas);

    editorPreview = new JPanel();
    editorPreview.setLayout(new FlowLayout());
    editorPreview.add(scrollPane);

    this.add(editorPreview, BorderLayout.CENTER);

    editorPreview.repaint();

    animationCanvas.revalidate();

    this.pack();

    timer.start();
  }


  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    List<IAnimatedShape> listToBeSet = new ArrayList<>();
    listToBeSet.addAll(frames.get(currentFrame).getPresentShapes().values());
    setShapes(listToBeSet);

    animationCanvas.repaint();
  }

  @Override
  public void setShapes(List<IAnimatedShape> as) {
    animationCanvas.setShapes(as);
  }

  @Override
  public void setListOfNames(List<String> names) {
    for (String name : names) {
      if (!presentShapes.contains(name)) {
        presentShapes.add(name);
        presentShapesBox.addItem(name);
      }
    }
  }

  @Override
  public void setAnimation(List<IMoment> frames) {
    this.frames = frames;
    this.totalFrames = frames.size();
  }

  @Override
  public void writeToOutput() {
    System.out.println("Showing animation on window");
  }

  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    for (Map.Entry<String, JButton> button : playbackButtons.entrySet()) {
      button.getValue().addActionListener(actionEvent);
      button.getValue().setActionCommand(button.getKey());
    }

    for (Map.Entry<String, JButton> button : editButtons.entrySet()) {
      button.getValue().addActionListener(actionEvent);
      button.getValue().setActionCommand(button.getKey());
    }

    this.listener = actionEvent;
  }

  @Override
  public void changeAnimationSpeed(int delta) {
    this.animationSpeed += delta;
    if (animationSpeed <= 0) {
      animationSpeed = 1;
    }

    animationSpeedLabel.setText("\t\tspeed: " + animationSpeed);

    System.out.println(animationSpeed);

    timer = new Timer(1000 / animationSpeed, this);
    timer.setInitialDelay(0);
    timer.setActionCommand("timer");
    timer.start();

    if (delta > 0) {
      this.lastPressedButton = playbackButtons.get("increase speed");
    } else {
      this.lastPressedButton = playbackButtons.get("decrease speed");
    }


  }

  @Override
  public void resume() {
    timer.setRepeats(true);
    if (isPlaying) {
      timer.stop();
      playbackButtons.get("play").setText("Play");
      isPlaying = false;
    } else {
      playbackButtons.get("play").setText("Pause");
      isPlaying = true;
      timer.start();
    }

    this.lastPressedButton = playbackButtons.get("play");
  }

  @Override
  public void toggleLoopback() {
    this.isLoopbackOn = !this.isLoopbackOn;

    if (isLoopbackOn) {
      isLoopingLabel.setText("\t\tlooping: ON");
    } else {
      isLoopingLabel.setText("\t\tlooping: OFF");
    }

    this.lastPressedButton = playbackButtons.get("loop toggle");
  }

  @Override
  public void restart() {
    this.currentFrame = 1;

    this.lastPressedButton = playbackButtons.get("restart");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(timer)) {
      System.out.println("Current tick: " + currentFrame);
      if (currentFrame < totalFrames) {
        List<IAnimatedShape> listToBeSet = new ArrayList<>(
            frames.get(currentFrame).getPresentShapes().values());
        this.setShapes(listToBeSet);

        refresh();

        currentFrame++;
      } else {
        if (isLoopbackOn) {
          currentFrame = 1;
        }
      }
    }
  }


  @Override
  public Map<String, String> getNewShapeInfo() {
    String name = JOptionPane.showInputDialog("Please enter the name of shape");
    String type = Objects.requireNonNull(addShapeOptions.getSelectedItem()).toString();

    Map<String, String> shapeInfo = new HashMap<>();
    shapeInfo.put("name", name);
    shapeInfo.put("type", type);

    return shapeInfo;
  }

  @Override
  public String getSelectedShape() {
    String name = Objects.requireNonNull(presentShapesBox.getSelectedItem()).toString();
    if (!presentShapes.contains(name)) {
      presentShapesBox.removeItem(name);
    }
    presentShapesBox.revalidate();
    presentShapesBox.repaint();
    return name;
  }

  @Override
  public void showKeyframeControls(
      Map<String, List<Integer>> keyFrames) {

    editorPreview.remove(editKeyframesPane);

    String name = Objects.requireNonNull(presentShapesBox.getSelectedItem()).toString();
    List<Integer> keyframesForShape = keyFrames.get(name);

    keyframesList = new JList<>();
    DefaultListModel<String> listModel = new DefaultListModel();

    if (keyframesForShape != null) {
      for (int i = 1; i < keyframesForShape.size(); i += 2) {
        int startKeyframe = keyframesForShape.get(i - 1);
        int endKeyframe = keyframesForShape.get(i);

        String currentKeyframe = "Initial Tick: " + startKeyframe +
            "\t\t \t\t" + "End Tick: " + endKeyframe;

        listModel.addElement(currentKeyframe);
      }
    }

    keyframesList.setModel(listModel);

    editKeyframesPane = new JPanel();
    editKeyframesPane.setLayout(new BorderLayout());

    JLabel info = new JLabel();
    info.setText("<html>Keyframes for " + name + "<br/>-------------------------"
        + "<br/>Keyframe Endpoints</html>");
    editKeyframesPane.add(info, BorderLayout.NORTH);
    editKeyframesPane.add(keyframesList, BorderLayout.CENTER);

    JToolBar editKeyframeControls = new JToolBar();
    JButton addKeyframe = new JButton("Add keyframe");

    addKeyframe.setActionCommand("add keyframe");
    addKeyframe.addActionListener(listener);
    editKeyframeControls.add(addKeyframe);

    JButton removeKeyframe = new JButton("Delete keyframe");
    removeKeyframe.setActionCommand("delete keyframe");
    removeKeyframe.addActionListener(listener);
    editKeyframeControls.add(removeKeyframe);

    JButton modifyKeyframe = new JButton("Modify keyframe");
    modifyKeyframe.setActionCommand("modify keyframe");
    modifyKeyframe.addActionListener(listener);
    editKeyframeControls.add(modifyKeyframe);

    editButtons.put("add keyframe", addKeyframe);
    editButtons.put("delete keyframe", removeKeyframe);
    editButtons.put("modify keyframe", modifyKeyframe);

    editKeyframesPane.add(editKeyframeControls, BorderLayout.SOUTH);

    editorPreview.add(editKeyframesPane);

    editorPreview.revalidate();
    editorPreview.repaint();
  }

  @Override
  public Map<String, Integer> getSelectedKeyframe() {
    String name = presentShapesBox.getSelectedItem().toString();

    Map<String, Integer> selectedKeyframe = new HashMap<>();
    selectedKeyframe.put(name, keyframesList.getSelectedIndex());

    return selectedKeyframe;
  }

  @Override
  public int addKeyframe() {
    String keyframeLocation;
    keyframeLocation =
        JOptionPane.showInputDialog(
            "Please enter the tick location of the keyframe you wish to add");

    if (Integer.parseInt(keyframeLocation) >= 1) {
      return Integer.parseInt(keyframeLocation);
    }

    throw new IllegalArgumentException(
        "Cannot add keyframe at tick " + Integer.parseInt(keyframeLocation));
  }

  @Override
  public IAnimatedShape modifyKeyframe(IAnimatedShape a) {

    JTextField newXField = new JTextField(String.valueOf(a.getModelPosition().getX()), 4);
    JTextField newYField = new JTextField(String.valueOf(a.getModelPosition().getY()), 4);
    JTextField newRedField =
        new JTextField(String.valueOf(a.getModelShape().getModelColor().getRedValue()), 4);
    JTextField newGreenField =
        new JTextField(String.valueOf(a.getModelShape().getModelColor().getGreenValue()), 4);
    JTextField newBlueField =
        new JTextField(String.valueOf(a.getModelShape().getModelColor().getBlueValue()), 4);
    JTextField newWidthField = new JTextField(
        String.valueOf(a.getModelShape().getSize().get("width")), 4);
    JTextField newHeightField = new JTextField(
        String.valueOf(a.getModelShape().getSize().get("height")), 4);

    JPanel options = new JPanel();
    options.add(new JLabel("X: "));
    options.add(newXField);
    options.add(Box.createVerticalStrut(15));
    options.add(new JLabel("Y: "));
    options.add(newYField);
    options.add(Box.createVerticalStrut(15));
    options.add(new JLabel("Red: "));
    options.add(newRedField);
    options.add(Box.createVerticalStrut(15));
    options.add(new JLabel("Green: "));
    options.add(newGreenField);
    options.add(Box.createVerticalStrut(15));
    options.add(new JLabel("Blue: "));
    options.add(newBlueField);
    options.add(Box.createVerticalStrut(15));
    options.add(new JLabel("Width: "));
    options.add(newWidthField);
    options.add(Box.createVerticalStrut(15));
    options.add(new JLabel("Height"));
    options.add(newHeightField);
    options.add(Box.createVerticalStrut(15));

    int result = JOptionPane.showConfirmDialog(null, options,
        "Please enter new keyframe info", JOptionPane.OK_CANCEL_OPTION);
    IAnimatedShape newShape = a;

    if (result == JOptionPane.OK_OPTION) {
      IColor tempColor = new ModelColor(Double.parseDouble(newRedField.getText()),
          Double.parseDouble(newGreenField.getText()),
          Double.parseDouble(newBlueField.getText()));

      IPosition tempPosition = new ModelPosition(Double.parseDouble(newXField.getText()),
          Double.parseDouble(newYField.getText()));

      newShape.changeColor(tempColor);
      newShape.setModelPosition(tempPosition);
      newShape.resize(Double.parseDouble(newWidthField.getText()),
          Double.parseDouble(newHeightField.getText()));

      return newShape;
    }
    return null;
  }
}
