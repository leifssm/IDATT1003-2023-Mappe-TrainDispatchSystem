package edu.ntnu.stud;

import edu.ntnu.stud.menu.Menu;
import java.time.LocalTime;
import org.jetbrains.annotations.NotNull;

/**
 * A user interface for the train system.
 */
public class TrainInterface {
  /**
   * The departures of the train system.
   */
  private final TrainGroup departures = new TrainGroup();

  /**
   * The current time of the train system, starts as {@link LocalTime#MIN}.
   */
  private LocalTime currentTime = LocalTime.MIN;

  /**
   * The minimum size of the display table.
   */
  private static final int MIN_TABLE_DISPLAY_SIZE = 10;

  /**
   * Creates a new train interface.
   */
  TrainInterface() {}

  /**
   * Initializes the train interface with random departures.
   */
  public void init() {
    departures.addDeparture(
        TrainDeparture.createRandomDeparture(30, "Trondheim")
    );
    departures.addDeparture(
        TrainDeparture.createRandomDeparture(63, "Oslo S")
    );
    departures.addDeparture(
        TrainDeparture.createRandomDeparture(14, "Bergen")
    );
    departures.addDeparture(
        TrainDeparture.createRandomDeparture(59, "Kristiansand")
    );
    departures.addDeparture(
        TrainDeparture.createRandomDeparture(19, "Bodø")
    );
    departures.addDeparture(
        TrainDeparture.createRandomDeparture(67, "Stavanger")
    );
    departures.addDeparture(
        TrainDeparture.createRandomDeparture(74, "Alta")
    );
    departures.addDeparture(
        TrainDeparture.createRandomDeparture(78, "Alta")
    );
  }

  /**
   * Starts the train interface's menu.
   */
  public void start() {
    // TODO add method for all setters
    new Menu("Main Menu")
        .addOption("Add new departure", this::addDeparture)
        .addOption("Assign track to departure", this::changeDepartureTrack)
        .addOption("Set departure delay", this::giveDepartureDelay)
        .addOption("Find departure by number", this::findDepartureFromNumber)
        .addOption("Find departures by destination", this::findDepartureFromDestination)
        .addOption("Set the time", this::setClock)
        .addOption("Quit", () -> {
          InputParser.close();
          System.exit(0);
        })
        .setRunBefore(this::showDepartures)
        .setRunAfter(InputParser::waitForUser)
        .run();
  }

  /**
   * Lets the user add a new departure. It asks the user for inputs, validates it and creates a new
   * departure.
   */
  private void addDeparture() {
    // Gets the planned departure from the user
    System.out.println("When does the train leave?");
    final LocalTime plannedDeparture = InputParser.getTime("Departure");

    // Gets the train line from the user
    System.out.println(
        "Whats the train's line? The line can only contain capital letters and numbers, and must "
            + "contain between 2 and 7 letters."
    );
    final String line = InputParser.getString(
        "Line",
        Utils.trainLinePattern,
        "The line can only contain capital letters and numbers, and must contain "
            + "between 2 and 7 letters."
    );

    // Gets the destination from the user
    System.out.println("What is the train's destination?");
    final String destination = InputParser.getString(
        "Destination",
        ".+",
        "The destination cannot be empty."
    );

    // Gets a non-colliding train number from the user
    System.out.println("What is the train number? The number has to be bigger than or equal to 1.");
    final int trainNumber = InputParser.getInt("Train number", n -> {
      if (n < 1) {
        System.out.println("The train number cannot be smaller than 1.");
        return false;
      }
      if (departures.getDepartureFromNumber(n) == null) {
        return true;
      }
      System.out.println("The train number is already in use.");
      return false;
    }, null);

    // Gets the track from the user
    System.out.println(
        "What track is it supposed to leave from? Write -1 if it should be unassigned."
    );
    final int track = InputParser.getInt(
        "Track",
        n -> n >= -1 && n != 0,
        "Track cannot be 0 or smaller than -1."
    );

    // Gets the delay from the user
    LocalTime delay = LocalTime.MIN;
    System.out.println("Is the train already delayed?");
    final boolean isDelayed = InputParser.getBoolean("Delayed?", false);
    if (isDelayed) {
      System.out.println("How long is the train delayed?");
      delay = InputParser.getTime("Delay");
    }

    // Should not throw an exception, since the data is already validated
    final TrainDeparture departure = new TrainDeparture(
        plannedDeparture,
        line,
        trainNumber,
        destination,
        track,
        delay
    );

    departures.addDeparture(departure);
    System.out.println("Added departure: " + departure);
  }

  /**
   * Lets the user change the track of a departure. Asks for a train number, checks if a
   * corresponding train exists, then lets the user change the track
   */
  private void changeDepartureTrack() {
    TrainDeparture departure = findDepartureFromNumber();
    if (departure == null) {
      return;
    }
    System.out.println("Which track should it leave from?");
    final int track = InputParser.getInt(
        "Track",
        n -> n >= 1,
        "Track cannot be smaller than 1."
    );
    final int previousTrack = departure.getTrack();
    departure.setTrack(track);
    if (previousTrack != track) {
      System.out.printf(
          "\nTrack changed from %s to %s.\n",
          previousTrack,
          track
      );
    } else {
      System.out.printf("\nTrack was already sat to %s, no change was made.\n", track);
    }
  }

