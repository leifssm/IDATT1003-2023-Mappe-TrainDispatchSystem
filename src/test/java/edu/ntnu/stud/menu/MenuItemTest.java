package edu.ntnu.stud.menu;

import edu.ntnu.stud.TestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {
  @Test
  @DisplayName("Constructor throws when any given parameter is null")
  void constructor() {
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new MenuItem(null, () -> {}),
        "Argument for @NotNull parameter 'name' of "
            + "edu/ntnu/stud/menu/MenuItem.<init> must not be null",
        "Expected thrown exception when name is null"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new MenuItem("Test", null),
        "Argument for @NotNull parameter 'action' of "
            + "edu/ntnu/stud/menu/MenuItem.<init> must not be null",
        "Expected thrown exception when action is null"
    );
  }

  // TODO make all relevant methods public or private
  @Test
  @DisplayName("toString() returns the name of the item")
  void testToString() {
    MenuItem item = new MenuItem("Test", () -> {});
    assertEquals("Test", item.toString());
  }

  @Test
  @DisplayName("run() runs the associated action")
  void run() {
    AtomicBoolean ranFunction = new AtomicBoolean(false);
    MenuItem item = new MenuItem("Test", () -> ranFunction.set(true));
    item.run();
    assertTrue(ranFunction.get());
  }
}