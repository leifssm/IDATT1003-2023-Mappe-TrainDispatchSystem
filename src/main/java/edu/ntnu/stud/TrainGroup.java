package edu.ntnu.stud;

import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public class TrainGroup {
  private final ArrayList<TrainDeparture> departures = new ArrayList<>();

  TrainGroup() {}

  TrainGroup(@NotNull List<TrainDeparture> departures) {
    for (TrainDeparture departure : departures) {
      addDeparture(departure);
    }
  }

  public void addDeparture(@NotNull TrainDeparture departure) throws InvalidParameterException {
    final int id = departure.getTrainNumber();
    if (id != -1 && getDepartureFromNumber(id) != null) {
      throw new InvalidParameterException("Train number already exists");
    }
    departures.add(departure);
  }

  public TrainDeparture getDepartureFromNumber(int trainNumber) {
    return departures
        .stream()
        .filter(departure -> departure.getTrainNumber() == trainNumber)
        .findFirst()
        .orElse(null);
  }

  public TrainDeparture[] getDepartureFromDestination(@NotNull String destination) {
    return departures
        .stream()
        .filter(departure -> departure.getDestination().equals(destination))
        .toArray(TrainDeparture[]::new);
  }

  private Stream<TrainDeparture> getSortedDepartureStream() {
    return departures
      .stream()
      .sorted((a, b) -> {
        final LocalTime aDeparture = a.getPlannedDeparture();
        final LocalTime bDeparture = b.getPlannedDeparture();
        return aDeparture.compareTo(bDeparture);
      });
  }

  public TrainDeparture[] getDeparturesFromTime(@NotNull LocalTime time) {
    // TODO add reason for array or just dont return array
    return getSortedDepartureStream()
      .filter(departure -> departure.getDelayedDeparture().isAfter(time))
      //.limit(LINES)
      .toArray(TrainDeparture[]::new);
  }

  public TrainDeparture[] getDeparturesFromTime() {
    return getSortedDepartureStream().toArray(TrainDeparture[]::new);
  }

  public int removePassedDepartures(@NotNull LocalTime time) {
    AtomicInteger removed = new AtomicInteger();
    departures.removeIf(departure -> {
      if (!departure.getDelayedDeparture().isAfter(time)) {
        removed.getAndIncrement();
        return true;
      }
      return false;
    });
    return removed.get();
  }
}
