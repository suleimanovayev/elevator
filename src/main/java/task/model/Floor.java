package task.model;

import java.util.List;

public class Floor {
    private Floor next;
    private Floor prev;
    private List<Passenger> passengersOnFloor;
    private int number;
    private boolean elevatorButton;

    public Floor(int number) {
        this.number = number;
    }

    public List<Passenger> getPassengersOnFloor() {
        return passengersOnFloor;
    }

    public void setPassengersOnFloor(List<Passenger> passengersOnFloor) {
        this.passengersOnFloor = passengersOnFloor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isElevatorButton() {
        return elevatorButton;
    }

    public void setElevatorButton(boolean elevatorButton) {
        this.elevatorButton = elevatorButton;
    }

    public Floor getNext() {
        return next;
    }

    public void setNext(Floor next) {
        this.next = next;
    }

    public Floor getPrev() {
        return prev;
    }

    public void setPrev(Floor prev) {
        this.prev = prev;
    }
}
