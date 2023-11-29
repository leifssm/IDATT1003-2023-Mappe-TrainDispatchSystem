package edu.ntnu.stud.menu;

import org.jetbrains.annotations.NotNull;

/**
 * A menu item with a name and an associated action.
 */
class MenuItem {
  /**
   * The name of the menu item.
   */
  private final @NotNull String name;

  /**
   * The action to run when the menu item is selected.
   */
  private final @NotNull Runnable action;

  /**
   * Create a new menu item with the given name and action.
   *
   * @param name The name of the menu item.
   * @param action The action to run when the menu item is selected.
   * @throws IllegalArgumentException If the name or the action is null.
   */
  MenuItem(@NotNull String name, @NotNull Runnable action) {
    this.name = name;
    this.action = action;
  }

  /**
   * Returns the name of the menu item.
   *
   * @return The name of the menu item.
   */
  public @NotNull String toString() {
    return name;
  }

  /**
   * Run the action associated to the menu item.
   */
  public void run() {
    action.run();
  }
}