package edu.ntnu.stud.menu;

import org.jetbrains.annotations.NotNull;

public class MenuItem {
  private final @NotNull String name;

  private final @NotNull Runnable action;

  MenuItem(@NotNull String name, @NotNull Runnable action) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (action == null) {
      throw new IllegalArgumentException("Action cannot be null");
    }
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