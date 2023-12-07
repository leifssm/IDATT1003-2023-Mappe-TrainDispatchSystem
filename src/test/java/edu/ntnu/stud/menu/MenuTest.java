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
      assertDoesNotThrow(
          () -> new Menu("Test Menu"),
          "Expected no exception"
      );
    }

    @Test
    @DisplayName("setRunBefore() should never throw")
    void setRunBeforeDoesntThrowWithNull() {
      assertDoesNotThrow(
          () -> menu.setRunBefore(null),
          "runBefore should be able to be set to null"
      );
      assertDoesNotThrow(
          () -> menu.setRunBefore(() -> {}),
          "runBefore should be able to be set to a Runnable"
      );
    }

    @Test
    @DisplayName("setRunAfter() should never throw")
    void setRunAfterDoesntThrowWithNull() {
      assertDoesNotThrow(
          () -> menu.setRunAfter(null),
          "runAfter should be able to be set to null"
      );
      assertDoesNotThrow(
          () -> menu.setRunAfter(() -> {}),
          "runAfter should be able to be set to a Runnable"
      );
    }

    @Test
    @DisplayName("print() gives expected output")
    void printShouldSucceed() {
      TestHelper.expectOutput(
          "══ Test Menu ══",
          "1: Test Option 1",
          "2: Test Option 2"
      );

      menu
          .addOption("Test Option 1", () -> {})
          .addOption("Test Option 2", () -> {})
          .print();
    }

    @Test
    @DisplayName("runOnce() gives expected output with one option")
    void runOnceWithOneOptionShouldSucceed() {
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

      menu
          .addOption("Test Option 1", () -> {})
          .runOnce();
    }

    @Test
    @DisplayName(
        "runOnce() gives expected output with multiple options and a defined runBefore and runAfter"
    )
    void runOnceWithMultipleOptions() {
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
          .setRunAfter(() -> System.out.println("Running After"))
          .runOnce();
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {
    @Test
    @DisplayName("Constructor throws when the name is set as null")
    void constructorThrowsWhenNameIsNull() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new Menu(null),
          "Argument for @NotNull parameter 'name' of "
              + "edu/ntnu/stud/menu/Menu.<init> must not be null",
          "Expected thrown exception when name is null"
      );
    }

    @Test
    @DisplayName("addOption() throws when the given name is null")
    void addOptionThrowsWhenNameIsNull() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> menu.addOption(null, () -> {
          }),
          "Argument for @NotNull parameter 'name' of "
              + "edu/ntnu/stud/menu/Menu.addOption must not be null",
          "Expected thrown exception when name is null"
      );
    }

    @Test
    @DisplayName("addOption() throws when the given action is null")
    void addOptionThrowsWhenActionIsNull() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> menu.addOption("Test Option", null),
          "Argument for @NotNull parameter 'action' of "
              + "edu/ntnu/stud/menu/Menu.addOption must not be null",
          "Expected thrown exception when name is null"
      );
    }

    @Test
    @DisplayName("runOnce() throws if no options has been added to the menu")
    void runOnceWithZeroOptionShouldThrow() {
      TestHelper.assertThrowsWithMessage(
          IllegalStateException.class,
          () -> menu.runOnce(),
          "The menu must have at least one option",
          "Expected thrown exception when menu has no options"
      );
    }
  }
}