package cs3500.samegame.model.hw02;

import java.util.Objects;


/**
 * Implementation of the SameGameModel interface for a game with four different pieces.
 */
public enum MyPiece {
  RED("R"),
  BLUE("B"),
  GREEN("G"),
  YELLOW("Y");

  private final String color;

  MyPiece(String color) {
    this.color = Objects.requireNonNull(color, "Color cannot be null");
  }

  public String getColor() {
    return color;
  }

  @Override
  public String toString() {
    return color;
  }
}
