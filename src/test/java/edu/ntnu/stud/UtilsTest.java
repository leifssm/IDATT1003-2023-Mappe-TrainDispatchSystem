package edu.ntnu.stud;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
  @Test
  @DisplayName("padCenter() pads the string equally with spaces on both sides")
  void padCenter() {
    assertEquals("", Utils.padCenter("", 0));
    assertEquals(" ", Utils.padCenter("", 1));
    assertEquals("  ", Utils.padCenter("", 2));
    assertEquals("   ", Utils.padCenter("", 3));
    assertEquals("    ", Utils.padCenter("", 4));
    assertEquals(" a  ", Utils.padCenter("a", 4));
    assertEquals(" ab ", Utils.padCenter("ab", 4));
    assertEquals("abc ", Utils.padCenter("abc", 4));
    assertEquals("abcd", Utils.padCenter("abcd", 4));
    assertEquals("  abc   ", Utils.padCenter("abc", 8));
    assertEquals(" 23 ", Utils.padCenter(23, 4));
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> Utils.padCenter(null, 0),
        "Argument for @NotNull parameter 'string' of "
            + "edu/ntnu/stud/Utils.padCenter must not be null",
        "Trying to pad null string should throw"
    );

  }
}