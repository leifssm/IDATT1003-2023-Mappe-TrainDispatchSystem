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
    MenuItem item = new MenuItem(name, action);
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
    this.afterAction = afterAction;
    return this;
  }

  /**
   * Print the menu to the console.
   */
  public void print() {
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
    if (beforeAction != null) {
      beforeAction.run();
    }
    print();
    String error = "The number must be bigger than 1";
    if (entries.size() > 1) {
      error = String.format("The number must be between 1 and %d", entries.size());
    }
    final int choice = InputParser.getInt(
        "Option",
        n -> 1 <= n && n <= entries.size(),
        error
    ) - 1;
    final MenuItem item = entries.get(choice);
    System.out.printf("\n ══ Picked option \"%s\" ══\n", item);
    item.run();
    if (afterAction != null) {
      afterAction.run();
    }
  }

  /**
   * Prints the menu and runs the selected option until the program is terminated.
   */
  public void run() {
    while (true) {
      runOnce();
    }
  }
}
