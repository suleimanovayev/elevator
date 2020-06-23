package task.model;

import java.util.List;

public class Elevator {
    private int maxCapacity;
    private boolean up;
    private List<Passenger> peopleInside;

    public Elevator(final int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean isUp() {
        return up;
    }

    public List<Passenger> getPeopleInside() {
        return peopleInside;
    }

    public void setPeopleInside(List<Passenger> peopleInside) {
        this.peopleInside = peopleInside;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(final int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
