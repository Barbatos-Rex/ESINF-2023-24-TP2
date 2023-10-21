package pt.ipp.isep.esinf.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.esinf.TestUtils;
import pt.ipp.isep.esinf.struct.auxiliary.TreeCluster;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ImporterTest {
    private Importer importer;

    @BeforeEach
    void setUp() {
        importer = new Importer();
    }

    @AfterEach
    void tearDown() {
        importer = null;
    }

    @Test
    void importTrips() {

        TreeCluster cluster = importer.importData();
        List<Integer> ints = TestUtils.readTripsIds();

        for (Integer anInt : ints) {
            assertTrue(cluster.getTripTree().search(anInt).isPresent(), "Missing id: " + anInt);
        }
    }

    @Test
    void importVehicles() {
        TreeCluster cluster = importer.importData();
        List<Integer> ints = TestUtils.readVehicleIds();

        for (Integer anInt : ints) {
            assertTrue(cluster.getVehicleTree().search(anInt).isPresent(), "Missing id: " + anInt);
        }

    }
}