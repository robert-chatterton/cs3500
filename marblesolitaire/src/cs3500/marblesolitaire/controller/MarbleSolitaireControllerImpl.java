package cs3500.marblesolitaire.controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Implements the MarbleSolitaireController interface.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {

  private final Appendable out;
  private final Scanner scan;
  private boolean quit;

  /**
   * Constructs a new MarbleSolitaireControllerImpl.
   * @param in a Readable object
   * @param out an Appendable object.
   * @throws IllegalArgumentException if {@code in} or {@code out} are {@code null}.
   */
  public MarbleSolitaireControllerImpl(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("In and Out cannot be null");
    }
    this.quit = false;
    this.out = out;
    this.scan = new Scanner(in);
  }

  @Override
  public void playGame(MarbleSolitaireModel model) { // use a switch statement
    // check for a null model
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    while (!model.isGameOver()) {
      try {
        out.append(model.getGameState() + "\n");
        out.append(String.format("Score: %d", model.getScore()) + "\n");

        int xFromMove;
        int yFromMove;
        int xToMove;
        int yToMove;

        yFromMove = this.getInput() - 1;
        if (quit) {
          break;
        }
        xFromMove = this.getInput() - 1;
        if (quit) {
          break;
        }
        yToMove = this.getInput() - 1;
        if (quit) {
          break;
        }
        xToMove = this.getInput() - 1;
        if (quit) {
          break;
        }

        try {
          model.move(yFromMove, xFromMove, yToMove, xToMove);
        } catch (IllegalArgumentException e) {
          out.append("Invalid move. Play again. " + e.getLocalizedMessage() + ".\n");
          continue;
        }

      } catch (IOException ioe) {
        // the model was unable to write or read
        throw new IllegalStateException("Append failed", ioe);
      }
      if (quit) {
        break;
      }
    }

    if (quit) {
      // game ended with a quit
      try {
        out.append("Game quit!\nState of game when quit:\n");
        out.append(model.getGameState() + "\n");
        out.append(String.format("Score: %d", model.getScore()) + "\n");
      } catch (IOException ioe) {
        // the model was unable to write or read
        throw new IllegalStateException("Append failed", ioe);
      }
    } else {
      // ending the game naturally
      try {
        out.append("Game over!\n");
        out.append(model.getGameState() + "\n");
        out.append(String.format("Score: %d", model.getScore()) + "\n");
      } catch (IOException ioe) {
        // the model was unable to write or read
        throw new IllegalStateException("Append failed", ioe);
      }
    }
  }

  /**
   * Handles retrieving input for a move.
   * @return the integer value representing the move to be made.
   * @throws IllegalStateException when input or output lines are disconnected.
   */
  private int getInput() {
    boolean validInput = false;
    int move = -1;
    String s;
    while (!validInput) {
      try {
        s = scan.next();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Input stream closed");
      }
      try {
        int m = Integer.parseInt(s);
        if (m > 0) {
          move = m;
          validInput = true;
        } else {
          try {
            out.append("Invalid move. Play again. Move must be a positive integer.\n");
          } catch (IOException ioe) {
            throw new IllegalStateException("Append failed", ioe);
          }
        }
      } catch (NumberFormatException e) {
        if (s.equalsIgnoreCase("q")) {
          this.quit = true;
          return 0;
        } else {
          try {
            out.append(String.format("Invalid move. Play again. Unknown command %s.\n", s));
          } catch (IOException ioe) {
            throw new IllegalStateException("Append failed", ioe);
          }
          return this.getInput();
        }
      }
    }
    return move;
  }
}
