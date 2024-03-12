package cs3500.samegame;

import cs3500.samegame.model.hw02.MyPiece;
import cs3500.samegame.model.hw02.SameGameModel;
import cs3500.samegame.model.hw04.SameGameCreator;

/**
 * Represents the entry point for running SameGame with different game types.
 */
public class SameGame {

  /**
   * Main method for running SameGame with different game types.
   *
   * @param args command-line arguments specifying the game type and optional parameters
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("No arguments provided");
      return;
    }

    String gameTypeArg = args[0].toLowerCase();
    SameGameModel<MyPiece> game = null;

    switch (gameTypeArg) {
      case "fourpiece":
        game = createGame(args, SameGameCreator.GameType.FOURPIECE);
        break;
      case "gravity":
        game = createGame(args, SameGameCreator.GameType.GRAVITY);
        break;
      case "automatch":
        game = createGame(args, SameGameCreator.GameType.AUTOMATCH);
        break;
      default:
        System.out.println("Invalid game type");
        return;
    }

    if (game != null) {
      System.out.println(game);
    }
  }

  /**
   * Creates a game model based on the provided game type and command-line arguments.
   *
   * @param args     command-line arguments
   * @param gameType the type of game to create
   * @return the created game model
   */
  private static SameGameModel<MyPiece> createGame(String[] args, SameGameCreator.GameType
          gameType) {
    int rows = 10; // default number of rows
    int cols = 10; // default number of columns
    int swaps = 10; // default number of swaps

    if (gameType != SameGameCreator.GameType.FOURPIECE && args.length >= 4) {
      try {
        rows = Integer.parseInt(args[1]);
        cols = Integer.parseInt(args[2]);
        swaps = Integer.parseInt(args[3]);
      } catch (NumberFormatException e) {
        System.out.println("Invalid arguments for board size and swaps");
        return null;
      }
    } else if (args.length != 1) {
      System.out.println("Invalid number of arguments");
      return null;
    }

    try {
      return SameGameCreator.createGame(gameType);
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid game type");
      return null;
    }
  }
}