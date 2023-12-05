package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class represents a group of train departures, manages train number overlap and the sorting,
 * querying, and deleting of train departures.
 */
public class TrainGroup {
  /**
   * A list of all the train departures.
   */
  private final @NotNull List<TrainDeparture> departures = new ArrayList<>();

  /**
   * Create a new empty train group.
   */
  TrainGroup() {}

  /**
   * Create a new train group with the given departures.
   *
   * @param departures The departures to add to the group.
   * @throws IllegalArgumentException If the list of departures contains null or duplicate train
   *                                  numbers.
   */
  TrainGroup(@NotNull List<@NotNull TrainDeparture> departures) throws IllegalArgumentException {
    // Tries adding all the departures to the group for all elements in the list or until it throws
    try {
      for (TrainDeparture departure : departures) {
        addDeparture(departure);
      }
    } catch (IllegalArgumentException e) {
      // The only two errors that can be thrown is a null error and duplicate train number error,
      // so the program chooses the error based on the thrown message
      if (Objects.equals(e.getMessage(), "Train number already in use")) {
        throw new IllegalArgumentException(
            "List of departures cannot contain duplicate train numbers",
            e
        );
      }
      throw new IllegalArgumentException("List of departures cannot contain null", e);
    }
  }

  /**
   * Adds a departure to the group.
   *
   * @param departure The departure to add. Cannot be null
   * @throws IllegalArgumentException If the train number of the departure is already present.
   */
  public void addDeparture(@NotNull TrainDeparture departure) throws IllegalArgumentException {
    // Throws if the train number is already in use
    if (doesDepartureExists(departure)) {
      throw new IllegalArgumentException("Train number already in use");
    }
    departures.add(departure);
  }

  /**
   * Returns the departure with the given train number, or null if it doesn't find one.
   *
   * @param trainNumber The train number to search for
   * @return The departure with the given train number, or null if it doesn't find one.
   */
  public @Nullable TrainDeparture getDepartureFromNumber(int trainNumber) {
    // Creates a stream, and filters it by matching train number. Returns any match or null, which
    // is not a problem since the train number is unique
    return departures
        .stream()
        .filter(departure -> departure.getTrainNumber() == trainNumber)
        .findAny()
        .orElse(null);
  }


  /**
   * Returns true if the departure with the given train number exists.
   *
   * @param trainNumber The train number to search by
   * @return True if the departure with the given train number exists.
   */
  public boolean doesDepartureExists(int trainNumber) {
    return getDepartureFromNumber(trainNumber) != null;
  }

  /**
   * Returns true if a departure with a similar train number to the given departure exists.
   *
   * @param departure The train departure to search with
   * @return True a similar departure exists.
   */
  public boolean doesDepartureExists(@NotNull TrainDeparture departure) {
    return departures.contains(departure);
  }

  /**
   * Returns an array with the departures with the given destination.
   *
   * @param destination The destination to query with
   * @return An array with the departures that match the given destination.
   * @throws IllegalArgumentException If the destination is null
   */
  public @NotNull TrainDeparture[] getDepartureFromDestination(
      @NotNull String destination
  ) throws IllegalArgumentException {
    // Creates a stream, and filters it by matching the destination, and then returns an array of
    // all matches. Here I have decided to use an array instead of a list, since I only use the
    // value to iterate over, and an array is faster for that purpose.
    return departures
        .stream()
        .filter(departure -> departure.getDestination().equals(destination))
        .toArray(TrainDeparture[]::new);
  }

  /**
   * A helper function that returns a stream of departures sorted by planned departure time.
   *
   * @return A stream of departures sorted by planned departure time.
   */
  private @NotNull Stream<TrainDeparture> getSortedDepartureStream() {
    return departures
      .stream()
      .sorted(
          Comparator.comparing(TrainDeparture::getPlannedDeparture)
      );
  }

  /**
   * Returns an array with all departures after the given time and forward.
   *
   * @param time The time to get departures query after
   * @return An array with all departures after the given time and forward.
   * @throws IllegalArgumentException If the given time is null
   */
  public @NotNull TrainDeparture[] getDeparturesFromTime(
      @NotNull LocalTime time
  ) throws IllegalArgumentException {
    // Filters the stream by departure time, and returns an array of all matches. I have used an
    // array here because of the same reason listed in getDepartureFromDestination()
    return getSortedDepartureStream()
      .filter(departure -> departure.getDelayedDeparture().isAfter(time))
      .toArray(TrainDeparture[]::new);
  }

  /**
   * Returns a sorted array containing all the departures in the group.
   *
   * @return A sorted array containing all the departures in the group.
   */
  public @NotNull TrainDeparture[] getDepartures() {
    // Returns all departures as an array, sorted by departure time, I have used an array here
    // because of the same reason listed in getDepartureFromDestination()
    return getSortedDepartureStream().toArray(TrainDeparture[]::new);
  }

  /**
   * Returns the number of departures in the group.
   *
   * @return The number of departures in the group.
   */
  public int size() {
    return departures.size();
  }

  /**
   * Removes all departures that are not after the given time.
   *
   * @param time The time to keep departures after
   * @return The number of departures removed
   * @throws IllegalArgumentException If the given time is null
   */
  public int removePassedDepartures(@NotNull LocalTime time) throws IllegalArgumentException {
    // Removes all departures that are not after the given time, and returns the number of removed
    // departures
    final int oldLength = departures.size();
    departures.removeIf(departure -> !departure.getDelayedDeparture().isAfter(time));
    return oldLength - departures.size();
  }
}
