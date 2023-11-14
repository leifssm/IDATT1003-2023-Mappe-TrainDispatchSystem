package edu.ntnu.stud.Menu;

import edu.ntnu.stud.InputParser;

import java.util.ArrayList;

public class Menu {
  private final String name;

  private final ArrayList<MenuItem> entries = new ArrayList();

  private Runnable beforeAction;

  public Menu(String name) {
    this.name = name;
  }

  public Menu addOption(String name, Runnable action) {
    MenuItem item = new MenuItem(name, action);
    entries.add(item);
    return this;
  }

  public Menu setRunBefore(Runnable beforeAction) {
    this.beforeAction = beforeAction;
    return this;
  }

  public void run() {
    while (true) {
      if (beforeAction != null) {
        beforeAction.run();
      }
      System.out.println(name);
      for (int i = 0; i < entries.size(); i++) {
        final MenuItem item = entries.get(i);
        System.out.printf("%d: %s\n", i + 1, item);
      }
      System.out.println();
      final int choice = InputParser.getInt("Valg", n -> 1 <= n && n <= entries.size()) - 1;
      final MenuItem item = entries.get(choice);
      System.out.printf("\n ══ Valgte \"%s\" ══\n", item);
      item.run();
    }
  }
}
