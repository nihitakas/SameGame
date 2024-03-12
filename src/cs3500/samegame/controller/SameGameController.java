package cs3500.samegame.controller;

import cs3500.samegame.model.hw02.SameGameModel;

import java.util.List;

/**
 * Represents a controller for the SameGame application.
 *
 * @param <T> the type of elements in the game board
 */
public interface SameGameController<T> {
  /**
   * Plays a new game of SameGame with the provided model.
   * uses the startGae method on the model that matches the inputs
   *
   * @param model  the model being used
   * @param rows   the number of rows the board should have
   * @param cols   the number of columns the board should have
   * @param swaps  how many swaps the player gets to use
   * @param random determines if the board is created randomly or not
   * @throws IllegalArgumentException if the provided board is null
   * @throws IllegalStateException    ONLY if the controller is unable to recieve input
   *                                  or transmit output, or is the game cannot be started
   **/
  void playGame(SameGameModel<T> model, int rows, int cols, int swaps, boolean random);

  /**
   * Plays a new game of SameGame with the provided model.
   * uses the startGae method on the model that matches the inputs
   *
   * @param model     the model being used
   * @param swaps     how many swaps the player gets to use
   * @param initBoard the given board to play the game on
   * @throws IllegalArgumentException if the provided board is null
   * @throws IllegalStateException    ONLY if the controller is unable to recieve input
   *                                  or transmit output, or is the game cannot be started
   **/
  void playGame(SameGameModel<T> model, List<List<T>> initBoard, int swaps);


}
