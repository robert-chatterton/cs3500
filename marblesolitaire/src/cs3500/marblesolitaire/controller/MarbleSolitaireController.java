package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Represents a controller for the Marble Solitaire game.
 */
public interface MarbleSolitaireController {

  /**
   * Plays a new game of Marble Solitaire using the given model.
   * @param model the MarbleSolitaireModel
   */
  void playGame(MarbleSolitaireModel model);
}
