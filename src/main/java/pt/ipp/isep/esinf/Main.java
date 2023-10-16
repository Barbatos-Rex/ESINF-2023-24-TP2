package pt.ipp.isep.esinf;

import pt.ipp.isep.esinf.domain.trip.Trip;
import pt.ipp.isep.esinf.domain.vehicle.Vehicle;
import pt.ipp.isep.esinf.functionality.Search;
import pt.ipp.isep.esinf.io.Importer;
import pt.ipp.isep.esinf.struct.MaxMinAverageOfTripByType;
import pt.ipp.isep.esinf.struct.auxiliary.TreeCluster;
import pt.ipp.isep.esinf.struct.auxiliary.TripStartEnd;
import pt.ipp.isep.esinf.struct.auxiliary.TripVehicle;
import pt.ipp.isep.esinf.struct.auxiliary.VehicleTrips;
import pt.ipp.isep.esinf.struct.simple.TwoDTree;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        TreeCluster result = new Importer().importData();

        Search s = new Search(result);

        Optional<TripVehicle> tv = s.findTripAndVehicle(561);
        Optional<VehicleTrips> vt = s.findVehicleAndTripsById(108);

        if (tv.isPresent()) {
            System.out.println(tv.get());
        }

        System.out.println();

        if (vt.isPresent()) {
            System.out.println(vt.get());
        }


        MaxMinAverageOfTripByType ex2 = result.getTripTree().getMaxMinAvgBetween(150, 156, result.getVehicleTree());
        ex2.updateValues();
        System.out.println(ex2);

        Map<Trip, TripStartEnd> ex3 = result.getTripTree().obtainTripStartEnd(1500, 1555);
        for (TripStartEnd value : ex3.values()) {
            System.out.println(value);
            System.out.println("----------------------------------------------------------");
        }


        Map<Integer,Trip> ex4 = result.getTripTree().findLongestTripByVehicleIds(Set.of(130,131,132,550,554));
        System.out.println();
        for (Map.Entry<Integer, Trip> integerTripEntry : ex4.entrySet()) {
            System.out.println();
            System.out.println("VehicleId: "+integerTripEntry.getKey());
            System.out.println("Longest Trip: "+integerTripEntry.getValue().getId().getId());
            System.out.println("Trip Distance: "+String.format("%.3f",integerTripEntry.getValue().tripDistance()) + " km");
            System.out.println();
            System.out.println("//////////////////////////////////////////////////////////////////////////////");
        }









    }
}