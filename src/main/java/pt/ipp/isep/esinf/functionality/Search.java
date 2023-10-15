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


    public Optional<VehicleTrips> findVehicleAndTripsById(int id) {

        Optional<Vehicle> v = tree.getVehicleTree().search(id);
        if (v.isPresent()) {
            Set<Trip> trips = tree.getTripTree().findAllThatMatch(e -> e.getId().getVehicle() == id);
            return Optional.of(new VehicleTrips(v.get(), trips));
        }
        return Optional.empty();
    }

    public Optional<TripVehicle> findTripAndVehicle(int id) {
        Optional<Trip> opt = tree.getTripTree().search(id);
        if (opt.isEmpty()) {
            return Optional.empty();
        }
        Trip t = opt.get();
        Optional<Vehicle> optv = tree.getVehicleTree().search(t.getId().getVehicle());
        if (optv.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new TripVehicle(optv.get(), t));
    }


}