  /**
   * Lets the user change the delay of a departure. Asks for a train number, checks if a
   * corresponding train exists, then lets the user change the track.
   */
  private void giveDepartureDelay() {
    TrainDeparture departure = findDepartureFromNumber();
    if (departure == null) {
      return;
    }

    final LocalTime previousDelay = departure.getDelay();
    if (departure.isDelayed()) {
      System.out.printf("The train is already delayed with %s.\n", previousDelay);
    }

    System.out.println("How much do you want to change the delay to?");
    final LocalTime delay = InputParser.getTime("Delay");

    if (previousDelay.equals(delay)) {
      System.out.printf("The train is already delayed with %s, no change was made.\n", delay);
    } else {
      departure.setDelay(delay);
      System.out.printf("The delay was changed from %s to %s.\n", previousDelay, delay);
    }
  }

  /**
   * Helper method to find a departure from a train number.
   *
   * @return A train departure with the corresponding train number
   */
  private TrainDeparture findDepartureFromNumber() {
    // TODO add escape?
    if (departures.length() == 0) {
      System.out.println("There are no train departures to find.");
      return null;
    }
    System.out.println("Write the train number of the train you want to find.");
    final int trainNumber = InputParser.getInt("Train number", n -> {
      TrainDeparture dep = departures.getDepartureFromNumber(n);
      if (dep == null) {
        System.out.printf("Couldn't find a departure with the number %s. Try again.\n", n);
      }
      return dep != null;
    }, null);
    TrainDeparture departure = departures.getDepartureFromNumber(trainNumber);
    System.out.printf("Found the train %s.\n", departure);
    return departure;
  }

  /**
   * Lets the user query the departures, and gets all departures for a destination.
   */
  private void findDepartureFromDestination() {
    TrainDeparture[] departures = null;
    while (departures == null || departures.length == 0) {
      System.out.println("Write the destination of the departure(s) you want to find.");
      final String destination = InputParser.getString("Destination");
      departures = this.departures.getDepartureFromDestination(destination);
      if (departures.length == 0) {
        System.out.printf(
            "Couldn't find any departures with the destination %s. Try again.",
            destination
        );
      }
    }
    System.out.println(
        "\nFound "
            + departures.length
            + " departure"
            + (departures.length >= 2 ? "s" : "")
            + ":"
    );
    for (int i = 0; i < departures.length; i++) {
      System.out.printf(" %s. %s\n", i + 1, departures[i]);
    }
    System.out.println();
  }

  /**
   * Lets the user change the current time, but doesn't let the user pick an earlier time.
   */
  private void setClock() {
    System.out.printf(
        "Enter a new time, the time must be later than the current time (%s).\n",
        currentTime
    );
    currentTime = InputParser.getTime(
        "Time",
        time -> time.isAfter(currentTime),
        "The time must be after %s.\n".formatted(currentTime)
    );

    System.out.printf("The time got changed to %s.\n", currentTime);
  }

  /**
   * Prints the departures to the console with a stylized table.
   */
  private void showDepartures() {
    final TrainDeparture[] sortedDepartures = departures.getDeparturesFromTime(currentTime);

    System.out.println("                      ╔══════════════╦═══════╗");
    System.out.printf("                      ║  Departures  ║ %s ║\n", currentTime);
    System.out.println("╔═══════════╤═════════╩═╤═══════╤════╩══╤════╩═════════════╤═══════╗");
    System.out.println("║  Arrives  │ Expected  │ Track │ Line  │ Destination      │ Train ║");
    for (TrainDeparture departure : sortedDepartures) {
      System.out.println(formatDeparture(departure));
    }
    for (int i = 0; i < MIN_TABLE_DISPLAY_SIZE - sortedDepartures.length; i++) {
      System.out.println("║           │           │       │       │                  │       ║");
    }
    System.out.println("╚═══════════╧═══════════╧═══════╧═══════╧══════════════════╧═══════╝");
  }

  /**
   * Parses a train departure to a string.
   *
   * @param departure The train departure to format
   * @return A string representation of the train departure for the train table
   * @throws IllegalArgumentException If the departure is null
   */
  private static @NotNull String formatDeparture(@NotNull TrainDeparture departure) {
    final String track = departure.getTrack() != -1
        ? Utils.pc(departure.getTrack(), 7)
        : "       ";
    final String line = Utils.pc(departure.getLine(), 7);

    final String destination = String.format("%-16s", departure.getDestination());

    final String trainNumber = departure.getTrainNumber() != -1
        ? Utils.pc(departure.getTrainNumber(), 7)
        : "       ";

    return "║   %s   │   %s   │%s│%s│ %s │%s║".formatted(
        departure.getPlannedDeparture(),
        departure.isDelayed() ? departure.getDelayedDeparture() : "     ",
        track,
        line,
        destination,
        trainNumber
    );
  }
}
