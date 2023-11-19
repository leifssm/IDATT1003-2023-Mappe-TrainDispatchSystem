package edu.ntnu.stud.Menu;

import edu.ntnu.stud.InputParser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Menu {
  private final @NotNull String name;

  private final @NotNull ArrayList<MenuItem> entries = new ArrayList();

  private Runnable beforeAction;
  private Runnable afterAction;

  public Menu(@NotNull String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    this.name = name;
  }

  public Menu addOption(@NotNull String name, @NotNull Runnable action) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (action == null) {
      throw new IllegalArgumentException("Action cannot be null");
    }
    MenuItem item = new MenuItem(name, action);
    entries.add(item);
    return this;
  }

  public Menu setRunBefore(@NotNull Runnable beforeAction) {
    if (beforeAction == null) {
      throw new IllegalArgumentException("Action cannot be null");
    }
    this.beforeAction = beforeAction;
    return this;
  }

  public Menu setRunAfter(Runnable afterAction) {
    if (afterAction == null) {
      throw new IllegalArgumentException("Action cannot be null");
    }
    this.afterAction = afterAction;
    return this;
  }

  public void print() {
    System.out.println(name);
    for (int i = 0; i < entries.size(); i++) {
      final MenuItem item = entries.get(i);
      System.out.printf("%d: %s\n", i + 1, item);
    }
    System.out.println();
  }
  public void run() {
    while (true) {
      if (beforeAction != null) {
        beforeAction.run();
      }
      print();
      String error = "Tallet må være 1";
      if (entries.size() > 1) {
        // TODO fix all %d
        error = String.format("Tallet må være mellom 1 og %d", entries.size());
      }
      final int choice = InputParser.getInt(
          "Valg",
          n -> 1 <= n && n <= entries.size(),
          error
      ) - 1;
      final MenuItem item = entries.get(choice);
      System.out.printf("\n ══ Valgte \"%s\" ══\n", item);
      item.run();
      if (afterAction != null) {
        afterAction.run();
      }
    }
  }
}
