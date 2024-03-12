package cs3500.samegame.model.hw04;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs3500.samegame.model.hw02.MyPiece;
import cs3500.samegame.model.hw02.SameGameModel;

/**
 * Represents an abstract class for the SameGame model, providing common functionality
 * shared among different game variants.
 */
public abstract class AbstractSameGame implements SameGameModel<MyPiece> {
  protected boolean gameStarted;
  public List<List<MyPiece>> board;
  int swaps;
  int score;


  /**
   * Constructs an AbstractSameGame with default settings.
   */
  public AbstractSameGame() {
    this.gameStarted = false;
    this.board = new ArrayList<List<MyPiece>>();
    this.swaps = 0;
    this.score = 0;
  }

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
  @Override
  public void swap(int fromRow, int fromCol, int toRow, int toCol) {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started yet");
    }

    if (this.gameOver()) {
      throw new IllegalStateException("Game is over");
    }

    if (fromRow < 0 || fromRow >= board.size() || fromCol < 0 || fromCol >= board.get(fromRow)
            .size() ||
            toRow < 0 || toRow >= board.size() || toCol < 0 || toCol >= board.get(toRow).size()) {
      throw new IllegalArgumentException("Given coordinates are out of bounds");
    }

    if (board.get(fromRow).get(fromCol) == null && board.get(toRow).get(toCol) == null) {
      throw new IllegalArgumentException("Both coordinates are not on the board");
    }

    if (fromRow != toRow && fromCol != toCol) {
      throw new IllegalStateException("Pieces must be on same row or column");
    }

    if (fromRow == toRow && fromCol == toCol) {
      throw new IllegalStateException("A piece cannot swap with itself");
    }

    MyPiece temp = board.get(fromRow).get(fromCol);
    board.get(fromRow).set(fromCol, board.get(toRow).get(toCol));
    board.get(toRow).set(toCol, temp);

