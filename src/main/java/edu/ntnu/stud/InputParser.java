package edu.ntnu.stud;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser {
  public interface FloatInputValidator { boolean test(float input); }
  public interface IntegerInputValidator { boolean test(int input); }
  public interface CharInputValidator { boolean test(char input); }
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
  public static int getInt(String prompt, IntegerInputValidator validator) {
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
  public static float getFloat(String prompt, FloatInputValidator validator) {
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
  public static char getChar(String prompt, CharInputValidator validator) {
    initialize();
    String input;
    while (true) {
      System.out.print(prompt);
      input = scanner.nextLine();
      if (input.length() == 1 && validator.test(input.charAt(0))) return input.charAt(0);
    }
  }
  public static LocalTime getTime(String prompt) {
    initialize();
    LocalTime output;
    final Pattern pattern = Pattern.compile("^[0-2]?[0-9]:[0-5][0-9]$");
    while (true) {
      final String result = getString(prompt, pattern);
      try {
        return LocalTime.parse(result);
      } catch (Exception ignored) {};
    }
  }
}