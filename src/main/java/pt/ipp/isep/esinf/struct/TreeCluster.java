package pt.ipp.isep.esinf.struct;

import pt.ipp.isep.esinf.domain.trip.Trip;
import pt.ipp.isep.esinf.domain.vehicle.Vehicle;
import pt.ipp.isep.esinf.struct.searchable.SearchableBST;

public class TreeCluster {
    private SearchableBST<Vehicle, String> vehicleTree;

    private SearchableBST<Trip, String> tripTree;

    public TreeCluster(SearchableBST<Vehicle, String> vehicleTree, SearchableBST<Trip, String> tripTree) {
        this.vehicleTree = vehicleTree;
        this.tripTree = tripTree;
    }

    public SearchableBST<Vehicle, String> getVehicleTree() {
        return vehicleTree;
    }

    public SearchableBST<Trip, String> getTripTree() {
        return tripTree;
    }
}
