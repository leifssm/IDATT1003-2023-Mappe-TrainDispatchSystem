package edu.ntnu.stud.menu;

import edu.ntnu.stud.TestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuTest {
  @AfterEach
  void tearDown() {
    TestHelper.tearDown();
  }

  @Test
  @DisplayName("Constructor should only throw when name is null")
  void constructor() {
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new Menu(null),
        "Argument for @NotNull parameter 'name' of "
            + "edu/ntnu/stud/menu/Menu.<init> must not be null",
        "Expected thrown exception when name is null"
    );
    assertDoesNotThrow(
        () -> new Menu("Test Menu"),
        "Expected no exception"
    );
  }

  @Test
  @DisplayName("addOption() throws when any given parameter is null")
  void addOption() {
    Menu menu = new Menu("Test Menu");

    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> menu.addOption(null, () -> {}),
        "Argument for @NotNull parameter 'name' of "
            + "edu/ntnu/stud/menu/Menu.addOption must not be null",
        "Expected thrown exception when name is null"
    );

    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> menu.addOption("Test Option", null),
        "Argument for @NotNull parameter 'action' of "
            + "edu/ntnu/stud/menu/Menu.addOption must not be null",
        "Expected thrown exception when name is null"
    );

    TestHelper.setupMockInput("4", "1");
    TestHelper.expectOutput(
        "══ Test Menu ══",
        "1: Test Option 1",
        "2: Test Option 2",
        "",
        "Option: The number must be between 1 and 2",
        "Option: ",
        " ══ Picked option \"Test Option 1\" ══"
    );

    menu
        .addOption("Test Option 1", () -> {})
        .addOption("Test Option 2", () -> {})
        .runOnce();
  }

  @Test
  @DisplayName("setRunBefore() should never throw")
  void setRunBefore() {
    Menu menu = new Menu("Test Menu");

    assertDoesNotThrow(
        () -> menu.setRunBefore(null),
        "RunBefore should be able to be set to null"
    );

    TestHelper.setupMockInput("4", "1");
    TestHelper.expectOutput(
        "Run before",
        "══ Test Menu ══",
        "1: Test Option 1",
        "2: Test Option 2",
        "",
        "Option: The number must be between 1 and 2",
        "Option: ",
        " ══ Picked option \"Test Option 1\" ══"
    );

    menu
        .addOption("Test Option 1", () -> {})
        .addOption("Test Option 2", () -> {})
        .setRunBefore(() -> System.out.println("Run before"))
        .runOnce();
  }

  @Test
  @DisplayName("setRunAfter() should never throw")
  void setRunAfter() {
    Menu menu = new Menu("Test Menu");

    assertDoesNotThrow(
        () -> menu.setRunAfter(null),
        "RunAfter should be able to be set to null"
    );

    TestHelper.setupMockInput("4", "1");
    TestHelper.expectOutput(
        "══ Test Menu ══",
        "1: Test Option 1",
        "2: Test Option 2",
        "",
        "Option: The number must be between 1 and 2",
        "Option: ",
        " ══ Picked option \"Test Option 1\" ══",
        "Run after"
    );

    menu
        .addOption("Test Option 1", () -> {})
        .addOption("Test Option 2", () -> {})
        .setRunAfter(() -> System.out.println("Run after"))
        .runOnce();
  }

  @Test
  @DisplayName("print() gives expected output")
  void print() {
    TestHelper.expectOutput(
        "══ Test Menu ══",
        "1: Test Option 1",
        "2: Test Option 2"
    );
    new Menu("Test Menu")
        .addOption("Test Option 1", () -> {})
        .addOption("Test Option 2", () -> {})
        .print();
  }

  @Test
  @DisplayName("runOnce() gives expected output with one option")
  void runOnceWithOneOption() {
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

    new Menu("Test Menu")
        .addOption("Test Option 1", () -> {})
        .runOnce();

    TestHelper.assertThrowsWithMessage(
        IllegalStateException.class,
        () -> new Menu("Test Menu").runOnce(),
        "The menu must have at least one option",
        "Expected thrown exception when menu has no options"
    );
  }

  @Test
  @DisplayName("runOnce() gives expected output with multiple options")
  void runOnceWithMultipleOptions() {
    TestHelper.setupMockInput("9", "-10", "-1", "0", "6", "2");
    TestHelper.expectOutput(
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
        " ══ Picked option \"Test Option 2\" ══"
    );

    new Menu("Test Menu")
        .addOption("Test Option 1", () -> {})
        .addOption("Test Option 2", () -> {})
        .addOption("Test Option 3", () -> {})
        .addOption("Test Option 4", () -> {})
        .addOption("Test Option 5", () -> {})
        .runOnce();
  }
}