package pt.ipp.isep.esinf;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestUtils {

    private TestUtils() {
    }


    public static List<Integer> readTripsIds() {
        List<Integer> ints = new ArrayList<>();
        Scanner sc = new Scanner(TestUtils.class.getClassLoader().getResourceAsStream("tripsids.txt"));
        while (sc.hasNextLine()) {
            ints.add(Integer.parseInt(sc.nextLine()));
        }
        return ints;
    }

    public static List<Integer> readVehicleIds() {
        List<Integer> ints = new ArrayList<>();
        Scanner sc = new Scanner(TestUtils.class.getClassLoader().getResourceAsStream("vehiclesids.txt"));
        while (sc.hasNextLine()) {
            ints.add(Integer.parseInt(sc.nextLine()));
        }
        return ints;
    }
}
