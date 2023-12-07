package edu.ntnu.stud;

import java.io.*;
import java.util.NoSuchElementException;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A helper class that makes it easier to test the program
 */
public class TestHelper {
  /**
   * The original input stream. It is stored so that it can be restored later.
   */
  private static final InputStream originalIn = System.in;

  /**
   * The original print stream. It is stored so that it can be restored later.
   */
  private static final PrintStream originalOut = System.out;

  /**
   * Creates a input stream with the given input, and initializes the InputParser simpleton with it,
   * so that if a method is called from InputParser, it will read from the given input.
   *
   * @param inputs A list of strings, each representing one line of input
   */
  public static void setupMockInput(@NotNull String... inputs) {
    String joinedString = String.join("\n", inputs) + "\n";
    ByteArrayInputStream stream = new ByteArrayInputStream(joinedString.getBytes());
    InputParser.close();
    InputParser.initialize(stream);
  }

  /**
   * Sets the {@link System#out} to a mock {@link ByteArrayOutputStream}, with an overwritten close
   * method, that throws if the stream's content doesn't match the given input to the function.
   *
   * @param output A list of strings, each representing one line printed to the output
   * @throws AssertionFailedError If the given output doesn't match the given output
   */
  public static void expectOutput(
      @NotNull String... output
  ) throws NoSuchElementException, AssertionFailedError {
    final String concatinated = String.join("\n", output);

    // Creates a new ByteArrayOutputStream, with an overwritten close method that throws if the
    // given output doesn't match the expected output.
    final ByteArrayOutputStream byteStream = new ByteArrayOutputStream() {
      @Override
      public synchronized void close() throws IOException {
        try {
          assertEquals(
              concatinated,
              this.toString().trim().replaceAll("\r", ""),
              "Expected output does not match given output"
          );
        } finally {
          // Closes the stream even if the assertion throws.
          super.close();
        }
      }
    };
    final PrintStream stream = new PrintStream(byteStream);
    System.setOut(stream);
  }

  /**
   * Tears down the test environment, and checks that all inputs from the mock input were consumed.
   * It also closes the mock output if one was set up, checking that the mock output matches the
   * givem output at the same time. This method should be called in the {@code @AfterEach} method
   *
   * @throws AssertionFailedError If not all inputs were consumed, or if the mock output didn't
   *                              match the given output.
   */
  public static void tearDown() throws AssertionFailedError {
    try {
      // If the input parser is initialized, getting a new input should throw.
      if (InputParser.isInitialized()) {
        assertThrows(
            NoSuchElementException.class,
            () -> InputParser.getString(),
            "Not all inputs were consumed"
        );
      }

      // If the output steam doesnt match the original, it must be because of a mock output stream,
      // so we close it, making it run the comparing function in the expectOutput function.
      if (!System.out.equals(originalOut) && System.out instanceof PrintStream) {
        System.out.close();
      }
    } finally {
      // Closes the input parser, and restores the original input and output streams even if the
      // code above throws.
      InputParser.close();
      System.setOut(originalOut);
      System.setIn(originalIn);
    }
  }

  /**
   * A helper function that is a shorthand of two assertions, that first checks if it the executable
   * throws the expected error, and then checks if the error message matches the expected error
   * message.
   *
   * @param expectedError The expected error class
   * @param executable The executable that should throw the given error
   * @param expectedErrorMessage The expected error message when the executable throws
   * @param messageIfNotThrown The error message to show if the executable doesn't throw.
   * @throws AssertionFailedError If the executable doesn't throw, or if the error message doesn't
   *                              match the expected error message.
   */
  public static void assertThrowsWithMessage(
      Class<? extends Throwable> expectedError,
      Executable executable,
      String expectedErrorMessage,
      String messageIfNotThrown
  ) throws AssertionFailedError {
    Throwable error = assertThrows(expectedError, executable, messageIfNotThrown);
    assertEquals(
        expectedErrorMessage,
        error.getMessage()
    );
  }
}
