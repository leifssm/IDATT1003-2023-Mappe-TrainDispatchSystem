package edu.ntnu.stud;

import java.time.LocalTime;

import org.junit.jupiter.api.*;

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

  @Nested
  @DisplayName("Positive tests")
  class PositiveTests {
    @Test
    @DisplayName("Constructor shouldn't throw with valid arguments")
    void constructorDoesntThrowWithValidValues() {
      // Arrange
      LocalTime plannedDeparture = LocalTime.of(12, 34);
      String line = "4BU2";
      int trainNumber = 19;
      String destination = "Trondheim";
      int track = 9;
      LocalTime delay = LocalTime.of(3, 5);
      // Act
      // Assert
      assertDoesNotThrow(
          () -> new TrainDeparture(
              plannedDeparture,
              line,
              trainNumber,
              destination,
              track,
              delay
          ),
          "Expected no exception"
      );
    }

    @Test
    @DisplayName("createRandomDeparture() should return a departure with random values")
    void createRandomDepartureCreatesRandomDepartures() {
      // Arrange
      int departure1TrainNumber = 1;
      String departure1Destination = "Trondheim";
      int departure2TrainNumber = 5;
      String departure2Destination = "Alta";
      // Act
      TrainDeparture departure1 = TrainDeparture.createRandomDeparture(
          departure1TrainNumber,
          departure1Destination
      );
      TrainDeparture departure2 = TrainDeparture.createRandomDeparture(
          departure2TrainNumber,
          departure2Destination
      );
      // Assert
      assertEquals(departure1TrainNumber, departure1.getTrainNumber());
      assertEquals(departure2TrainNumber, departure2.getTrainNumber());

      assertEquals(departure1Destination, departure1.getDestination());
      assertEquals(departure2Destination, departure2.getDestination());
    }

    @Test
    @DisplayName("hashCode() return a unique int linked to the train number")
    void hashCodeReturnsAnIntRelatedToTheTrainNumber() {
      // Arrange
      int departure1TrainNumber = 23;
      String departure1Destination = "Trondheim";
      int departure2TrainNumber = 84;
      String departure2Destination = "Alta";
      // Act
      TrainDeparture departure1 = TrainDeparture.createRandomDeparture(
          departure1TrainNumber,
          departure1Destination
      );
      TrainDeparture departure2 = TrainDeparture.createRandomDeparture(
          departure2TrainNumber,
          departure2Destination
      );
      // Assert
      assertEquals(54, departure1.hashCode());
      assertEquals(115, departure2.hashCode());
    }

    @Test
    @DisplayName("equals() should compare by train number")
    void equalsComparesByTrainNumber() {
      // Arrange
      int departure1TrainNumber = 19;
      String departure1Destination = "Moss";
      int departure2TrainNumber = 30;
      String departure2Destination = "Moss";
      // Act
      TrainDeparture departureWithSimilarId = TrainDeparture.createRandomDeparture(
          departure1TrainNumber,
          departure1Destination
      );
      TrainDeparture departureWithNonSimilarId = TrainDeparture.createRandomDeparture(
          departure2TrainNumber,
          departure2Destination
      );
      // Assert
      assertEquals(departure, departureWithSimilarId);
      assertNotEquals(departure, departureWithNonSimilarId);
      assertNotEquals(departure, null);
    }

    @Test
    @DisplayName(
        "setPlannedDeparture() should not throw when setting to LocalTime"
    )
    void setPlannedDepartureDoesntThrowWithLocalTime() {
      // Arrange
      LocalTime plannedDeparture = LocalTime.of(1, 58);
      // Act
      departure.setPlannedDeparture(plannedDeparture);
      // Assert
      assertEquals(plannedDeparture, departure.getPlannedDeparture());
    }

    @Test
    @DisplayName("getPlannedDeparture() should return the planned departure")
    void getPlannedDepartureReturnsLocalTime() {
      // Arrange
      LocalTime expectedPlannedDeparture = LocalTime.of(12, 34);
      // Act
      // Assert
      assertEquals(expectedPlannedDeparture, departure.getPlannedDeparture());
    }

    @Test
    @DisplayName("getDelayedDeparture() should return the delayed departure")
    void getDelayedDepartureReturnsPlannedDeparturePlusDelay() {
      // Arrange
      LocalTime expectedPlannedDeparture = LocalTime.of(15, 39);
      // Act
      // Assert
      assertEquals(expectedPlannedDeparture, departure.getDelayedDeparture());
    }

    @Test
    @DisplayName("getLine() should return the line name")
    void getLineReturnsLineName() {
      // Arrange
      String expectedLine = "4BU2";
      // Act
      // Assert
      assertEquals(expectedLine, departure.getLine());
    }

    @Test
    @DisplayName("getDestination() should return the destination")
    void getDestinationReturnsDestination() {
      // Arrange
      String expectedDestination = "Trondheim";
      // Act
      // Assert
      assertEquals(expectedDestination, departure.getDestination());
    }

    @Test
    @DisplayName("setDestination() sets the destination if it matches the requirements")
    void setDestinationSucceedsWhenSettingToStringWithValidCharacters() {
      // Arrange
      String destination = "Geilo - Vest";
      // Act
      departure.setDestination(destination);
      // Assert
      assertEquals(destination, departure.getDestination());
    }

    @Test
    @DisplayName("getTrainNumber() should return the train number")
    void getTrainNumberReturnsTrainNumber() {
      // Arrange
      int expectedTrainNumber = 19;
      // Act
      // Assert
      assertEquals(expectedTrainNumber, departure.getTrainNumber());
    }

    @Test
    @DisplayName("getTrack() should return the assigned track")
    void getTrackReturnsTrack() {
      // Arrange
      int expectedTrack = 9;
      // Act
      // Assert
      assertEquals(expectedTrack, departure.getTrack());
    }

    @Test
    @DisplayName("getDelay() should return the delay")
    void getDelayReturnsDelay() {
      // Arrange
      LocalTime expectedDelay = LocalTime.of(3, 5);
      // Act
      // Assert
      assertEquals(expectedDelay, departure.getDelay());
    }

    @Test
    @DisplayName("isDelayed() should return if the delay is greater than 00:00")
    void isDelayedReturnsIfTheDepartureIsDelayed() {
      // Arrange
      // Assert - Shouldn't be twice, but in this case it should check to not get a false positive
      assertTrue(departure.isDelayed());
      // Act
      departure.setDelay(LocalTime.MIN);
      // Assert
      assertFalse(departure.isDelayed());
    }

    @Test
    @DisplayName("toString() should return a string representation of the departure")
    void toStringReturnsStringRepresentationOfDeparture() {
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

    @Test
    @DisplayName("setDelay() should be able to set to LocalTime")
    void setDelaySucceedsWhenSettingToLocalTime() {
      // Arrange
      LocalTime delay = LocalTime.of(1, 2);
      // Act
      departure.setDelay(delay);
      // Assert
      assertEquals(delay, departure.getDelay());
    }

    @Test
    @DisplayName("setTrack() should not throw when setting to a number greater than 0, or -1")
    void setTrackSucceedsWhenSettingToNegativeOneOrNumberGreaterThanZero() {
      // Arrange
      int track = 10;
      // Act
      departure.setTrack(track);
      // Assert
      assertEquals(track, departure.getTrack());

      // Arrange
      int negativeOneTrack = -1;
      // Act
      departure.setTrack(negativeOneTrack);
      // Assert
      assertEquals(negativeOneTrack, departure.getTrack());
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {
    @Test
    @DisplayName("Constructor should throw with line set to null")
    void constructorShouldThrowWithLineAsNull() {
      // Arrange
      LocalTime plannedDeparture = LocalTime.of(12, 34);
      String line = null;
      int trainNumber = 19;
      String destination = "Trondheim";
      int track = 9;
      LocalTime delay = LocalTime.of(3, 5);
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new TrainDeparture(
              plannedDeparture,
              line,
              trainNumber,
              destination,
              track,
              delay
          ),
          "Argument for @NotNull parameter 'line' of edu/ntnu/stud/TrainDeparture.<init> must not be null",
          "Expected thrown exception when line is null"
      );
    }

    @Test
    @DisplayName("Constructor should throw if the line name is too short")
    void constructorShouldThrowWithShortLine() {
      // Arrange
      LocalTime plannedDeparture = LocalTime.of(12, 34);
      String line = "A";
      int trainNumber = 19;
      String destination = "Trondheim";
      int track = 9;
      LocalTime delay = LocalTime.of(3, 5);
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new TrainDeparture(
              plannedDeparture,
              line,
              trainNumber,
              destination,
              track,
              delay
          ),
          "Line must be a string with a length between 2 and 7 characters.",
          "Expected thrown exception when line name is smaller than 2 characters"
      );
    }

    @Test
    @DisplayName("Constructor should throw when line contains illegal characters")
    void constructorShouldThrowWithLineContainingIllegalCharacters() {
      // Arrange
      LocalTime plannedDeparture = LocalTime.of(12, 34);
      String line = "aaa";
      int trainNumber = 19;
      String destination = "Trondheim";
      int track = 9;
      LocalTime delay = LocalTime.of(3, 5);
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new TrainDeparture(
              plannedDeparture,
              line,
              trainNumber,
              destination,
              track,
              delay
          ),
          "Line can only contain capital letters and numbers",
          "Expected thrown exception when line "
              + "doesnt contain only capital letters or numbers"
      );
    }

    @Test
    @DisplayName("Constructor should throw if the line name is too long")
    void constructorShouldThrowWithLongLine() {
      // Arrange
      LocalTime plannedDeparture = LocalTime.of(12, 34);
      String line = "ABCEFGHIJ";
      int trainNumber = 19;
      String destination = "Trondheim";
      int track = 9;
      LocalTime delay = LocalTime.of(3, 5);
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new TrainDeparture(
              plannedDeparture,
              line,
              trainNumber,
              destination,
              track,
              delay
          ),
          "Line must be a string with a length between 2 and 7 characters.",
          "Expected thrown exception when line name is longer than 2 characters"
      );
    }

    @Test
    @DisplayName("Constructor should throw if the train number is 0")
    void constructorShouldThrowWithZeroAsTrainNumber() {
      // Arrange
      LocalTime plannedDeparture = LocalTime.of(12, 34);
      String line = "4BU2";
      int trainNumber = 0;
      String destination = "Trondheim";
      int track = 9;
      LocalTime delay = LocalTime.of(3, 5);
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new TrainDeparture(
              plannedDeparture,
              line,
              trainNumber,
              destination,
              track,
              delay
          ),
          "Train number cannot be less than 1",
          "Expected thrown exception when train number is 0"
      );
    }

    @Test
    @DisplayName("Constructor should throw if the train number is smaller than 1")
    void constructorShouldThrowWithSmallTrainNumber() {
      // Arrange
      LocalTime plannedDeparture = LocalTime.of(12, 34);
      String line = "4BU2";
      int trainNumber = 0;
      String destination = "Trondheim";
      int track = 9;
      LocalTime delay = LocalTime.of(3, 5);
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new TrainDeparture(
              plannedDeparture,
              line,
              trainNumber,
              destination,
              track,
              delay
          ),
          "Train number cannot be less than 1",
          "Expected thrown exception when train number is smaller than 1"
      );
    }

    @Test
    @DisplayName("Constructor should throw with the destination set to null")
    void constructorShouldThrowWithDestinationAsNull() {
      // Arrange
      LocalTime plannedDeparture = LocalTime.of(12, 34);
      String line = "4BU2";
      int trainNumber = 19;
      String destination = null;
      int track = 9;
      LocalTime delay = LocalTime.of(3, 5);
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new TrainDeparture(
              plannedDeparture,
              line,
              trainNumber,
              destination,
              track,
              delay
          ),
          "Argument for @NotNull parameter 'destination' of "
              + "edu/ntnu/stud/TrainDeparture.<init> must not be null",
          "Expected thrown exception when destination is null"
      );
    }

    @Test
    @DisplayName("Constructor should throw if the destination is an empty string")
    void constructorShouldThrowWithEmptyDestination() {
      // Arrange
      LocalTime plannedDeparture = LocalTime.of(12, 34);
      String line = "4BU2";
      int trainNumber = 19;
      String destination = "";
      int track = 9;
      LocalTime delay = LocalTime.of(3, 5);
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new TrainDeparture(
              plannedDeparture,
              line,
              trainNumber,
              destination,
              track,
              delay
          ),
          "Destination must contain at least 1 character",
          "Expected thrown exception when destination is an empty string"
      );
    }

    @Test
    @DisplayName("Constructor should throw with the delay set to null")
    void constructorShouldThrowWithDelayAsNull() {
      // Arrange
      LocalTime plannedDeparture = LocalTime.of(12, 34);
      String line = "4BU2";
      int trainNumber = 19;
      String destination = "Trondheim";
      int track = 9;
      LocalTime delay = null;
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> new TrainDeparture(
              plannedDeparture,
              line,
              trainNumber,
              destination,
              track,
              delay
          ),
          "Argument for @NotNull parameter 'delay' of "
              + "edu/ntnu/stud/TrainDeparture.<init> must not be null",
          "Expected thrown exception when delay is null"
      );
    }

    @Test
    @DisplayName(
        "setPlannedDeparture() should throw when setting to null"
    )
    void setPlannedDepartureThrowsWhenSettingToNull() {
      // Arrange
      LocalTime plannedDeparture = null;
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setPlannedDeparture(plannedDeparture),
          "Argument for @NotNull parameter 'plannedDeparture' of "
              + "edu/ntnu/stud/TrainDeparture.setPlannedDeparture must not be null",
          "Expected to not be able to set track to null"
      );
    }

    @Test
    @DisplayName("setDestination() should throw when trying to set the destination to null")
    void setDestinationThrowsWhenSettingToNull() {
      // Arrange
      String destination = null;
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setDestination(destination),
          "Argument for @NotNull parameter 'destination' of "
              + "edu/ntnu/stud/TrainDeparture.setDestination must not be null",
          "Expected to not be able to set destination to null"
      );
    }
    @Test
    @DisplayName("setDestination() should throw when trying to set the destination to empty string")
    void setDestinationThrowsWhenSettingToEmptyString() {
      // Arrange
      String destination = "";
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setDestination(destination),
          "Destination must contain at least 1 character",
          "Expected to not be able to set destination to an empty string"
      );
    }

    @Test
    @DisplayName(
        "setDestination() should throw when trying to set the destination to a long string"
    )
    void setDestinationThrowsWhenSettingToLongString() {
      // Arrange
      String destination = "A very long destination";
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setDestination(destination),
          "Destination cannot be longer than 16 characters",
          "Expected to not be able to set destination to an empty string"
      );
    }

    @Test
    @DisplayName(
        "setDestination() should throw when trying to set the destination to a string containing "
            + "illegal characters"
    )
    void setDestinationThrowsWhenSettingToIllegalString() {
      // Arrange
      String destination = "Destination 123";
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setDestination(destination),
          "Destination can only contain capital letters, dashes, and spaces",
          "Expected to not be able to set destination to a string containing " +
              "illegal characters"
      );
    }

    @Test
    @DisplayName("setDelay() should throw if trying to set to null")
    void setDelayThrowsWhenSettingToNull() {
      // Arrange
      LocalTime delay = null;
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setDelay(delay),
          "Argument for @NotNull parameter 'delay' of "
              + "edu/ntnu/stud/TrainDeparture.setDelay must not be null",
          "Expected to not be able to set delay to null"
      );
    }

    @Test
    @DisplayName("setTrack() should throw when setting to 0")
    void setTrackThrowsWhenSettingNumberToZero() {
      // Arrange
      int track = 0;
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setTrack(track),
          "Track id must be greater than 0, or -1 if it is undefined",
          "Expected to not be able to set track to 0"
      );
    }

    @Test
    @DisplayName("setTrack() should throw when setting to a number smaller than -1")
    void setTrackThrowsWhenSettingNumberToANumberSmallerThanNegativeOne() {
      // Arrange
      int track = -2;
      // Act
      // Assert
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setTrack(track),
          "Track id must be greater than 0, or -1 if it is undefined",
          "Expected to not be able to set track to a number smaller than -1"
      );
    }
  }
}