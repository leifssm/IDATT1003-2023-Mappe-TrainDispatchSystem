package edu.ntnu.stud;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.regex.Pattern;

import org.intellij.lang.annotations.RegExp;
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
      // Arrange
      // Assert - Shouldn't be twice, but in this case it should check to not get a false positive
      assertFalse(InputParser.isInitialized());
      // Act
      InputParser.initialize(new ByteArrayInputStream("".getBytes()));
      // Assert
      assertTrue(InputParser.isInitialized());
    }

    @Test
    @DisplayName("getString() returns an unvalidated string from the user")
    void getStringReturnsAStringFromUser() {
      // Arrange
      TestHelper.setupMockInput("test", "test2", "hello");
      // Act
      // Assert
      assertEquals("test", InputParser.getString());
      assertEquals("test2", InputParser.getString());
      assertEquals("hello", InputParser.getString());
    }

    @Test
    @DisplayName("getString() can be set to validate the input with a regex")
    void getStringRegexWithValidation() {
      // Arrange
      TestHelper.setupMockInput("teST", "test2", "hello");
      String prompt = "Test";
      @RegExp String regexValidation = "[a-z]+";
      // Act
      // Assert
      assertEquals("hello", InputParser.getString(prompt, regexValidation));
    }

    @Test
    @DisplayName("getString() can be set to validate the input with a pattern")
    void getStringPatternWithValidation() {
      // Arrange
      TestHelper.setupMockInput("teST", "test2", "hello");
      String prompt = "Test";
      Pattern pattern = Pattern.compile("[a-z]+");
      // Act
      // Assert
      assertEquals("hello", InputParser.getString(prompt, pattern));
    }

    @Test
    @DisplayName("getInt() returns an int from the user")
    void getInt() {
      // Arrange
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
      String prompt = "Test";
      // Act
      // Assert
      assertEquals(-2147483648, InputParser.getInt(prompt));
      assertEquals(2147483647, InputParser.getInt(prompt));
      assertEquals(-1, InputParser.getInt(prompt));
    }

    @Test
    @DisplayName("getInt() can be set to validate the input")
    void getIntWithValidation() {
      // Arrange
      TestHelper.setupMockInput("", " ", "en", "to", "-", "-1", "0", "1");
      String prompt = "Test";
      // Act
      // Assert
      assertEquals(1, InputParser.getInt(prompt, n -> n > 0));
    }

    @Test
    @DisplayName("getFloat() returns a float from the user")
    void getFloat() {
      // Arrange
      TestHelper.setupMockInput("", " ", "en", "to", "-", "-0.1", "0.1", "1.9", "2");
      String prompt = "Test";
      // Act
      // Assert
      assertEquals(-0.10000000149011612, InputParser.getFloat(prompt));
      assertEquals(0.10000000149011612, InputParser.getFloat(prompt));
      assertEquals(1.899999976158142, InputParser.getFloat(prompt));
      assertEquals(2, InputParser.getFloat(prompt));
    }

    @Test
    @DisplayName("getFloat() can be set to validate the input")
    void getFloatWithValidation() {
      // Arrange
      TestHelper.setupMockInput("", " ", "en", "to", "-", "-0.1", "0.1", "1.9", "2", "7", "8");
      String prompt = "Test";
      // Act
      // Assert
      assertEquals(8, InputParser.getFloat(prompt, n -> n > 7));
    }

    @Test
    @DisplayName("getChar() returns an int from the user")
    void getChar() {
      // Arrange
      TestHelper.setupMockInput("", "das", "ds", "FSFA", "A", "d", "+/", "/", "7", "  ", " ");
      String prompt = "Test";
      // Act
      // Assert
      assertEquals('A', InputParser.getChar(prompt));
      assertEquals('d', InputParser.getChar(prompt));
      assertEquals('/', InputParser.getChar(prompt));
      assertEquals('7', InputParser.getChar(prompt));
      assertEquals(' ', InputParser.getChar(prompt));
    }

    @Test
    @DisplayName("getChar() can be set to validate the input")
    void getCharWithValidation() {
      // Arrange
      TestHelper.setupMockInput("", "das", "ds", "FSFA", "A", "B", "C", "D", "E", "FF", "G");
      String prompt = "Test";
      // Act
      // Assert
      assertEquals('G', InputParser.getChar(prompt, n -> n > 'E'));
    }

    @Test
    @DisplayName("getTime() returns a LocalTime from the user")
    void getTime() {
      // Arrange
      TestHelper.setupMockInput("", "2", "12:0", "0:60", "000:00", "24:00", "4:00", "23:59", "04:00");
      String prompt = "Test";
      // Act
      // Assert
      assertEquals(LocalTime.of(4, 0), InputParser.getTime(prompt));
      assertEquals(LocalTime.of(23, 59), InputParser.getTime(prompt));
      assertEquals(LocalTime.of(4, 0), InputParser.getTime(prompt));
    }

    @Test
    @DisplayName("getTime() can be set to validate the input")
    void getTimeWithValidation() {
      // Arrange
      TestHelper.setupMockInput("4:00", "23:59", "14:00");
      String prompt = "Test";
      // Act
      // Assert
      assertEquals(
          LocalTime.of(14, 0),
          InputParser.getTime(prompt, t -> t.getHour() > 12 && t.getHour() < 20)
      );
    }

    @Test
    @DisplayName("getBoolean() returns a boolean from the user")
    void getBoolean() {
      // Arrange
      TestHelper.setupMockInput("", "das", "ds", "FSF", "A", "B", "Y", "n", "E", "FF", "G", "N", "y");
      String prompt = "Test";
      // Act
      // Assert
      assertTrue(InputParser.getBoolean(prompt));
      assertFalse(InputParser.getBoolean(prompt));
      assertFalse(InputParser.getBoolean(prompt));
      assertTrue(InputParser.getBoolean(prompt));
    }

    @Test
    @DisplayName("getBoolean() can return a default value if the user does not pass anything")
    void getBooleanWithDefault() {
      // Arrange
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
      String prompt = "Test";
      // Act
      // Assert
      assertTrue(InputParser.getBoolean(prompt, false));
      assertTrue(InputParser.getBoolean(prompt, true));
      assertFalse(InputParser.getBoolean(prompt, false));
    }

    @Test
    @DisplayName("waitForUser() waits for the user to enter any value")
    void waitForUser() {
      // Arrange
      String prompt = "Press enter if you want to continue the program";
      // Act
      // Assert
      TestHelper.setupMockInput("", "", "any string", " ");
      InputParser.waitForUser();
      InputParser.waitForUser(prompt);
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
      // Arrange
      InputStream stream = null;
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> InputParser.initialize(stream),
          "Argument for @NotNull parameter 'stream' of "
              + "edu/ntnu/stud/InputParser.initialize must not be null",
          "InputParser should not be able to be initialized with a null stream"
      );
    }
  }
}