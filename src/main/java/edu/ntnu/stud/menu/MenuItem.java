package edu.ntnu.stud.menu;

import org.jetbrains.annotations.NotNull;

public class MenuItem {
  private final @NotNull String name;

  private final @NotNull Runnable action;

  MenuItem(@NotNull String name, @NotNull Runnable action) {
    this.name = name;
    this.action = action;
  }

  public @NotNull String toString() {
    return name;
  }

  public void run() {
    action.run();
  }
}
