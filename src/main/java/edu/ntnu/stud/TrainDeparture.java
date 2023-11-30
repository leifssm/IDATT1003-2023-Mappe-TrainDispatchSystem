package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a train departure.
 */
public class TrainDeparture {
  /**
   * When the train is planned to depart.
   */
  private LocalTime plannedDeparture;
  /**
   * Which line the train belongs to.
   */
  private final String line;
  /**
   * The train id.
   */
  private final int trainNumber;
  /**
   * The end destination of the train. Must be a string of length greater than 0.
   */
  private String destination;
  /**
   * A number greater than 0 symbolizing which track the train is departing from. Is -1 if not set.
   */
  private int track;
  /**
   * How long the train is delayed. Equals {@link LocalTime#MIN} if not delayed.
   */
  private LocalTime delay;

  /**
   * A function that checks if a given object is equal to {@code this} object.
   *
   * @param o The object to compare to
   * @return A boolean indicating if the functions match
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TrainDeparture departure = (TrainDeparture) o;
    return trainNumber == departure.trainNumber;
  }

  /**
   * A function that returns a hashcode for {@code this} object. Uses the train id as the hashcode.
   *
   * @return A hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(trainNumber);
  }

  /**
   * Creates a train departure with a random departure time, line, track and delay, given a
   * train number and a destination.
   *
   * @param trainNumber The trains number
   * @param destination The trains destination
   * @return A randomized train departure
   */
  static @NotNull TrainDeparture createRandomDeparture(int trainNumber, String destination) {
    final LocalTime plannedDeparture = LocalTime.of(
        (int) (Math.random() * 24),
        (int) (Math.random() * 60)
    );
    final String line = String.format(
        "%c%d",
        (char) (Math.random() * 26 + 'A'),
        (int) (Math.random() * 100)
    );
    final int track = (int) (Math.random() * 100) + 1;
    final LocalTime delay = LocalTime.of(
        (int) (Math.random() * .5 + .5),
        (int) (Math.random() * 60)
    );
    return new TrainDeparture(plannedDeparture, line, trainNumber, destination, track, delay);
  }

  /**
   * Shorthand for {@link TrainDeparture#TrainDeparture(LocalTime, String, int, String, int)}.
   *
   * @see TrainDeparture#TrainDeparture(LocalTime, String, int, String, int)
   */
  TrainDeparture(
      @NotNull LocalTime plannedDeparture,
      @NotNull String line,
      int trainNumber,
      @NotNull String destination
  ) throws IllegalArgumentException {
    this(plannedDeparture, line, trainNumber, destination, -1);
  }

  /**
   * Shorthand for {@link TrainDeparture#TrainDeparture(
   *   LocalTime, String, int, String, int, LocalTime
   * )}.
   *
   * @see TrainDeparture#TrainDeparture(LocalTime, String, int, String, int)
   */
  TrainDeparture(
      @NotNull LocalTime plannedDeparture,
      @NotNull String line,
      int trainNumber,
      @NotNull String destination,
      int track
  ) throws IllegalArgumentException {
    this(plannedDeparture, line, trainNumber, destination, track, LocalTime.MIN);
  }

  /**
   * Creates a train departure with the given parameters.
   *
   * @param plannedDeparture The planned departure time
   * @param line The line name, can only contain capital letters and numbers, must be between 2 and
   *             7 characters, and cannot be null
   * @param trainNumber The train number, must be greater than 0
   * @param destination The destination, cannot be null or of length 0
   * @param track The track id, must be greater than 0 or -1 if not set
   * @param delay The delay, cannot be null
   * @throws IllegalArgumentException If any of the parameters are invalid
   */
  TrainDeparture(
      @NotNull LocalTime plannedDeparture,
      @NotNull String line,
      int trainNumber,
      @NotNull String destination,
      int track,
      @NotNull LocalTime delay
  ) throws IllegalArgumentException {
    if (line.length() < 2 || line.length() > 7) {
      throw new IllegalArgumentException(
          "Line must be a string with a length between 2 and 7 characters."
      );
    }
    if (!Utils.trainLinePattern.matcher(line).matches()) {
      throw new IllegalArgumentException(
          "Line can only contain capital letters and numbers"
      );
    }
    this.line = line;

    if (trainNumber < 1) {
      throw new IllegalArgumentException("Train number cannot be less than 1");
    }
    this.trainNumber = trainNumber;

    setPlannedDeparture(plannedDeparture);
    setDestination(destination);
    setTrack(track);
    setDelay(delay);
  }

