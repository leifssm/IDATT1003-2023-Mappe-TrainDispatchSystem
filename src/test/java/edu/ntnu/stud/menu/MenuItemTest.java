package edu.ntnu.stud.menu;

import edu.ntnu.stud.TestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {
  @Test
  void constructor() {
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new MenuItem(null, () -> {}),
        "Argument for @NotNull parameter 'name' of " +
            "edu/ntnu/stud/menu/MenuItem.<init> must not be null",
        "Expected thrown exception when name is null"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new MenuItem("Test", null),
        "Argument for @NotNull parameter 'action' of " +
            "edu/ntnu/stud/menu/MenuItem.<init> must not be null",
        "Expected thrown exception when action is null"
    );
  }

  // TODO make all relevant methods public or private
  @Test
  void testToString() {
    MenuItem item = new MenuItem("Test", () -> {});
    assertEquals("Test", item.toString());
  }

  @Test
  void run() {
    MenuItem item = new MenuItem("Test", () -> {});
    assertEquals("Test", item.toString());
  }
}