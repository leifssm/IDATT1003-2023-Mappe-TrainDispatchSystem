package edu.ntnu.stud;

import java.time.LocalTime;

import edu.ntnu.stud.Menu.Menu;
import org.jetbrains.annotations.NotNull;

public class TrainInterface {
  private final TrainGroup departures = new TrainGroup();

  private LocalTime currentTime = LocalTime.MIN;

  TrainInterface() {}

  public void init() {
    departures.addDeparture(TrainDeparture.createRandomDeparture(30, "Trondheim"));
    departures.addDeparture(TrainDeparture.createRandomDeparture(63, "Oslo S"));
    departures.addDeparture(TrainDeparture.createRandomDeparture(14, "Bergen"));
    departures.addDeparture(TrainDeparture.createRandomDeparture(59, "Kristiansand"));
    departures.addDeparture(TrainDeparture.createRandomDeparture(19, "Bodø"));
    departures.addDeparture(TrainDeparture.createRandomDeparture(67, "Stavanger"));
    departures.addDeparture(TrainDeparture.createRandomDeparture(74, "Alta"));
    departures.addDeparture(TrainDeparture.createRandomDeparture(78, "Alta"));
  }

  public void start() {
    // TODO add method for all setters
    // TODO change to english
    new Menu("══ Hovedmeny ══")
        .addOption("Legg til ny togavgang", this::addDeparture)
        .addOption("Gi togspor til togavgang", this::changeDepartureTrack)
        .addOption("Sett forsinkelse", this::giveDepartureDelay)
        .addOption("Finn avgang med nummer", this::findDepartureFromNumber)
        .addOption("Finn avganger med destinasjon", this::findDepartureFromDestination)
        .addOption("Sett klokken", this::setClock)
        .addOption("Avslutt", () -> {
          InputParser.close();
          System.exit(0);
        })
        .setRunBefore(this::showDepartures)
        .setRunAfter(() -> stall())
        .run();
  }

  private void addDeparture() {
    System.out.println("Når skal toget gå?");
    final LocalTime plannedDeparture = InputParser.getTime("Avgang");
    System.out.println("Hvilken toglinje er det?");
    final String line = InputParser.getString("Linje", Utils.trainLinePattern);

    System.out.println("Hva er endestoppet?");
    final String destination = InputParser.getString("Endestopp");

    System.out.println("Hva er tognummeret? Skriv -1 hvis den ikke skal bli satt enda.");
    final int trainNumber = InputParser.getInt("Tognummer", n ->
      n == -1 ||
      n > -1 && departures.getDepartureFromNumber(n) == null
    );


    System.out.println("Hvilket spor skal den gå fra? Skriv -1 hvis den ikke skal bli satt enda.");
    final int track = InputParser.getInt("Spor", n -> n >= -1);

    LocalTime delay = LocalTime.MIN;
    System.out.println("Er toget allerede forsinka?");
    final boolean isDelayed = InputParser.getBoolean("Forsinka?", false);
    if (isDelayed) {
      System.out.println("Hvor lenge er den forsinka?");
      delay = InputParser.getTime("Forsinkelse");
    }

    // TODO add try catch
    final TrainDeparture departure = new TrainDeparture(
        plannedDeparture,
        line,
        trainNumber,
        destination,
        track,
        delay
    );

    departures.addDeparture(departure);
  }

  private void giveTrackToDeparture() {
    TrainDeparture departure = findDepartureFromNumber();
    System.out.println("Hvilket spor skal den gå fra?");
    final int previousTrack = departure.getTrack();
    final int track = InputParser.getInt("Spor", n -> n >= 0);
    departure.setTrack(track);
    System.out.println();
    if (previousTrack != track) {
      System.out.printf(
          "Spor endret fra %s til %s.\n",
          previousTrack,
          track
      );
    } else {
      System.out.printf("Spor var allerede satt til %s, ingen endring gjort.\n", track);
    }

    try {
      Thread.sleep(1000);
    } catch (InterruptedException ignored) {
      // Do nothing
    }
  }

