package cs3500.samegame.model.hw02;

import java.util.List;

/**
 * The operations and observations necessary for a game of FunGame. These methods are shared amongst
 * all different implementations of the game, but the specific behaviors of a few of them depend on
 * the type of FunGame being implemented and must be clarified in that implementation.
 *
 * @param <T> the type representing the pieces in the game
 */
public interface SameGameModel<T> {

  /**
   * Swaps the piece at (fromRow, fromCol) and (toRow, toCol).
   *
   * <p>Note that at least one of those coordinates must have an
   * actual piece to be a legal swap.</p>
   *
   * <p>If the swap results in an existing piece being above missing ones,
   * the implementation of the game decides what happens and that behavior must be mentioned
   * there.</p>
   *
   * <p>Aside from the above, the legality of the swap coordinates depends
   * on the implementation of the game.</p>
   *
   * <p>The number of swaps remaining is then decreased.</p>
   *
   * @param fromRow the row of the first piece (0-based index)
   * @param fromCol the col of the first piece (0-based index)
   * @param toRow   the row of the first piece (0-based index)
   * @param toCol   the col of the first piece (0-based index)
   * @throws IllegalStateException    if the game is over
   * @throws IllegalArgumentException if any argument is out-of-bounds
   * @throws IllegalArgumentException if both coordinates have no pieces on the board
   * @throws IllegalArgumentException if the swap coordinates are illegal according to the game
   * @throws IllegalStateException    if there are no swaps remaining
   */
  void swap(int fromRow, int fromCol, int toRow, int toCol);

  /**
   * Starting from the piece at (row, col), removes all piece of the same color as long as they are
   * connected to this piece by some number of pieces in cardinal directions. These connected pieces
   * of the same color are called a "matching block".
   *
   * <p>If the match results in existing pieces above missing ones,
   * the implementation of the game decides what happens then. This must be mentioned in that
   * implementation.</p>
   *
   * <p>The implementation of the game determines any other rules for a match
   * to be legal.</p>
   *
   * @param row the row of the piece to remove (0-based index)
   * @param col the col of the piece to remove (0-based index)
   * @throws IllegalStateException    if the game is over
   * @throws IllegalArgumentException if either argument is out-of-bounds
   * @throws IllegalStateException    if the coordinate has no piece
   * @throws IllegalStateException    if the matching block chosen is invalid according to the rules
   *                                  of the game
   */
  void removeMatch(int row, int col);

  /**
   * Returns the width of the board (also known as the number of rows).
   *
   * @return the number of rows in the board
   * @throws IllegalStateException if the game has not started
   */
  int width();

  /**
   * Returns the length of the board (also known as the number of cols).
   *
   * @return the number of cols in the board
   * @throws IllegalStateException if the game has not started
   */
  int length();

  /**
   * Returns the piece at the coordinate (row, col). Returns @code{null} if no piece is at that
   * coordinate.
   *
   * @param row the row in the board (0-based index)
   * @param col the col in the board (0-based index)
   * @return the piece at the given coordinate or @code{null} if nothing is there
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalArgumentException if either argument is out-of-bounds
   */
  T pieceAt(int row, int col);

  /**
   * Returns the current score of the game is defined by the implementation of this game.
   *
   * @return the current score
   * @throws IllegalStateException if the game has not started
   */
  int score();

  /**
   * Returns the number of swaps remaining in this game.
   *
   * @return the number of swaps remaining.
   * @throws IllegalStateException if the game has not started
   */
  int remainingSwaps();

  /**
   * Returns true if and only if the game is over is defined by the implementation of this game.
   *
   * @return true if the game is over, false otherwise
   * @throws IllegalStateException if the game has not started
   */
  boolean gameOver();

  /**
   * Starts the game with a board with the specified number of rows and columns. The game allows the
   * specified number of legal swaps until swaps fail. The random variable dictates whether the
   * board is generated deterministically or randomly.
   *
   * <p>The deterministic approach is based on
   * the variation of SameGame implemented and should be documented there.</p>
   *
   * @param rows   the number of rows in the board
   * @param cols   the number of columns in the board
   * @param swaps  the number of swaps allowed in the game
   * @param random true if the board should be setup randomly
   * @throws IllegalStateException    if the game has already started
   * @throws IllegalArgumentException if rows or cols are less than or equal to 0
   * @throws IllegalArgumentException if swaps is negative
   */
  void startGame(int rows, int cols, int swaps, boolean random);

  /**
   * <p>Starts the game with the given board, including all pieces within.
   * Missing pieces in the given board are represented with @code{null} (to account for any
   * implementation of the piece type T). The game allows the specified number of legal swaps until
   * swaps fail.</p>
   *
   * <p>The expected representation of the given board:
   * <ul>
   * <li>The outer list is a non-null, non-empty list that stores
   * non-null, non-empty lists.</li>
   * <li>The inner lists represent rows of the board.</li>
   * <li>Index 0 of the outer list is the top row of board.</li>
   * <li>The length of the outer list is the number of rows.</li>
   * <li>The length of the inner list (a row) is the number of columns.</li>
   * <li>Finally, every row of this list must have the same length.</li>
   * </ul></p>
   *
   * <p>The board parameter must not be edited by the model in anyway. Nor
   * should the user be able to affect the game by manipulating this argument.</p>
   *
   * <p>If there are missing pieces in the given board (represented by @code{null}),
   * the implementation decides what occurs to any existing floating pieces.
   * That must be documented in that implementation.</p>
   *
   * @param board the board to start the game with
   * @param swaps the number of swaps
   * @throws IllegalStateException    if the game has already started
   * @throws IllegalArgumentException if board is null or empty or an invalid representation
   * @throws IllegalArgumentException if individual rows or columns of the board have different
   *                                  lengths (a non-rectangular board)
   * @throws IllegalArgumentException if swaps is negative
   */
  void startGame(List<List<T>> board, int swaps);

  /**
   * <p>Returns a new array of 4 new pieces in a particular order:
   * RED, GREEN, BLUE, and YELLOW. Clients can use this method to create their own custom boards
   * using the pieces understood by this game.</p>
   *
   * <p>Each call returns a new array.</p>
   *
   * @return array of exactly 4 different objects as specified above
   */
  T[] createListOfPieces();

}
