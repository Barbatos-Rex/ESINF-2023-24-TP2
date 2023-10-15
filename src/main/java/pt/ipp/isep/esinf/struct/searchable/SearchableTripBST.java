package pt.ipp.isep.esinf.struct.searchable;

import pt.ipp.isep.esinf.domain.trip.Trip;
import pt.ipp.isep.esinf.domain.vehicle.Vehicle;
import pt.ipp.isep.esinf.struct.MaxMinAverageOfTripByType;
import pt.ipp.isep.esinf.struct.auxiliary.TripStartEnd;

import java.util.*;

public class SearchableTripBST extends SearchableBST<Trip, Integer> {

    //Can be achived by in Order Traversal, however, recursive is more impressive, it will (might) not blow up, and I like to do this overcomplexity just for fun!
    //But using Order traversal will be slower because it will loop twice for each node instead of tapping them directlly, but the complexity is the same.
    //Any methods like this are just for this are just for avoiding lopping twice; how wonderfull.
    public MaxMinAverageOfTripByType getMaxMinAvgBetween(double dayNum1, double dayNum2, SearchableVehicleBST vehicleTree) {
        if (dayNum1 > dayNum2) {
            return getMaxMinAvgBetween(dayNum2, dayNum1, vehicleTree);
        }
        MaxMinAverageOfTripByType result = new MaxMinAverageOfTripByType(dayNum1, dayNum2);
        getMaxMinAvgBetween(dayNum1, dayNum2, vehicleTree, result, getRootNode());
        return result;
    }

    private void getMaxMinAvgBetween(double dayNum1, double dayNum2, SearchableVehicleBST vehicleTree,
                                     MaxMinAverageOfTripByType struct, SearchableNode<Trip, Integer> root) {
        if (root == null) {
            return;
        }

        Optional<Vehicle> v = vehicleTree.search(root.getElement().getId().getVehicle());
        if (v.isPresent()) {
            Vehicle vehicle = v.get();
            struct.addEntry(root.getElement(), vehicle.getType());
        }

        getMaxMinAvgBetween(dayNum1, dayNum2, vehicleTree, struct, root.getLeft());
        getMaxMinAvgBetween(dayNum1, dayNum2, vehicleTree, struct, root.getRight());
    }


    public Map<Trip, TripStartEnd> obtainTripStartEnd(Set<Trip> trips) {
        Map<Trip, TripStartEnd> result = new TreeMap<>();
        for (Trip trip : trips) {
            result.put(trip, new TripStartEnd(trip));
        }
        return result;
    }

    public Map<Trip, TripStartEnd> obtainTripStartEnd(int lower, int higher) {
        if (lower > higher) {
            return obtainTripStartEnd(higher, lower);
        }
//        Set<Trip> trips = new HashSet<>();
//        findTripsByCodeBoundary(lower, higher, trips, getRootNode());
        return obtainTripStartEnd(/*trips*/findAllThatMatch(t -> t.getId().getId() >= lower && t.getId().getId() <= higher));
    }


    private void findTripsByCodeBoundary(int lower, int higher, Set<Trip> result, SearchableNode<Trip, Integer> root) {
        if (root == null) {
            return;
        }

        if (root.getIndexKey() < lower) {
            findTripsByCodeBoundary(lower, higher, result, root.getRight());
            return;
        }

        if (root.getIndexKey() > higher) {
            findTripsByCodeBoundary(lower, higher, result, root.getLeft());
            return;
        }
        findTripsByCodeBoundary(lower, higher, result, root.getLeft());
        result.add(root.getElement());
        findTripsByCodeBoundary(lower, higher, result, root.getRight());
    }


    public Map<Vehicle, Trip> findLongestTripByVehicle(Set<Vehicle> vehicles) {
        Set<Integer> setOfIds = new HashSet<>();
        Map<Vehicle, Trip> result = new HashMap<>();
        //Could have used Java Streams API, but you know, mestrado is not licenciatura
        for (Vehicle vehicle : vehicles) {
            setOfIds.add(vehicle.getId());
        }
        Map<Integer, TreeSet<Trip>> tmp = new HashMap<>();
        findAllTripsByVehicle(tmp, getRootNode());
        for (Vehicle vehicle : vehicles) {
            try {
                result.put(vehicle, tmp.get(vehicle.getId()).first());
            } catch (Exception e) {
                System.out.println("[WARNING]: No trips found for VehicleId " + vehicle.getId());
                System.out.println("Adding Null to result");
                result.put(vehicle, null);
            }
        }
        return result;
    }

    //This one returns without the vehicle information (exept the id)
    public Map<Integer, Trip> findLongestTripByVehicleIds(Set<Integer> vehicles) {
        Map<Integer, TreeSet<Trip>> tmp = new HashMap<>();
        Map<Integer, Trip> result = new HashMap<>();
        findAllTripsByVehicle(tmp, getRootNode());
        for (Integer vehicle : vehicles) {
            try {
                result.put(vehicle, tmp.get(vehicle).first());
            } catch (Exception e) {
                System.out.println("[WARNING]: No trips found for VehicleId " + vehicle);
            }
        }
        return result;
    }


    private void findAllTripsByVehicle(Map<Integer, TreeSet<Trip>> result, SearchableNode<Trip, Integer> root) {
        if (root == null) {
            return;
        }
        if (!result.containsKey(root.getElement().getId().getVehicle())) {
            result.put(root.getElement().getId().getVehicle(), new TreeSet<>(new Trip.TripDistanceComparator()));
        }
        result.get(root.getElement().getId().getVehicle()).add(root.getElement());
        findAllTripsByVehicle(result, root.getLeft());
        findAllTripsByVehicle(result, root.getRight());
    }

}
