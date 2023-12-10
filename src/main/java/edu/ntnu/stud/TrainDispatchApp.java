package edu.ntnu.stud;

/**
 * <h1>TrainDispatchApp.</h1>
 * <p>The access point for the application, creates and runs a train interface instance.</p>
 * <br>
 * <h2>Role and Responsibility:</h2>
 * <p>
 *   This class is only responsible for creating and starting a train interface instance. It does
 *   not use any arguments, and does not have any properties.
 * </p>
 *
 * @see TrainInterface
 */
public class TrainDispatchApp {
  /**
   * The main method for the application. Initializes and starts a
   * {@link TrainInterface} instance.
   *
   * @param args The command line arguments, not used
   */
  public static void main(String[] args) {
    // Creates a new train interface instance before initializing and starting it.
    final TrainInterface trainInterface = new TrainInterface();
    trainInterface.init();
    trainInterface.start();
  }
}
