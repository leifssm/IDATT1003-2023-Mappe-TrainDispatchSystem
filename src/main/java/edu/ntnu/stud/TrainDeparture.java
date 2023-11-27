package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public class TrainDeparture {
  private @NotNull LocalTime plannedDeparture;
  private final @NotNull String line;
  private final int trainNumber;
  private @NotNull String destination;
  private int track;
  private @NotNull LocalTime delay;

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

  @Override
  public int hashCode() {
    return Objects.hash(trainNumber);
  }

  static @NotNull TrainDeparture createRandomDeparture(int trainNumber, String destination) {
    final LocalTime plannedDeparture = LocalTime.of(
        (int) (Math.random() * 24),
        (int) (Math.random() * 60)
    );
    final String line = String.format(
        "%s%s",
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

  TrainDeparture(
      LocalTime plannedDeparture,
      String line,
      int trainNumber,
      String destination
  ) throws IllegalArgumentException {
    this(plannedDeparture, line, trainNumber, destination, -1);
  }

  TrainDeparture(
      LocalTime plannedDeparture,
      String line,
      int trainNumber,
      String destination,
      int track
  ) throws IllegalArgumentException {
    this(plannedDeparture, line, trainNumber, destination, track, LocalTime.MIN);
  }

  TrainDeparture(
      LocalTime plannedDeparture,
      String line,
      int trainNumber,
      String destination,
      int track,
      LocalTime delay
  ) throws IllegalArgumentException {
    if (line == null) {
      throw new IllegalArgumentException("Line cannot be null");
    }
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

  public LocalTime getPlannedDeparture() {
    return plannedDeparture;
  }

  public void setPlannedDeparture(
      @NotNull LocalTime plannedDeparture
  ) throws IllegalArgumentException {
    this.plannedDeparture = plannedDeparture;
  }

  public LocalTime getDelayedDeparture() {
    return plannedDeparture
      .plusHours(delay.getHour())
      .plusMinutes(delay.getMinute())
      .plusSeconds(delay.getSecond());
  }

  public @NotNull String getLine() {
    return line;
  }

  public @NotNull String getDestination() {
    return destination;
  }

  public void setDestination(@NotNull String destination) throws IllegalArgumentException {
    if (destination.isEmpty()) {
      throw new IllegalArgumentException("Destination cannot be a string of length 0");
    }
    this.destination = destination;
  }

  public int getTrainNumber() {
    return trainNumber;
  }

  public int getTrack() {
    return track;
  }

  public void setTrack(int track) throws IllegalArgumentException {
    if (track < -1 || track == 0) {
      throw new IllegalArgumentException("Track id must be greater than 0, or -1 if undefined");
    }
    this.track = track;
  }

  public @NotNull LocalTime getDelay() {
    return delay;
  }

  public boolean isDelayed() {
    return !delay.equals(LocalTime.MIN);
  }

  public void setDelay(LocalTime delay) throws IllegalArgumentException {
    this.delay = delay;
  }

  public @NotNull String toString() {
    return String.format("%s (Tog #%s) til %s: Planlagt kl %s%s %s",
      line,
      trainNumber,
      destination,
      plannedDeparture,
      isDelayed() ? ", men forsinket til " + getDelayedDeparture() : " med ingen forsinkelse",
      track > 0 ? "fra spor " + trainNumber : "uten satt spor"
    );
  }
}