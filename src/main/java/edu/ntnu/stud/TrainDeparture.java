package edu.ntnu.stud;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class TrainDeparture {
  private final LocalTime plannedDeparture;
  private final String line;
  private final String destination;
  private final int trainNumber;
  private int track;
  private LocalTime delay;

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
    final int track = (int) (Math.random() * 100);
    final LocalTime delay = LocalTime.of(
        (int) (Math.random() * .5 + .5),
        (int) (Math.random() * 60)
    );
    return new TrainDeparture(plannedDeparture, line, trainNumber, destination, track, delay);
  }

  TrainDeparture(LocalTime plannedDeparture, String line, int trainNumber, String destination) {
    this(plannedDeparture, line, trainNumber, destination, -1);
  }

  TrainDeparture(
      LocalTime plannedDeparture,
      String line,
      int trainNumber,
      String destination,
      int track
  ) {
    this(plannedDeparture, line, trainNumber, destination, track, LocalTime.MIN);
  }

  TrainDeparture(
      LocalTime plannedDeparture,
      String line,
      int trainNumber,
      String destination,
      int track,
      LocalTime delay
  ) {
    if (plannedDeparture == null) {
      throw new InvalidParameterException("Planned departure cannot be null");
    }
    this.plannedDeparture = plannedDeparture;

    if (line == null) {
      throw new InvalidParameterException("Line cannot be null");
    }
    if (!Utils.trainLinePattern.matcher(line).matches()) {
      throw new InvalidParameterException("Line can only contain capital letters and numbers");
    }
    this.line = line;


    if (destination == null) {
      throw new InvalidParameterException("Destination cannot be null");
    }
    if (destination.isEmpty()) {
      throw new InvalidParameterException("Destination cannot be a string of length 0");
    }
    this.destination = destination;

    if (trainNumber < 1) {
      throw new InvalidParameterException("Train number cannot be less than 1");
    }
    this.trainNumber = trainNumber;
    setTrack(track);
    setDelay(delay);
  }

  public LocalTime getPlannedDeparture() {
    return plannedDeparture;
  }

  public LocalTime getDelayedDeparture() {
    return plannedDeparture
      .plusHours(delay.getHour())
      .plusMinutes(delay.getMinute())
      .plusSeconds(delay.getSecond());
  }

  public String getLine() {
    return line;
  }

  public String getDestination() {
    return destination;
  }

  public int getTrainNumber() {
    return trainNumber;
  }

  public int getTrack() {
    return track;
  }

  public void setTrack(int track) {
    if (track < -1 || track == 0) {
      throw new InvalidParameterException("Track id must be greater than 0, or -1 if undefined");
    }
    this.track = track;
  }

  public LocalTime getDelay() {
    return delay;
  }

  public boolean isDelayed() {
    return !delay.equals(LocalTime.MIN);
  }

  public void setDelay(LocalTime delay) {
    if (delay == null) {
      throw new InvalidParameterException("Delay cannot be null");
    }
    this.delay = delay;
  }

  public String toString() {
    return String.format("%s til %s%s: Planlagt avgang %s, %s fra spor %s",
      line,
      destination,
      trainNumber > -1 ? " (Tog #" + trainNumber + ")" : "",
      plannedDeparture,
      isDelayed() ? "forsinket til " + getDelayedDeparture() : "med ingen forsinkelse",
      track
    );
  }
}