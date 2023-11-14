package edu.ntnu.stud;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TrainGroupTest {
  public static void main(String[] args) {
    // TODO do i really need to test user input

  }

  @AfterEach
  void tearDown() {
    TestHelper.tearDown();
  }

  @Test
  void addDeparture() {
    //TestHelper.setupMockInput("1", "2", "3");
    //TestHelper.setupMockOutput("test:", "testa:");
    //TrainGroup trainGroup = new TrainGroup();
    //trainGroup.addDeparture();
  }

  @Test
  void getDepartureFromNumber() {
  }

  @Test
  void getDepartureFromDestination() {
  }

  @Test
  void getDeparturesFromTime() {
  }

  @Test
  void testGetDeparturesFromTime() {
  }

  @Test
  void removePassedDepartures() {
  }
}