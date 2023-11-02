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
        LocalTime currentTime = LocalTime.MIN;
        while (true) {
            showDepartures(currentTime);
            System.out.println(
                    "Velg oppgave:\n" +
                    "1. Legg til ny togavgang\n" +
                    "2. Gi togspor til togavgang\n" + // Finn med tognummer og sett spor
                    "3. Sett forsinkelse\n" + // Finn med tognummer og sett forsinkelse
                    "4. Finn avgang med nummer\n" + // Finn med tognummer
                    "5. Finn avgang med destinasjon\n" + // Finn med destinasjon
                    "6. Sett klokken\n" + // kan ikke være mindre
                    "7. Avslutt"
            );
            final int option = InputParser.getInt("Valg: ", (int n) -> n >= 1 && n <= 7);
            System.out.println();
            switch (option) {
                case 1 -> addDeparture();
                case 2 -> giveDepartureTrack();
                case 3 -> giveDepartureDelay();
                case 4 -> findDepartureFromNumber();
                case 5 -> findDepartureFromDestination();
                case 6 -> setClock(currentTime);
                case 7 -> System.exit(0);
            };
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }
    }

    private void addDeparture() {
        // TODO
    }




    private void showDeparturesFrom() {
        showDeparturesFrom(LocalTime.MIN);
    }

    private void giveDepartureTrack() {
        // TODO
    }
    private void giveDepartureTrack() {
        TrainDeparture departure = findDepartureFromNumber();
        System.out.println("Hvilket spor skal den gå fra?");
        final int previousTrack = departure.getTrack();
        final int track = InputParser.getInt("Spor: ", (int n) -> n >= 0);
        departure.setTrack(track);
        System.out.println();
        if (previousTrack != track) System.out.printf("Spor endret fra %s til %s.\n", previousTrack, track);
        else System.out.printf("Spor var allerede satt til %s, ingen endring gjort.\n", track);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }

    private void giveDepartureDelay() {
        // TODO
    }

    private void giveDepartureDelay() {
        TrainDeparture departure = findDepartureFromNumber();
        final LocalTime previousDelay = departure.getDelay();
        if (departure.isDelayed()) System.out.printf("Toget er allerede forsinka med %s.\n", previousDelay);
        System.out.println("Hvor mye vil du endre togets forsinkelse til?");
        final LocalTime delay = InputParser.getTime("Forsinkelse: ");
        departure.setDelay(delay);
        System.out.println();
        if (previousDelay != delay) System.out.printf("Forsinkelse endret fra %s til %s.\n", previousDelay, delay);
        else System.out.printf("Forsinkelsen var allerede satt til %s, ingen endring gjort.\n", delay);
    }

    private void findDepartureFromNumber() {
        // TODO
    }

    private TrainDeparture findDepartureFromNumber() {
        TrainDeparture departure = null;
        while (departure == null) {
            System.out.println("Velg en togavgang å gi spor til. Skriv inn tognummeret.");
            final int trainNumber = InputParser.getInt("Tognummer: ", (int n) -> n >= 0);
            departure = departures.getDepartureFromNumber(trainNumber);
            if (departure == null) System.out.printf("Fant ikke toget med nummer %s. Prøv igjen.", trainNumber);
        }
        System.out.printf("Fant toget %s.\n", departure);
        return departure;
    }

    private void findDepartureFromDestination() {
        // TODO
    }

    private void findDepartureFromDestination() {
        TrainDeparture[] departures = null;
        while (departures == null || departures.length == 0) {
            System.out.println("Velg en togavgang å gi spor til. Skriv inn tognummeret.");
            final String destination = InputParser.getString("Destinasjon: ");
            departures = this.departures.getDepartureFromDestination(destination);
            if (departures.length == 0) System.out.printf("Fant ingen tog med destinasjonen %s. Prøv igjen.", destination);
        }
        System.out.println("Fant tog:");
        for (int i = 0; i < departures.length; i++) {
            System.out.println(i + 1 + ". " + departures[i]);
        }
        System.out.println();
    }

    private void setClock() {
        // TODO
    }

    private LocalTime setClock(LocalTime currentTime) {
        LocalTime newTime = LocalTime.MIN;
        do {
            newTime = InputParser.getTime("Skriv inn nytt klokkeslett, klokkeslettet må være større enn det forrige: ");
        } while (!newTime.isAfter(currentTime));
        return newTime;
    }


    private void showDepartures(LocalTime time) {
        if (time == null) throw new IllegalArgumentException("Time cannot be null");
        final TrainDeparture[] sortedDepartures = departures.getDeparturesFromTime(time);

        System.out.println("                ╔════════════╦═══════╗");
        System.out.printf( "                ║ Departures ║ %s ║\n", time);
        System.out.println("╔═════════╤═════╩═══╤══════╤═╩═══════╩════════╤═══════╗");
        System.out.println("║ Planned │ Delayed │ Line │ Destination      │ Train ║");
        for (TrainDeparture departure : sortedDepartures) {
            System.out.println(parseDeparture(departure));
        }
        for (int i = 0; i < 5 - sortedDepartures.length; i++) {
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
