package pt.ipp.isep.esinf;

import pt.ipp.isep.esinf.auxiliary.TripVehicle;
import pt.ipp.isep.esinf.auxiliary.VehicleTrips;
import pt.ipp.isep.esinf.functionality.Search;
import pt.ipp.isep.esinf.io.Importer;
import pt.ipp.isep.esinf.struct.TreeCluster;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        TreeCluster result = new Importer().importData();

        Search s = new Search(result);

        Optional<TripVehicle> tv = s.findTripAndVehicle("T-561");
        Optional<VehicleTrips> vt = s.findVehicleAndTripsById("V-108");

        if (tv.isPresent()){
            System.out.println(tv.get());
        }

        System.out.println();

        if (vt.isPresent()){
            System.out.println(vt.get());
        }







    }
}