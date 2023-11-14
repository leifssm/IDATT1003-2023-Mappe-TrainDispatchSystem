package edu.ntnu.stud;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TrainDepartureTest {

  private TrainDeparture departure;
  @BeforeEach
  void setUp() {
    departure = new TrainDeparture(LocalTime.of(12, 34), "4BU2", 19, "Trondheim", 9, LocalTime.of(3, 5));
  }

  @AfterEach
  void tearDown() {
    departure = null;
  }

  @Test
  void constructor() {
    assertDoesNotThrow(() ->
        new TrainDeparture(
            LocalTime.of(12, 34),
            "4BU2",
            19,
            "Trondheim",
            9,
            LocalTime.of(3, 5)
        )
    );
    assertThrows(InvalidParameterException.class, () ->
        new TrainDeparture(
            null,
            "4BU2",
            19,
            "Trondheim",
            9,
            LocalTime.of(3, 3)
        )
    );
    assertThrows(InvalidParameterException.class, () ->
        new TrainDeparture(
            LocalTime.of(12, 34),
            null,
            19,
            "Trondheim",
            9,
            LocalTime.of(3, 5)
        )
    );
    assertDoesNotThrow(() ->
        new TrainDeparture(
            LocalTime.of(12, 34),
            "4BU2",
            -1,
            "Trondheim",
            9,
            LocalTime.of(3, 5)
        )
    );
    assertThrows(InvalidParameterException.class, () ->
        new TrainDeparture(
            LocalTime.of(12, 34),
            "4BU2",
            -2,
            "Trondheim",
            9,
            LocalTime.of(3, 5)
        )
    );
    assertThrows(InvalidParameterException.class, () ->
        new TrainDeparture(
            LocalTime.of(12, 34),
            "4BU2",
            -1000,
            "Trondheim",
            9,
            LocalTime.of(3, 5)
        )
    );
    assertThrows(InvalidParameterException.class, () ->
        new TrainDeparture(
            LocalTime.of(12, 34),
            "4BU2",
            19,
            null,
            9,
            LocalTime.of(3, 5)
        )
    );
    assertThrows(InvalidParameterException.class, () ->
        new TrainDeparture(
            LocalTime.of(12, 34),
            "4BU2",
            19,
            "Trondheim",
            9,
            null
        )
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
  void getTrainNumber() {
    assertNotEquals(-1, departure.getTrainNumber());
    assertEquals(19, departure.getTrainNumber());
  }

  @Test
  void getTrack() {
    // not really required
    assertNotEquals(-1, departure.getTrack());
    assertEquals(9, departure.getTrack());
  }

  @Test
  void setTrack() {
    departure.setTrack(10);
    assertEquals(10, departure.getTrack());
    departure.setTrack(0);
    assertEquals(0, departure.getTrack());
    // TODO add test for error message
    assertThrows(InvalidParameterException.class, () -> departure.setTrack(-1));
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
    assertThrows(InvalidParameterException.class, () -> departure.setDelay(null));
  }

  @Test
  void testToString() {
    assertEquals("4BU2 til Trondheim (Tog #19): Planlagt avgang 12:34, forsinket til 15:39 fra spor 9", departure.toString());
    departure.setDelay(LocalTime.MIN);
    assertEquals("4BU2 til Trondheim (Tog #19): Planlagt avgang 12:34, med ingen forsinkelse fra spor 9", departure.toString());
    // departure.setTrack(-1);
    // TODO add test for -1
  }
}