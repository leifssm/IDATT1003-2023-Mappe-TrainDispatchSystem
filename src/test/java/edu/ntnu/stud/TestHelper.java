package edu.ntnu.stud;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestHelper {
  static private final InputStream originalIn = System.in;
  static private final PrintStream originalOut = System.out;
  static void setupMockInput(String... input) {
    String joinedString = String.join("\n", input);
    ByteArrayInputStream stream =  new ByteArrayInputStream(joinedString.getBytes());
    InputParser.initialize(stream);
  }

  static void setupMockOutput(String... output) {
    final int[] index = {0};
    ByteArrayOutputStream byteStream = new ByteArrayOutputStream() {
      @Override
      public synchronized void write(byte[] b, int off, int len) {
        String s = new String(b, 0, len).trim();
        if (output.length <= index[0]) {
          throw new NoSuchElementException("Got more outputs than expected");
        }
        if (!output[index[0]].trim().equals(s)) {
          throw new AssertionError("Expected \"" + output[index[0]] + "\" got \"" + s + "\"");
        }
        index[0]++;
        super.write(b, off, len);
      }
    };
    PrintStream stream = new PrintStream(byteStream);
    System.setOut(stream);
  }

  static void tearDown() {
    try {
      if (InputParser.isInitialized()) {
        assertThrows(NoSuchElementException.class, () -> InputParser.getString("Should throw"));
      }
      if (!System.out.equals(originalOut) && System.out instanceof PrintStream) {
        assertThrows(NoSuchElementException.class, () -> System.out.println("Should throw"));
      }
    } finally {
      InputParser.close();
      System.setOut(originalOut);
      System.setIn(originalIn);
    }
  }
}
