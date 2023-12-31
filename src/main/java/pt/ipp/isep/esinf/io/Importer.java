package pt.ipp.isep.esinf.io;

import pt.ipp.isep.esinf.domain.trip.*;
import pt.ipp.isep.esinf.domain.vehicle.Vehicle;
import pt.ipp.isep.esinf.struct.auxiliary.TreeCluster;
import pt.ipp.isep.esinf.struct.searchable.SearchableTripBST;
import pt.ipp.isep.esinf.struct.searchable.SearchableVehicleBST;

import java.io.InputStream;
import java.util.*;

public class Importer {

    /**
     * Complexity: O(t)+2*O(v), where t is the ammount of trips and v the ammount of vehicles in the respective files
     *
     * @return The cluster of trees that represent the data on the three files
     */
    public TreeCluster importData() {
        InputStream vedStaticIce = getClass().getClassLoader().getResourceAsStream("VED_Static_Data_ICE&HEV.csv");
        InputStream vedStaticEv = getClass().getClassLoader().getResourceAsStream("VED_Static_Data_PHEV&EV.csv");
        InputStream ved180404 = getClass().getClassLoader().getResourceAsStream("VED_180404_week.csv");

        SearchableVehicleBST vehicleTree = new SearchableVehicleBST();
        SearchableTripBST tripTree = new SearchableTripBST();
        importVehicles(vehicleTree, vedStaticEv);
        importVehicles(vehicleTree, vedStaticIce);
        importTrip(tripTree, ved180404);
        return new TreeCluster(vehicleTree, tripTree);
    }

    /**
     * Complexity: O(t)
     *
     * @param result The result tree to be populated
     * @param stream The Stream that represents the file to fecth all the trips data
     */
    private void importTrip(SearchableTripBST result, InputStream stream) {
        try (Scanner sc = new Scanner(stream)) {
            sc.nextLine();
            Map<TripId, Trip> tripMap = new HashMap<>();
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] split = line.split(",");

                TripId id = new TripId(Integer.parseInt(split[2]), Integer.parseInt(split[1]), split[0]);
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
                result.insert(value.getId().getId(), value);
            }
        }
    }

    /**
     * Complexity: O(v)
     *
     * @param tree   The result tree to be populated
     * @param stream The Stream that represents the file to fecth all the vehicles data
     */
    private void importVehicles(SearchableVehicleBST tree, InputStream stream) {
        List<Vehicle> vs = new ArrayList<>();
        try (Scanner sc = new Scanner(stream)) {
            sc.nextLine();
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] split = line.split(",");
                Vehicle v = new Vehicle(Integer.parseInt(split[0]), split[1], split[2], split[3], split[4], split[5], split[6]);
                vs.add(v);
            }
            Collections.shuffle(vs);
            for (Vehicle v : vs) {
                tree.insert(v.getId(), v);
            }
        }
    }


}