  /**
   * Returns the planned departure time.
   *
   * @return The planned departure time
   */
  public @NotNull LocalTime getPlannedDeparture() {
    return plannedDeparture;
  }

  /**
   * Sets the planned departure time.
   *
   * @param plannedDeparture The planned departure time
   * @throws IllegalArgumentException If trying to set planned departure to null
   */
  public void setPlannedDeparture(
      @NotNull LocalTime plannedDeparture
  ) throws IllegalArgumentException {
    this.plannedDeparture = plannedDeparture;
  }

  /**
   * Returns the delayed departure time.
   *
   * @return The departure time with the added delay
   */
  public @NotNull LocalTime getDelayedDeparture() {
    return plannedDeparture
      .plusHours(delay.getHour())
      .plusMinutes(delay.getMinute())
      .plusSeconds(delay.getSecond());
  }

  /**
   * Returns the line name.
   *
   * @return The line name
   */
  public @NotNull String getLine() {
    return line;
  }

  /**
   * Returns the destination.
   *
   * @return The destination
   */
  public @NotNull String getDestination() {
    return destination;
  }

  /**
   * Sets the destination.
   *
   * @param destination The destination, must be a string of length greater than 0
   * @throws IllegalArgumentException If the destination is null or of length 0
   */
  public void setDestination(@NotNull String destination) throws IllegalArgumentException {
    if (destination.isEmpty()) {
      throw new IllegalArgumentException("Destination cannot be an empty string");
    }
    this.destination = destination;
  }

  /**
   * Returns the train number.
   *
   * @return The train number
   */
  public int getTrainNumber() {
    return trainNumber;
  }

  /**
   * Returns the track id.
   *
   * @return The track id
   */
  public int getTrack() {
    return track;
  }

  /**
   * Sets the track id.
   *
   * @param track The track id, must be greater than 0 or -1 if undefined
   * @throws IllegalArgumentException If the track id is 0, or less than -1
   */
  public void setTrack(int track) throws IllegalArgumentException {
    if (track < -1 || track == 0) {
      throw new IllegalArgumentException("Track id must be greater than 0, or -1 if undefined");
    }
    this.track = track;
  }

  /**
   * Returns the delay.
   *
   * @return The delay
   */
  public @NotNull LocalTime getDelay() {
    return delay;
  }

  /**
   * Returns a boolean representing if the train is delayed or not.
   *
   * @return If the train is delayed or not
   */
  public boolean isDelayed() {
    return !delay.equals(LocalTime.MIN);
  }

  /**
   * Sets the delay.
   *
   * @param delay The delay, cannot be null
   * @throws IllegalArgumentException If the delay is null
   */
  public void setDelay(@NotNull LocalTime delay) throws IllegalArgumentException {
    this.delay = delay;
  }

  /**
   * Returns a string representation of the train departure.
   *
   * @return A string representation of the train departure
   */
  public @NotNull String toString() {
    return String.format("%s (Train #%d) to %s: Expected %s%s %s",
      line,
      trainNumber,
      destination,
      plannedDeparture,
      isDelayed() ? ", but delayed until " + getDelayedDeparture() : " with no delays",
      track > 0 ? "from track " + trainNumber : "without an assigned track"
    );
  }
}