    this.swaps--;
  }

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
  @Override
  public void removeMatch(int row, int col) {
    if (this.gameOver()) {
      throw new IllegalStateException("Game is over.");
    } else if (row < 0 || row >= this.board.size() || col < 0
            || col >= this.board.get(row).size()) {
      throw new IllegalArgumentException("Row and col must be within bounds");
    } else if (this.board.get(row).get(col) == null) {
      throw new IllegalStateException("Piece cannot be null");
    }

    MyPiece pieceColor = this.board.get(row).get(col);

    boolean[][] visited = new boolean[this.board.size()][this.board.get(0).size()];
    int count = dfs(row, col, pieceColor, visited);

    if (count < 3) {
      throw new IllegalStateException("There must be at least three adjacent pieces for a match");
    } else {
      for (int i = 0; i < visited.length; i++) {
        for (int j = 0; j < visited[i].length; j++) {
          if (visited[i][j]) {
            this.board.get(i).set(j, null);
          }
        }
      }
    }

    this.score += calculateMatchScore(count);
  }

  protected int dfs(int row, int col, MyPiece pieceColor, boolean[][] visited) {
    if (row < 0 || row >= board.size() || col < 0 || col >= board.get(row).size()
            ||
            visited[row][col] || board.get(row).get(col) == null
            ||
            !board.get(row).get(col).getColor().equals(pieceColor.getColor())) {
      return 0;
    }
    visited[row][col] = true;
    int count = 1;

    // Recursively search in adjacent cells
    count += dfs(row - 1, col, pieceColor, visited); // Up
    count += dfs(row + 1, col, pieceColor, visited); // Down
    count += dfs(row, col - 1, pieceColor, visited); // Left
    count += dfs(row, col + 1, pieceColor, visited); // Right

    board.get(row).set(col, null);

    return count;
  }

  protected int calculateMatchScore(int count) {
    if (count < 3) {
      return 0;
    } else {
      return (count) - 2;
    }
  }

  /**
   * Returns the width of the board (also known as the number of rows).
   *
   * @return the number of rows in the board
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int width() {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game hasn't started.");
    }
    return this.board.get(0).size();
  }

  /**
   * Returns the length of the board (also known as the number of cols).
   *
   * @return the number of cols in the board
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int length() {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game hasn't started.");
    }
    return this.board.size();
  }

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
  @Override
  public MyPiece pieceAt(int row, int col) {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet");
    }

    if (row < 0 || row >= board.size() || col < 0 || col >= board.get(row).size()) {
      throw new IllegalArgumentException("Invalid row or column index");
    }

    return board.get(row).get(col);
  }


  /**
   * Returns the current score of the game is defined by the implementation of this game.
   *
   * @return the current score
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int score() {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game hasn't started.");
    }
    return this.score;
  }

  /**
   * Returns the number of swaps remaining in this game.
   *
   * @return the number of swaps remaining.
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int remainingSwaps() {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game hasn't started.");
    }
    return this.swaps;
  }

  /**
   * Returns true if and only if the game is over is defined by the implementation of this game.
   *
   * @return true if the game is over, false otherwise
   * @throws IllegalStateException if the game has not started
   */

  @Override
  public boolean gameOver() {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game hasn't started.");
    }

    for (List<MyPiece> row : board) {
      for (MyPiece piece : row) {
        if (piece != null) {
          return false; // Game is not over if there is at least one non-null piece
        }
      }
    }

    return true; // The board is empty, the game is over
  }

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
  @Override
  public void startGame(int rows, int cols, int swaps, boolean random) {
    if (this.gameStarted) {
      throw new IllegalArgumentException("Game has already started");
    } else if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Rows or columns must be greater than 2");
    }
    if (swaps < 0) {
      throw new IllegalArgumentException("number of swaps cant be negative");
    }

    intializeBoard(rows, cols, random);
    this.swaps = swaps;
    this.gameStarted = true;
  }

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

  @Override
  public void startGame(List<List<MyPiece>> board, int swaps) {
    if (this.gameStarted) {
      throw new IllegalStateException("Game has already started");
    }

    if (!isValidBoard(board)) {
      throw new IllegalArgumentException("Board is not represented correctly");
    }
    if (swaps < 0) {
      throw new IllegalArgumentException("Number of swaps cant be negative");
    }

    ArrayList<List<MyPiece>> clonedBoard = new ArrayList<>();

    for (int i = 0; i < board.size(); i++) {
      ArrayList<MyPiece> row = new ArrayList<>();
      for (int j = 0; j < board.get(i).size(); j++) {
        row.add(board.get(i).get(j));
      }
      clonedBoard.add(row);
    }

    this.gameStarted = true;
    this.swaps = swaps;
    this.board = clonedBoard;
  }

  /**
   * <p>Returns a new array of 4 new pieces in a particular order:
   * RED, GREEN, BLUE, and YELLOW. Clients can use this method to create their own custom boards
   * using the pieces understood by this game.</p>
   *
   * <p>Each call returns a new array.</p>
   *
   * @return array of exactly 4 different objects as specified above
   */
  @Override
  public MyPiece[] createListOfPieces() {
    return new MyPiece[]{MyPiece.RED, MyPiece.GREEN, MyPiece.BLUE, MyPiece.YELLOW};
  }

  protected void intializeBoard(int rows, int cols, boolean random) {
    List<List<MyPiece>> grid = new ArrayList<>();

    MyPiece[] pattern = createListOfPieces();
    Random rand = new Random();

    for (int i = 0; i < rows; i++) {
      List<MyPiece> row = new ArrayList<>();
      for (int j = 0; j < cols; j++) {
        int numColors = pattern.length;
        if (random) {
          row.add(pattern[rand.nextInt(numColors)]);
        } else {
          row.add(pattern[(i * cols + j) % numColors]);
        }
      }
      grid.add(row);
    }

    this.board = grid;
  }

  protected boolean isValidBoard(List<List<MyPiece>> board) {
    if (board == null || board.isEmpty()) {
      System.out.println("Board is null or empty.");
      return false;
    }

    int numRows = board.size();
    if (numRows <= 0) {
      System.out.println("Number of rows is not greater than 0.");
      return false;
    }

    int numCols = board.get(0).size();
    if (numCols <= 0) {
      System.out.println("Number of columns is not greater than 0.");
      return false;
    }

    for (List<MyPiece> row : board) {
      if (row == null || row.size() != numCols) {
        System.out.println("Invalid row detected.");
        return false;
      }
    }

    return true;
  }
}
