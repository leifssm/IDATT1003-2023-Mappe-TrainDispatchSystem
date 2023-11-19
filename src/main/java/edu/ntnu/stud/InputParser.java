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

  public static String getString(String prompt) {
    initialize();
    System.out.print(prompt + ": ");
    return scanner.nextLine();
  }

  public static String getString(String prompt, @NonNls @NotNull String regex) {
    return getString(prompt, regex, "Strengen passer ikke kriteriene");
  }

  public static String getString(
      String prompt,
      @NonNls @NotNull String regex,
      String errorMessage
  ) {
    final Pattern pattern = Pattern.compile(regex);
    return getString(prompt, pattern, errorMessage);
  }

  public static String getString(String prompt, Pattern pattern) {
    return getString(prompt, pattern, "Strengen passer ikke kriteriene");
  }

  public static String getString(String prompt, Pattern pattern, String errorMessage) {
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

  public static int getInt(String prompt) {
    while (true) {
      try {
        return Integer.parseInt(getString(prompt));
      } catch (NumberFormatException ignored) {
        System.out.println("Verdien er ikke et gyldig tall");
      }
    }
  }

  public static int getInt(String prompt, InputValidator<Integer> validator) {
    return getInt(prompt, validator, "Heltallet passer ikke kriteriene");
  }

  public static int getInt(String prompt, InputValidator<Integer> validator, String errorMessage) {
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

  public static float getFloat(String prompt) {
    while (true) {
      try {
        return Float.parseFloat(getString(prompt));
      } catch (NumberFormatException ignored) {
        System.out.println("Verdien er ikke et gyldig desimaltall");
      }
    }
  }

  public static float getFloat(String prompt, InputValidator<Float> validator) {
    return getFloat(prompt, validator, "Desimaltallet passer ikke kriteriene");
  }

  public static float getFloat(
      String prompt,
      InputValidator<Float> validator,
      String errorMessage
  ) {
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

  public static char getChar(String prompt) {
    String input;
    while (true) {
      input = getString(prompt, "^.$", null);
      if (input.length() == 1) {
        return input.charAt(0);
      }
      System.out.println("Du kan ikke gi flere tegn");
    }
  }

  public static char getChar(String prompt, InputValidator<Character> validator) {
    return getChar(prompt, validator, "Tegnet passer ikke kriteriene");
  }

  public static char getChar(
      String prompt,
      InputValidator<Character> validator,
      String errorMessage
  ) {
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

  public static LocalTime getTime(String prompt) {
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
      };
      try {
        return LocalTime.parse(result);
      } catch (DateTimeParseException ignored) {
        System.out.println("Tiden er ikke gyldig");
      }
    }
  }

  public static LocalTime getTime(
      String prompt,
      InputValidator<LocalTime> validator,
      String errorMessage
  ) {
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

  public static LocalTime getTime(String prompt, InputValidator<LocalTime> validator) {
    return getTime(prompt, validator, "Tiden passer ikke kriteriene");
  }

  public static boolean getBoolean(String prompt) {
    return getString(
        prompt + " [y/n]",
        "^[ynYN]$",
        "Vennligst bare bruk ett av tegnene gitt"
    ).equalsIgnoreCase("y");
  }

  public static boolean getBoolean(String prompt, boolean defaultValue) {
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