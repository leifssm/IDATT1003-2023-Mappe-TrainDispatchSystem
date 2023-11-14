package edu.ntnu.stud;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class InputParser {
  public interface InputValidator<T> { boolean test(T input); }

  private static Scanner scanner;

  static void initialize(@NotNull InputStream stream) {
    if (scanner != null) {
      return;
    }
    scanner = new Scanner(stream, StandardCharsets.UTF_8);
  }
  static void initialize() {
    initialize(System.in);
  }

  static boolean isInitialized() {
    return scanner != null;
  }

  public static void close() {
    if (InputParser.scanner == null) {
      return;
    }
    scanner.close();
    scanner = null;
  }

  public static int getInt(String prompt) {
    initialize();

    while (true) {
      System.out.print(prompt + ": ");
      try {
        return Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException ignored) {
        // Do nothing
      }
    }
  }

  public static int getInt(String prompt, InputValidator<Integer> validator) {
    int result;
    while (true) {
      result = getInt(prompt);
      if (validator.test(result)) {
        return result;
      }
    }
  }

  public static float getFloat(String prompt) {
    initialize();
    while (true) {
      System.out.print(prompt + ": ");
      try {
        return Float.parseFloat(scanner.nextLine());
      } catch (NumberFormatException ignored) {
        // Do nothing
      }
    }
  }

  public static float getFloat(String prompt, InputValidator<Float> validator) {
    float result;
    while (true) {
      result = getFloat(prompt);
      if (validator.test(result)) {
        return result;
      }
    }
  }

  public static String getString(String prompt) {
    initialize();
    System.out.print(prompt + ": ");
    return scanner.nextLine();
  }

  public static String getString(String prompt, @NonNls @NotNull String regex) {
    initialize();
    final Pattern pattern = Pattern.compile(regex);
    while (true) {
      final Matcher t = pattern.matcher(getString(prompt));
      if (t.matches()) {
        return t.group();
      }
    }
  }

  public static String getString(String prompt, Pattern pattern) {
    initialize();
    while (true) {
      final Matcher t = pattern.matcher(getString(prompt));
      if (t.matches()) {
        return t.group();
      }
    }
  }

  public static char getChar(String prompt) {
    initialize();
    String input;
    while (true) {
      input = getString(prompt);
      if (input.length() == 1) {
        return input.charAt(0);
      }
    }
  }

  public static char getChar(String prompt, InputValidator<Character> validator) {
    initialize();
    String input;
    while (true) {
      input = getString(prompt);
      if (input.length() == 1 && validator.test(input.charAt(0))) {
        return input.charAt(0);
      }
    }
  }

  public static LocalTime getTime(String prompt) {
    initialize();
    final Pattern pattern = Pattern.compile("^[0-2]?[0-9]:[0-5][0-9]$");
    while (true) {
      final String result = getString(prompt, pattern);
      final String paddedResult = result.length() == 4 ? "0" + result : result;
      try {
        return LocalTime.parse(paddedResult);
      } catch (DateTimeParseException ignored) {
        // Do nothing
      }
    }
  }

  public static LocalTime getTime(String prompt, InputValidator<LocalTime> validator) {
    while (true) {
      final LocalTime result = getTime(prompt);
      if (validator.test(result)) {
        return result;
      }
    }
  }

  public static boolean getBoolean(String prompt) {
    initialize();
    return getString(prompt + " [y/n]", "^[ynYN]$").equalsIgnoreCase("y");
  }

  public static boolean getBoolean(String prompt, boolean defaultValue) {
    initialize();
    final String result = getString(prompt + (defaultValue ? " [Y/n]" : " [y/N]"), "^[ynYN]?$");
    if (result.isEmpty()) {
      return defaultValue;
    }
    return result.equalsIgnoreCase("y");
  }
}