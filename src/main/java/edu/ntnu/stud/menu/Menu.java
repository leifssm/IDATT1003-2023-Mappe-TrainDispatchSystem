package edu.ntnu.stud.menu;

import edu.ntnu.stud.InputParser;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Menu {
  private final @NotNull String name;

  private final @NotNull List<MenuItem> entries = new ArrayList();

  private Runnable beforeAction;
  private Runnable afterAction;

  public Menu(@NotNull String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    this.name = name;
  }

  public Menu addOption(
      @NotNull String name,
      @NotNull Runnable action
  ) throws IllegalArgumentException {
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

  public Menu setRunBefore(Runnable beforeAction) {
    this.beforeAction = beforeAction;
    return this;
  }

  public Menu setRunAfter(Runnable afterAction) {
    this.afterAction = afterAction;
    return this;
  }

  public void print() {
    System.out.println("══ " + name + " ══");
    for (int i = 0; i < entries.size(); i++) {
      final MenuItem item = entries.get(i);
      System.out.printf("%d: %s\n", i + 1, item);
    }
    System.out.println();
  }

  public void runOnce() {
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
  public void run() {
    while (true) {
      runOnce();
    }
  }
}
