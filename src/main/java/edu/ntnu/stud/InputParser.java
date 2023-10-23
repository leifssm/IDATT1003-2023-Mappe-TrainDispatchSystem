package edu.ntnu.stud;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

interface FloatInputValidator { boolean test(float input); }
interface IntegerInputValidator { boolean test(int input); }
public class InputParser {
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
    int result = 0;
    String input;
    while (true) {
      System.out.print(prompt);
      input = scanner.nextLine();
      try {
        result = Integer.parseInt(input);
        return result;
      } catch (Exception e) {};
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
  public static char getChar(String prompt) {
    initialize();
    String input;
    while (true) {
      System.out.print(prompt);
      input = scanner.nextLine();
      if (input.length() == 1) return input.charAt(0);
    }
  }
}