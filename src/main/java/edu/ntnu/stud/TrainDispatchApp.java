package edu.ntnu.stud;

/**
 * The access point for the application, creates and runs a train interface instance.
 */
public class TrainDispatchApp {
  /**
   * The main method of the application. Initializes and starts the application.
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
