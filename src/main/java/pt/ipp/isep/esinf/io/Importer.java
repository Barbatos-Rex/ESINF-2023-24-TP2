package pt.ipp.isep.esinf.io;

import pt.ipp.isep.esinf.domain.trip.*;
import pt.ipp.isep.esinf.domain.vehicle.Vehicle;
import pt.ipp.isep.esinf.struct.TreeCluster;
import pt.ipp.isep.esinf.struct.searchable.SearchableBST;

import java.io.InputStream;
import java.util.*;

public class Importer {


    public TreeCluster importData() {
        InputStream vedStaticIce = getClass().getClassLoader().getResourceAsStream("VED_Static_Data_ICE&HEV.csv");
        InputStream vedStaticEv = getClass().getClassLoader().getResourceAsStream("VED_Static_Data_PHEV&EV.csv");
        InputStream ved180404 = getClass().getClassLoader().getResourceAsStream("VED_180404_week.csv");

        SearchableBST<Vehicle, String> vehicleTree = new SearchableBST<>();
        SearchableBST<Trip, String> tripTree = new SearchableBST<>();
        importVehicles(vehicleTree, vedStaticEv);
        importVehicles(vehicleTree, vedStaticIce);
        importTrip(tripTree, ved180404);
        return new TreeCluster(vehicleTree, tripTree);
    }

    private void importTrip(SearchableBST<Trip, String> result, InputStream stream) {
        try (Scanner sc = new Scanner(stream)) {
            sc.nextLine();
            Map<TripId, Trip> tripMap = new HashMap<>();
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] split = line.split(",");

                TripId id = new TripId(split[2], split[1], split[0]);
                TimeCoordenates coordenates = new TimeCoordenates(split[3], split[4], split[5]);
                TripFuelExpendures expendures = new TripFuelExpendures(split[6], split[7], split[8], split[9], split[10], split[11]);
                BatteryUsage batteryUsage = new BatteryUsage(split[12], split[13], split[14], split[15], split[16]);
                FuelTrimBank bank = new FuelTrimBank(split[17], split[18], split[19], split[20]);
                if (!tripMap.containsKey(id)) {
                    tripMap.put(id, new Trip(id));
                }
                tripMap.get(id).addEntry(new TripEntry(coordenates, expendures, batteryUsage, bank));
            }
            for (Trip value : tripMap.values()) {
                result.insert("T-" + value.getId().getId(), value);
            }
        }
    }

    private void importVehicles(SearchableBST<Vehicle, String> tree, InputStream stream) {
        List<Vehicle> vs = new ArrayList<>();
        try (Scanner sc = new Scanner(stream)) {
            sc.nextLine();
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] split = line.split(",");
                Vehicle v = new Vehicle(split[0], split[1], split[2], split[3], split[4], split[5], split[6]);
                vs.add(v);
            }
            Collections.shuffle(vs);
            for (Vehicle v : vs) {
                tree.insert("V-" + v.getId(), v);
            }
        }
    }


}
