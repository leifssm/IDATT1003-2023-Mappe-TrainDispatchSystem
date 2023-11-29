package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

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
    try {
      for (TrainDeparture departure : departures) {
        addDeparture(departure);
      }
    } catch (IllegalArgumentException e) {
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
    final int id = departure.getTrainNumber();
    if (getDepartureFromNumber(id) != null) {
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
  public TrainDeparture getDepartureFromNumber(int trainNumber) {
    return departures
        .stream()
        .filter(departure -> departure.getTrainNumber() == trainNumber)
        .findFirst()
        .orElse(null);
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
      .sorted((a, b) -> {
        final LocalTime aDeparture = a.getPlannedDeparture();
        final LocalTime bDeparture = b.getPlannedDeparture();
        return aDeparture.compareTo(bDeparture);
      });
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
    // TODO add reason for array or just dont return array
    return getSortedDepartureStream()
      .filter(departure -> departure.getDelayedDeparture().isAfter(time))
      //.limit(LINES)
      .toArray(TrainDeparture[]::new);
  }

  /**
   * Returns an array with all departures in the group.
   *
   * @return An array with all departures in the group.
   */
  public @NotNull TrainDeparture[] getDeparturesFromTime() {
    return getSortedDepartureStream().toArray(TrainDeparture[]::new);
  }

  /**
   * Returns the number of departures in the group.
   *
   * @return The number of departures in the group.
   */
  public int length() {
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
    final int oldLength = departures.size();
    departures.removeIf(departure -> !departure.getDelayedDeparture().isAfter(time));
    return oldLength - departures.size();
  }
}
