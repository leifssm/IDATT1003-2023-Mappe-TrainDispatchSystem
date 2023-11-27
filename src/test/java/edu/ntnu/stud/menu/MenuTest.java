package edu.ntnu.stud.menu;

import edu.ntnu.stud.TestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuTest {
  @AfterEach
  void tearDown() {
    TestHelper.tearDown();
  }

  @Test
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
        "Valg: Tallet må være mellom 1 og 2",
        "Valg: ",
        " ══ Valgte \"Test Option 1\" ══"
    );

    menu
        .addOption("Test Option 1", () -> {})
        .addOption("Test Option 2", () -> {})
        .runOnce();
  }

  @Test
  void setRunBefore() {
    Menu menu = new Menu("Test Menu");

    TestHelper.setupMockInput("4", "1");
    TestHelper.expectOutput(
        "Run before",
        "══ Test Menu ══",
        "1: Test Option 1",
        "2: Test Option 2",
        "",
        "Valg: Tallet må være mellom 1 og 2",
        "Valg: ",
        " ══ Valgte \"Test Option 1\" ══"
    );

    assertDoesNotThrow(
        () -> menu.setRunBefore(null),
        "RunBefore should be able to be set to null"
    );

    menu
        .addOption("Test Option 1", () -> {})
        .addOption("Test Option 2", () -> {})
        .setRunBefore(() -> System.out.println("Run before"))
        .runOnce();
  }

  @Test
  void setRunAfter() {
    Menu menu = new Menu("Test Menu");

    TestHelper.setupMockInput("4", "1");
    TestHelper.expectOutput(
        "══ Test Menu ══",
        "1: Test Option 1",
        "2: Test Option 2",
        "",
        "Valg: Tallet må være mellom 1 og 2",
        "Valg: ",
        " ══ Valgte \"Test Option 1\" ══",
        "Run after"
    );

    assertDoesNotThrow(
        () -> menu.setRunAfter(null),
        "RunAfter should be able to be set to null"
    );


    menu
        .addOption("Test Option 1", () -> {})
        .addOption("Test Option 2", () -> {})
        .setRunAfter(() -> System.out.println("Run after"))
        .runOnce();
  }

  @Test
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
  void runOnce() {
    TestHelper.setupMockInput("9", "-10", "-1", "0", "6", "2");
    TestHelper.expectOutput(
        "══ Test Menu ══",
        "1: Test Option 1",
        "2: Test Option 2",
        "3: Test Option 3",
        "4: Test Option 4",
        "5: Test Option 5",
        "",
        "Valg: Tallet må være mellom 1 og 5",
        "Valg: Tallet må være mellom 1 og 5",
        "Valg: Tallet må være mellom 1 og 5",
        "Valg: Tallet må være mellom 1 og 5",
        "Valg: Tallet må være mellom 1 og 5",
        "Valg: ",
        " ══ Valgte \"Test Option 2\" ══"
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