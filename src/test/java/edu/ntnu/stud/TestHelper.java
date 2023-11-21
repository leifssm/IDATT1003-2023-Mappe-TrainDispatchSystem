package edu.ntnu.stud;

import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;

import java.io.*;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestHelper {
  private static final InputStream originalIn = System.in;
  private static final PrintStream originalOut = System.out;
  public static void setupMockInput(String... input) throws NullPointerException {
    String joinedString = String.join("\n", input) + "\n";
    ByteArrayInputStream stream =  new ByteArrayInputStream(joinedString.getBytes());
    InputParser.initialize(stream);
  }

  public static void expectOutput(
      String... output
  ) throws NoSuchElementException, AssertionFailedError {
    String concatinated = String.join("\n", output);
    ByteArrayOutputStream byteStream = new ByteArrayOutputStream() {
      @Override
      public synchronized void close() throws IOException {
        try {
          assertEquals(
              concatinated,
              this.toString().trim().replaceAll("\r", ""),
              "Expected output does not match actual output"
          );
        } finally {
          super.close();
        }
      }
    };
    PrintStream stream = new PrintStream(byteStream);
    System.setOut(stream);
  }

  public static void tearDown() throws AssertionFailedError {
    try {
      if (InputParser.isInitialized()) {
        assertThrows(
            NoSuchElementException.class,
            () -> InputParser.getString(),
            "Not all inputs were consumed"
        );
      }
      if (!System.out.equals(originalOut) && System.out instanceof PrintStream) {
        System.out.close();
      }
    } finally {
      InputParser.close();
      System.setOut(originalOut);
      System.setIn(originalIn);
    }
  }

  public static void assertThrowsWithMessage(
      Class<? extends Throwable> expectedError,
      Executable executable,
      String expectedErrorMessage,
      String messageIfNotThrown
  ) {
    Throwable error = assertThrows(expectedError, executable, messageIfNotThrown);
    assertTrue(
        error.getMessage().equals(expectedErrorMessage),
        "Expected error message \""
            + expectedErrorMessage
            + "\" got \""
            + error.getMessage()
            + "\""
    );
  }
}
