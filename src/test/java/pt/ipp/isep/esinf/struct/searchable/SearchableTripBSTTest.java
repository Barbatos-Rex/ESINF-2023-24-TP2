package pt.ipp.isep.esinf.struct.searchable;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.esinf.TestUtils;
import pt.ipp.isep.esinf.domain.trip.Trip;
import pt.ipp.isep.esinf.domain.vehicle.Vehicle;
import pt.ipp.isep.esinf.io.Importer;
import pt.ipp.isep.esinf.struct.MaxMinAverageOfTripByType;
import pt.ipp.isep.esinf.struct.MaxMinAverageOfTripEntry;
import pt.ipp.isep.esinf.struct.auxiliary.TreeCluster;
import pt.ipp.isep.esinf.struct.auxiliary.TripStartEnd;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchableTripBSTTest {

    private static List<Integer> tripIds;

    private TreeCluster cluster;

    @BeforeAll
    static void initialize() {
        tripIds = TestUtils.readTripsIds();
    }

    @BeforeEach
    void setUp() {
        cluster = new Importer().importData();
    }

    @AfterEach
    void tearDown() {
        cluster = null;
    }

    @Test
    void printContextEx2() {
        System.out.println("<!--Execute exercise 2 (Between days: 150 - 156)-->");
        MaxMinAverageOfTripByType ex2 = cluster.getTripTree().getMaxMinAvgBetween(150, 156, cluster.getVehicleTree());
        ex2.updateValues();
        System.out.println(ex2);
    }

    @Test
    void getMaxMinAvgBetweenStartAndEndAreTheSameTest() {
        MaxMinAverageOfTripByType result1 = cluster.getTripTree().getMaxMinAvgBetween(150, 156, cluster.getVehicleTree());
        MaxMinAverageOfTripByType result2 = cluster.getTripTree().getMaxMinAvgBetween(156, 150, cluster.getVehicleTree());
        assertEquals(result1.getStart(), result2.getStart());
        assertEquals(result1.getEnd(), result2.getEnd());
    }

    @Test
    void getMaxMinAvgBetweenTypesAreCorrectTest() {
        MaxMinAverageOfTripByType result = cluster.getTripTree().getMaxMinAvgBetween(150, 156, cluster.getVehicleTree());
        result.updateValues();
        for (Map.Entry<String, MaxMinAverageOfTripEntry> entry : result.getTrips().entrySet()) {
            assertEquals(entry.getKey(), entry.getValue().getType());
        }
    }

    @Test
    void getMaxMinAvgBetweenTipVehicleIsInCorrectTypeTest() {
        MaxMinAverageOfTripByType result = cluster.getTripTree().getMaxMinAvgBetween(150, 156, cluster.getVehicleTree());
        result.updateValues();
        for (Map.Entry<String, MaxMinAverageOfTripEntry> entry : result.getTrips().entrySet()) {
            for (Trip trip : entry.getValue().getTrips()) {
                Optional<Vehicle> opt = cluster.getVehicleTree().search(trip.getId().getVehicle());
                assertTrue(opt.isPresent());
                assertEquals(entry.getKey(), opt.get().getType());
            }
        }
    }

    @Test
    void getMaxMinAvgBetweenTripsAreInRangeTest() {
        MaxMinAverageOfTripByType result = cluster.getTripTree().getMaxMinAvgBetween(150, 156, cluster.getVehicleTree());
        result.updateValues();
        for (Map.Entry<String, MaxMinAverageOfTripEntry> entry : result.getTrips().entrySet()) {
            for (Trip trip : entry.getValue().getTrips()) {
                double day = Double.parseDouble(trip.getId().getDayNum());
                assertTrue(day >= 150 && day <= 156, "Day " + day + " is not between 150 and 156 (inclusive both)");
            }
        }
    }

    @Test
    void printContextEx3() {
        System.out.println("<!--Execute exercise 3 (Trips range: 1500 - 1555)-->");
        Map<Trip, TripStartEnd> ex3 = cluster.getTripTree().obtainTripStartEnd(1500, 1555);
        for (TripStartEnd value : ex3.values()) {
            System.out.println(value);
            System.out.println("----------------------------------------------------------");
        }
    }

    @Test
    void obtainTripStartEndTripExistsTest() {
        Map<Trip, TripStartEnd> result = cluster.getTripTree().obtainTripStartEnd(Integer.MIN_VALUE, Integer.MAX_VALUE);
        for (Integer tripId : tripIds) {
            Optional<Trip> opt = cluster.getTripTree().search(tripId);
            assertTrue(opt.isPresent());
            Trip trip = opt.get();
            assertTrue(result.containsKey(trip));
        }
    }

    @Test
    void obtainTripStartEndStartAndEndEqualOnTripTest() {
        Map<Trip, TripStartEnd> result = cluster.getTripTree().obtainTripStartEnd(Integer.MIN_VALUE, Integer.MAX_VALUE);
        for (Integer tripId : tripIds) {
            Optional<Trip> opt = cluster.getTripTree().search(tripId);
            Trip trip = opt.get();
            assertEquals(trip.getEntries().first().getCoordenates(), result.get(trip).obtainStartAndEnd().getFirst());
            assertEquals(trip.getEntries().last().getCoordenates(), result.get(trip).obtainStartAndEnd().getSecond());
        }
    }

    @Test
    void findLongestTripByVehicle() {
    }

    @Test
    void findLongestTripByVehicleIds() {
    }

    @Test
    void generateCoordenate2DTree() {
    }
}