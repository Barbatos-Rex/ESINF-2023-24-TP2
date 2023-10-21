package pt.ipp.isep.esinf.functionality;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.esinf.TestUtils;
import pt.ipp.isep.esinf.domain.trip.Trip;
import pt.ipp.isep.esinf.io.Importer;
import pt.ipp.isep.esinf.struct.auxiliary.TreeCluster;
import pt.ipp.isep.esinf.struct.auxiliary.TripVehicle;
import pt.ipp.isep.esinf.struct.auxiliary.VehicleTrips;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchTest {
    private static TreeCluster cluster;
    private Search search;

    @BeforeAll
    static void initialize() {
        cluster = new Importer().importData();
    }

    @BeforeEach
    void setUp() {
        search = new Search(cluster);
    }

    @AfterEach
    void tearDown() {
        search = null;
    }

    @Test
    void printContext() {
        System.out.println("<!--Execute exercise 1 (VehicleId=561 , TripId=108)-->");

        Optional<TripVehicle> tv = search.findTripAndVehicle(561);
        Optional<VehicleTrips> vt = search.findVehicleAndTripsById(108);

        tv.ifPresent(System.out::println);

        System.out.println();

        vt.ifPresent(System.out::println);
    }

    @Test
    void findTripAndVehicleResultIsPresentTest() {
        List<Integer> ids = TestUtils.readTripsIds();
        for (Integer id : ids) {
            Optional<TripVehicle> opt = search.findTripAndVehicle(id);
            assertTrue(opt.isPresent());
            TripVehicle elem = opt.get();
            assertTrue(cluster.getTripTree().search(elem.getTrip().getId().getId()).isPresent());
            assertTrue(cluster.getVehicleTree().search(elem.getVehicle().getId()).isPresent());
            assertEquals(elem.getTrip().getId().getVehicle(), elem.getVehicle().getId());
            assertEquals(elem.getTrip().getId().getId(), id);
        }
    }

    @Test
    void findTripAndVehicleTripIsOnTreeTest() {
        List<Integer> ids = TestUtils.readTripsIds();
        for (Integer id : ids) {
            Optional<TripVehicle> opt = search.findTripAndVehicle(id);
            TripVehicle elem = opt.get();
            assertTrue(cluster.getTripTree().search(elem.getTrip().getId().getId()).isPresent());
        }
    }

    @Test
    void findTripAndVehicleVehicleIsOnTreeTest() {
        List<Integer> ids = TestUtils.readTripsIds();
        for (Integer id : ids) {
            Optional<TripVehicle> opt = search.findTripAndVehicle(id);
            TripVehicle elem = opt.get();
            assertTrue(cluster.getVehicleTree().search(elem.getVehicle().getId()).isPresent());
        }
    }

    @Test
    void findTripAndVehicleVehicleIdIsEqualInTripAndInVehicleTest() {
        List<Integer> ids = TestUtils.readTripsIds();
        for (Integer id : ids) {
            Optional<TripVehicle> opt = search.findTripAndVehicle(id);
            TripVehicle elem = opt.get();
            assertEquals(elem.getTrip().getId().getVehicle(), elem.getVehicle().getId());
        }
    }

    @Test
    void findTripAndVehicleTripIdIsCorrectTest() {
        List<Integer> ids = TestUtils.readTripsIds();
        for (Integer id : ids) {
            Optional<TripVehicle> opt = search.findTripAndVehicle(id);
            TripVehicle elem = opt.get();
            assertEquals(elem.getTrip().getId().getId(), id);
        }
    }


    @Test
    void findVehicleAndTripsByIdVehicleIsAlwaysPresentTest() {
        List<Integer> ids = TestUtils.readVehicleIds();
        for (Integer id : ids) {
            Optional<VehicleTrips> opt = search.findVehicleAndTripsById(id);
            assertTrue(opt.isPresent());
            VehicleTrips elem = opt.get();
            assertEquals(cluster.getVehicleTree().search(id).get(), elem.getVehicle());
            for (Trip trip : elem.getTrips()) {
                Trip clusterTrip = cluster.getTripTree().search(trip.getId().getId()).get();
                assertEquals(clusterTrip, trip);
                assertEquals(trip.getId().getVehicle(), id.intValue());
            }
        }
    }

    @Test
    void findVehicleAndTripsByIdVehicleIsOnTheTreeTest() {
        List<Integer> ids = TestUtils.readVehicleIds();
        for (Integer id : ids) {
            Optional<VehicleTrips> opt = search.findVehicleAndTripsById(id);
            VehicleTrips elem = opt.get();
            assertEquals(cluster.getVehicleTree().search(id).get(), elem.getVehicle());
        }
    }

    @Test
    void findVehicleAndTripsByIdTripsAreOnTreeTest() {
        List<Integer> ids = TestUtils.readVehicleIds();
        for (Integer id : ids) {
            Optional<VehicleTrips> opt = search.findVehicleAndTripsById(id);
            VehicleTrips elem = opt.get();
            for (Trip trip : elem.getTrips()) {
                Trip clusterTrip = cluster.getTripTree().search(trip.getId().getId()).get();
                assertEquals(clusterTrip, trip);
            }
        }
    }

    @Test
    void findVehicleAndTripsByIdTripHasCorrectVehicleTest() {
        List<Integer> ids = TestUtils.readVehicleIds();
        for (Integer id : ids) {
            Optional<VehicleTrips> opt = search.findVehicleAndTripsById(id);
            VehicleTrips elem = opt.get();
            for (Trip trip : elem.getTrips()) {
                assertEquals(trip.getId().getVehicle(), id.intValue());
            }
        }
    }
}