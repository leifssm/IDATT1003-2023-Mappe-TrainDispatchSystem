package edu.ntnu.stud;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public class Utils {
  public static final Pattern trainLinePattern = Pattern.compile("^[A-Z0-9]{2,7}$");

  public static @NotNull String padCenter(
      @NotNull String string,
      int padding
  ) throws IllegalArgumentException {
    if (string == null) {
      throw new IllegalArgumentException("String cannot be null");
    }
    if (string.length() > padding) {
      return string;
    }
    final int totalPadding = padding - string.length();
    final int rightPadding = totalPadding / 2;
    final int leftPadding = totalPadding - rightPadding;
    return " ".repeat(leftPadding) + string + " ".repeat(rightPadding);
  }

  public static @NotNull String pc(int n, int padding) {
    return padCenter(String.valueOf(n), padding);
  }

  public static @NotNull String pc(@NotNull String string, int padding) {
    return padCenter(string, padding);
  }
}
