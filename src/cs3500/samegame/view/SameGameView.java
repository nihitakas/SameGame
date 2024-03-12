package cs3500.samegame.view;

import java.io.IOException;

/**
 * View for the game. Focuses on representing the state of the game as a board with pieces.
 *
 * @param <T> the type representing the pieces in the game
 */
public interface SameGameView<T> {

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   * @throws IOException if the rendering fails for some reason
   */
  void render() throws IOException;

}
