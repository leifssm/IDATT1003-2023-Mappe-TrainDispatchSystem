package edu.ntnu.stud.Menu;

public class MenuItem {
  private final String name;

  private final Runnable action;

  MenuItem(String name, Runnable action) {
    this.name = name;
    this.action = action;
  }

  public String toString() {
    return name;
  }

  public void run() {
    action.run();
  }
}
