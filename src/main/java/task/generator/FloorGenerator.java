package task.generator;

import org.apache.commons.lang3.RandomUtils;
import task.model.Floor;

public class FloorGenerator {

    public static Floor[] generateFloor(int MIN_FLOORS, int MAX_FLOORS) {
        int size = 0;
        int quantityFloors = RandomUtils.nextInt(MIN_FLOORS, MAX_FLOORS + 1);
        Floor[] floors = new Floor[quantityFloors];

        for (int i = 1; i <= quantityFloors; i++) {
            Floor floor = new Floor(i);
            if (i == 1) {
                floors[0] = floor;
            } else {
                Floor prev = floors[size];
                floors[size].setNext(floor);
                floors[++size] = floor;
                floors[size].setPrev(prev);
            }
        }
        return floors;
    }

    public static int generateFloorForPassenger(int start, int end, int current) {
        int newFloor = RandomUtils.nextInt(start, end + 1);
        if (newFloor == current) {
            return generateFloorForPassenger(start, end, newFloor);
        }
        return newFloor;
    }
}
