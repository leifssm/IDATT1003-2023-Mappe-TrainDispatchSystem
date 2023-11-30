package edu.ntnu.stud;

import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrainDepartureTest {

  private TrainDeparture departure;

  @BeforeEach
  void setUp() {
    departure = new TrainDeparture(
        LocalTime.of(12, 34),
        "4BU2",
        19,
        "Trondheim",
        9,
        LocalTime.of(3, 5)
    );
  }

  @AfterEach
  void tearDown() {
    departure = null;
  }

  @Test
  void constructor() {
    assertDoesNotThrow(
        () -> new TrainDeparture(
            LocalTime.of(12, 34),
            "4BU2",
            19,
            "Trondheim",
            9,
            LocalTime.of(3, 5)
        ),
        "Expected no exception"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new TrainDeparture(
            LocalTime.of(12, 34),
            null,
            19,
            "Trondheim",
            9,
            LocalTime.of(3, 5)
        ),
        "Argument for @NotNull parameter 'line' of edu/ntnu/stud/TrainDeparture.<init> must not be null",
        "Expected thrown exception when line is null"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new TrainDeparture(
            LocalTime.of(12, 34),
            "A",
            19,
            "Trondheim",
            9,
            LocalTime.of(3, 5)
        ),
        "Line must be a string with a length between 2 and 7 characters.",
        "Expected thrown exception when line name is smaller than 2 characters"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new TrainDeparture(
            LocalTime.of(12, 34),
            "aa",
            19,
            "Trondheim",
            9,
            LocalTime.of(3, 5)
        ),
        "Line can only contain capital letters and numbers",
        "Expected thrown exception when line "
            + "doesnt contain only capital letters or numbers"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new TrainDeparture(
            LocalTime.of(12, 34),
            "ABCDEZDE",
            19,
            "Trondheim",
            9,
            LocalTime.of(3, 5)
        ),
        "Line must be a string with a length between 2 and 7 characters.",
        "Expected thrown exception when line name is longer than 7 characters"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new TrainDeparture(
            LocalTime.of(12, 34),
            "4BU2",
            0,
            "Trondheim",
            9,
            LocalTime.of(3, 5)
        ),
        "Train number cannot be less than 1",
        "Expected thrown exception when train number is smaller than 1"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new TrainDeparture(
            LocalTime.of(12, 34),
            "4BU2",
            -1,
            "Trondheim",
            9,
            LocalTime.of(3, 5)
        ),
        "Train number cannot be less than 1",
        "Expected thrown exception when train number is smaller than 1"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new TrainDeparture(
            LocalTime.of(12, 34),
            "4BU2",
            -1000,
            "Trondheim",
            9,
            LocalTime.of(3, 5)
        ),
        "Train number cannot be less than 1",
        "Expected thrown exception when train number is smaller than 1"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new TrainDeparture(
            LocalTime.of(12, 34),
            "4BU2",
            19,
            null,
            9,
            LocalTime.of(3, 5)
        ),
        "Argument for @NotNull parameter 'destination' of "
            + "edu/ntnu/stud/TrainDeparture.<init> must not be null",
        "Expected thrown exception when destination is null"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new TrainDeparture(
            LocalTime.of(12, 34),
            "4BU2",
            19,
            "",
            9,
            LocalTime.of(3, 5)
        ),
        "Destination cannot be an empty string",
        "Expected thrown exception when destination is null"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new TrainDeparture(
            LocalTime.of(12, 34),
            "4BU2",
            19,
            "Trondheim",
            9,
            null
        ),
        "Argument for @NotNull parameter 'delay' of "
            + "edu/ntnu/stud/TrainDeparture.<init> must not be null",
        "Expected thrown exception when delay is null"
    );
  }

  @Test
  void createRandomDeparture() {
    TrainDeparture departure1 = TrainDeparture.createRandomDeparture(1, "Trondheim");
    TrainDeparture departure2 = TrainDeparture.createRandomDeparture(5, "Alta");
    assertEquals(1, departure1.getTrainNumber());
    assertEquals(5, departure2.getTrainNumber());

    assertEquals("Trondheim", departure1.getDestination());
    assertEquals("Alta", departure2.getDestination());
  }

  @Test
  void testHashCode() {
    assertEquals(departure.hashCode(), 50);
  }

  @Test
  void testEquals() {
    TrainDeparture departureWithSimilarId = TrainDeparture.createRandomDeparture(19, "Moss");
    TrainDeparture departureWithNonSimilarId = TrainDeparture.createRandomDeparture(30, "Moss");

    assertEquals(departure, departureWithSimilarId);
    assertNotEquals(departure, departureWithNonSimilarId);
  }

  @Test
  void setPlannedDeparture() {
    assertEquals(LocalTime.of(12, 34), departure.getPlannedDeparture());
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> departure.setPlannedDeparture(null),
        "Argument for @NotNull parameter 'plannedDeparture' of "
            + "edu/ntnu/stud/TrainDeparture.setPlannedDeparture must not be null",
        "Expected to not be able to set track to 0"
    );
    departure.setPlannedDeparture(LocalTime.of(1, 58));
    assertEquals(LocalTime.of(1, 58), departure.getPlannedDeparture());
  }

  @Test
  void getPlannedDeparture() {
    assertNotEquals(null, departure.getPlannedDeparture());
    assertEquals(LocalTime.of(12, 34), departure.getPlannedDeparture());
  }

  @Test
  void getDelayedDeparture() {
    assertNotEquals(null, departure.getDelayedDeparture());
    assertEquals(LocalTime.of(15, 39), departure.getDelayedDeparture());
  }

  @Test
  void getLine() {
    assertNotEquals(null, departure.getLine());
    assertNotEquals("", departure.getLine());
    assertEquals("4BU2", departure.getLine());
  }

  @Test
  void getDestination() {
    assertNotEquals(null, departure.getDestination());
    assertNotEquals("", departure.getDestination());
    assertEquals("Trondheim", departure.getDestination());
  }

  @Test
  void setDestinaton() {
    departure.setDestination("Kristiansand");
    assertEquals("Kristiansand", departure.getDestination());
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> departure.setDestination(null),
        "Argument for @NotNull parameter 'destination' of "
            + "edu/ntnu/stud/TrainDeparture.setDestination must not be null",
        "Expected to not be able to set track to 0"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> departure.setDestination(""),
        "Destination cannot be an empty string",
        "Expected to not be able to set track to a negative number that isnt -1"
    );
    departure.setDestination("Geilo");
    assertEquals("Geilo", departure.getDestination());
  }

  @Test
  void getTrainNumber() {
    assertNotEquals(-1, departure.getTrainNumber());
    assertEquals(19, departure.getTrainNumber());
  }

  @Test
  void getTrack() {
    assertNotEquals(-1, departure.getTrack());
    assertEquals(9, departure.getTrack());
  }

  @Test
  void setTrack() {
    departure.setTrack(10);
    assertEquals(10, departure.getTrack());
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> departure.setTrack(0),
        "Track id must be greater than 0, or -1 if undefined",
        "Expected to not be able to set track to 0"
    );
    assertEquals(10, departure.getTrack());
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> departure.setTrack(-2),
        "Track id must be greater than 0, or -1 if undefined",
        "Expected to not be able to set track to a negative number that isnt -1"
    );
    departure.setTrack(-1);
    assertEquals(-1, departure.getTrack());
  }

  @Test
  void getDelay() {
    assertNotEquals(null, departure.getDelay());
    assertEquals(LocalTime.of(3, 5), departure.getDelay());
  }

  @Test
  void isDelayed() {
    assertTrue(departure.isDelayed());
    departure.setDelay(LocalTime.MIN);
    assertFalse(departure.isDelayed());
  }

  @Test
  void setDelay() {
    departure.setDelay(LocalTime.of(1, 2));
    assertEquals(LocalTime.of(1, 2), departure.getDelay());
    departure.setDelay(LocalTime.MIN);
    assertEquals(LocalTime.MIN, departure.getDelay());
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> departure.setDelay(null),
        "Argument for @NotNull parameter 'delay' of "
            + "edu/ntnu/stud/TrainDeparture.setDelay must not be null",
        "Expected to not be able to set delay to null"
    );
  }

  @Test
  void testToString() {
    assertEquals(
        "4BU2 (Train #19) to Trondheim: Expected 12:34, but delayed until 15:39 from track 19",
        departure.toString()
    );
    departure.setDelay(LocalTime.MIN);
    assertEquals(
        "4BU2 (Train #19) to Trondheim: Expected 12:34 with no delays from track 19",
        departure.toString()
    );
    departure.setTrack(-1);
    assertEquals(
        "4BU2 (Train #19) to Trondheim: Expected 12:34 with no delays without an assigned track",
        departure.toString()
    );
  }
}