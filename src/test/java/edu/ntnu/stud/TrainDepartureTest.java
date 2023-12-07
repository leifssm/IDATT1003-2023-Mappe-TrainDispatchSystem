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
    }

    @Test
    @DisplayName("createRandomDeparture() should return a departure with random values")
    void createRandomDepartureCreatesRandomDepartures() {
      TrainDeparture departure1 = TrainDeparture.createRandomDeparture(1, "Trondheim");
      TrainDeparture departure2 = TrainDeparture.createRandomDeparture(5, "Alta");
      assertEquals(1, departure1.getTrainNumber());
      assertEquals(5, departure2.getTrainNumber());

      assertEquals("Trondheim", departure1.getDestination());
      assertEquals("Alta", departure2.getDestination());
    }

    @Test
    @DisplayName("hashCode() return a unique int linked to the train number")
    void hashCodeReturnsAnIntRelatedToTheTrainNumber() {
      TrainDeparture departure1 = TrainDeparture.createRandomDeparture(23, "Trondheim");
      TrainDeparture departure2 = TrainDeparture.createRandomDeparture(84, "Alta");
      assertEquals(54, departure1.hashCode());
      assertEquals(115, departure2.hashCode());
    }

    @Test
    @DisplayName("equals() should compare by train number")
    void equalsComparesByTrainNumber() {
      TrainDeparture departureWithSimilarId = TrainDeparture.createRandomDeparture(19, "Moss");
      TrainDeparture departureWithNonSimilarId = TrainDeparture.createRandomDeparture(30, "Moss");

      assertEquals(departure, departureWithSimilarId);
      assertNotEquals(departure, departureWithNonSimilarId);
      assertNotEquals(departure, null);
    }

    @Test
    @DisplayName(
        "setPlannedDeparture() should not throw when setting to LocalTime"
    )
    void setPlannedDepartureDoesntThrowWithLocalTime() {
      departure.setPlannedDeparture(LocalTime.of(1, 58));
      assertEquals(LocalTime.of(1, 58), departure.getPlannedDeparture());
    }

    @Test
    @DisplayName("getPlannedDeparture() should return the planned departure")
    void getPlannedDepartureReturnsLocalTime() {
      assertEquals(LocalTime.of(12, 34), departure.getPlannedDeparture());
    }

    @Test
    @DisplayName("getDelayedDeparture() should return the delayed departure")
    void getDelayedDepartureReturnsPlannedDeparturePlusDelay() {
      assertEquals(LocalTime.of(15, 39), departure.getDelayedDeparture());
    }

    @Test
    @DisplayName("getLine() should return the line name")
    void getLineReturnsLineName() {
      assertEquals("4BU2", departure.getLine());
    }

    @Test
    @DisplayName("getDestination() should return the destination")
    void getDestinationReturnsDestination() {
      assertEquals("Trondheim", departure.getDestination());
    }

    @Test
    @DisplayName("setDestination() sets the destination if it matches the requirements")
    void setDestinationSucceedsWhenSettingToStringWithValidCharacters() {
      departure.setDestination("Geilo - Vest");
      assertEquals("Geilo - Vest", departure.getDestination());
    }

    @Test
    @DisplayName("getTrainNumber() should return the train number")
    void getTrainNumberReturnsTrainNumber() {
      assertEquals(19, departure.getTrainNumber());
    }

    @Test
    @DisplayName("getTrack() should return the assigned track")
    void getTrackReturnsTrack() {
      assertEquals(9, departure.getTrack());
    }

    @Test
    @DisplayName("getDelay() should return the delay")
    void getDelayReturnsDelay() {
      assertEquals(LocalTime.of(3, 5), departure.getDelay());
    }

    @Test
    @DisplayName("isDelayed() should return if the delay is greater than 00:00")
    void isDelayedReturnsIfTheDepartureIsDelayed() {
      assertTrue(departure.isDelayed());
      departure.setDelay(LocalTime.MIN);
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
      departure.setDelay(LocalTime.of(1, 2));
      assertEquals(LocalTime.of(1, 2), departure.getDelay());
      departure.setDelay(LocalTime.MIN);
      assertFalse(departure.isDelayed());
    }

    @Test
    @DisplayName("setTrack() should not throw when setting to a number greater than 0, or -1")
    void setTrackSucceedsWhenSettingToNegativeOneOrNumberGreaterThanZero() {
      departure.setTrack(10);
      assertEquals(10, departure.getTrack());
      departure.setTrack(-1);
      assertEquals(-1, departure.getTrack());
    }
  }

  @Nested
  @DisplayName("Negative tests")
  class NegativeTests {
    @Test
    @DisplayName("Constructor should throw with line set to null")
    void constructorShouldThrowWithLineAsNull() {
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
    }

    @Test
    @DisplayName("Constructor should throw if the line name is too short")
    void constructorShouldThrowWithShortLine() {
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
    }

    @Test
    @DisplayName("Constructor should throw when line contains illegal characters")
    void constructorShouldThrowWithLineContainingIllegalCharacters() {
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
    }

    @Test
    @DisplayName("Constructor should throw if the line name is too long")
    void constructorShouldThrowWithLongLine() {
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
          "Expected thrown exception when line name is longer than 2 characters"
      );
    }

    @Test
    @DisplayName("Constructor should throw if the train number is smaller than 0")
    void constructorShouldThrowWithSmallTrainNumber() {
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
    }

    @Test
    @DisplayName("Constructor should throw with the destination set to null")
    void constructorShouldThrowWithDestinationAsNull() {
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
    }

    @Test
    @DisplayName("Constructor should throw if the destination is an empty string")
    void constructorShouldThrowWithEmptyDestination() {
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
          "Destination must contain at least 1 character",
          "Expected thrown exception when destination is an empty string"
      );
    }

    @Test
    @DisplayName("Constructor should throw with the delay set to null")
    void constructorShouldThrowWithDelayAsNull() {
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
    @DisplayName(
        "setPlannedDeparture() should throw when setting to null"
    )
    void setPlannedDepartureThrowsWhenSettingToNull() {
      assertEquals(LocalTime.of(12, 34), departure.getPlannedDeparture());
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setPlannedDeparture(null),
          "Argument for @NotNull parameter 'plannedDeparture' of "
              + "edu/ntnu/stud/TrainDeparture.setPlannedDeparture must not be null",
          "Expected to not be able to set track to null"
      );
    }

    @Test
    @DisplayName("setDestination() should throw when trying to set the destination to null")
    void setDestinationThrowsWhenSettingToNull() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setDestination(null),
          "Argument for @NotNull parameter 'destination' of "
              + "edu/ntnu/stud/TrainDeparture.setDestination must not be null",
          "Expected to not be able to set destination to null"
      );
    }
    @Test
    @DisplayName("setDestination() should throw when trying to set the destination to empty string")
    void setDestinationThrowsWhenSettingToEmptyString() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setDestination(""),
          "Destination must contain at least 1 character",
          "Expected to not be able to set destination to an empty string"
      );
    }

    @Test
    @DisplayName(
        "setDestination() should throw when trying to set the destination to a long string"
    )
    void setDestinationThrowsWhenSettingToLongString() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setDestination("A very long destination"),
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
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setDestination("Destination 123"),
          "Destination can only contain capital letters, dashes, and spaces",
          "Expected to not be able to set destination to a string containing " +
              "illegal characters"
      );
    }

    @Test
    @DisplayName("setDelay() should throw if trying to set to null")
    void setDelayThrowsWhenSettingToNull() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setDelay(null),
          "Argument for @NotNull parameter 'delay' of "
              + "edu/ntnu/stud/TrainDeparture.setDelay must not be null",
          "Expected to not be able to set delay to null"
      );
    }

    @Test
    @DisplayName("setTrack() should throw when setting to 0 or a number smaller than -1")
    void setTrackThrowsWhenSettingNumberToZeroOrANumberSmallerThanNegativeOne() {
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setTrack(0),
          "Track id must be greater than 0, or -1 if it is undefined",
          "Expected to not be able to set track to 0"
      );
      TestHelper.assertThrowsWithMessage(
          IllegalArgumentException.class,
          () -> departure.setTrack(-2),
          "Track id must be greater than 0, or -1 if it is undefined",
          "Expected to not be able to set track to a number smaller than -1"
      );
    }
  }
}