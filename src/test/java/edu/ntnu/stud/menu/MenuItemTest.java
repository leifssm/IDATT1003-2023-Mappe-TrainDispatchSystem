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
      // Arrange
      String name = "Test";
      Runnable action = () -> {};
      // Act
      // Assert
      assertDoesNotThrow(
          () -> new MenuItem(name, action),
          "Expected no exception"
      );
    }

    @Test
    @DisplayName("getName() returns the name of the item")
    void getNameReturnsItemName() {
      // Arrange
      final String name = "Test";
      Runnable action = () -> {};
      // Act
      MenuItem item = new MenuItem(name, action);
      // Assert
      assertEquals(name, item.getName());
    }

    @Test
    @DisplayName("toString() returns a string representation of the item")
    void toStringReturnsStringRepresentation() {
      // Arrange
      final String name = "Test";
      Runnable action = () -> {};
      // Act
      MenuItem item = new MenuItem(name, action);
      // Assert
      assertEquals(
          "MenuItem [name = 'Test']",
          item.toString()
      );
    }

    @Test
    @DisplayName("run() runs the associated action")
    void runRunsAction() {
      // Arrange
      AtomicBoolean ranFunction = new AtomicBoolean(false);

      String name = "Test";
      Runnable action = () -> ranFunction.set(true);

      MenuItem item = new MenuItem(name, action);
      // Act
      item.run();
      // Assert
      assertTrue(ranFunction.get());
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {
    @Test
    @DisplayName("Constructor throws when the name is set as null")
    void constructorThrowsWhenNameIsNull() {
      // Arrange
      String name = null;
      Runnable action = () -> {};
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new MenuItem(name, action),
          "Argument for @NotNull parameter 'name' of "
              + "edu/ntnu/stud/menu/MenuItem.<init> must not be null",
          "Expected thrown exception when name is null"
      );
    }

    @Test
    @DisplayName("Constructor throws when the action is set as null")
    void constructorThrowsWhenActionIsNull() {
      // Arrange
      String name = "Test";
      Runnable action = null;
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new MenuItem(name, action),
          "Argument for @NotNull parameter 'action' of "
              + "edu/ntnu/stud/menu/MenuItem.<init> must not be null",
          "Expected thrown exception when action is null"
      );
    }
  }
}