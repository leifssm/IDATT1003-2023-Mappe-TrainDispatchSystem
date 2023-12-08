package edu.ntnu.stud.menu;

import edu.ntnu.stud.TestHelper;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuTest {
  private Menu menu;
  @BeforeEach
  void setUp() {
    menu = new Menu("Test Menu");
  }

  @AfterEach
  void tearDown() {
    menu = null;
    TestHelper.tearDown();
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {
    @Test
    @DisplayName("Constructor doesn't throw when a name is given")
    void constructorDoesntThrowWithGivenName() {
      // Arrange
      String name = "Test Menu";
      // Act
      // Assert
      assertDoesNotThrow(
          () -> new Menu(name),
          "Expected no exception"
      );
    }

    @Test
    @DisplayName("setRunBefore() should not throw when given null")
    void setRunBeforeDoesntThrowWithNull() {
      // Arrange
      Runnable runBefore = null;
      // Act
      // Assert
      assertDoesNotThrow(
          () -> menu.setRunBefore(runBefore),
          "runBefore should be able to be set to null"
      );
    }

    @Test
    @DisplayName("setRunBefore() should not throw when given a runnable")
    void setRunBeforeDoesntThrowWithRunnable() {
      // Arrange
      Runnable runBefore = () -> {};
      // Act
      // Assert
      assertDoesNotThrow(
          () -> menu.setRunBefore(runBefore),
          "runBefore should be able to be set to a Runnable"
      );
    }

    @Test
    @DisplayName("setRunAfter() should not throw when given null")
    void setRunAfterDoesntThrowWithNull() {
      // Arrange
      Runnable runAfter = null;
      // Act
      // Assert
      assertDoesNotThrow(
          () -> menu.setRunAfter(runAfter),
          "runAfter should be able to be set to null"
      );
    }

    @Test
    @DisplayName("setRunAfter() should not throw when given a runnable")
    void setRunAfterDoesntThrowWithRunnable() {
      // Arrange
      Runnable runAfter = () -> {};
      // Act
      // Assert
      assertDoesNotThrow(
          () -> menu.setRunAfter(runAfter),
          "runAfter should be able to be set to a Runnable"
      );
    }

    @Test
    @DisplayName("print() gives expected output")
    void printShouldSucceed() {
      // Arrange
      TestHelper.expectOutput(
          "══ Test Menu ══",
          "1: Test Option 1",
          "2: Test Option 2"
      );
      menu
          .addOption("Test Option 1", () -> {})
          .addOption("Test Option 2", () -> {});
      // Act
      menu.print();
      // Assert
      // Happens in the tearDown method
    }

    @Test
    @DisplayName("runOnce() gives expected output with one option")
    void runOnceWithOneOptionShouldSucceed() {
      // Arrange
      TestHelper.setupMockInput("0", "2", "1");
      TestHelper.expectOutput(
          "══ Test Menu ══",
          "1: Test Option 1",
          "",
          "Option: The number must be 1",
          "Option: The number must be 1",
          "Option: ",
          " ══ Picked option \"Test Option 1\" ══"
      );
      menu.addOption("Test Option 1", () -> {});
      // Act
      menu.runOnce();
      // Assert
      // Happens in the tearDown method
    }

    @Test
    @DisplayName(
        "runOnce() gives expected output with multiple options and a defined runBefore and runAfter"
    )
    void runOnceWithMultipleOptions() {
      // Arrange
      TestHelper.setupMockInput("9", "-10", "-1", "0", "6", "2");
      TestHelper.expectOutput(
          "Running Before",
          "══ Test Menu ══",
          "1: Test Option 1",
          "2: Test Option 2",
          "3: Test Option 3",
          "4: Test Option 4",
          "5: Test Option 5",
          "",
          "Option: The number must be between 1 and 5",
          "Option: The number must be between 1 and 5",
          "Option: The number must be between 1 and 5",
          "Option: The number must be between 1 and 5",
          "Option: The number must be between 1 and 5",
          "Option: ",
          " ══ Picked option \"Test Option 2\" ══",
          "Running After"
      );
      menu
          .addOption("Test Option 1", () -> {})
          .addOption("Test Option 2", () -> {})
          .addOption("Test Option 3", () -> {})
          .addOption("Test Option 4", () -> {})
          .addOption("Test Option 5", () -> {})
          .setRunBefore(() -> System.out.println("Running Before"))
          .setRunAfter(() -> System.out.println("Running After"));
      // Act
      menu.runOnce();
      // Assert
      // Happens in the tearDown method
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
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new Menu(name),
          "Argument for @NotNull parameter 'name' of "
              + "edu/ntnu/stud/menu/Menu.<init> must not be null",
          "Expected thrown exception when name is null"
      );
    }

    @Test
    @DisplayName("addOption() throws when the given name is null")
    void addOptionThrowsWhenNameIsNull() {
      // Arrange
      String name = null;
      Runnable action = () -> {};
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> menu.addOption(name, action),
          "Argument for @NotNull parameter 'name' of "
              + "edu/ntnu/stud/menu/Menu.addOption must not be null",
          "Expected thrown exception when name is null"
      );
    }

    @Test
    @DisplayName("addOption() throws when the given action is null")
    void addOptionThrowsWhenActionIsNull() {
      // Arrange
      String name = "Test Option";
      Runnable action = null;
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> menu.addOption(name, action),
          "Argument for @NotNull parameter 'action' of "
              + "edu/ntnu/stud/menu/Menu.addOption must not be null",
          "Expected thrown exception when name is null"
      );
    }

    @Test
    @DisplayName("runOnce() throws if no options has been added to the menu")
    void runOnceWithZeroOptionShouldThrow() {
      // Arrange
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalStateException.class,
          () -> menu.runOnce(),
          "The menu must have at least one option",
          "Expected thrown exception when menu has no options"
      );
    }
  }
}