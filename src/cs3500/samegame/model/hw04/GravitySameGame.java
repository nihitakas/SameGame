package cs3500.samegame.model.hw04;

import java.util.List;

import cs3500.samegame.model.hw02.MyPiece;

/**
 * Represents a variation of the SameGame implementation where pieces fall down after a swap.
 */
public class GravitySameGame extends AbstractSameGame {

  /**
   * Constructs a GravitySameGame object.
   */
  public GravitySameGame() {
    super();
  }

  @Override
  public void swap(int fromRow, int fromCol, int toRow, int toCol) {
    super.swap(fromRow, fromCol, toRow, toCol);
    applyGravity();
  }

  @Override
  public void startGame(int rows, int cols, int swaps, boolean random) {
    super.startGame(rows, cols, swaps, random);
    applyGravity();

  }

  @Override
  public void startGame(List<List<MyPiece>> board, int swaps) {
    super.startGame(board, swaps);
    applyGravity();
  }

  /**
   * Applies gravity to the game board by making pieces fall down if there are empty
   * spaces below them.
   */
  public void applyGravity() {
    for (int col = 0; col < width(); col++) {
      applyGravityToColumn(col);
    }
  }

  private void applyGravityToColumn(int col) {
    for (int row = length() - 1; row >= 0; row--) {
      if (pieceAt(row, col) == null) {
        int fallDistance = countEmptySpaces(row, col);
        if (fallDistance > 0) {
          int newRow = row + fallDistance;
          swapPieces(row, col, newRow, col);
        }
      }
    }
  }

  private int countEmptySpaces(int row, int col) {
    int count = 0;
    for (int i = row + 1; i < length(); i++) {
      if (pieceAt(i, col) == null) {
        count++;
      }
    }
    return count;
  }

  private void swapPieces(int fromRow, int fromCol, int toRow, int toCol) {
    MyPiece temp = pieceAt(fromRow, fromCol);
    board.get(fromRow).set(fromCol, pieceAt(toRow, toCol));
    board.get(toRow).set(toCol, temp);
  }
}