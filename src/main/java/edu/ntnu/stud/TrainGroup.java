package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.platform.commons.util.ToStringBuilder;

/**
 * <h1>TrainGroup.</h1>
 * <p>
 *   A class that represents a group of {@link TrainDeparture train departures}, manages train
 *   number overlap and the sorting, querying, and deleting of
 *   {@link TrainDeparture train departures}.
 * </p>
 * <br>
 * <h2>Role and Responsibility:</h2>
 * <p>
 *   This class is responsible for being a group of {@link TrainDeparture train departures}, with
 *   the options to add, remove and querying departures. This class is not responsible for creating
 *   or validating {@link TrainDeparture train departures}, or for outputting the contents. That is
 *   the responsibility of the {@link TrainInterface} class.
 * </p>
 *
 * @see TrainDeparture
 * @see TrainInterface
 */
public class TrainGroup {
  /**
   * A hashset of all the train departures. The data type hashset was chosen because it does
   * inherently not allow duplicates, which is perfect since the train numbers of the departures are
   * unique. It is final because it should never be replaces, and it is private with no getters
   * because it should never be accessed directly.
   */
  private final @NotNull Set<TrainDeparture> departures = new HashSet<>();

  /**
   * Creates a new empty train group.
   */
  public TrainGroup() {}

  /**
   * Adds a departure to the group if one with the same train number doesn't already exist.
   *
   * @param departure The departure to add. Cannot be null
   * @throws IllegalArgumentException If the train number of the departure is already present.
   */
  public void addDeparture(@NotNull TrainDeparture departure) throws IllegalArgumentException {
    final boolean addedDeparture = departures.add(departure);
    if (!addedDeparture) {
      throw new IllegalArgumentException("Train number already in use");
    }
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
  public boolean doesDepartureExists(TrainDeparture departure) {
    if (departure == null) {
      return false;
    }
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
    // array to iterate over, and an array is faster for that purpose.
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
   * Returns an array with all departures from the given time and forward.
   *
   * @param time The time to get departures query from
   * @return An array with all departures from the given time and forward.
   * @throws IllegalArgumentException If the given time is null
   */
  public @NotNull TrainDeparture[] getDeparturesFromTime(
      @NotNull LocalTime time
  ) throws IllegalArgumentException {
    // Filters the stream by departure time, and returns an array of all matches. I have used an
    // array here because of the same reason listed in getDepartureFromDestination()
    return getSortedDepartureStream()
      .filter(departure -> !departure.getDelayedDeparture().isBefore(time))
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
   * Removes all departures that are before the given time.
   *
   * @param time The time to keep departures after
   * @return The number of departures removed
   * @throws IllegalArgumentException If the given time is null
   */
  public int removeDeparturesBefore(@NotNull LocalTime time) throws IllegalArgumentException {
    // Removes all departures that are not after the given time, and returns the number of removed
    // departures
    final int oldLength = departures.size();
    departures.removeIf(departure -> departure.getDelayedDeparture().isBefore(time));
    return oldLength - departures.size();
  }

  /**
   * Returns a string representation of the object.
   *
   * @return A string representation of the object.
   */
  public @NotNull String toString() {
    return new ToStringBuilder(this)
        .append("departures", departures)
        .toString();
  }
}
