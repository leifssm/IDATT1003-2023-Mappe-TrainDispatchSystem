package edu.ntnu.stud;

// ═ ╠ ║ ╬ ╦ ╩ ╚ ╔ ╗ ╝ ╣ ═ ║ ╬ ╦ ╩ ╚ ╔ ╗ ╝ ╣

import java.time.LocalTime;

public class Interface {
    static int LINES = 5;
    public static void main(String[] args) {
        showDepartures(new TrainDeparture[]{new TrainDeparture(LocalTime.of(3, 4), "L1", 45, "Trondheim S", 5, LocalTime.of(23,0))});
    }
    static void showDepartures(TrainDeparture[] departures) {
        showDepartures(departures, LocalTime.of(0,0));
    }
    static void showDepartures(TrainDeparture[] departures, LocalTime time) {
        if (time == null) throw new IllegalArgumentException("Time cannot be null");
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
    static private String parseDeparture(TrainDeparture departure) {
        final String formattedLine = String.format("%-4s", departure.getLine());
        final String formattedTrainId = String.format("%-5s", departure.getTrainNumber());
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
