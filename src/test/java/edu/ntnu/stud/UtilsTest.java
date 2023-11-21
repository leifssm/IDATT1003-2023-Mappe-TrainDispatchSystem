package edu.ntnu.stud;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
  @Test
  void padCenter() {
    assertEquals("", Utils.padCenter("", 0));
    assertEquals(" ", Utils.padCenter("", 1));
    assertEquals("  ", Utils.padCenter("", 2));
    assertEquals("   ", Utils.padCenter("", 3));
    assertEquals("    ", Utils.padCenter("", 4));
    assertEquals("  a ", Utils.padCenter("a", 4));
    assertEquals(" ab ", Utils.padCenter("ab", 4));
    assertEquals(" abc", Utils.padCenter("abc", 4));
    assertEquals("abcd", Utils.padCenter("abcd", 4));
  }
}