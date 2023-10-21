package pt.ipp.isep.esinf;

import pt.ipp.isep.esinf.domain.trip.TimeCoordenates;
import pt.ipp.isep.esinf.domain.trip.Trip;
import pt.ipp.isep.esinf.functionality.Search;
import pt.ipp.isep.esinf.io.Importer;
import pt.ipp.isep.esinf.struct.MaxMinAverageOfTripByType;
import pt.ipp.isep.esinf.struct.auxiliary.TreeCluster;
import pt.ipp.isep.esinf.struct.auxiliary.TripStartEnd;
import pt.ipp.isep.esinf.struct.auxiliary.TripVehicle;
import pt.ipp.isep.esinf.struct.auxiliary.VehicleTrips;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {


        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-d")) {
                System.setProperty("Debug", "true");
            }
        }

        TreeCluster result = new Importer().importData();
        File f = new File("./log.txt");
        if (!f.exists()) {
            f.createNewFile();
        }

        PrintWriter pw = new PrintWriter(f);

        Search s = new Search(result);

        pw.println("<!--Execute exercise 1 (No relevant parameters to add)-->");

        Optional<TripVehicle> tv = s.findTripAndVehicle(561);
        Optional<VehicleTrips> vt = s.findVehicleAndTripsById(108);

        if (tv.isPresent()) {
            System.out.println(tv.get());
            pw.println(tv.get());
        }

        System.out.println();

        if (vt.isPresent()) {
            System.out.println(vt.get());
            pw.println(vt.get());
        }

        pw.println("<!--Execute exercise 2 (Between days: 150 - 156)-->");
        MaxMinAverageOfTripByType ex2 = result.getTripTree().getMaxMinAvgBetween(150, 156, result.getVehicleTree());
        ex2.updateValues();
        System.out.println(ex2);
        System.out.println("\n");
        pw.println(ex2);
        pw.println("\n");


        pw.println("<!--Execute exercise 3 (Trips range: 1500 - 1555)-->");
        Map<Trip, TripStartEnd> ex3 = result.getTripTree().obtainTripStartEnd(1500, 1555);
        for (TripStartEnd value : ex3.values()) {
            System.out.println(value);
            System.out.println("----------------------------------------------------------");
            pw.println(value);
            pw.println("----------------------------------------------------------");
        }
        System.out.println("\n");
        pw.println("\n");

        pw.println("<!--Execute exercise 4 (Vehicles searched: 130 , 131, 132, 550 and 554)-->");
        Map<Integer, Trip> ex4 = result.getTripTree().findLongestTripByVehicleIds(Set.of(130, 131, 132, 550, 554));
        System.out.println();
        for (Map.Entry<Integer, Trip> integerTripEntry : ex4.entrySet()) {
            System.out.println();
            System.out.println("VehicleId: " + integerTripEntry.getKey());
            System.out.println("Longest Trip: " + integerTripEntry.getValue().getId().getId());
            System.out.println("Trip Distance: " + String.format("%.3f", integerTripEntry.getValue().tripDistance()) + " km");
            System.out.println();
            System.out.println("//////////////////////////////////////////////////////////////////////////////");

            pw.println();
            pw.println("VehicleId: " + integerTripEntry.getKey());
            pw.println("Longest Trip: " + integerTripEntry.getValue().getId().getId());
            pw.println("Trip Distance: " + String.format("%.3f", integerTripEntry.getValue().tripDistance()) + " km");
            pw.println();
            pw.println("//////////////////////////////////////////////////////////////////////////////");
        }
        System.out.println("\n");
        pw.println("\n");

        pw.println("<!--Execute exercise 5 ((42.24882,-83.76743139) to (43.25883,-84.76743125))-->");
        Trip ex5 = result.getTripTree().generateCoordenate2DTree().obtainClosestCoordenatesToOriginDestination(
                TimeCoordenates.genCoordWithoutTime("42.24882", "-83.76743139"),
                TimeCoordenates.genCoordWithoutTime("43.25883", "-84.76743125"));
        System.out.println("Trip: " + ex5);
        System.out.println("Start: " + ex5.getEntries().getFirst().getCoordenates());
        System.out.println("End: " + ex5.getEntries().getLast().getCoordenates());
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        pw.println("Trip: " + ex5);
        pw.println("Start: " + ex5.getEntries().getFirst().getCoordenates());
        pw.println("End: " + ex5.getEntries().getLast().getCoordenates());
        pw.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");


        pw.println("<!--Execute exercise 5 ((42.24882,-83.76743139) to (43.25883,-84.76743125) top 10 longest trips)-->");
        Set<Trip> ex6 = result.getTripTree().generateCoordenate2DTree().topNLongestTripsBetweenInArea(
                TimeCoordenates.genCoordWithoutTime("42.24882", "-83.76743139"), TimeCoordenates.genCoordWithoutTime("43.25883", "-84.76743125"), 10);


        int count = 1;
        for (Trip trip : ex6) {
            System.out.print("(" + count + "º)Trip: ");
            System.out.println(trip);
            System.out.print("\nTraveld Distance: ");
            System.out.println(trip.tripRealDistance());
            System.out.println("#####################################################################################");


            pw.print("(" + count + "º)Trip: ");
            pw.println(trip);
            pw.print("\nTraveld Distance: ");
            pw.println(trip.tripRealDistance());
            pw.println("#####################################################################################");

            count++;
        }

        pw.println("\n\nDate: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS").format(new Date()));
        pw.flush();
        pw.close();


    }


}