package edu.ntnu.stud;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.Arrays;

public class TrainInterface {
    static int LINES = 5;
    private final TrainGroup departures = new TrainGroup();

    TrainInterface() {}

    public TrainDeparture[] getDepartures() {
        return departures.getDepartures();
    }

    public void init() {
        departures.addDeparture(TrainDeparture.createRandomDeparture(30, "Trondheim"));
        departures.addDeparture(TrainDeparture.createRandomDeparture(63, "Oslo S"));
        departures.addDeparture(TrainDeparture.createRandomDeparture(14, "Bergen"));
        departures.addDeparture(TrainDeparture.createRandomDeparture(59, "Kristiansand"));
        departures.addDeparture(TrainDeparture.createRandomDeparture(19, "Bodø"));
        departures.addDeparture(TrainDeparture.createRandomDeparture(67, "Stavanger"));
        departures.addDeparture(TrainDeparture.createRandomDeparture(74, "Alta"));
    }

    public void start() {
        LocalTime currentTime = LocalTime.of(0,0);
        showDeparturesFrom(currentTime);
    }





    private void showDeparturesFrom() {
        showDeparturesFrom(LocalTime.MIN);
    }
    private void showDeparturesFrom(LocalTime time) {
        if (time == null) throw new IllegalArgumentException("Time cannot be null");
        final TrainDeparture[] departures = getDeparturesFrom(time);

        System.out.println("                ╔════════════╦═══════╗");
        System.out.printf( "                ║ Departures ║ %s ║\n", time);
        System.out.println("╔═════════╤═════╩═══╤══════╤═╩═══════╩════════╤═══════╗");
        System.out.println("║ Planned │ Delayed │ Line │ Destination      │ Train ║");
        for (var departure : departures) {
            System.out.println(parseDeparture(departure));
        }
        for (int i = 0; i < LINES - departures.length; i++) {
            System.out.println("║         │         │      │                  │       ║");
        }
        System.out.println("╚═════════╧═════════╧══════╧══════════════════╧═══════╝");
    }
    public TrainDeparture[] getDeparturesFrom(LocalTime time) {
        return Arrays.stream(getDepartures())
                .sorted((a, b) -> {
                    final LocalTime aDeparture = a.getPlannedDeparture();
                    final LocalTime bDeparture = b.getPlannedDeparture();
                    return aDeparture.compareTo(bDeparture);
                })
                .filter(departure -> departure.getDelayedDeparture().isAfter(time))
                .limit(LINES)
                .toArray(TrainDeparture[]::new);

    }

    static private String parseDeparture(@NotNull TrainDeparture departure) {
        final String formattedLine = String.format("%-4s", departure.getLine());
        final boolean shouldShowTrack = departure.getTrack() != -1;
        final String formattedTrainId = String.format("%-5s", shouldShowTrack ? departure.getTrainNumber() : "");
        final String formattedDestination = String.format("%-16s", departure.getDestination());
        final String format = "║  %s  │  %s  │ %s │ %s │ %s ║";
        return format.formatted(
                departure.getPlannedDeparture(),
                departure.isDelayed() ? departure.getDelayedDeparture() : "     ",
                formattedLine,
                formattedDestination,
                formattedTrainId);
    }
}
