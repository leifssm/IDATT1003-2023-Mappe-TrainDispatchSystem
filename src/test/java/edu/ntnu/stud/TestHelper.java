package edu.ntnu.stud;

import java.io.*;
import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;
import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1>TestHelper.</h1>
 * <p>A helper class that makes it easier to test the program</p>
 * <br>
 * <h2>Role and Responsibility:</h2>
 * <p>
 *   This class is responsible for providing helper methods that makes it easier to test the
 *   application. The class provides methods for {@link #setupMockInput(String...) mocking inputs},
 *   for {@link #expectOutput(String...) testing the output}, for {@link #tearDown() tearing down
 *   the mock inputs and outputs}, and a shorthand for {@link #assertThrowsWithMessage testing
 *   thrown errors}.
 * </p>
 */
public class TestHelper {
  /**
   * The original input stream. It is immutably stored so that it can be restored on teardown.
   */
  private static final InputStream originalIn = System.in;

  /**
   * The original print stream. It is immutably stored so that it can be restored on teardown.
   */
  private static final PrintStream originalOut = System.out;

  /**
   * Creates an input stream with the given input, and initializes the InputParser simpleton with it,
   * so that if a method is called from InputParser, it will read from the given input.
   *
   * @param inputs A list of strings, each representing one line of input
   */
  public static void setupMockInput(String... inputs) {
    System.out.println(Arrays.toString(inputs));
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
      String... output
  ) throws AssertionFailedError {
    final String concatenated = String.join("\n", output);

    // Creates a new ByteArrayOutputStream, with an overwritten close method that throws if the
    // given output doesn't match the expected output.
    final ByteArrayOutputStream byteStream = new ByteArrayOutputStream() {
      @Override
      public synchronized void close() throws IOException {
        try {
          assertEquals(
              concatenated,
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
   * given output at the same time. This method should be called in the {@code @AfterEach} method
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
            InputParser::getString,
            "Not all inputs were consumed"
        );
      }

      // If the output steam doesn't match the original, it must be because of a mock output stream,
      // so we close it, making it run the comparing function in the expectOutput function.
      if (!System.out.equals(originalOut) && System.out != null) {
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
   * A helper function that is a shorthand of two assertions, that first checks if the executable
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
