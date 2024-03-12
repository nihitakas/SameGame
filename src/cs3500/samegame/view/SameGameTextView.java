package cs3500.samegame.view;

import cs3500.samegame.model.hw02.MyPiece;
import cs3500.samegame.model.hw02.SameGameModel;
import java.io.IOException;

/**
 * Represents a text-based view of the SameGame.
 * @param <T> the type of the model
 */
public class SameGameTextView<T> implements SameGameView<MyPiece> {
  private final SameGameModel<T> model;
  private Appendable ap;

  public SameGameTextView(SameGameModel<T> model, Appendable ap) {
    this.model = model;
    this.ap = ap;
  }

  /**
   * Constructor for a text-based view for the SameGame.
   * @param model the SameGame model
   */
  public SameGameTextView(SameGameModel<T> model) {
    this.model = model;
  }

  /**
   * Returns a string representation of the SameGame board.
   * @return a string representation of the SameGame board
   */
  @Override
  public String toString() {
    String board = "";
    for (int i = 0; i < this.model.width(); i++) {
      for (int j = 0; j < this.model.length(); j++) {
        if (this.model.pieceAt(i, j) == null) {
          if (j == this.model.length() - 1) {
            board += "X";
          } else {
            board += "X ";
          }
        } else {
          if (j == this.model.length() - 1) {
            board += this.model.pieceAt(i, j) + "";
          } else {
            board += this.model.pieceAt(i, j) + " ";
          }
        }
        if (j == this.model.length() - 1 && i < this.model.width() - 1) {
          board += "\n";
        }
      }
    }

    return board;
  }

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   * @throws IOException if the rendering fails for some reason
   */
  @Override
  public void render() throws IOException {
    ap.append(this.toString() + "\n");
  }
}


