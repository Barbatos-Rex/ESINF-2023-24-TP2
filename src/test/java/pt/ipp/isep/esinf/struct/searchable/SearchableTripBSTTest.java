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
    void obtainTripStartEnd() {
    }

    @Test
    void testObtainTripStartEnd() {
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