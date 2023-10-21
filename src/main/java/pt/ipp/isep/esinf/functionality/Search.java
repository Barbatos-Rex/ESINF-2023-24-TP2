package pt.ipp.isep.esinf.functionality;

import pt.ipp.isep.esinf.domain.trip.Trip;
import pt.ipp.isep.esinf.domain.vehicle.Vehicle;
import pt.ipp.isep.esinf.struct.auxiliary.TreeCluster;
import pt.ipp.isep.esinf.struct.auxiliary.TripVehicle;
import pt.ipp.isep.esinf.struct.auxiliary.VehicleTrips;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Search {
    private TreeCluster tree;


    public Search(TreeCluster tree) {
        this.tree = tree;
    }


    /**
     * Complexity: O(log(n)) + O(n) = O(n)
     * @param id
     * @return
     */
    public Optional<VehicleTrips> findVehicleAndTripsById(int id) {
        // O(log(n)) is essentially a binary search in this case
        Optional<Vehicle> v = tree.getVehicleTree().search(id);
        if (v.isPresent()) {
            // O(n) essentially a full Collection iteration and filtering all those whose vehicle id matches the targeted id
            Set<Trip> trips = tree.getTripTree().findAllThatMatch(e -> e.getId().getVehicle() == id);
            return Optional.of(new VehicleTrips(v.get(), trips));
        }
        return Optional.empty();
    }


    /**
     * Complexity: 2*O(log(n)) = O(log(n))
     * @param id
     * @return
     */
    public Optional<TripVehicle> findTripAndVehicle(int id) {
        //O(log(n)) same reason as above
        Optional<Trip> opt = tree.getTripTree().search(id);
        if (opt.isEmpty()) {
            return Optional.empty();
        }
        Trip t = opt.get();
        //O(log(n)) same reason as in 28
        Optional<Vehicle> optv = tree.getVehicleTree().search(t.getId().getVehicle());
        if (optv.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new TripVehicle(optv.get(), t));
    }


}
