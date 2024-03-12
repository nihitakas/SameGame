import cs3500.samegame.controller.SameGameTextController;
import cs3500.samegame.model.hw02.MockSameGameModel;

import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for the {@link SameGameTextController} class.
 */
public class ControllerTests {
  @Test
  public void testConstructor_NullArguments() {
    try {
      new SameGameTextController<>(null, new StringWriter());
      fail("Expected IllegalArgumentException for null Readable");
    } catch (IllegalArgumentException e) {
      //thrown
    }

    try {
      new SameGameTextController<>(new StringReader(""), null);
      fail("Expected IllegalArgumentException for null Appendable");
    } catch (IllegalArgumentException e) {
      //thrown
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_NullReadable() {
    new SameGameTextController<>(null, new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_NullAppendable() {
    new SameGameTextController<>(new StringReader(""), null);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayGame_ModelNull() {
    SameGameTextController<Integer> controller = new SameGameTextController<>(
            new StringReader(""), new StringWriter());
    controller.playGame(null, 3, 3, 10, true);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayGame_ModelNullInitBoard() {
    SameGameTextController<Integer> controller = new SameGameTextController<>(
            new StringReader(""), new StringWriter());
    controller.playGame(null, Arrays.asList(), 10);
  }

  @Test
  public void testPlayGame() {
    String input = "s\n1\n1\n1\n1\n";
    StringReader reader = new StringReader(input);
    StringWriter output = new StringWriter();

    MockSameGameModel<Integer> model = new MockSameGameModel<>(3, 3);
    SameGameTextController<Integer> controller = new SameGameTextController<>(reader, output);

    controller.playGame(model, 3, 3, 5, false);

    assertEquals("Remaining swaps: 5\nScore: 0\n", output.toString());
  }

  @Test
  public void testPlayGameInvalidMove() {
    String input = "s\n1\n1\n1\n1\n";
    StringReader reader = new StringReader(input);
    StringWriter output = new StringWriter();

    MockSameGameModel<Integer> model = new MockSameGameModel<>(3, 3);
    SameGameTextController<Integer> controller = new SameGameTextController<>(reader, output);

    controller.playGame(model, 3, 3, 5, false);

    assertTrue(output.toString().contains("Invalid move"));
  }

  @Test
  public void testPlayGame_GameQuit() {
    String input = "q\n";
    StringReader reader = new StringReader(input);
    StringWriter output = new StringWriter();

    MockSameGameModel<Integer> model = new MockSameGameModel<>(3, 3);
    SameGameTextController<Integer> controller = new SameGameTextController<>(reader, output);

    controller.playGame(model, 3, 3, 5, false);

    assertTrue(output.toString().contains("Game quit!"));
  }


}
