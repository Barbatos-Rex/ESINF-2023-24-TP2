package pt.ipp.isep.esinf.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.esinf.struct.auxiliary.TreeCluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        List<Integer> ints = new ArrayList<>();

        Scanner sc = new Scanner(getClass().getClassLoader().getResourceAsStream("tripsids.txt"));
        while (sc.hasNextLine()) {
            ints.add(Integer.parseInt(sc.nextLine()));
        }

        for (Integer anInt : ints) {
            assertTrue(cluster.getTripTree().search(anInt).isPresent(), "Missing id: " + anInt);
        }
    }

    @Test
    void importVehicles() {
        TreeCluster cluster = importer.importData();
        List<Integer> ints = new ArrayList<>();

        Scanner sc = new Scanner(getClass().getClassLoader().getResourceAsStream("vehiclesids.txt"));
        while (sc.hasNextLine()) {
            ints.add(Integer.parseInt(sc.nextLine()));
        }

        for (Integer anInt : ints) {
            assertTrue(cluster.getVehicleTree().search(anInt).isPresent(), "Missing id: " + anInt);
        }

    }
}