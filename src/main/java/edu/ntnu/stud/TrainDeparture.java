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
        this.plannedDeparture = plannedDeparture;
        this.line = line;
        this.trainNumber = trainNumber;
        this.destination = destination;
        this.track = track;
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

    public void setDelay(LocalTime delay) {
        this.delay = delay;
    }
}