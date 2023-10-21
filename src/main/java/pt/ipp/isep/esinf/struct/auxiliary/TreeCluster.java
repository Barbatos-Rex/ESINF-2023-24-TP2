package pt.ipp.isep.esinf.struct.auxiliary;

import pt.ipp.isep.esinf.struct.searchable.SearchableTripBST;
import pt.ipp.isep.esinf.struct.searchable.SearchableVehicleBST;

public class TreeCluster {
    private final SearchableVehicleBST vehicleTree;

    private final SearchableTripBST tripTree;

    public TreeCluster(SearchableVehicleBST vehicleTree, SearchableTripBST tripTree) {
        this.vehicleTree = vehicleTree;
        this.tripTree = tripTree;
    }

    public SearchableVehicleBST getVehicleTree() {
        return vehicleTree;
    }

    public SearchableTripBST getTripTree() {
        return tripTree;
    }
}
