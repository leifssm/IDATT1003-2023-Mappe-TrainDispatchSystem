package edu.ntnu.stud;

import java.security.InvalidParameterException;
import java.time.LocalTime;

public class TrainDeparture {
    private final LocalTime plannedDeparture;
    private final String line;
    private final int trainNumber;
    private final String destination;
    private int track;
    private LocalTime delay;
    TrainDeparture(LocalTime plannedDeparture, String line, int trainNumber, String destination) {
        this(plannedDeparture, line, trainNumber, destination, -1);
    }
    TrainDeparture(LocalTime plannedDeparture, String line, int trainNumber, String destination, int track) {
        this(plannedDeparture, line, trainNumber, destination, track, LocalTime.MIN);
    }
    TrainDeparture(LocalTime plannedDeparture, String line, int trainNumber, String destination, int track, LocalTime delay) {
        if (plannedDeparture == null) throw new InvalidParameterException("Planned departure cannot be null");
        this.plannedDeparture = plannedDeparture;

        if (line == null) throw new InvalidParameterException("Line cannot be null");
        if (line.length() > 3) throw new InvalidParameterException("Line cannot be longer than 3 characters");
        if (line.length() < 2) throw new InvalidParameterException("Line cannot be shorter than 2 characters");
        this.line = line;

        if (trainNumber < -1) throw new InvalidParameterException("Train number cannot be less than -1");
        this.trainNumber = trainNumber;

        if (destination == null) throw new InvalidParameterException("Destination cannot be null");
        this.destination = destination;

        if (track < 0) throw new InvalidParameterException("Track id cannot be less than 0");
        this.track = track;

        if (delay == null) throw new InvalidParameterException("Delay cannot be null");
        this.delay = delay;
    }

    public LocalTime getPlannedDeparture() {
        return plannedDeparture;
    }
    public LocalTime getDelayedDeparture() {
        return plannedDeparture
                .plusHours(delay.getHour())
                .plusMinutes(delay.getMinute())
                .plusSeconds(delay.getSecond());
    }

    public String getLine() {
        return line;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getDestination() {
        return destination;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public LocalTime getDelay() {
        return delay;
    }

    public boolean isDelayed() {
        return !delay.equals(LocalTime.MIN);
    }
    public void setDelay(LocalTime delay) {
        this.delay = delay;
    }
}