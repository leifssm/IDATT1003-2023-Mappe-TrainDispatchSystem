package edu.ntnu.stud.menu;

import edu.ntnu.stud.TestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {
    @Test
    @DisplayName("Constructor doesn't throw when given valid parameters")
    void constructorDoesntThrowWithValidParams() {
      assertDoesNotThrow(
          () -> new MenuItem("Test", () -> {}),
          "Expected no exception"
      );
    }

    @Test
    @DisplayName("toString() returns the name of the item")
    void toStringReturnsItemName() {
      MenuItem item = new MenuItem("Test", () -> {});
      assertEquals("Test", item.toString());
    }

    @Test
    @DisplayName("run() runs the associated action")
    void runRunsAction() {
      AtomicBoolean ranFunction = new AtomicBoolean(false);
      MenuItem item = new MenuItem("Test", () -> ranFunction.set(true));
      item.run();
      assertTrue(ranFunction.get());
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {

    @Test
    @DisplayName("Constructor throws when the name is set as null")
    void throwsWhenNameIsNull() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new MenuItem(null, () -> {
          }),
          "Argument for @NotNull parameter 'name' of "
              + "edu/ntnu/stud/menu/MenuItem.<init> must not be null",
          "Expected thrown exception when name is null"
      );
    }

    @Test
    @DisplayName("Constructor throws when the action is set as null")
    void throwsWhenActionIsNull() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new MenuItem("Test", null),
          "Argument for @NotNull parameter 'action' of "
              + "edu/ntnu/stud/menu/MenuItem.<init> must not be null",
          "Expected thrown exception when action is null"
      );
    }
  }
}