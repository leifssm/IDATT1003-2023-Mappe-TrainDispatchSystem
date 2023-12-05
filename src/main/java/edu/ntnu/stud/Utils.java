package edu.ntnu.stud;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * This class contains utility methods that are used in multiple places in the application.
 */
public class Utils {
  /**
   * Pattern for validating train line names.
   */
  public static final Pattern trainLinePattern = Pattern.compile("^[A-Z0-9]{2,7}$");

  /**
   * Pattern for validating destination names.
   */
  public static final Pattern destinationPattern = Pattern.compile("^[a-zæøå -]{1,16}$", Pattern.CASE_INSENSITIVE);

  /**
   * Pads the given string evenly on both sides so that the string end up at least with a length
   * equal to the given number.
   *
   * @param string The string to pad
   * @param padding The expected minimum length of the returned string after padding
   * @return String with padding
   * @throws IllegalArgumentException If given a null instead of a string
   */
  public static @NotNull String padCenter(
      @NotNull String string,
      int padding
  ) throws IllegalArgumentException {
    // If the string doesn't need padding, return it.
    if (string.length() >= padding) {
      return string;
    }

    // Adds the padding evenly on both sides of the string
    final int totalPadding = padding - string.length();

    final int rightPadding = (int) Math.ceil((double) totalPadding / 2);
    final int leftPadding = totalPadding - rightPadding;

    return " ".repeat(leftPadding) + string + " ".repeat(rightPadding);
  }

  /**
   * Extension of {@link Utils#padCenter(String, int)}, except that it accepts an integer.
   *
   * @see Utils#padCenter(String, int)
   */
  public static @NotNull String padCenter(int n, int padding) {
    return padCenter(String.valueOf(n), padding);
  }
}
