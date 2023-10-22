package pt.ipp.isep.esinf.struct.searchable;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.esinf.TestUtils;
import pt.ipp.isep.esinf.domain.trip.TimeCoordenates;
import pt.ipp.isep.esinf.domain.trip.Trip;
import pt.ipp.isep.esinf.domain.vehicle.Vehicle;
import pt.ipp.isep.esinf.io.Importer;
import pt.ipp.isep.esinf.struct.MaxMinAverageOfTripByType;
import pt.ipp.isep.esinf.struct.MaxMinAverageOfTripEntry;
import pt.ipp.isep.esinf.struct.auxiliary.TreeCluster;
import pt.ipp.isep.esinf.struct.auxiliary.TripStartEnd;
import pt.ipp.isep.esinf.struct.simple.Trip2DTree;
import pt.ipp.isep.esinf.struct.simple.TwoDTree;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SearchableTripBSTTest {

    private static List<Integer> tripIds;

    private static List<Integer> vehicleIds;

    private TreeCluster cluster;

    @BeforeAll
    static void initialize() {
        tripIds = TestUtils.readTripsIds();
        vehicleIds = TestUtils.readVehicleIds();
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
    void printContextEx4() {
        System.out.println("<!--Execute exercise 4 (Vehicles searched: 130 , 131, 132, 550 and 554)-->");
        Map<Integer, Trip> ex4 = cluster.getTripTree().findLongestTripByVehicleIds(Set.of(130, 131, 132, 550, 554));
        System.out.println();
        for (Map.Entry<Integer, Trip> integerTripEntry : ex4.entrySet()) {
            System.out.println();
            System.out.println("VehicleId: " + integerTripEntry.getKey());
            if (integerTripEntry.getValue() != null) {
                System.out.println("Longest Trip: " + integerTripEntry.getValue().getId().getId());
                System.out.println("Trip Distance: " + String.format("%.3f", integerTripEntry.getValue().tripDistance()) + " km");
            }
            System.out.println();
            System.out.println("//////////////////////////////////////////////////////////////////////////////");
        }
    }

    @Test
    void findLongestTripByVehicleVehicleIdsMatchTest() {
        Map<Integer, Trip> result = cluster.getTripTree().findLongestTripByVehicleIds(new HashSet<>(vehicleIds));
        for (Map.Entry<Integer, Trip> entry : result.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            assertEquals(entry.getKey(), entry.getValue().getId().getVehicle());
        }
    }

    @Test
    void findLongestTripByVehicleHasAtLeastOneTripTest() {
        Map<Integer, Trip> result = cluster.getTripTree().findLongestTripByVehicleIds(new HashSet<>(vehicleIds));
        for (Integer vehicleId : result.keySet()) {
            Set<Trip> trips = cluster.getTripTree().findAllThatMatch(t -> t.getId().getVehicle() == vehicleId);
            if (result.get(vehicleId) == null) {
                assertEquals(0, trips.size());
                continue;
            }
            assertFalse(trips.isEmpty());
        }
    }

    @Test
    void findLongestTripByVehicleIsLongestTripTest() {
        Map<Integer, Trip> result = cluster.getTripTree().findLongestTripByVehicleIds(new HashSet<>(vehicleIds));
        for (Integer vehicleId : result.keySet()) {
            Set<Trip> trips = cluster.getTripTree().findAllThatMatch(t -> t.getId().getVehicle() == vehicleId);
            if (result.get(vehicleId) == null) {
                continue;
            }
            TreeSet<Trip> ordered = new TreeSet<>(new Trip.TripDistanceComparator());
            ordered.addAll(trips);
            assertEquals(ordered.first(), result.get(vehicleId));
        }
    }

//    @Test
//    void generateCoordenate2DTreeHasAllTripsPointsTest() {
//        Trip2DTree result = cluster.getTripTree().generateCoordenate2DTree();
//        for (Integer tripId : tripIds) {
//            Optional<Trip> opt = cluster.getTripTree().search(tripId);
//            Trip t = opt.get();
//            assertTrue(result.contains(t.getEntries().first().getCoordenates()));
//            assertTrue(result.contains(t.getEntries().last().getCoordenates()));
//        }
//    }

    @Test
    void generateCoordenate2DTreeTripIsOnCorrectNodeTest() {
        Trip2DTree result = cluster.getTripTree().generateCoordenate2DTree();
        for (TwoDTree.TwoDNode<Trip> node : result) {
            Trip t = node.getElement();
            TimeCoordenates start = t.getEntries().first().getCoordenates();
            TimeCoordenates end = t.getEntries().last().getCoordenates();
            assertTrue(condition(start, end, node.getX(), node.getY()));
        }
    }

    private boolean condition(TimeCoordenates start, TimeCoordenates end, double x, double y) {
        double x1 = Double.parseDouble(start.getLatitude());
        double y1 = Double.parseDouble(start.getLongitude());
        double x2 = Double.parseDouble(end.getLatitude());
        double y2 = Double.parseDouble(end.getLongitude());
        return (x1 == x && y1 == y) || (x2 == x && y2 == y);
    }
}