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

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {
    @Test
    @DisplayName(
        "Adding a list of departures with the constructor should not throw if they have "
            + "different train numbers"
    )
    void constructorShouldNotThrowWhenAddingTrainsWithDifferentTrainNumbers() {
      ArrayList<TrainDeparture> departures = new ArrayList<>();
      departures.add(
          TrainDeparture.createRandomDeparture(1, "Oslo")
      );
      departures.add(
          TrainDeparture.createRandomDeparture(2, "Trondheim")
      );

      assertDoesNotThrow(
          () -> new TrainGroup(departures),
          "Empty args should not throw"
      );
    }
    @Test
    @DisplayName("Constructor doesn't throw when given no parameters")
    void constructorDoesntThrowWithNoParameters() {
      assertDoesNotThrow(
          () -> new TrainGroup(),
          "Empty list should not throw"
      );
    }

    @Test
    @DisplayName(
        "getDepartureFromNumber() returns the corresponding departure to the train "
            + "number, or null if it doesnt find any"
    )
    void getDepartureFromNumberReturnsCorrespondingDepartureOrNull() {
      TrainDeparture departure1 = departures.getDepartureFromNumber(1);
      assertNotNull(
          departure1,
          "Expected departure to not be null"
      );
      assertEquals(
          "Oslo",
          departure1.getDestination(),
          "Expected destination to be Oslo"
      );
      TrainDeparture departure2 = departures.getDepartureFromNumber(3);
      assertNotNull(
          departure2,
          "Expected departure to not be null"
      );
      assertEquals(
          "C3",
          departure2.getLine(),
          "Expected line to be C3"
      );
      TrainDeparture departure3 = departures.getDepartureFromNumber(2);
      assertNotNull(
          departure3,
          "Expected departure to not be null"
      );
      assertEquals(
          "Trondheim",
          departure3.getDestination(),
          "Expected destination to be Trondheim"
      );
      assertNull(
          departures.getDepartureFromNumber(4),
          "Expected null when train number is not in use"
      );
    }

    @Test
    @DisplayName(
        "doesDepartureExists() should return true if a departure with the given train number exists"
    )
    void doesDepartureExistsReturnsIfADepartureWithAGivenTrainNumberExists() {
      assertTrue(
          departures.doesDepartureExists(1),
          "Expected departure with train number 1 to exist"
      );
      assertTrue(
          departures.doesDepartureExists(3),
          "Expected departure with train number 3 to exist"
      );
      assertFalse(
          departures.doesDepartureExists(4),
          "Expected departure with train number 4 to not exist"
      );
    }

    @Test
    @DisplayName(
        "doesDepartureExists() should return true if a departure similar to the given " +
            "departure exists"
    )
    void doesDepartureExistsReturnsIfADepartureSimilarToADepartureExists() {
      TrainDeparture existingDeparture = departures.getDepartureFromNumber(2);
      assertNotNull(
          existingDeparture,
          "Expected departure to not be null"
      );
      assertTrue(
          departures.doesDepartureExists(existingDeparture),
          "Expected departure to exist"
      );
      TrainDeparture nonExistingDeparture = new TrainDeparture(
          LocalTime.of(16, 0),
          "D4",
          4,
          "Bergen"
      );
      assertFalse(
          departures.doesDepartureExists(nonExistingDeparture),
          "Didn't expect departure with train number 4 to exist"
      );
    }

    @Test
    @DisplayName(
        "getDepartureFromDestination() should return an array with all matching departures"
    )
    void getDepartureFromDestinationReturnsAnArrayWithMatchingDepartures() {
      TrainDeparture[] osloDepartures = departures.getDepartureFromDestination("Oslo");
      assertEquals(1, osloDepartures.length, "Expected only one departure to Oslo");
      TrainDeparture[] trondheimDepartures = departures.getDepartureFromDestination("Trondheim");
      assertEquals(
          2,
          trondheimDepartures.length,
          "Expected two departure to Trondheim"
      );
      TrainDeparture[] bergenDepartures = departures.getDepartureFromDestination("Bergen");

      assertEquals(
          0,
          bergenDepartures.length,
          "Expected no departures to Bergen"
      );
    }

    @Test
    @DisplayName("getDeparturesFromTime() gets all departures from and after a given time")
    void getDeparturesFromTimeGetsAllDeparturesOnAndAfterTime() {
      TrainDeparture[] allDepartures = departures.getDepartures();
      assertEquals(3, allDepartures.length, "Expected three departures");

      TrainDeparture[] departuresFromNine = departures.getDeparturesFromTime(
          LocalTime.of(12, 0)
      );
      assertEquals(2, departuresFromNine.length, "Expected two departures");

      TrainDeparture[] departuresFromTwelve = departures.getDeparturesFromTime(
          LocalTime.of(12, 1)
      );
      assertEquals(1, departuresFromTwelve.length, "Expected one departures");
    }

    @Test
    @DisplayName("size() should return the size of the group")
    void sizeReturnAmountOfDepartures() {
      assertEquals(3, departures.size(), "Expected three departures");
    }

    @Test
    @DisplayName("removePassedDepartures() removes passed departures")
    void removePassedDeparturesRemovesCorrectDepartures() {
      assertEquals(3, departures.size(), "Expected three departures");

      departures.removeDeparturesBefore(LocalTime.of(11, 59));
      assertEquals(2, departures.size(), "Expected two departures");

      departures.removeDeparturesBefore(LocalTime.of(12, 0));
      assertEquals(2, departures.size(), "Expected two departure");

      departures.removeDeparturesBefore(LocalTime.of(12, 1));
      assertEquals(1, departures.size(), "Expected one departure");
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {
    @Test
    @DisplayName("Constructor should throw when given null")
    void constructorThrowsWhenDeparturesIsNull() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new TrainGroup(null),
          "Argument for @NotNull parameter 'departures' of "
              + "edu/ntnu/stud/TrainGroup.<init> must not be null",
          "Trying to create a TrainGroup with null should throw"
      );
    }

    @Test
    @DisplayName(
        "Constructor should throw when given a list with departures with similar train numbers"
    )
    void constructorThrowsWhenGivenListOfDeparturesWithSimilarTrainNumbers() {
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
    }

    @Test
    @DisplayName("Using a list with null in the constructor should throw")
    void constructorThrowsWhenGivenListContainingNull() {
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
    @DisplayName("addDeparture() throws when trying to add null")
    void addDepartureThrowsWithNull() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departures.addDeparture(null),
          "Argument for @NotNull parameter 'departure' of "
              + "edu/ntnu/stud/TrainGroup.addDeparture must not be null",
          "Trying to add null to the group should throw a IllegalArgumentException"
      );
    }

    @Test
    @DisplayName("addDeparture() throws when trying to add a duplicate")
    void addDepartureThrowsWithADuplicateTrainNumber() {
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
    @DisplayName("getDepartureFromDestination() should throw when given null")
    void getDepartureFromDestination() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departures.getDepartureFromDestination(null),
          "Argument for @NotNull parameter 'destination' of "
              + "edu/ntnu/stud/TrainGroup.getDepartureFromDestination must not be null",
          "Trying to get departures with destination null should throw"
      );
    }

    @Test
    @DisplayName("getDeparturesFromTime() throws when given null")
    void getDeparturesFromTime() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departures.getDeparturesFromTime(null),
          "Argument for @NotNull parameter 'time' of "
              + "edu/ntnu/stud/TrainGroup.getDeparturesFromTime must not be null",
          "Trying to get departures from time null should throw"
      );
    }
  }
}