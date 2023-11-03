package pt.ipp.isep.esinf.struct.searchable;

import pt.ipp.isep.esinf.domain.trip.TimeCoordenates;
import pt.ipp.isep.esinf.domain.trip.Trip;
import pt.ipp.isep.esinf.domain.vehicle.Vehicle;
import pt.ipp.isep.esinf.struct.MaxMinAverageOfTripByType;
import pt.ipp.isep.esinf.struct.auxiliary.TripStartEnd;
import pt.ipp.isep.esinf.struct.simple.Trip2DTree;

import java.util.*;

public class SearchableTripBST extends SearchableBST<Trip, Integer> {

    //Can be achived by in Order Traversal, however, recursive is more impressive, it will (might) not blow up, and I like to do this overcomplexity just for fun!
    //But using Order traversal will be slower because it will loop twice for each node instead of tapping them directly, but the complexity is the same.
    //Any methods like this are just for avoiding lopping twice; how wonderful.

    /**
     * Complexity: O(1) + O(1) + O(nlog(n)) = O(nlog(n))
     *
     * @param dayNum1
     * @param dayNum2
     * @param vehicleTree
     * @return
     */
    public MaxMinAverageOfTripByType getMaxMinAvgBetween(double dayNum1, double dayNum2, SearchableVehicleBST vehicleTree) {
        //O(1) Simple recursive call to ensure params are correct
        if (dayNum1 > dayNum2) {
            return getMaxMinAvgBetween(dayNum2, dayNum1, vehicleTree);
        }
        //O(1)
        MaxMinAverageOfTripByType result = new MaxMinAverageOfTripByType(dayNum1, dayNum2);
        //O(nlog(n))
        getMaxMinAvgBetween(dayNum1, dayNum2, vehicleTree, result, getRootNode());
        return result;
    }

    //O(n*log(n))
    private void getMaxMinAvgBetween(double dayNum1, double dayNum2, SearchableVehicleBST vehicleTree,
                                     MaxMinAverageOfTripByType struct, SearchableNode<Trip, Integer> root) {
        //O(1)
        if (root == null) {
            return;
        }
        //O(log(n))
        Optional<Vehicle> v = vehicleTree.search(root.getElement().getId().getVehicle());
        if (v.isPresent()) {
            Vehicle vehicle = v.get();
            struct.addEntry(root.getElement(), vehicle.getType());
        }

        //O(n) call
        getMaxMinAvgBetween(dayNum1, dayNum2, vehicleTree, struct, root.getLeft());
        getMaxMinAvgBetween(dayNum1, dayNum2, vehicleTree, struct, root.getRight());
    }


    //O(nlog(n))
    public Map<Trip, TripStartEnd> obtainTripStartEnd(Set<Trip> trips) {
        //O(1)
        Map<Trip, TripStartEnd> result = new TreeMap<>();
        for (Trip trip : trips) { //O(n)
            //O(log(n))
            result.put(trip, new TripStartEnd(trip));
        }
        return result;
    }

    /**
     * Complexity: O(1) + O(log(n))+O(nlog(n)) = O(nlog(n))
     *
     * @param lower
     * @param higher
     * @return
     */
    public Map<Trip, TripStartEnd> obtainTripStartEnd(int lower, int higher) {
        //O(1)
        if (lower > higher) {
            return obtainTripStartEnd(higher, lower);
        }
//        Set<Trip> trips = new HashSet<>();
//        findTripsByCodeBoundary(lower, higher, trips, getRootNode());
        //O(log(n)) + O(nlog(n))
        return obtainTripStartEnd(/*trips*/findAllThatMatch(t -> t.getId().getId() >= lower && t.getId().getId() <= higher));
    }

    @Deprecated(since = "1.0.0", forRemoval = true)
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

    @Deprecated(since = "1.0.0")
    public Map<Vehicle, Trip> findLongestTripByVehicle(Set<Vehicle> vehicles) {
        Map<Vehicle, Trip> result = new HashMap<>();
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

    /**
     * Complexity: O(1) + O(nlog(n)) + O(n) * (O(1)+O(log(n))) = O(nlog(n))
     *
     * @param vehicles
     * @return
     */
    public Map<Integer, Trip> findLongestTripByVehicleIds(Set<Integer> vehicles) {
        //O(1)
        Map<Integer, TreeSet<Trip>> tmp = new HashMap<>();
        Map<Integer, Trip> result = new HashMap<>();
        //O(nlog(n))
        findAllTripsByVehicle(tmp, getRootNode());
        //O(n)
        for (Integer vehicle : vehicles) {
            try {
                //O(1)+O(log(n))
                result.put(vehicle, tmp.get(vehicle).first());
            } catch (Exception e) {
                if (Boolean.parseBoolean(System.getProperty("Debug", "false"))) {
                    System.out.println("[WARNING]: No trips found for VehicleId " + vehicle);
                    System.out.println("Adding null to trip");
                }
                result.put(vehicle, null);
            }
        }
        return result;
    }

    //O(n) * (O(1) + O(log(n))) = O(nlog(n))
    private void findAllTripsByVehicle(Map<Integer, TreeSet<Trip>> result, SearchableNode<Trip, Integer> root) {
        //O(1)
        if (root == null) {
            return;
        }
        //O(1)
        if (!result.containsKey(root.getElement().getId().getVehicle())) {
            result.put(root.getElement().getId().getVehicle(), new TreeSet<>(new Trip.TripDistanceComparator()));
        }
        //O(log(n))
        result.get(root.getElement().getId().getVehicle()).add(root.getElement());

        //O(n) call
        findAllTripsByVehicle(result, root.getLeft());
        findAllTripsByVehicle(result, root.getRight());
    }

    /**
     * Complexity: O(nlog(n))
     *
     * @return
     */
    public Trip2DTree generateCoordenate2DTree() {
        Trip2DTree result = new Trip2DTree();
        generateCoordenate2DTree(result, getRootNode());
        return result;
    }

    //O(n) * (O(1) + O(log(n))) = O(nlog(n))
    private void generateCoordenate2DTree(Trip2DTree tree, SearchableNode<Trip, Integer> root) {
        //O(1)
        if (root == null) {
            return;
        }
        //O(log(n))
        TimeCoordenates start = root.getElement().getEntries().getFirst().getCoordenates();
        TimeCoordenates end = root.getElement().getEntries().getLast().getCoordenates();
        //O(log(n))
        tree.insert(Double.parseDouble(start.getLatitude()), Double.parseDouble(start.getLongitude()), root.getElement());
        tree.insert(Double.parseDouble(end.getLatitude()), Double.parseDouble(end.getLongitude()), root.getElement());

        //O(n) call
        generateCoordenate2DTree(tree, root.getLeft());
        generateCoordenate2DTree(tree, root.getRight());
    }

}
