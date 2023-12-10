package edu.ntnu.stud.menu;

import org.jetbrains.annotations.NotNull;
import org.junit.platform.commons.util.ToStringBuilder;

/**
 * <h1>MenuItem.</h1>
 * <p>
 *   An immutable package-private class that's used from {@link edu.ntnu.stud.menu.Menu}. Represents
 *   a labeled runnable function.
 * </p>
 * <br>
 * <h2>Role and Responsibility:</h2>
 * <p>
 *   This class is responsible for creating being a container for a labeled {@link Runnable} action,
 *   and nothing else. The class {@link edu.ntnu.stud.menu.Menu} contains all of the functionality.
 * </p>
 *
 * @see edu.ntnu.stud.menu.Menu
 */
class MenuItem {
  /**
   * The name of the menu item. A non-null immutable string because the class is immutable.
   */
  private final @NotNull String name;

  /**
   * The {@link Runnable} action to run when the menu item is selected. It is immutable and
   * non-null.
   */
  private final @NotNull Runnable action;

  /**
   * Create a new immutable menu item with the given name and action. There is no criteria for the
   * parameters other than that neither the name nor the action can be null. It trows if either of
   * the given parameters are null.
   *
   * @param name The name of the menu item.
   * @param action The action to run when the menu item is selected.
   * @throws IllegalArgumentException If the name or the action is null.
   */
  public MenuItem(@NotNull String name, @NotNull Runnable action) {
    // Set the name and action of the menu item.
    this.name = name;
    this.action = action;
  }

  /**
   * Returns the {@link MenuItem#name} of the menu item as a non-null string.
   *
   * @return The name of the menu item.
   * @see MenuItem#name
   */
  public @NotNull String getName() {
    return name;
  }

  /**
   * Run the associated {@link MenuItem#action} of the menu item.
   */
  public void run() {
    action.run();
  }

  /**
   * Returns a string representation of the object.
   *
   * @return A string representation of the object.
   */
  public @NotNull String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .toString();
  }
}