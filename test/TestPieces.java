import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cs3500.samegame.model.hw02.FourPieceSameGame;
import cs3500.samegame.model.hw02.MyPiece;
import cs3500.samegame.model.hw02.SameGameModel;
import cs3500.samegame.view.SameGameTextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link FourPieceSameGame} class and its methods.
 */
public class TestPieces {

  private FourPieceSameGame game;

  @Before
  public void setUp() {
    game = new FourPieceSameGame();
  }

  @Test
  public void testStartGameWithRowsColsAndSwaps() {
    game.startGame(5, 5, 10, true);
    assertEquals(5, game.length());
    assertEquals(5, game.width());
    assertEquals(10, game.remainingSwaps());
  }

  @Test
  public void testStartGameWithBoardAndSwaps() {
    List<List<MyPiece>> board = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      List<MyPiece> row = new ArrayList<>();
      for (int j = 0; j < 5; j++) {
        row.add(MyPiece.RED);
      }
      board.add(row);
    }
    game.startGame(board, 10);
    assertEquals(5, game.length());
    assertEquals(5, game.width());
    assertEquals(10, game.remainingSwaps());
  }

  @Test
  public void testSwapPieces() {
    List<List<MyPiece>> board = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      List<MyPiece> row = new ArrayList<>();
      for (int j = 0; j < 5; j++) {
        row.add(MyPiece.RED);
      }
      board.add(row);
    }
    game.startGame(board, 10);

    game.swap(0, 0, 0, 1);

    assertEquals(MyPiece.RED, game.pieceAt(0, 0));
    assertEquals(MyPiece.RED, game.pieceAt(0, 1));
    assertFalse(game.pieceAt(0, 0) == game.pieceAt(0, 1));
  }

  @Test
  public void testLength() {
    game.startGame(5, 5, 10, false);
    assertEquals(5, game.length());
  }

  @Test
  public void testWidth() {
    game.startGame(5, 5, 10, false);
    assertEquals(5, game.width());
  }

  @Test
  public void testBuildsBoard() {
    FourPieceSameGame game = new FourPieceSameGame();
    SameGameTextView<MyPiece> sameGameTextView = new SameGameTextView<>(game);

    ArrayList<MyPiece> row1 = new ArrayList<>();
    row1.add(MyPiece.RED);
    row1.add(MyPiece.BLUE);
    row1.add(MyPiece.YELLOW);

    ArrayList<MyPiece> row2 = new ArrayList<>();
    row2.add(MyPiece.GREEN);
    row2.add(MyPiece.GREEN);
    row2.add(null);

    ArrayList<MyPiece> row3 = new ArrayList<>();
    row3.add(MyPiece.GREEN);
    row3.add(MyPiece.GREEN);
    row3.add(MyPiece.YELLOW);

    List<List<MyPiece>> board = new ArrayList<>();
    board.add(row1);
    board.add(row2);
    board.add(row3);

    game.startGame(board, 3);
    String expected = "R B Y\n" +
            "G G X\n" +
            "G G Y";

    assertEquals(expected, sameGameTextView.toString());
  }

  @Test
  public void testCreateEmptyBoard() {
    SameGameModel<MyPiece> game = new FourPieceSameGame();
    game.startGame(5, 5, 10, true);
    assertEquals(5, game.width());
    assertEquals(5, game.length());
  }

  @Test
  public void testRemoveMatch() {
    SameGameModel<MyPiece> game = new FourPieceSameGame();
    game.startGame(5, 5, 10, true);
    game.removeMatch(2, 2);
    assertEquals(0, game.remainingSwaps());
  }

  @Test
  public void testGameOver() {
    FourPieceSameGame game = new FourPieceSameGame();
    assertTrue(game.gameOver()); // Game hasn't started yet

    game.startGame(3, 3, 5, true);
    assertFalse(game.gameOver()); // Game started with pieces

    game.removeMatch(0, 0);
    assertFalse(game.gameOver()); // Pieces removed, but more remain

    game.removeMatch(0, 1);
    game.removeMatch(0, 2);
    assertTrue(game.gameOver()); // All pieces removed, game over
  }

  @Test
  public void testSwap() {
    FourPieceSameGame game = new FourPieceSameGame();
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, MyPiece.GREEN, MyPiece.RED),
            Arrays.asList(MyPiece.GREEN, MyPiece.BLUE, MyPiece.YELLOW),
            Arrays.asList(MyPiece.RED, MyPiece.GREEN, MyPiece.BLUE)
    );
    game.startGame(initialBoard, 10);

    // Perform a valid swap
    game.swap(0, 0, 1, 0);
    assertEquals(MyPiece.RED, game.pieceAt(1, 0));
    assertEquals(MyPiece.GREEN, game.pieceAt(0, 0));

    // Perform an invalid swap (pieces not on the same row or column)
    try {
      game.swap(0, 0, 1, 1);
    } catch (IllegalStateException e) {
      // Expected exception
    }
  }

  @Test
  public void testIllegalSwap() {
    FourPieceSameGame game = new FourPieceSameGame();
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, MyPiece.GREEN, MyPiece.RED),
            Arrays.asList(MyPiece.GREEN, MyPiece.BLUE, MyPiece.YELLOW),
            Arrays.asList(MyPiece.RED, MyPiece.GREEN, MyPiece.BLUE)
    );
    game.startGame(initialBoard, 10);

    // swap with itself
    IllegalStateException exception = null;
    try {
      game.swap(0, 0, 0, 0);
    } catch (IllegalStateException e) {
      exception = e;
    }

    // Assert that the expected exception was thrown
    assertEquals("Expected IllegalStateException", IllegalStateException.class,
            exception.getClass());
  }

  @Test
  public void testStartGameWithInvalidRowsCols() {
    FourPieceSameGame game = new FourPieceSameGame();

    // start the game with 0 rows and 4 columns
    IllegalArgumentException exception1 = null;
    try {
      game.startGame(0, 4, 5, true);
    } catch (IllegalArgumentException e) {
      exception1 = e;
    }

    // Assert that the expected exception was thrown
    assertEquals("Expected IllegalArgumentException", IllegalArgumentException.class,
            exception1.getClass());

    // Attempt to start the game with 3 rows and 0 columns
    IllegalArgumentException exception2 = null;
    try {
      game.startGame(3, 0, 5, true);
    } catch (IllegalArgumentException e) {
      exception2 = e;
    }
  }

  @Test
  public void testStartGameWithNegativeSwaps() {
    FourPieceSameGame game = new FourPieceSameGame();

    // Attempt to start the game with negative swaps
    IllegalArgumentException exception = null;
    try {
      game.startGame(3, 4, -5, true);
    } catch (IllegalArgumentException e) {
      exception = e;
    }

    // Assert that the expected exception was thrown
    assertEquals("Expected IllegalArgumentException", IllegalArgumentException.class,
            exception.getClass());
  }

  @Test
  public void testPieceAt() {
    FourPieceSameGame game = new FourPieceSameGame();
    game.startGame(3, 3, 5, true);

    assertNull(game.pieceAt(0, 0)); // Empty space
    assertNotNull(game.pieceAt(1, 1)); // Non-empty space
  }

  @Test
  public void testScore() {
    FourPieceSameGame game = new FourPieceSameGame();
    game.startGame(3, 3, 5, true);

    assertEquals(0, game.score()); // Initial score

    // Remove a matching block to increase score
    game.removeMatch(0, 0);
    assertEquals(1, game.score());

    // Remove another matching block to increase score
    game.removeMatch(1, 1);
    assertEquals(3, game.score()); // Two blocks removed, score increased by 2
  }

  @Test
  public void testRemainingSwaps() {
    FourPieceSameGame game = new FourPieceSameGame();
    game.startGame(3, 3, 5, true);

    assertEquals(5, game.remainingSwaps()); // Initial swaps

    game.swap(0, 0, 1, 1);
    assertEquals(4, game.remainingSwaps()); // Swapped once, decreased by 1
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSwapCoordinates() {
    FourPieceSameGame game = new FourPieceSameGame();
    game.startGame(3, 3, 5, true);
    game.swap(0, 0, 3, 3); // Should throw IllegalArgumentException
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRemoveMatchCoordinates() {
    FourPieceSameGame game = new FourPieceSameGame();
    game.startGame(3, 3, 5, true);
    game.removeMatch(3, 3); // Should throw IllegalArgumentException
  }
}

