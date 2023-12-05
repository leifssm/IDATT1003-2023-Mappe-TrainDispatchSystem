package edu.ntnu.stud.menu;

import edu.ntnu.stud.InputParser;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * A helper object that makes creating a user interface easier.
 */
public class Menu {
  /**
   * The name of the menu, shows when the menu is shown.
   */
  private final @NotNull String name;

  /**
   * A list of options in the menu.
   */
  private final @NotNull List<MenuItem> entries = new ArrayList<>();

  /**
   * Action to run right before the menu is shown.
   */
  private Runnable beforeAction;

  /**
   * Action to run right after the menu is shown.
   */
  private Runnable afterAction;

  /**
   * Create a new menu with the given name.
   *
   * @param name The name of the menu.
   */
  public Menu(@NotNull String name) {
    this.name = name;
  }

  /**
   * Add a menu option.
   *
   * @param name The title of the option.
   * @param action The action associated to the option.
   * @return The {@code this} object.
   * @throws IllegalArgumentException If the name or the action is null.
   */
  public Menu addOption(
      @NotNull String name,
      @NotNull Runnable action
  ) throws IllegalArgumentException {
    // Creates a new MenuItem and adds it to its list.
    final MenuItem item = new MenuItem(name, action);
    entries.add(item);
    return this;
  }

  /**
   * Set an action to run before the menu is shown.
   *
   * @param beforeAction The action to run, set to null to remove.
   * @return The {@code this} object.
   */
  public Menu setRunBefore(Runnable beforeAction) {
    // Set the action to run before the menu is shown.
    this.beforeAction = beforeAction;
    return this;
  }

  /**
   * Set an action to run after the menu is shown.
   *
   * @param afterAction The action to run, set to null to remove.
   * @return The {@code this} object.
   */
  public Menu setRunAfter(Runnable afterAction) {
    // Set the action to run after the menu is shown.
    this.afterAction = afterAction;
    return this;
  }

  /**
   * Print the menu to the console.
   */
  public void print() {
    // Prints the title and entries to the console.
    System.out.println("══ " + name + " ══");
    for (int i = 0; i < entries.size(); i++) {
      final MenuItem item = entries.get(i);
      System.out.printf("%d: %s\n", i + 1, item);
    }
    System.out.println();
  }

  /**
   * Prints the menu and runs the selected option once.
   */
  public void runOnce() {
    if (entries.isEmpty()) {
      throw new IllegalStateException("The menu must have at least one option");
    }

    // Runs the before action if it is set.
    if (beforeAction != null) {
      beforeAction.run();
    }
    print();

    // Creates a relevant error message.
    final String error = entries.size() == 1
        ? "The number must be 1"
        : "The number must be between 1 and %d".formatted(entries.size());

    // Gets a valid validates the user input.
    final int choice = InputParser.getInt(
        "Option",
        n -> 1 <= n && n <= entries.size(),
        error
    ) - 1;

    // Runs the selected option.
    final MenuItem item = entries.get(choice);
    System.out.printf("\n ══ Picked option \"%s\" ══\n", item);
    item.run();

    // Runs the after action if it is set.
    if (afterAction != null) {
      afterAction.run();
    }
  }

  /**
   * Prints the menu and runs the selected option until the program is terminated.
   */
  public void run() {
    // Loops the menu until the program is terminated.
    while (true) {
      runOnce();
    }
  }
}
