package cs3500.samegame.controller;

import cs3500.samegame.model.hw02.SameGameModel;
import cs3500.samegame.view.SameGameTextView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the controller for a SameGame text-based application.
 *
 * @param <T> the type of elements in the game board
 */
public class SameGameTextController<T> implements SameGameController<T> {
  private final Appendable ap;
  private SameGameTextView sameGameTextView;
  Scanner scanner;

  /**
   * Constructor for SameGameTextController.
   *
   * @param rd readable input
   * @param ap readable output
   * @throws IllegalArgumentException if either of its arguments are null
   */
  public SameGameTextController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.ap = ap;
    this.scanner = new Scanner(rd);
  }

  private void play(SameGameModel gameModel) throws IOException {
    while (!gameModel.gameOver() && scanner.hasNext()) {
      this.getState(gameModel);
      String userInput = this.scanner.next();
      processUserInput(gameModel, userInput);
    }
  }

  private void processUserInput(SameGameModel gameModel, String userInput) throws IOException {
    if (userInput.equals("q") || userInput.equals("Q")) {
      this.getQuit();
      this.getState(gameModel);
    } else if (userInput.equals("s")) {
      handleSwapInput(gameModel);
    } else if (userInput.equals("m")) {
      handleRemoveMatchInput(gameModel);
    } else {
      this.ap.append("Invalid command. Try again. Options: q, s, m.\n");
    }
  }

  private void handleSwapInput(SameGameModel gameModel) throws IOException {
    int row1 = this.getUserInput();
    if (row1 == -3) {
      this.getQuit();
      return;
    }
    int col1 = this.getUserInput();
    if (col1 == -3) {
      this.getQuit();
      return;
    }
    int row2 = this.getUserInput();
    if (row2 == -3) {
      this.getQuit();
      return;
    }
    int col2 = this.getUserInput();
    if (col2 == -3) {
      this.getQuit();
      return;
    }

    try {
      gameModel.swap(row1 - 1, col1 - 1, row2 - 1, col2 - 1);
      if (gameModel.gameOver()) {
        this.getGameOverState(gameModel);
      }
    } catch (IllegalStateException | IllegalArgumentException exception) {
      this.ap.append("Invalid move. Try again. " + exception.getMessage() + ".\n");
    }
  }

  private void handleRemoveMatchInput(SameGameModel gameModel) throws IOException {
    int index1 = this.getUserInput();
    int index2 = this.getUserInput();

    try {
      gameModel.removeMatch(index1 - 1, index2 - 1);
      if (gameModel.gameOver()) {
        this.getGameOverState(gameModel);
      }
    } catch (IllegalStateException | IllegalArgumentException exception) {
      this.ap.append("Invalid move. Try again." + exception.getMessage() + ".\n");
    }
  }

  private int getUserInput() {
    while (this.scanner.hasNext()) {
      String userInput = this.scanner.next();

      if (userInput.equals("q") || userInput.equals("Q")) {
        break;
      } else {
        try {
          int userInputAsInt = Integer.parseInt(userInput);

          if (userInputAsInt >= 0) {
            return userInputAsInt;
          } else {
            System.out.println("Invalid input. Please enter a non-negative integer.");
          }

        } catch (NumberFormatException ignore) {
          System.out.println("Invalid input. Please enter a valid integer.");
        }
      }
    }

    throw new IllegalStateException("Scanner does not have next input.");
  }

  private void getGameOverState(SameGameModel model) throws IOException {
    this.ap.append("Game over.\n");
    this.getState(model);
  }

  private void getQuit() throws IOException {
    this.ap.append("Game quit!\n");
    this.ap.append("State of game when quit:\n");
  }

  private void getState(SameGameModel fourPieceSameGame) {
    try {
      this.sameGameTextView.render();
      this.ap.append("Remaining swaps: " + fourPieceSameGame.remainingSwaps() + "\n");
      this.ap.append("Score: " + fourPieceSameGame.score() + "\n");
    } catch (IOException exception) {
      throw new IllegalStateException("Issue appending.");
    }
  }

  @Override
  public void playGame(SameGameModel<T> model, int rows, int cols, int swaps, boolean random) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }

    this.sameGameTextView = new SameGameTextView(model, this.ap);

    try {
      model.startGame(rows, cols, swaps, random);
      this.play(model);
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to receive input or transmit output.");
    }
  }

  @Override
  public void playGame(SameGameModel<T> model, List<List<T>> initBoard, int swaps) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }

    this.sameGameTextView = new SameGameTextView(model, this.ap);

    try {
      model.startGame(initBoard, swaps);

      if (model.gameOver()) {
        this.ap.append("Game over.\n");
        this.getState(model);
      } else {
        this.play(model);
      }
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to receive input or transmit output.");
    }
  }
}


