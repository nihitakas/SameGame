import org.junit.Before;
import org.junit.Test;

import cs3500.samegame.model.hw02.MyPiece;
import cs3500.samegame.model.hw04.AutoMatchSameGame;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * This class contains a set of JUnit tests for the AutoMatchSameGame and GravitySameGame
 * implementations.
 * These tests cover various scenarios related to auto-matching of pieces and the application of
 * gravity after swaps in the SameGame implementation.
 */
public class AutoAndGravityTests {
  private AutoMatchSameGame game;

  @Before
  public void setUp() {
    game = new AutoMatchSameGame();
  }

  @Test
  public void testAutoMatchAfterSwap() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, MyPiece.RED, MyPiece.RED, MyPiece.RED),
            Arrays.asList(MyPiece.BLUE, MyPiece.BLUE, MyPiece.BLUE, MyPiece.BLUE),
            Arrays.asList(MyPiece.GREEN, MyPiece.GREEN, MyPiece.GREEN, MyPiece.GREEN)
    );
    game.startGame(initialBoard, 10);

    game.swap(0, 0, 1, 0);

    assertFalse(game.pieceAt(0, 0) == MyPiece.RED);
    assertFalse(game.pieceAt(1, 0) == MyPiece.BLUE);
  }


  @Test
  public void testAutoMatchAfterMultipleSwaps() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, MyPiece.BLUE, MyPiece.RED, MyPiece.BLUE),
            Arrays.asList(MyPiece.BLUE, MyPiece.RED, MyPiece.BLUE, MyPiece.RED),
            Arrays.asList(MyPiece.RED, MyPiece.BLUE, MyPiece.RED, MyPiece.BLUE)
    );
    game.startGame(initialBoard, 10);

    game.swap(0, 0, 0, 1);
    game.swap(1, 0, 1, 1);

    assertFalse(game.pieceAt(0, 0) == MyPiece.RED);
    assertFalse(game.pieceAt(0, 1) == MyPiece.BLUE);
    assertFalse(game.pieceAt(1, 0) == MyPiece.BLUE);
    assertFalse(game.pieceAt(1, 1) == MyPiece.RED);
  }

  @Test
  public void testAutoMatchWithNoPossibleMatches() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, MyPiece.BLUE, MyPiece.RED, MyPiece.BLUE),
            Arrays.asList(MyPiece.BLUE, MyPiece.RED, MyPiece.BLUE, MyPiece.RED),
            Arrays.asList(MyPiece.RED, MyPiece.BLUE, MyPiece.RED, MyPiece.BLUE)
    );
    game.startGame(initialBoard, 10);

    game.swap(0, 0, 0, 1);
    game.removeMatch(2, 3);

    assertTrue(game.remainingSwaps() == 9);
  }

  @Test
  public void testAutoMatchWithFewSwapsRemaining() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, MyPiece.BLUE, MyPiece.RED, MyPiece.BLUE),
            Arrays.asList(MyPiece.BLUE, MyPiece.RED, MyPiece.BLUE, MyPiece.RED),
            Arrays.asList(MyPiece.RED, MyPiece.BLUE, MyPiece.RED, MyPiece.BLUE)
    );
    game.startGame(initialBoard, 3);

    game.swap(0, 0, 0, 1);
    game.swap(1, 0, 1, 1);
    game.swap(2, 0, 2, 1);

    assertFalse(game.gameOver());
  }

  @Test
  public void testApplyGravityAfterSwap() {
    // Set up the initial board
    game.startGame(3, 3, 10, false);
    game.board = Arrays.asList(
            Arrays.asList(MyPiece.RED, null, MyPiece.BLUE),
            Arrays.asList(MyPiece.GREEN, null, MyPiece.RED),
            Arrays.asList(MyPiece.BLUE, MyPiece.RED, MyPiece.GREEN)
    );

    game.swap(0, 0, 1, 0);

    assertNull(game.pieceAt(0, 0));
    assertEquals(MyPiece.RED, game.pieceAt(1, 0));
  }

  @Test
  public void testApplyGravityAfterStartGameWithEmptySpaces() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, null, MyPiece.BLUE),
            Arrays.asList(MyPiece.GREEN, null, MyPiece.RED),
            Arrays.asList(MyPiece.BLUE, null, MyPiece.GREEN)
    );
    game.startGame(initialBoard, 10);

    assertNull(game.pieceAt(0, 1));
    assertEquals(MyPiece.GREEN, game.pieceAt(2, 1));
  }

  @Test
  public void testNoGravityWhenNoEmptySpaces() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, MyPiece.RED, MyPiece.BLUE),
            Arrays.asList(MyPiece.GREEN, MyPiece.RED, MyPiece.RED),
            Arrays.asList(MyPiece.BLUE, MyPiece.GREEN, MyPiece.GREEN)
    );
    game.startGame(initialBoard, 10);

    game.swap(0, 0, 1, 0);

    assertEquals(MyPiece.RED, game.pieceAt(0, 0));
    assertEquals(MyPiece.GREEN, game.pieceAt(1, 0));
  }

  @Test
  public void testApplyGravityWithNoSwaps() {
    // Set up the initial board
    game.startGame(3, 3, 0, false);
    game.board = Arrays.asList(
            Arrays.asList(null, null, MyPiece.BLUE),
            Arrays.asList(null, null, MyPiece.RED),
            Arrays.asList(MyPiece.RED, MyPiece.RED, MyPiece.GREEN)
    );

    game.applyGravity();
    assertNull(game.pieceAt(0, 0));
    assertNull(game.pieceAt(1, 0));
  }

  @Test
  public void testAutoMatchAfterRemovingMatch() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, MyPiece.RED, MyPiece.RED, MyPiece.RED),
            Arrays.asList(MyPiece.BLUE, MyPiece.BLUE, MyPiece.BLUE, MyPiece.BLUE),
            Arrays.asList(MyPiece.GREEN, MyPiece.GREEN, MyPiece.GREEN, MyPiece.GREEN)
    );
    game.startGame(initialBoard, 10);

    game.removeMatch(0, 0); // Removing the match at (0,0)

    assertFalse(game.pieceAt(0, 0) == MyPiece.RED);
    assertFalse(game.pieceAt(1, 0) == MyPiece.BLUE);
  }

  @Test
  public void testAutoMatchAfterRemovingMultipleMatches() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, MyPiece.RED, MyPiece.RED, MyPiece.RED),
            Arrays.asList(MyPiece.BLUE, MyPiece.BLUE, MyPiece.BLUE, MyPiece.BLUE),
            Arrays.asList(MyPiece.GREEN, MyPiece.GREEN, MyPiece.GREEN, MyPiece.GREEN)
    );
    game.startGame(initialBoard, 10);

    game.removeMatch(0, 0); // Removing the match at (0,0)
    game.removeMatch(1, 0); // Removing the match at (1,0)

    assertFalse(game.pieceAt(0, 0) == MyPiece.RED);
    assertFalse(game.pieceAt(1, 0) == MyPiece.BLUE);
    assertFalse(game.pieceAt(2, 0) == MyPiece.GREEN);
  }

  @Test
  public void testApplyGravityAfterMultipleSwaps() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, MyPiece.RED, null),
            Arrays.asList(MyPiece.GREEN, MyPiece.GREEN, null),
            Arrays.asList(MyPiece.BLUE, MyPiece.BLUE, MyPiece.BLUE)
    );
    game.startGame(initialBoard, 10);

    game.swap(0, 0, 2, 0);
    game.swap(1, 0, 2, 1);

    assertNull(game.pieceAt(0, 0));
    assertNull(game.pieceAt(1, 0));
    assertNull(game.pieceAt(2, 0));
    assertEquals(MyPiece.RED, game.pieceAt(2, 1));
    assertEquals(MyPiece.GREEN, game.pieceAt(2, 2));
  }

  @Test
  public void testApplyGravityWithEmptyColumn() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, null, MyPiece.RED),
            Arrays.asList(MyPiece.GREEN, null, MyPiece.GREEN),
            Arrays.asList(MyPiece.BLUE, null, MyPiece.BLUE)
    );
    game.startGame(initialBoard, 10);

    game.applyGravity();

    assertNull(game.pieceAt(0, 1));
    assertNull(game.pieceAt(1, 1));
    assertNull(game.pieceAt(2, 1));
  }

  @Test
  public void testApplyGravityWithEntireColumnEmpty() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, null, MyPiece.RED),
            Arrays.asList(MyPiece.GREEN, null, MyPiece.GREEN),
            Arrays.asList(null, null, null)
    );
    game.startGame(initialBoard, 10);

    game.applyGravity();

    assertNull(game.pieceAt(0, 1));
    assertNull(game.pieceAt(1, 1));
    assertNull(game.pieceAt(2, 1));
  }

  @Test
  public void testApplyGravityWithEntireColumnFilled() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(null, MyPiece.RED, null),
            Arrays.asList(null, MyPiece.GREEN, null),
            Arrays.asList(null, MyPiece.BLUE, null)
    );
    game.startGame(initialBoard, 10);

    game.applyGravity();

    assertEquals(MyPiece.RED, game.pieceAt(0, 0));
    assertEquals(MyPiece.GREEN, game.pieceAt(1, 0));
    assertEquals(MyPiece.BLUE, game.pieceAt(2, 0));
  }

  @Test
  public void testApplyGravityWithMultipleEmptyColumns() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(null, MyPiece.RED, null),
            Arrays.asList(null, MyPiece.GREEN, null),
            Arrays.asList(null, MyPiece.BLUE, null)
    );
    game.startGame(initialBoard, 10);

    game.applyGravity();

    assertNull(game.pieceAt(0, 1));
    assertNull(game.pieceAt(1, 1));
    assertNull(game.pieceAt(2, 1));
  }

  @Test
  public void testAutoMatchWithSingleMatch() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, MyPiece.RED, MyPiece.RED, MyPiece.RED),
            Arrays.asList(MyPiece.BLUE, MyPiece.BLUE, MyPiece.BLUE, MyPiece.BLUE),
            Arrays.asList(MyPiece.GREEN, MyPiece.GREEN, MyPiece.GREEN, MyPiece.GREEN)
    );
    game.startGame(initialBoard, 10);

    game.removeMatch(0, 0);

    assertNull(game.pieceAt(0, 0));
    assertNull(game.pieceAt(0, 1));
    assertNull(game.pieceAt(0, 2));
    assertNull(game.pieceAt(0, 3));
  }

  @Test
  public void testAutoMatchWithMultipleMatches() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, MyPiece.RED, MyPiece.RED, MyPiece.RED),
            Arrays.asList(MyPiece.BLUE, MyPiece.BLUE, MyPiece.BLUE, MyPiece.BLUE),
            Arrays.asList(MyPiece.GREEN, MyPiece.GREEN, MyPiece.GREEN, MyPiece.GREEN)
    );
    game.startGame(initialBoard, 10);

    game.removeMatch(0, 0);

    assertNull(game.pieceAt(0, 0));
    assertNull(game.pieceAt(0, 1));
    assertNull(game.pieceAt(0, 2));
    assertNull(game.pieceAt(0, 3));

    game.removeMatch(1, 0);

    assertNull(game.pieceAt(1, 0));
    assertNull(game.pieceAt(1, 1));
    assertNull(game.pieceAt(2, 2));
    assertNull(game.pieceAt(3, 3));

    game.removeMatch(2, 0);

    assertNull(game.pieceAt(2, 0));
    assertNull(game.pieceAt(2, 1));
    assertNull(game.pieceAt(2, 2));
    assertNull(game.pieceAt(2, 3));
  }

  @Test
  public void testAutoMatchWithNoMatches() {
    List<List<MyPiece>> initialBoard = Arrays.asList(
            Arrays.asList(MyPiece.RED, MyPiece.RED, MyPiece.BLUE, MyPiece.RED),
            Arrays.asList(MyPiece.BLUE, MyPiece.BLUE, MyPiece.GREEN, MyPiece.BLUE),
            Arrays.asList(MyPiece.GREEN, MyPiece.GREEN, MyPiece.RED, MyPiece.GREEN)
    );
    game.startGame(initialBoard, 10);

    game.removeMatch(0, 0);

    assertTrue(game.remainingSwaps() == 10);
  }
}
