package edu.ntnu.stud;

import java.io.ByteArrayInputStream;
import java.time.LocalTime;
import java.util.regex.Pattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

  @AfterEach
  void tearDown() {
    TestHelper.tearDown();
  }

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {
    @Test
    @DisplayName("isInitialized() returns if the parser is initialized")
    void isInitializedReturnIfInputParserIsInitialized() {
      assertFalse(InputParser.isInitialized());
      InputParser.initialize(new ByteArrayInputStream("".getBytes()));
      assertTrue(InputParser.isInitialized());
    }

    @Test
    @DisplayName("getString() returns an unvalidated string from the user")
    void getStringReturnsAStringFromUser() {
      TestHelper.setupMockInput("test", "test2", "hello");
      assertEquals("test", InputParser.getString());
      assertEquals("test2", InputParser.getString());
      assertEquals("hello", InputParser.getString());
    }

    @Test
    @DisplayName("getString() can be set to validate the input with a regex")
    void getStringRegexWithValidation() {
      TestHelper.setupMockInput("teST", "test2", "hello");
      assertEquals("hello", InputParser.getString("Test", "[a-z]+"));
    }

    @Test
    @DisplayName("getString() can be set to validate the input with a pattern")
    void getStringPatternWithValidation() {
      TestHelper.setupMockInput("teST", "test2", "hello");
      Pattern pattern = Pattern.compile("[a-z]+");
      assertEquals("hello", InputParser.getString("Test", pattern));
    }

    @Test
    @DisplayName("getInt() returns an int from the user")
    void getInt() {
      TestHelper.setupMockInput(
          "",
          " ",
          "en",
          "to",
          "-",
          "0.1",
          "0,1",
          "-2147483649",
          "2147483648",
          "-2147483648",
          "2147483647",
          "-1"
      );
      assertEquals(-2147483648, InputParser.getInt("Test"));
      assertEquals(2147483647, InputParser.getInt("Test"));
      assertEquals(-1, InputParser.getInt("Test"));
    }

    @Test
    @DisplayName("getInt() can be set to validate the input")
    void getIntWithValidation() {
      TestHelper.setupMockInput("", " ", "en", "to", "-", "-1", "0", "1");
      assertEquals(1, InputParser.getInt("Test", n -> n > 0));
    }

    @Test
    @DisplayName("getFloat() returns a float from the user")
    void getFloat() {
      TestHelper.setupMockInput("", " ", "en", "to", "-", "-0.1", "0.1", "1.9", "2");
      assertEquals(-0.10000000149011612, InputParser.getFloat("Test"));
      assertEquals(0.10000000149011612, InputParser.getFloat("Test"));
      assertEquals(1.899999976158142, InputParser.getFloat("Test"));
      assertEquals(2, InputParser.getFloat("Test"));
    }

    @Test
    @DisplayName("getFloat() can be set to validate the input")
    void getFloatWithValidation() {
      TestHelper.setupMockInput("", " ", "en", "to", "-", "-0.1", "0.1", "1.9", "2", "7", "8");
      assertEquals(8, InputParser.getFloat("Test", n -> n > 7));
    }

    @Test
    @DisplayName("getChar() returns an int from the user")
    void getChar() {
      TestHelper.setupMockInput("", "das", "ds", "FSFA", "A", "d", "+/", "/", "7", "  ", " ");
      assertEquals('A', InputParser.getChar("Test"));
      assertEquals('d', InputParser.getChar("Test"));
      assertEquals('/', InputParser.getChar("Test"));
      assertEquals('7', InputParser.getChar("Test"));
      assertEquals(' ', InputParser.getChar("Test"));
    }

    @Test
    @DisplayName("getChar() can be set to validate the input")
    void getCharWithValidation() {
      TestHelper.setupMockInput("", "das", "ds", "FSFA", "A", "B", "C", "D", "E", "FF", "G");
      assertEquals('G', InputParser.getChar("Test", n -> n > 'E'));
    }

    @Test
    @DisplayName("getTime() returns a LocalTime from the user")
    void getTime() {
      TestHelper.setupMockInput("", "2", "12:0", "0:60", "000:00", "24:00", "4:00", "23:59", "04:00");
      assertEquals(LocalTime.of(4, 0), InputParser.getTime("Test"));
      assertEquals(LocalTime.of(23, 59), InputParser.getTime("Test"));
      assertEquals(LocalTime.of(4, 0), InputParser.getTime("Test"));
    }

    @Test
    @DisplayName("getTime() can be set to validate the input")
    void getTimeWithValidation() {
      TestHelper.setupMockInput("4:00", "23:59", "14:00");
      assertEquals(
          LocalTime.of(14, 0),
          InputParser.getTime("Test", t -> t.getHour() > 12 && t.getHour() < 20)
      );
    }

    @Test
    @DisplayName("getBoolean() returns a boolean from the user")
    void getBoolean() {
      TestHelper.setupMockInput("", "das", "ds", "FSF", "A", "B", "Y", "n", "E", "FF", "G", "N", "y");
      assertTrue(InputParser.getBoolean("Test"));
      assertFalse(InputParser.getBoolean("Test"));
      assertFalse(InputParser.getBoolean("Test"));
      assertTrue(InputParser.getBoolean("Test"));
    }

    @Test
    @DisplayName("getBoolean() can return a default value if the user does not pass anything")
    void getBooleanWithDefault() {
      TestHelper.setupMockInput(
          "da",
          "ds",
          "FS",
          "A",
          "B",
          "E",
          "Y",
          "FF",
          "G",
          "YY",
          "NN",
          "",
          "HE",
          ""
      );
      assertTrue(InputParser.getBoolean("Test", false));
      assertTrue(InputParser.getBoolean("Test", true));
      assertFalse(InputParser.getBoolean("Test", false));
    }

    @Test
    @DisplayName("waitForUser() waits for the user to enter any value")
    void waitForUser() {
      TestHelper.setupMockInput("", "", "any string", " ");
      InputParser.waitForUser();
      InputParser.waitForUser("Press enter if you want to continue the program");
      InputParser.waitForUser();
      InputParser.waitForUser();
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {
    @Test
    @DisplayName("initialize() throws when trying to initialize with null")
    void initializeThrowsWithNull() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> InputParser.initialize(null),
          "Argument for @NotNull parameter 'stream' of "
              + "edu/ntnu/stud/InputParser.initialize must not be null",
          "InputParser should not be able to be initialized with a null stream"
      );
    }
  }
}