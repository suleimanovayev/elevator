package task.generator;

import org.apache.commons.lang3.RandomUtils;
import task.model.Passenger;

import java.util.ArrayList;
import java.util.List;

public class PassengersGenerator {

    public static List<Passenger> generatePassengers(int maxOnFloor) {
        List<Passenger> passengers = new ArrayList<>();
        int quantityPassengers = RandomUtils.nextInt(0, maxOnFloor + 1);
        for (int i = 0; i < quantityPassengers; i++) {
            passengers.add(new Passenger());
        }
        return passengers;
    }
}
