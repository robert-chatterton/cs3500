package cs3500.excellence.controller;

import cs3500.excellence.controller.commands.AddKeyframe;
import cs3500.excellence.controller.commands.AddShape;
import cs3500.excellence.controller.commands.AnimationControls;
import cs3500.excellence.controller.commands.DecreaseSpeed;
import cs3500.excellence.controller.commands.DeleteShape;
import cs3500.excellence.controller.commands.EditShape;
import cs3500.excellence.controller.commands.IncreaseSpeed;
import cs3500.excellence.controller.commands.ModifyKeyframe;
import cs3500.excellence.controller.commands.PlayAnimation;
import cs3500.excellence.controller.commands.RemoveKeyframe;
import cs3500.excellence.controller.commands.RestartAnimation;
import cs3500.excellence.controller.commands.ToggleLoop;
import cs3500.excellence.model.ExcellenceAnimator;
import cs3500.excellence.view.IView;
import cs3500.excellence.view.IViewModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * The controller implementation for the animation model. It contains multiple controls that allows
 * to change playback aspects of the animation, such as playing, pausing, increasing/decreasing
 * animation speed etc.
 */
public class AnimatorController implements IController, ActionListener {

  private final ExcellenceAnimator model;
  private final IView view;
  private final IViewModel viewModel;


  /**
   * A constructor for when the view needs direct access to the model to edit different animation
   * values.
   *
   * @param model the model with the animation's information
   * @param view  the view that is displaying the information about the animation
   */
  public AnimatorController(ExcellenceAnimator model, IView view) {
    this.model = model;
    this.view = view;
    this.viewModel = null;
  }

  /**
   * A constructor for when the view just needs access to the information of the model to display
   * visual view. Since it only needs access to it, it takes in an {@link IViewModel}.
   *
   * @param model the ViewModel that the view will be reading information from
   * @param view  the view that will be displaying said information
   */
  public AnimatorController(IViewModel model, IView view) {
    this.view = view;
    this.viewModel = model;
    this.model = null;
  }

  @Override
  public void runAnimation() {
    if (model != null) {
      this.view.setAnimation(model.returnViewModel().getAnimation());
      view.setCommandButtonListener(this);
      view.setListOfNames(new ArrayList<>(model.returnViewModel().getPresentShapes().keySet()));
      view.makeVisible();
    } else if (viewModel != null) {
      view.makeVisible();
    }
  }

  @Override
  public void processCommand(String command) {
    AnimationControls cmd = null;

    switch (command) {
      case "play":
        cmd = new PlayAnimation();
        break;
      case "restart":
        cmd = new RestartAnimation();
        break;
      case "loop toggle":
        cmd = new ToggleLoop();
        break;
      case "decrease speed":
        cmd = new DecreaseSpeed();
        break;
      case "increase speed":
        cmd = new IncreaseSpeed();
        break;
      case "add":
        cmd = new AddShape();
        break;
      case "remove":
        cmd = new DeleteShape();
        break;
      case "edit":
        cmd = new EditShape();
        break;
      case "add keyframe":
        cmd = new AddKeyframe();
        break;
      case "delete keyframe":
        cmd = new RemoveKeyframe();
        break;
      case "modify keyframe":
        cmd = new ModifyKeyframe();
        break;
      default:
        cmd = null;
        view.showErrorMessage("Unknown action");
    }

    if (cmd != null) {
      cmd.executeControl(model, view);
    }

    System.out.println(command);
  }

  /**
   * Receives the action command from a button within the editor
   * view and processes said action command.
   *
   * @param e the button that was pressed that was also added this as an action listener
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    processCommand(e.getActionCommand());
  }
}
