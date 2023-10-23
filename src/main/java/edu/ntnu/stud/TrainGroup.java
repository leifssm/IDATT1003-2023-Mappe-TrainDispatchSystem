package edu.ntnu.stud;

import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class TrainGroup {
    private final ArrayList<TrainDeparture> departures = new ArrayList<>();

    TrainGroup() {}
    TrainGroup(ArrayList<TrainDeparture> departures) {
        for (TrainDeparture departure : departures) {
            addDeparture(departure);
        }
    }
    public void addDeparture(@NotNull TrainDeparture departure) {
        final int id = departure.getTrainNumber();
        if (id != -1 && getDeparture(id) != null) throw new InvalidParameterException("Train number already exists");
        departures.add(departure);
    }
    private TrainDeparture getDeparture(int trainNumber) {
        return departures
                .stream()
                .filter(departure -> departure.getTrainNumber() == trainNumber)
                .findFirst()
                .orElse(null);
    }
    public TrainDeparture[] getDepartures() {
        return departures.toArray(new TrainDeparture[departures.size()]);
    }
}