  private void giveDepartureDelay() {
    TrainDeparture departure = findDepartureFromNumber();
    final LocalTime previousDelay = departure.getDelay();
    if (departure.isDelayed()) {
      System.out.printf("Toget er allerede forsinka med %s.\n", previousDelay);
    }
    System.out.println("Hvor mye vil du endre togets forsinkelse til?");
    final LocalTime delay = InputParser.getTime("Forsinkelse");
    departure.setDelay(delay);
    System.out.println();
    if (previousDelay != delay) {
      System.out.printf("Forsinkelse endret fra %s til %s.\n", previousDelay, delay);
    } else {
      System.out.printf("Forsinkelsen var allerede satt til %s, ingen endring gjort.\n", delay);
    }
  }

  private TrainDeparture findDepartureFromNumber() {
    System.out.println("Skriv inn nummeret til toget du vil finne.");
    final int trainNumber = InputParser.getInt("Tognummer", n -> {
      TrainDeparture dep = departures.getDepartureFromNumber(n);
      if (dep == null) {
        System.out.printf("Fant ikke toget med nummer %s. Prøv igjen.\n", n);
      }
      return dep != null;
    });
    TrainDeparture departure = departures.getDepartureFromNumber(trainNumber);
    System.out.printf("Fant toget %s.\n", departure);
    return departure;
  }

  private void findDepartureFromDestination() {
    TrainDeparture[] departures = null;
    while (departures == null || departures.length == 0) {
      System.out.println("Skriv inn destinasjonen til avgangen(e) du vil finne.");
      final String destination = InputParser.getString("Destinasjon");
      departures = this.departures.getDepartureFromDestination(destination);
      if (departures.length == 0) {
        System.out.printf("Fant ingen tog med destinasjonen %s. Prøv igjen.", destination);
      }
    }
    System.out.printf("\nFant %s tog:\n", departures.length);
    for (int i = 0; i < departures.length; i++) {
      System.out.printf(" %s. %s\n", i + 1, departures[i]);
    }
    System.out.println();
  }

  private void setClock() {
    System.out.printf(
        "Skriv inn nytt klokkeslett, klokkeslettet må være større enn det forrige (%s)\n",
        currentTime
    );
    currentTime = InputParser.getTime("Klokkeslett", time -> time.isAfter(currentTime));
  }

  private void showDepartures() {
    final TrainDeparture[] sortedDepartures = departures.getDeparturesFromTime(currentTime);

    System.out.println("                       ╔══════════╦═══════╗");
    System.out.printf("                       ║ Avganger ║ %s ║\n", currentTime);
    System.out.println("╔═══════════╤══════════╩╤══════╤══╩════╤══╩══════════════╤════════╗");
    System.out.println("║ Planlagt  │ Forventet │ Spor │ Linje │ Destinasjon     │ Tognr. ║");
    for (TrainDeparture departure : sortedDepartures) {
      System.out.println(parseDeparture(departure));
    }
    for (int i = 0; i < 5 - sortedDepartures.length; i++) {
      System.out.println("║           │           │      │       │                 │        ║");
    }
    System.out.println("╚═══════════╧═══════════╧══════╧═══════╧═════════════════╧════════╝");
  }

  private static String parseDeparture(@NotNull TrainDeparture departure) {
    final String track = departure.getTrack() != -1
        ? Utils.pc(departure.getTrack(), 6)
        : "      ";
    final String line = Utils.pc(departure.getLine(), 7);


    final String destination = String.format("%-15s", departure.getDestination());



    final String trainNumber = departure.getTrainNumber() != -1
        ? Utils.pc(departure.getTrainNumber(), 8)
        : "        ";
    final String format = "║   %s   │   %s   │%s│%s│ %s │%s║";
    return format.formatted(
        departure.getPlannedDeparture(),
        departure.isDelayed() ? departure.getDelayedDeparture() : "     ",
        track,
        line,
        destination,
        trainNumber
    );
  }

  private static void stall() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ignored) {
      // Do nothing
    }
  }
}
