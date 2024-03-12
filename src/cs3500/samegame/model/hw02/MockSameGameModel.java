package cs3500.samegame.model.hw02;

import java.util.List;

/**
 * Represents a mock implementation of the SameGameModel interface for testing purposes.
 * This mock model provides empty implementations for most methods, allowing for easy testing
 * of the SameGameTextController without requiring a fully functional SameGameModel.
 *
 * @param <T> the type representing the pieces in the game
 */
public class MockSameGameModel<T> implements SameGameModel<T> {
  private int rows;
  private int cols;
  private int swaps;
  private boolean random;

  /**
   * Constructs a MockSameGameModel with the specified number of rows and columns.
   *
   * @param rows the number of rows in the mock model
   * @param cols the number of columns in the mock model
   */
  public MockSameGameModel(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.swaps = 0;
    this.random = false;
  }

  @Override
  public void swap(int fromRow, int fromCol, int toRow, int toCol) {
    //empty
  }

  @Override
  public void removeMatch(int row, int col) {
    //empty
  }

  @Override
  public int width() {
    return this.rows;
  }

  @Override
  public int length() {
    return this.cols;
  }

  @Override
  public T pieceAt(int row, int col) {
    return null;
  }

  @Override
  public int score() {
    return 0;
  }

  @Override
  public int remainingSwaps() {
    return this.swaps;
  }

  @Override
  public boolean gameOver() {
    // Mock implementation for determining game over state
    // No actual logic needed for testing
    return false; // Returning false as it's not relevant for testing
  }

  @Override
  public void startGame(int rows, int cols, int swaps, boolean random) {
    this.rows = rows;
    this.cols = cols;
    this.swaps = swaps;
    this.random = random;
  }

  @Override
  public void startGame(List<List<T>> board, int swaps) {
    this.swaps = swaps;
  }

  @Override
  public T[] createListOfPieces() {
    return null;
  }
}
