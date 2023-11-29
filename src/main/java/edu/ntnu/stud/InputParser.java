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

/**
 * A singleton class with lazy instantiation that manages and parses all user input.
 */
public class InputParser {
  /**
   * A functional interface for validating user input.
   *
   * @param <T> The expected type of input to validate.
   */
  public interface InputValidator<T> { boolean test(T input); }

  /**
   * The scanner used to read inputs from.
   */
  private static Scanner scanner;

  /**
   * Initialize the scanner with the given input stream. Does not override an already initialized
   * scanner.
   *
   * @param stream The input stream to read from.
   * @throws IllegalArgumentException If the stream is null
   */
  static void initialize(@NotNull InputStream stream) throws IllegalArgumentException {
    if (isInitialized()) {
      return;
    }
    scanner = new Scanner(stream, StandardCharsets.UTF_8);
  }

  /**
   * Initializes the scanner with {@link System#in}. Does not override an already initialized
   * scanner.
   */
  static void initialize() {
    initialize(System.in);
  }

  /**
   * Returns if the scanner is initialized.
   *
   * @return If the scanner is initialized.
   */
  static boolean isInitialized() {
    return scanner != null;
  }

  /**
   * Closes the scanner if it is initialized.
   */
  public static void close() {
    if (InputParser.scanner == null) {
      return;
    }
    scanner.close();
    scanner = null;
  }

  /**
   * Prompts the user and return the given string.
   *
   * @param prompt The prompt to show the user.
   * @return The string the user entered.
   * @throws IllegalStateException If the prompt is null.
   */
  public static @NotNull String getString(@NotNull String prompt) throws IllegalStateException {
    initialize();
    System.out.print(prompt + ": ");
    return scanner.nextLine();
  }

  /**
   * Gets the input from the user without a prompt, and returns the given string.
   * @return The string the user entered.
   */
  public static @NotNull String getString() {
    initialize();
    return scanner.nextLine();
  }

  /**
   * Prompts the user with the given prompt until the user enters a string that matches the given
   * pattern. If the string doesn't match, the error message is shown if it is not null, and the
   * user is prompted again.
   *
   * @param prompt The prompt to show the user.
   * @param pattern The pattern to match the input against.
   * @param errorMessage The error message to show the user if the input doesn't match the pattern.
   *                     Pass {@code null} to not show an error message.
   * @return The string the user entered that matches the pattern.
   * @throws IllegalArgumentException If the prompt or the pattern is null.
   */
  public static @NotNull String getString(
      @NotNull String prompt,
      @NotNull Pattern pattern,
      String errorMessage
  ) throws IllegalArgumentException {
    Matcher result;
    while (true) {
      result = pattern.matcher(getString(prompt));
      if (result.matches()) {
        return result.group();
      }
      if (errorMessage != null) {
        System.out.println(errorMessage);
      }
    }
  }

  /**
   * Prompts the user with the given prompt until the user enters a string that matches the given
   * regex. If the string doesn't match, the error message is shown if it is not null, and the
   * user is prompted again.
   *
   * @param prompt The prompt to show the user.
   * @param regex The regex to match the input against.
   * @param errorMessage The error message to show the user if the input doesn't match the pattern.
   *                     Pass {@code null} to not show an error message.
   * @return The string the user entered that matches the pattern.
   * @throws IllegalArgumentException If the prompt or the pattern is null.
   */
  public static @NotNull String getString(
      @NotNull String prompt,
      @NotNull String regex,
      String errorMessage
  ) throws IllegalArgumentException {
    final Pattern pattern = Pattern.compile(regex);
    return getString(prompt, pattern, errorMessage);
  }

  /**
   * Shorthand for {@link InputParser#getString(String, String, String)}. Defaults the error message
   * to "Strengen passer ikke kriteriene".
   *
   * @see InputParser#getString(String, String, String)
   */
  public static @NotNull String getString(
      @NotNull String prompt,
      @NonNls @NotNull String regex
  ) throws IllegalArgumentException {
    return getString(prompt, regex, "Strengen passer ikke kriteriene");
  }

  /**
   * Shorthand for {@link InputParser#getString(String, Pattern, String)}. Defaults the error message
   * to "Strengen passer ikke kriteriene".
   *
   * @see InputParser#getString(String, Pattern, String)
   */
  public static @NotNull String getString(
      @NotNull String prompt,
      @NotNull Pattern pattern
  ) throws IllegalArgumentException {
    return getString(prompt, pattern, "Strengen passer ikke kriteriene");
  }


