package task;

import task.generator.FloorGenerator;
import task.generator.PassengersGenerator;
import task.model.Elevator;
import task.model.Floor;
import task.model.Passenger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Program {
    private static final int MAX_PASSENGERS = 5;
    private static final int MAX_ON_FLOOR = 10;
    private static final int MIN_FLOOR = 5;
    private static final int MAX_FLOOR = 20;
    private static final int FIRST_FLOOR = 1;
    private static int[][] arrDisplay;
    private static int[] direct = new int[MAX_PASSENGERS];
    private int maxFloor = 0;

    public void run() {
        int count = 0;
        int passengersLetInside;
        List<Passenger> passengersInElevator;
        Elevator elevator = new Elevator(MAX_PASSENGERS);
        elevator.setUp(true);
        Floor[] floors = FloorGenerator.generateFloor(MIN_FLOOR, MAX_FLOOR);
        maxFloor = floors.length;

        generatePassengersForFloors(floors);

        boolean empty = false;
        Floor floor = floors[0];
        List<Passenger> passengers = passengersUp(floor, MAX_PASSENGERS);

        elevator.setPeopleInside(passengers);
        floor.getPassengersOnFloor().removeAll(passengers);

        direct = addNumsForDisplay(elevator);
        arrDisplay = new int[floors.length][];

        while (!empty) {
            System.out.println("*** STEP " + (++count) + " ***");

            passengers = passengersOutFromElevator(elevator.getPeopleInside(), floor.getNumber());
            elevator.getPeopleInside().removeAll(passengers);
            firstFloorOut(passengers, floor);
            int floorNumGenerate = floor.getNumber();

            passengersLetInside = MAX_PASSENGERS - elevator.getPeopleInside().size();

            if (elevator.getPeopleInside().isEmpty()) {
                floor.getPassengersOnFloor().addAll(passengers);
                if (floor.getPassengersOnFloor().isEmpty()) {
                    floor.getPassengersOnFloor().addAll(passengers);
                    int floorNumber = moveOnCall(floors);
                    floor = floors[floorNumber];
                }
                passengersInElevator = selectPassengers(floor, elevator, passengersLetInside);
                floor.getPassengersOnFloor().removeAll(passengersInElevator);
                firstFloorOut(passengers, floor);
            } else {
                if (elevator.isUp()) {
                    passengersInElevator = passengersUp(floor, passengersLetInside);
                } else {
                    passengersInElevator = passengersDown(floor, passengersLetInside);
                }
                elevator.getPeopleInside().addAll(passengersInElevator);
                floor.getPassengersOnFloor().removeAll(passengersInElevator);
                floor.getPassengersOnFloor().addAll(passengers);
            }
            addNeededFloor(passengers, floorNumGenerate);
            setTime(passengers);
            direct = addNumsForDisplay(elevator);
            setArr(floors);
            display(elevator, floor);

            empty = isEmptyFloors(floors) && isEmptyElevator(elevator);

            if (elevator.isUp()) {
                floor = floor.getNext();
            } else {
                floor = floor.getPrev();
            }
        }
        System.out.println("----All people are outside!----");
    }

    private boolean isEmptyElevator(Elevator elevator) {
        return elevator.getPeopleInside().isEmpty();
    }

    private void generatePassengersForFloors(Floor[] floors) {
        for (Floor floor : floors) {
            List<Passenger> passengerFirst = PassengersGenerator.generatePassengers(MAX_ON_FLOOR);
            setTime(passengerFirst);
            addNeededFloor(passengerFirst, floor.getNumber());
            floor.setPassengersOnFloor(passengerFirst);
        }
    }

    private int[] addNumsForDisplay(Elevator elevator) {
        return elevator.getPeopleInside().stream().mapToInt(Passenger::getNeededFloor).toArray();
    }

    private static void setArr(Floor[] floors) {

        for (int i = 0; i < floors.length; i++) {
            Floor floor = floors[i];
            List<Passenger> passengers = floor.getPassengersOnFloor();
            arrDisplay[i] = new int[passengers.size()];
            for (int j = 0; j < passengers.size(); j++) {
                arrDisplay[i][j] = passengers.get(j).getNeededFloor();
            }
        }
    }

    private static void display(Elevator elevator, Floor floor) {
        for (int i = arrDisplay.length - 1, n = arrDisplay.length; i >= 0; i--, n--) {
            System.out.print(" floor" + n + "|");
            for (int j = 0; j < arrDisplay[i].length; j++) {
                System.out.printf("%3d", arrDisplay[i][j]);
            }
            System.out.print(" | ");
            if (elevator.isUp() && floor.getNumber() == n) {
                System.out.print("^");
                for (int value : direct) {
                    System.out.printf("%3d", value);
                }
            } else if (!elevator.isUp() && floor.getNumber() == n) {
                System.out.print("V");
                for (int value : direct) {
                    System.out.printf("%3d", value);
                }
            }
            System.out.println();
        }
    }

    private void addNeededFloor(List<Passenger> passengerFirst, int floor) {
        passengerFirst.forEach(passenger -> {
            int floorNumber = FloorGenerator
                    .generateFloorForPassenger(FIRST_FLOOR, maxFloor, floor);
            passenger.setNeededFloor(floorNumber);
        });
    }

    private void firstFloorOut(List<Passenger> passengers, Floor floor) {
        if (floor.getNumber() == FIRST_FLOOR) {
            passengers.clear();
        }
    }

    private boolean upDirection(Passenger passenger, int floorNumber) {
        return passenger.getNeededFloor() > floorNumber;
    }

    private int moveOnCall(Floor[] floors) {
        List<List<Passenger>> allPassengers = Arrays.stream(floors)
                .map(Floor::getPassengersOnFloor)
                .collect(Collectors.toList());

        LocalDateTime time = LocalDateTime.MAX;
        Passenger passenger;
        int floorNumber = 0;

        for (int i = 0; i < allPassengers.size(); i++) {
            for (int a = 0; a < allPassengers.get(i).size(); a++) {
                passenger = allPassengers.get(i).get(a);
                if (time.isAfter(passenger.getTimeWaiting())) {
                    time = passenger.getTimeWaiting();
                    floorNumber = i;
                }
            }
        }
        return floorNumber;
    }

    private void setTime(List<Passenger> passengers) {
        passengers.forEach(x -> x.setTimeWaiting(LocalDateTime.now()));
    }

    private boolean isEmptyFloors(Floor[] floors) {
        return Arrays.stream(floors)
                .allMatch(x -> x.getPassengersOnFloor().isEmpty());
    }

    private List<Passenger> passengersUp(Floor floor, int passengersQuantity) {
        return floor.getPassengersOnFloor()
                .stream()
                .filter(x -> x.getNeededFloor() > floor.getNumber())
                .limit(passengersQuantity).collect(Collectors.toList());

    }

    private List<Passenger> passengersDown(Floor floor, int passengersQuantity) {
        return floor.getPassengersOnFloor()
                .stream()
                .filter(x -> x.getNeededFloor() < floor.getNumber())
                .limit(passengersQuantity).collect(Collectors.toList());
    }

    private List<Passenger> passengersOutFromElevator(List<Passenger> passengersIn, int floorNum) {
        return passengersIn
                .stream()
                .filter(passengers -> passengers.getNeededFloor() == floorNum)
                .collect(Collectors.toList());
    }

    private List<Passenger> selectPassengers(Floor floor, Elevator elevator, int passengersLetInside) {
        List<Passenger> passengersInElevator = new ArrayList<>();
        boolean up;
        try {
            up = upDirection(floor.getPassengersOnFloor().get(0), floor.getNumber());
        } catch (IndexOutOfBoundsException e) {
            return passengersInElevator;
        }
        if (up) {
            passengersInElevator = passengersUp(floor, passengersLetInside);
        } else {
            passengersInElevator = passengersDown(floor, passengersLetInside);
        }
        elevator.getPeopleInside().addAll(passengersInElevator);
        elevator.setUp(up);
        return passengersInElevator;
    }
}
