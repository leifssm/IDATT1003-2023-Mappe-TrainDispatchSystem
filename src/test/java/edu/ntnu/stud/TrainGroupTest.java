package edu.ntnu.stud;

import java.time.LocalTime;

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
    @DisplayName("Constructor doesn't throw when given no parameters")
    void constructorDoesntThrowWithNoParameters() {
      // Arrange
      // Act
      // Assert
      assertDoesNotThrow(
          TrainGroup::new,
          "Empty list should not throw"
      );
    }

    @Test
    @DisplayName(
        "getDepartureFromNumber() returns the corresponding departure to the train "
            + "number, or null if it doesnt find any"
    )
    void getDepartureFromNumberReturnsCorrespondingDepartureOrNull() {
      // Arrange
      // Act
      TrainDeparture departure1 = departures.getDepartureFromNumber(1);
      TrainDeparture departure2 = departures.getDepartureFromNumber(3);
      TrainDeparture departure3 = departures.getDepartureFromNumber(2);
      TrainDeparture departure4 = departures.getDepartureFromNumber(4);
      // Assert
      assertNotNull(
          departure1,
          "Expected departure to not be null"
      );
      assertNotNull(
          departure2,
          "Expected departure to not be null"
      );
      assertNotNull(
          departure3,
          "Expected departure to not be null"
      );
      assertNull(
          departure4,
          "Expected null when train number is not in use"
      );

      assertEquals(
          "Oslo",
          departure1.getDestination(),
          "Expected destination to be Oslo"
      );
      assertEquals(
          "C3",
          departure2.getLine(),
          "Expected line to be C3"
      );
      assertEquals(
          "Trondheim",
          departure3.getDestination(),
          "Expected destination to be Trondheim"
      );
    }

    @Test
    @DisplayName(
        "doesDepartureExists() should return true if a departure with the given train number exists"
    )
    void doesDepartureExistsReturnsIfADepartureWithAGivenTrainNumberExists() {
      // Arrange
      // Act
      boolean departure1Exists = departures.doesDepartureExists(1);
      boolean departure3Exists = departures.doesDepartureExists(3);
      boolean departure4Exists = departures.doesDepartureExists(4);

      // Assert
      assertTrue(
          departure1Exists,
          "Expected departure with train number 1 to exist"
      );
      assertTrue(
          departure3Exists,
          "Expected departure with train number 3 to exist"
      );
      assertFalse(
          departure4Exists,
          "Expected departure with train number 4 to not exist"
      );
    }

    @Test
    @DisplayName(
        "doesDepartureExists() should return true if a departure similar to the given " +
            "departure exists"
    )
    void doesDepartureExistsReturnsIfADepartureSimilarToADepartureExists() {
      // Arrange
      TrainDeparture existingDeparture = departures.getDepartureFromNumber(2);
      TrainDeparture nonExistingDeparture = new TrainDeparture(
          LocalTime.of(16, 0),
          "D4",
          4,
          "Bergen"
      );
      // Act
      boolean departure2Exists = departures.doesDepartureExists(existingDeparture);
      boolean departure4Exists = departures.doesDepartureExists(nonExistingDeparture);
      // Assert
      assertTrue(
          departure2Exists,
          "Expected departure to exist"
      );
      assertFalse(
          departure4Exists,
          "Didn't expect departure with train number 4 to exist"
      );
    }

    @Test
    @DisplayName(
        "getDepartureFromDestination() should return an array with all matching departures"
    )
    void getDepartureFromDestinationReturnsAnArrayWithMatchingDepartures() {
      // Arrange
      // Act
      TrainDeparture[] osloDepartures = departures.getDepartureFromDestination("Oslo");
      TrainDeparture[] trondheimDepartures = departures.getDepartureFromDestination("Trondheim");
      TrainDeparture[] bergenDepartures = departures.getDepartureFromDestination("Bergen");
      // Assert
      assertEquals(
          1,
          osloDepartures.length,
          "Expected only one departure to Oslo"
      );
      assertEquals(
          2,
          trondheimDepartures.length,
          "Expected two departure to Trondheim"
      );

      assertEquals(
          0,
          bergenDepartures.length,
          "Expected no departures to Bergen"
      );
    }

    @Test
    @DisplayName("getDeparturesFromTime() gets all departures from and after a given time")
    void getDeparturesFromTimeGetsAllDeparturesOnAndAfterTime() {
      // Arrange
      // Act
      TrainDeparture[] allDepartures = departures.getDepartures();
      TrainDeparture[] departuresFromNine = departures.getDeparturesFromTime(
          LocalTime.of(12, 0)
      );
      TrainDeparture[] departuresFromTwelve = departures.getDeparturesFromTime(
          LocalTime.of(12, 1)
      );
      // Assert
      assertEquals(3, allDepartures.length, "Expected three departures");
      assertEquals(2, departuresFromNine.length, "Expected two departures");
      assertEquals(1, departuresFromTwelve.length, "Expected one departures");
    }

    @Test
    @DisplayName("size() should return the size of the group")
    void sizeReturnAmountOfDepartures() {
      // Arrange
      // Act
      int size = departures.size();
      // Assert
      assertEquals(3, size, "Expected three departures");
    }

    @Test
    @DisplayName("removePassedDepartures() removes passed departures")
    void removePassedDeparturesRemovesCorrectDepartures() {
      // Arrange
      // Act
      int removedDeparturesBefore1159 = departures.removeDeparturesBefore(
          LocalTime.of(11, 59)
      );
      int removedDeparturesBefore1200 = departures.removeDeparturesBefore(
          LocalTime.of(12, 0)
      );
      int removedDeparturesBefore1201 = departures.removeDeparturesBefore(
          LocalTime.of(12, 1)
      );
      int removedDeparturesBefore1201Try2 = departures.removeDeparturesBefore(
          LocalTime.of(12, 1)
      );

      // Assert
      assertEquals(
          1,
          removedDeparturesBefore1159,
          "Expected one removed departure"
      );
      assertEquals(
          0,
          removedDeparturesBefore1200,
          "Expected zero removed departures"
      );
      assertEquals(
          1,
          removedDeparturesBefore1201,
          "Expected one removed departure"
      );
      assertEquals(
          0,
          removedDeparturesBefore1201Try2,
          "Expected zero removed departures"
      );
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {
    @Test
    @DisplayName("addDeparture() throws when trying to add null")
    void addDepartureThrowsWithNull() {
      // Arrange
      TrainDeparture departure = null;
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departures.addDeparture(departure),
          "Argument for @NotNull parameter 'departure' of "
              + "edu/ntnu/stud/TrainGroup.addDeparture must not be null",
          "Trying to add null to the group should throw a IllegalArgumentException"
      );
    }

    @Test
    @DisplayName("addDeparture() throws when trying to add a duplicate")
    void addDepartureThrowsWithADuplicateTrainNumber() {
      // Arrange
      TrainDeparture departure = new TrainDeparture(
          LocalTime.of(16, 0),
          "D4",
          2,
          "Bergen"
      );
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departures.addDeparture(departure),
          "Train number already in use",
          "Trying to add a departure with a train number that is already in use should throw"
      );
    }

    @Test
    @DisplayName("getDepartureFromDestination() should throw when given null")
    void getDepartureFromDestination() {
      // Arrange
      String destination = null;
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departures.getDepartureFromDestination(destination),
          "Argument for @NotNull parameter 'destination' of "
              + "edu/ntnu/stud/TrainGroup.getDepartureFromDestination must not be null",
          "Trying to get departures with destination null should throw"
      );
    }

    @Test
    @DisplayName("getDeparturesFromTime() throws when given null")
    void getDeparturesFromTime() {
      // Arrange
      LocalTime time = null;
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departures.getDeparturesFromTime(time),
          "Argument for @NotNull parameter 'time' of "
              + "edu/ntnu/stud/TrainGroup.getDeparturesFromTime must not be null",
          "Trying to get departures from time null should throw"
      );
    }
  }
}