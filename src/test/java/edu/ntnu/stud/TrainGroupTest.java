package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TrainGroupTest {
  private TrainGroup departures;

  @BeforeEach
  void setUp() {
    departures = new TrainGroup();
    departures.addDeparture(new TrainDeparture(
        LocalTime.of(12, 0),
        "A1",
        1,
        "Oslo"
    ));
    departures.addDeparture(new TrainDeparture(
        LocalTime.of(12, 30),
        "B2",
        2,
        "Trondheim"
    ));
    departures.addDeparture(new TrainDeparture(
        LocalTime.of(9, 0),
        "C3",
        3,
        "Trondheim"
    ));
  }

  @AfterEach
  void tearDown() {
    departures = null;
  }

  @Test
  @DisplayName("Tests all combinations of valid and invalid arguments for the constructor")
  void constructor() {
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new TrainGroup(null),
        "Argument for @NotNull parameter 'departures' of "
            + "edu/ntnu/stud/TrainGroup.<init> must not be null",
        "Trying to create a TrainGroup with null should throw"
    );
    ArrayList<TrainDeparture> departures = new ArrayList<>();
    departures.add(new TrainDeparture(
        LocalTime.of(12, 0),
        "A1",
        1,
        "Oslo"
    ));
    departures.add(new TrainDeparture(
        LocalTime.of(12, 30),
        "B2",
        1,
        "Trondheim"
    ));
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new TrainGroup(departures),
        "List of departures cannot contain duplicate train numbers",
        "Trying to create a TrainGroup with duplicates should throw"
    );
    assertDoesNotThrow(
        () -> new TrainGroup(new ArrayList<>()),
        "Empty args should not throw"
    );
    assertDoesNotThrow(
        () -> new TrainGroup(),
        "Empty list should not throw"
    );
    ArrayList<TrainDeparture> departuresWithNull = new ArrayList<>();
    departuresWithNull.add(null);
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> new TrainGroup(departuresWithNull),
        "List of departures cannot contain null",
        "Trying to create a TrainGroup with null should throw"
    );
  }

  @Test
  @DisplayName("addDeparture() throws when any given parameter is null or a duplicate")
  void addDeparture() {
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> departures.addDeparture(null),
        "Argument for @NotNull parameter 'departure' of "
            + "edu/ntnu/stud/TrainGroup.addDeparture must not be null",
        "Trying to add null to the group should throw a IllegalArgumentException"
    );
    TrainDeparture departure = new TrainDeparture(
        LocalTime.of(16, 0),
        "D4",
        2,
        "Bergen"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> departures.addDeparture(new TrainDeparture(
            LocalTime.of(16, 0),
            "D4",
            2,
            "Bergen"
        )),
        "Train number already in use",
        "Trying to add a departure with a train number that is already in use should throw"
    );
  }

  @Test
  @DisplayName(
      "getDepartureFromNumber() should a departure with a matching train number, or null if "
          + "one doesn't exist"
  )
  void getDepartureFromNumber() {
    TrainDeparture departure1 = departures.getDepartureFromNumber(1);
    assertEquals(
        "Oslo",
        departure1.getDestination(),
        "Expected destination to be Oslo"
    );
    TrainDeparture departure2 = departures.getDepartureFromNumber(2);
    assertEquals(
        "Trondheim",
        departure2.getDestination(),
        "Expected destination to be Trondheim"
    );
    TrainDeparture departure3 = departures.getDepartureFromNumber(3);
    assertEquals(
        "C3",
        departure3.getLine(),
        "Expected line to be C3"
    );
    assertNull(
        departures.getDepartureFromNumber(4),
        "Expected null when train number is not in use"
    );
  }

  @Test
  @DisplayName("getDepartureFromDestination() should return an array with all mathing departures")
  void getDepartureFromDestination() {
    TrainDeparture[] osloDepartures = departures.getDepartureFromDestination("Oslo");
    assertEquals(1, osloDepartures.length, "Expected only one departure to Oslo");
    TrainDeparture[] trondheimDepartures = departures.getDepartureFromDestination("Trondheim");
    assertEquals(
        2,
        trondheimDepartures.length,
        "Expected two departure to Trondheim"
    );
    assertEquals("B2", trondheimDepartures[0].getLine(), "Expected line to be B2");
    assertEquals("C3", trondheimDepartures[1].getLine(), "Expected line to be C3");
    assertEquals(
        0,
        departures.getDepartureFromDestination("Bergen").length,
        "Expected no departures to Bergen"
    );
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> departures.getDepartureFromDestination(null),
        "Argument for @NotNull parameter 'destination' of "
            + "edu/ntnu/stud/TrainGroup.getDepartureFromDestination must not be null",
        "Trying to get departures with destination null should throw"
    );
  }

  @Test
  @DisplayName("getDeparturesFromTime() gets all departures after a given time")
  void getDeparturesFromTime() {
    TrainDeparture[] allDepartures = departures.getDepartures();
    assertEquals(3, allDepartures.length, "Expected three departures");
    TrainDeparture[] departuresFromNine = departures.getDeparturesFromTime(
        LocalTime.of(11, 59)
    );
    assertEquals(2, departuresFromNine.length, "Expected two departures");
    TrainDeparture[] departuresFromTwelve = departures.getDeparturesFromTime(
        LocalTime.of(12, 0)
    );
    assertEquals(1, departuresFromTwelve.length, "Expected only one departure");
    TestHelper.assertThrowsWithMessage(
        IllegalArgumentException.class,
        () -> departures.getDeparturesFromTime(null),
        "Argument for @NotNull parameter 'time' of "
            + "edu/ntnu/stud/TrainGroup.getDeparturesFromTime must not be null",
        "Trying to get departures from time null should throw"
    );
  }

  @Test
  @DisplayName("size() should return the size of the group")
  void size() {
    assertEquals(3, departures.size(), "Expected three departures");
  }

  @Test
  @DisplayName("removePassedDepartures() removes passed departures")
  void removePassedDepartures() {
    assertEquals(3, departures.size(), "Expected three departures");
    departures.removeDeparturesBefore(LocalTime.of(11, 59));
    assertEquals(2, departures.size(), "Expected two departures");
    departures.removeDeparturesBefore(LocalTime.of(12, 0));
    assertEquals(1, departures.size(), "Expected one departure");
  }
}