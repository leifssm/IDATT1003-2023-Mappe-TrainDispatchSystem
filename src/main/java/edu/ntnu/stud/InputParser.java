package edu.ntnu.stud;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser {
  public interface InputValidator<T> { boolean test(T input); }
  static Scanner scanner;
  static void initialize() {
    if (scanner != null) return;
    scanner = new Scanner(System.in, StandardCharsets.UTF_8);
  }
  public static void close() {
    if (InputParser.scanner == null) return;
    scanner.close();
    scanner = null;
  }
  public static int getInt(String prompt, InputValidator<Integer> validator) {
    int result;
    while (true) {
      result = getInt(prompt);
      if (validator.test(result)) return result;
    }
  }
  public static int getInt(String prompt) {
    initialize();

    while (true) {
      System.out.print(prompt);
      try {
        return Integer.parseInt(scanner.nextLine());
      } catch (Exception ignored) {};
    }
  }
  public static float getFloat(String prompt, InputValidator<Float> validator) {
    float result;
    while (true) {
      result = getFloat(prompt);
      if (validator.test(result)) return result; 
    }
  }
  public static float getFloat(String prompt) {
    initialize();
    while (true) {
      System.out.print(prompt);
      try {
        return Float.parseFloat(scanner.nextLine());
      } catch (Exception ignored) {};
    }
  }
  public static String getString(String prompt) {
    initialize();
    System.out.print(prompt);
    return scanner.nextLine();
  }
  public static String getString(String prompt, @NonNls @NotNull String regex) {
    initialize();
    final Pattern pattern = Pattern.compile(regex);
    while (true) {
      System.out.print(prompt);
      final Matcher t = pattern.matcher(scanner.nextLine());
      if (t.matches()) return t.group();
    }
  }
  public static String getString(String prompt, Pattern pattern) {
    initialize();
    while (true) {
      System.out.print(prompt);
      final Matcher t = pattern.matcher(scanner.nextLine());
      if (t.matches()) return t.group();
    }
  }
  public static char getChar(String prompt) {
    initialize();
    String input;
    while (true) {
      System.out.print(prompt);
      input = scanner.nextLine();
      if (input.length() == 1) return input.charAt(0);
    }
  }
  public static char getChar(String prompt, InputValidator<Character> validator) {
    initialize();
    String input;
    while (true) {
      System.out.print(prompt);
      input = scanner.nextLine();
      if (input.length() == 1 && validator.test(input.charAt(0))) return input.charAt(0);
    }
  }
  public static LocalTime getTime(String prompt, InputValidator<LocalTime> validator) {
    while (true) {
      final LocalTime result = getTime(prompt);
      if (validator.test(result)) return result;
    }
  }
  public static LocalTime getTime(String prompt) {
    initialize();
    final Pattern pattern = Pattern.compile("^[0-2]?[0-9]:[0-5][0-9]$");
    while (true) {
      final String result = getString(prompt, pattern);
      try {
        return LocalTime.parse(result);
      } catch (Exception ignored) {};
    }
  }
}