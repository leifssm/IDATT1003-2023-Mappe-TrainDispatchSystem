package edu.ntnu.stud;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

  @AfterEach
  void tearDown() {
    TestHelper.tearDown();
  }

  @Test
  void initialize() {
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> InputParser.initialize(null),
        "Argument for @NotNull parameter 'stream' of " +
            "edu/ntnu/stud/InputParser.initialize must not be null",
        "InputParser should not be able to be initialized with a null stream"
    );
  }

  @Test
  void isInitialized() {
    assertFalse(InputParser.isInitialized());
    InputParser.initialize(new ByteArrayInputStream("".getBytes()));
    assertTrue(InputParser.isInitialized());
    InputParser.close();
  }

  @Test
  void getString() {
    TestHelper.setupMockInput("test", "test2", "hello");
    assertEquals("test", InputParser.getString());
    assertEquals("test2", InputParser.getString());
    assertEquals("hello", InputParser.getString());
  }

  @Test
  void getStringRegexWithValidation() {
    TestHelper.setupMockInput("teST", "test2", "hello");
    assertEquals("hello", InputParser.getString("Test", "[a-z]+"));
  }

  @Test
  void getStringPatternWithValidation() {
    TestHelper.setupMockInput("teST", "test2", "hello");
    Pattern pattern = Pattern.compile("[a-z]+");
    assertEquals("hello", InputParser.getString("Test", pattern));
  }

  @Test
  void getInt() {
    TestHelper.setupMockInput("", " ", "en", "to", "-", "0.1", "0,1", "-1");
    assertEquals(-1, InputParser.getInt("Test"));
  }

  @Test
  void getIntWithValidation() {
    TestHelper.setupMockInput("", " ", "en", "to", "-", "-1", "0", "1");
    assertEquals(1, InputParser.getInt("Test", n -> n > 0));
  }

  @Test
  void getFloat() {
    TestHelper.setupMockInput("", " ", "en", "to", "-", "-0.1", "0.1", "1.9", "2");
    assertEquals(-0.10000000149011612, InputParser.getFloat("Test"));
    assertEquals(0.10000000149011612, InputParser.getFloat("Test"));
    assertEquals(1.899999976158142, InputParser.getFloat("Test"));
    assertEquals(2, InputParser.getFloat("Test"));
  }

  @Test
  void getFloatWithValidation() {
    TestHelper.setupMockInput("", " ", "en", "to", "-", "-0.1", "0.1", "1.9", "2", "7", "8");
    assertEquals(8, InputParser.getFloat("Test", n -> n > 7));
  }

  @Test
  void getChar() {
    TestHelper.setupMockInput("", "das", "ds", "FSFA", "A", "d", "+/", "/", "7", "  ", " ");
    assertEquals('A', InputParser.getChar("Test"));
    assertEquals('d', InputParser.getChar("Test"));
    assertEquals('/', InputParser.getChar("Test"));
    assertEquals('7', InputParser.getChar("Test"));
    assertEquals(' ', InputParser.getChar("Test"));
  }

  @Test
  void getCharWithValidation() {
    TestHelper.setupMockInput("", "das", "ds", "FSFA", "A", "B", "C", "D", "E", "FF", "G");
    assertEquals('G', InputParser.getChar("Test", n -> n > 'E'));
  }

  @Test
  void getTime() {
    TestHelper.setupMockInput("", "2", "12:0", "0:60", "000:00", "24:00", "4:00", "23:59", "04:00");
    assertEquals(LocalTime.of(4, 0), InputParser.getTime("Test"));
    assertEquals(LocalTime.of(23, 59), InputParser.getTime("Test"));
    assertEquals(LocalTime.of(4, 0), InputParser.getTime("Test"));
  }

  @Test
  void getTimeWithValidation() {
    TestHelper.setupMockInput("4:00", "23:59", "14:00");
    assertEquals(
        LocalTime.of(14, 0),
        InputParser.getTime("Test", t -> t.getHour() > 12 && t.getHour() < 20)
    );
  }

  @Test
  void getBoolean() {
    TestHelper.setupMockInput("", "das", "ds", "FSF", "A", "B", "Y", "n", "E", "FF", "G", "N", "y");
    assertTrue(InputParser.getBoolean("Test"));
    assertFalse(InputParser.getBoolean("Test"));
    assertFalse(InputParser.getBoolean("Test"));
    assertTrue(InputParser.getBoolean("Test"));
  }

  @Test
  void getBooleanWithDefault() {
    TestHelper.setupMockInput("da", "ds", "FS", "A", "B", "E", "FF", "G", "YY", "NN", "", "HE", "");
    assertTrue(InputParser.getBoolean("Test", true));
    assertFalse(InputParser.getBoolean("Test", false));
  }
}