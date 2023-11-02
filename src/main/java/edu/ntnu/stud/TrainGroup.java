package edu.ntnu.stud;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TrainGroup {

    private final ArrayList<TrainDeparture> departures = new ArrayList<>();

    TrainGroup() {}
    TrainGroup(@NotNull ArrayList<TrainDeparture> departures) {
        for (TrainDeparture departure : departures) {
            addDeparture(departure);
        }
    }
    public void addDeparture(@NotNull TrainDeparture departure) {
        final int id = departure.getTrainNumber();
        if (id != -1 && getDepartureFromNumber(id) != null) throw new InvalidParameterException("Train number already exists");
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
        return getSortedDepartureStream()
                .filter(departure -> departure.getDelayedDeparture().isAfter(time))
//                .limit(LINES)
                .toArray(TrainDeparture[]::new);
    }
    public TrainDeparture[] getDeparturesFromTime() {
        return getSortedDepartureStream().toArray(TrainDeparture[]::new);
    }

}
