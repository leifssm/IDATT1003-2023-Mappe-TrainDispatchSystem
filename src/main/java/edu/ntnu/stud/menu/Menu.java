package edu.ntnu.stud.menu;

import edu.ntnu.stud.InputParser;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.platform.commons.util.ToStringBuilder;

/**
 * <h1>Menu</h1>
 * <p>A helper class that makes creating a user interface easier.</p>
 * <h2>Role and Responsibility:</h2>
 * <p>
 *   This class is responsible for creating a collection of {@link MenuItem}s using an
 *   {@link ArrayList}, to create an easier developer experience for creating menus. The menu can
 *   be assigned functions to run before the menu is shown and after an option is selected and run.
 *   After building the menu, with options using the {@link Menu#addOption(String, Runnable)}
 *   method, the {@link Menu#start} method can be called to start the menu.
 * </p>
 * @see MenuItem
 */
public class Menu {
  /**
   * The name of the menu, shows when the menu is shown. Cannot be null, and cannot be changed after
   * the menu is created, because it should never be necessary.
   */
  private final @NotNull String name;

  /**
   * An {@link ArrayList} of {@link MenuItem}s. The property is immutable, and cannot be accessed
   * directly. The data type is an {@link ArrayList} because the order of the items are important,
   * which an array is very useful for. An {@link ArrayList} was chosen over an Array because of
   * the easy usage the {@link ArrayList} provides.
   */
  private final @NotNull List<MenuItem> entries = new ArrayList<>();

  /**
   * A {@link Runnable} action to run right before the menu is shown. Can be set to null if no
   * action should be run before the menu is shown. The {@link Runnable} class was chosen because
   * it represents a function that takes no arguments and returns no value, which fits perfectly
   * because the action should have no effect on the menu's functionality.
   */
  private Runnable beforeAction;

  /**
   * A {@link Runnable} action to run right after the action associated to the selected option is
   * run. Can be set to null if no action should be run after the action is run. The
   * {@link Runnable} class was chosen for the same reason as in the {@link Menu#beforeAction}
   * method.
   * @see Menu#beforeAction
   */
  private Runnable afterAction;

  /**
   * Create a new menu with the given name. The given name becomes the menu title. There is no
   * criteria for the name, but it cannot be set as null.
   *
   * @param name The name of the menu.
   */
  public Menu(@NotNull String name) {
    this.name = name;
  }

  /**
   * Adds an option to the menu. Makes the associated action accessible through the menu to the
   * user. Neither the name nor the action can be null. Returns the {@code this} object to allow
   * the chaining of methods.
   *
   * @param name The title of the option.
   * @param action The action associated to the option.
   * @return The {@code this} object.
   * @throws IllegalArgumentException If the name or the action is null.
   */
  public @NotNull Menu addOption(
      @NotNull String name,
      @NotNull Runnable action
  ) throws IllegalArgumentException {
    // Creates a new MenuItem and adds it to its list.
    final MenuItem item = new MenuItem(name, action);
    entries.add(item);
    return this;
  }

  /**
   * Set the action to run before the menu is shown. The action is run every time right before the
   * menu is shown. The action can be set to null if no action should be run before the menu is
   * shown. Returns the {@code this} object to allow the chaining of methods.
   *
   * @param beforeAction The action to run, set to null to not have anything run before the menu.
   * @return The {@code this} object.
   */
  public @NotNull Menu setRunBefore(@Nullable Runnable beforeAction) {
    // Set the action to run before the menu is shown.
    this.beforeAction = beforeAction;
    return this;
  }

  /**
   * Set the action to run after the menu is shown. The action is run every time right after the
   * menu is shown. The action can be set to null if no action should be run after the menu is
   * shown. Returns the {@code this} object to allow the chaining of methods.
   *
   * @param afterAction The action to run, set to null to not have anything run after the menu.
   * @return The {@code this} object.
   */
  public @NotNull Menu setRunAfter(@Nullable Runnable afterAction) {
    // Set the action to run after the menu is shown.
    this.afterAction = afterAction;
    return this;
  }

  /**
   * Print the menu title, and all the options with their corresponding index to the console.
   */
  public void print() {
    // Prints the title and entries to the console.
    System.out.println("══ " + name + " ══");
    for (int i = 0; i < entries.size(); i++) {
      final MenuItem item = entries.get(i);
      System.out.printf("%d: %s\n", i + 1, item.getName());
    }
    System.out.println();
  }

  /**
   * If present, runs the runBefore {@link Runnable}, then prints the entire menu with the
   * {@link Menu#print} method. After printing, it waits for the user pick an option and runs the
   * corresponding action before running the runAfter {@link Runnable} if present.The runBefore and
   * runAfter actions are two separate actions to allow for more flexibility. If the menu has
   * no options, it throws an {@link IllegalStateException}.
   *
   * @throws IllegalStateException If the menu has no options.
   */
  public void runOnce() throws IllegalStateException {
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
        integer -> 1 <= integer && integer <= entries.size(),
        error
    ) - 1;

    // Runs the selected option.
    final MenuItem item = entries.get(choice);
    System.out.printf("\n ══ Picked option \"%s\" ══\n", item.getName());
    item.run();

    // Runs the after action if it is set.
    if (afterAction != null) {
      afterAction.run();
    }
  }

  /**
   * Runs the {@link Menu#runOnce} method in until the program is terminated. The
   * {@link Menu#runOnce} and this method are separate to allow for testing of the workflow.
   *
   * @throws IllegalStateException If the menu has no options.
   * @see Menu#runOnce
   */
  public void start() throws IllegalStateException{
    // Loops the menu until the program is terminated.
    while (true) {
      runOnce();
    }
  }

  /**
   * Returns a string representation of the object.
   *
   * @return A string representation of the object.
   */
  public @NotNull String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("beforeAction", beforeAction)
        .append("afterAction", afterAction)
        .append("entries", entries)
        .toString();
  }
}
