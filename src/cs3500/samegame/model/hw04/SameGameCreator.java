package cs3500.samegame.model.hw04;

import cs3500.samegame.model.hw02.FourPieceSameGame;
import cs3500.samegame.model.hw02.MyPiece;
import cs3500.samegame.model.hw02.SameGameModel;

/**
 * Represents the entry point for running SameGame with different game types.
 */
public class SameGameCreator {

  /**
   * Enumeration of supported game types.
   */
  public enum GameType {
    FOURPIECE, GRAVITY, AUTOMATCH
  }

  /**
   * Main method for running SameGame with different game types.
   *
   * @param type command-line arguments specifying the game type and optional parameters
   */
  public static SameGameModel<MyPiece> createGame(GameType type) {
    switch (type) {
      case FOURPIECE:
        return new FourPieceSameGame();
      case GRAVITY:
        return new GravitySameGame();
      case AUTOMATCH:
        return new AutoMatchSameGame();
      default:
        throw new IllegalArgumentException("Invalid game type");
    }
  }
}
