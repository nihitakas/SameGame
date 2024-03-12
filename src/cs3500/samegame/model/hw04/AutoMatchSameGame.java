package cs3500.samegame.model.hw04;

import cs3500.samegame.model.hw02.MyPiece;

/**
 * Represents a variation of the SameGame implementation where matches are automatically removed
 * after each swap or removal operation.
 */
public class AutoMatchSameGame extends GravitySameGame {

  @Override
  public void swap(int fromRow, int fromCol, int toRow, int toCol) {
    super.swap(fromRow, fromCol, toRow, toCol);
    autoMatch();
  }

  @Override
  public void removeMatch(int row, int col) {
    super.removeMatch(row, col);
    autoMatch();
  }

  private void autoMatch() {
    boolean matchFound = true;
    while (matchFound) {
      matchFound = false;
      for (int row = 0; row < length(); row++) {
        for (int col = 0; col < width(); col++) {
          MyPiece currentPiece = pieceAt(row, col);
          if (currentPiece != null) {
            int count = dfs(row, col, currentPiece, new boolean[length()][width()]);
            if (count >= 3) {
              removeMatch(row, col);
              score += count - 2;
              matchFound = true;
              break;
            }
          }
        }
        if (matchFound) {
          break;
        }
      }
    }
  }
}