  public static int getInt(@NotNull String prompt) throws IllegalArgumentException {
    while (true) {
      try {
        return Integer.parseInt(getString(prompt));
      } catch (NumberFormatException ignored) {
        System.out.println("Verdien er ikke et gyldig tall");
      }
    }
  }

  public static int getInt(
      @NotNull String prompt,
      @NotNull InputValidator<Integer> validator,
      String errorMessage
  ) throws IllegalArgumentException {
    int result;
    while (true) {
      result = getInt(prompt);
      if (validator.test(result)) {
        return result;
      }
      if (errorMessage != null) {
        System.out.println(errorMessage);
      }
    }
  }

  public static int getInt(
      @NotNull String prompt,
      @NotNull InputValidator<Integer> validator
  ) throws IllegalArgumentException {
    return getInt(prompt, validator, "Heltallet passer ikke kriteriene");
  }

  public static float getFloat(@NotNull String prompt) throws IllegalArgumentException {
    while (true) {
      try {
        return Float.parseFloat(getString(prompt));
      } catch (NumberFormatException ignored) {
        System.out.println("Verdien er ikke et gyldig desimaltall");
      }
    }
  }

  public static float getFloat(
      @NotNull String prompt,
      @NotNull InputValidator<Float> validator,
      String errorMessage
  ) throws IllegalArgumentException {
    float result;
    while (true) {
      result = getFloat(prompt);
      if (validator.test(result)) {
        return result;
      }
      if (errorMessage != null) {
        System.out.println(errorMessage);
      }
    }
  }

  public static float getFloat(
      @NotNull String prompt,
      @NotNull InputValidator<Float> validator
  ) throws IllegalArgumentException {
    return getFloat(prompt, validator, "Desimaltallet passer ikke kriteriene");
  }

  public static char getChar(@NotNull String prompt) throws IllegalArgumentException {
    String input;
    while (true) {
      input = getString(prompt, "^.$", "Du kan bare gi ett tegn");
      if (input.length() == 1) {
        return input.charAt(0);
      }
    }
  }

  public static char getChar(
      @NotNull String prompt,
      @NotNull InputValidator<Character> validator,
      String errorMessage
  ) throws IllegalArgumentException {
    char input;
    while (true) {
      input = getChar(prompt);
      if (validator.test(input)) {
        return input;
      }
      if (errorMessage != null) {
        System.out.println(errorMessage);
      }
    }
  }

  public static char getChar(
      @NotNull String prompt,
      @NotNull InputValidator<Character> validator
  ) throws IllegalArgumentException {
    return getChar(prompt, validator, "Tegnet passer ikke kriteriene");
  }

  public static @NotNull LocalTime getTime(@NotNull String prompt) throws IllegalArgumentException {
    final Pattern pattern = Pattern.compile("^[0-2]?[0-9]:[0-5][0-9]$");
    String result;
    while (true) {
      result = getString(
          prompt,
          pattern,
          "Vennligst skriv inn tiden på formatet \"h:mm\" eller \"hh:mm\""
      );
      if (result.length() == 4) {
        result = "0" + result;
      }
      try {
        return LocalTime.parse(result);
      } catch (DateTimeParseException ignored) {
        System.out.println("Tiden er ikke gyldig");
      }
    }
  }

  public static @NotNull LocalTime getTime(
      @NotNull String prompt,
      @NotNull InputValidator<LocalTime> validator,
      String errorMessage
  ) throws IllegalArgumentException {
    LocalTime result;
    while (true) {
      result = getTime(prompt);
      if (validator.test(result)) {
        return result;
      }
      if (errorMessage != null) {
        System.out.println(errorMessage);
      }
    }
  }

  public static @NotNull LocalTime getTime(
      @NotNull String prompt,
      @NotNull InputValidator<LocalTime> validator
  ) throws IllegalArgumentException {
    return getTime(prompt, validator, "Tiden passer ikke kriteriene");
  }

  public static boolean getBoolean(@NotNull String prompt) throws IllegalArgumentException {
    return getString(
        prompt + " [y/n]",
        "^[ynYN]$",
        "Vennligst bare bruk ett av tegnene gitt"
    ).equalsIgnoreCase("y");
  }

  public static boolean getBoolean(
      @NotNull String prompt,
      boolean defaultValue
  ) throws IllegalArgumentException {
    final String result = getString(
        prompt + (defaultValue ? " [Y/n]" : " [y/N]"),
        "^[ynYN]?$",
        "Vennligst bare bruk ett av tegnene gitt"
    );
    if (result.isEmpty()) {
      return defaultValue;
    }
    return result.equalsIgnoreCase("y");
  }
}