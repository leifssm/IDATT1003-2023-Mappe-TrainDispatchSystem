package edu.ntnu.stud;

import edu.ntnu.stud.menu.Menu;
import java.time.LocalTime;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    // Creates random departures
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
    // ASCII art from https://patorjk.com/software/taag/
    System.out.println(
        """
                            ________             _____
                            ___  __/____________ ___(_)______
                            __  /  __  ___/  __ `/_  /__  __ \\
                            _  /   _  /   / /_/ /_  / _  / / /
                            /_/    /_/    \\__,_/ /_/  /_/ /_/

                 ________ ____                      _____      ______
                 ___  __ \\__(_)____________________ __  /_________  /_
                 __  / / /_  /__  ___/__  __ \\  __ `/  __/  ___/_  __ \\
                 _  /_/ /_  / _(__  )__  /_/ / /_/ // /_ / /__ _  / / /
                 /_____/ /_/  /____/ _  .___/\\__,_/ \\__/ \\___/ /_/ /_/
                                     /_/
                                  Created by Leif Mørstad
            """
    );
    InputParser.waitForUser("                      Press enter to continue");

    // TODO add method for all setters
    // Uses a Menu instance to create a menu with all possible options
    new Menu("Main Menu")
        .addOption("Add new departure", this::addDeparture)
        .addOption("Assign track to departure", this::changeDepartureTrack)
        .addOption("Set departure delay", this::giveDepartureDelay)
        .addOption("Find departure by number", this::findDepartureFromNumber)
        .addOption("Find departures by destination", this::findDepartureFromDestination)
        .addOption("Set the time", this::setClock)
        .addOption("Remove passed departures", this::removeOldDepartures)
        .addOption("Quit", () -> {
          InputParser.close();
          System.exit(0);
        })
        .setRunBefore(this::showDepartures)
        .setRunAfter(InputParser::waitForUser)
        .run();
  }

  /**
   * Gets a valid train number from the user that doesn't collide with an existing train number.
   *
   * @return A valid train number that doesn't collide with another departure.
   */
  private int getNonCollidingTrainNumberFromUser() {
    System.out.println("What is the train number? The number has to be bigger than or equal to 1.");

    return InputParser.getInt("Train number", n -> {
      if (n < 1) {
        System.out.println("The train number cannot be smaller than 1.");
        return false;
      }
      if (departures.doesDepartureExists(n)) {
        System.out.println("The train number is already in use.");
        return false;
      }
      return true;
    }, null);
  }

  /**
   * Gets a valid track number from the user.
   *
   * @return A valid track number.
   */
  private int getTrackNumberFromUser() {
    System.out.println(
        "What track is it supposed to leave from? Write -1 if it should be unassigned."
    );

    return InputParser.getInt(
        "Track",
        n -> n >= -1 && n != 0,
        "Track cannot be 0 or smaller than -1."
    );
  }

  /**
   * Gets a valid destination from the user.
   *
   * @return A valid destination.
   */
  private @NotNull String getDestinationFromUser() {
    System.out.println("What is the train's destination?");
    return InputParser.getString(
        "Destination",
        Utils.destinationPattern,
        "The destination must be between 1 and 16 characters."
    );
  }

  /**
   * Gets a valid train line from the user.
   *
   * @return A valid train line.
   */
  private @NotNull String getTrainLineFromUser() {
    System.out.println(
        "Whats the train's line? The line can only contain capital letters and numbers, and must "
            + "contain between 2 and 7 letters."
    );
    return InputParser.getString(
        "Line",
        Utils.trainLinePattern,
        "The line can only contain capital letters and numbers, and must contain "
            + "between 2 and 7 letters."
    );
  }

  private @NotNull LocalTime getTrainDelay() {
    System.out.println("Is the train already delayed?");
    final boolean isDelayed = InputParser.getBoolean("Delayed?", false);

    if (isDelayed) {
      System.out.println("How long is the train delayed?");
      return InputParser.getTime("Delay");
    }
    return LocalTime.MIN;
  }

  private @NotNull LocalTime getTimeAfterTime(@NotNull LocalTime timeToCompare) {
    System.out.printf(
        "Enter a new time, the time must be later than the current time (%s).\n",
        timeToCompare
    );

    // Gets a time from the user that is after the current time, and sets it
    return InputParser.getTime(
        "Time",
         time -> time.isAfter(timeToCompare),
        "The time must be after %s.".formatted(timeToCompare)
    );
  }

  /**
   * Lets the user add a new departure. It asks the user for inputs, validates it and creates a new
   * departure.
   */
  private void addDeparture() {
    // Gets the planned departure from the user
    System.out.println("When does the train leave?");
    final LocalTime plannedDeparture = InputParser.getTime("Departure");

    final String line = getTrainLineFromUser();

    final String destination = getDestinationFromUser();

    final int trainNumber = getNonCollidingTrainNumberFromUser();

    final int track = getTrackNumberFromUser();

    final LocalTime delay = getTrainDelay();

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
    // Gets the departure from the user, and returns if it doesn't find one since the user can't
    // change the track of a departure if none exists.
    final TrainDeparture departure = findDepartureFromNumber();
    if (departure == null) {
      return;
    }

    final int track = getTrackNumberFromUser();

    final int previousTrack = departure.getTrack();

    // Prints a relevant message to the user
    if (previousTrack != track) {
      System.out.printf(
          "\nTrack changed from %s, to %s.\n",
          previousTrack == -1 ? "being unassigned" : "going from track " + previousTrack,
          track == -1 ? "being unassigned" : "going from track " + track
      );
      departure.setTrack(track);
    } else {
      System.out.printf(
          "\nThe track was already %s, no change was made.\n",
          track == -1 ? "unassigned" : "sat to track " + track
      );
    }
  }

  /**
   * Lets the user change the delay of a departure. Asks for a train number, checks if a
   * corresponding train exists, then lets the user change the track.
   */
  private void giveDepartureDelay() {
    // Gets the departure from the user, and returns if it doesn't find one since the user can't
    // change the delay of a departure if none exists.
    final TrainDeparture departure = findDepartureFromNumber();
    if (departure == null) {
      return;
    }

    // Gives the user the delay if one already exists
    final LocalTime previousDelay = departure.getDelay();
    if (departure.isDelayed()) {
      System.out.printf("The train is already delayed with %s.\n", previousDelay);
    }

    // Gets the new delay from the user
    System.out.println("How much do you want to change the delay to?");
    final LocalTime delay = InputParser.getTime("Delay");

    // Prints a relevant message to the user
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
  private @Nullable TrainDeparture findDepartureFromNumber() {
    // Returns null if there are no departures to select from
    if (departures.size() == 0) {
      System.out.println("There are no train departures to find.");
      return null;
    }

    // Gets a valid train number from the user
    System.out.println("Write the train number of the train you want to find.");
    final int trainNumber = InputParser.getInt(
        "Train number",
        n -> {
          final boolean departureExists = departures.doesDepartureExists(n);
          if (!departureExists) {
            System.out.printf("Couldn't find a departure with the number %d. Try again.\n", n);
          }
          return departureExists;
        },
        null
    );

    // Returns the departure with the given train number
    final TrainDeparture departure = departures.getDepartureFromNumber(trainNumber);
    System.out.printf("Found the train %s.\n", departure);
    return departure;
  }

  /**
   * Lets the user query the departures, and gets all departures for a destination.
   */
  private void findDepartureFromDestination() {
    // Returns if there are no departures to select from
    if (departures.size() == 0) {
      System.out.println("There are no train departures to find.");
      return;
    }

    // Gets a destination from the user until the program finds at least one departure with that
    // destination
    TrainDeparture[] departures;
    do {
      System.out.println("Write the destination of the departure(s) you want to find.");
      final String destination = InputParser.getString("Destination");

      departures = this.departures.getDepartureFromDestination(destination);

      if (departures.length == 0) {
        System.out.printf(
            "Couldn't find any departures with the destination %s. Try again.\n",
            destination
        );
      }
    } while (departures.length == 0);

    System.out.printf(
        "\nFound %d departure%s:\n",
        departures.length,
        departures.length >= 2 ? "s" : ""
    );
    for (int i = 0; i < departures.length; i++) {
      System.out.printf(" %d. %s\n", i + 1, departures[i]);
    }
    System.out.println();
  }

  /**
   * Lets the user change the current time, but doesn't let the user pick an earlier time.
   */
  private void setClock() {
    currentTime = getTimeAfterTime(currentTime);
    System.out.printf("\nThe time got changed to %s.\n", currentTime);
  }

  private void removeOldDepartures() {
    int removed = departures.removeDeparturesBefore(currentTime);
    if (removed == 0) {
      System.out.printf(
          "No departures were removed, as there are no departures before %s.\n",
          currentTime
      );
    } else {
      System.out.printf("Removed %d departures.\n", removed);
    }
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
    System.out.println("╟───────────┼───────────┼───────┼───────┼──────────────────┼───────╢");
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
        ? Utils.padCenter(departure.getTrack(), 7)
        : "       ";

    final String line = Utils.padCenter(departure.getLine(), 7);

    final String trainNumber = departure.getTrainNumber() != -1
        ? Utils.padCenter(departure.getTrainNumber(), 7)
        : "       ";

    return "║   %s   │   %s   │%s│%s│ %-16s │%s║".formatted(
        departure.getPlannedDeparture(),
        departure.isDelayed() ? departure.getDelayedDeparture() : "     ",
        track,
        line,
        departure.getDestination(),
        trainNumber
    );
  }
}
