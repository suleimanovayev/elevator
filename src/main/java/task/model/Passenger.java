package task.model;

import java.time.LocalDateTime;
import java.util.Comparator;

public class Passenger implements Comparator<Passenger> {
    private int neededFloor;
    private LocalDateTime timeWaiting;

    public Passenger() {
    }

    public int getNeededFloor() {
        return neededFloor;
    }

    public void setNeededFloor(int neededFloor) {
        this.neededFloor = neededFloor;
    }

    public LocalDateTime getTimeWaiting() {
        return timeWaiting;
    }

    public void setTimeWaiting(LocalDateTime timeWaiting) {
        this.timeWaiting = timeWaiting;
    }


    @Override
    public String toString() {
        return "Passenger{" +
                "neededFloor=" + neededFloor +
                ", timeWaiting=" + timeWaiting +
                '}';
    }

    @Override
    public int compare(Passenger o1, Passenger o2) {
        return o1.getTimeWaiting().compareTo(o2.getTimeWaiting());
    }
}
