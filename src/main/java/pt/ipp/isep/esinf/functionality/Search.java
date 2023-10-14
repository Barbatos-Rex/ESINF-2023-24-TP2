package pt.ipp.isep.esinf.functionality;

import pt.ipp.isep.esinf.auxiliary.TripVehicle;
import pt.ipp.isep.esinf.auxiliary.VehicleTrips;
import pt.ipp.isep.esinf.domain.trip.Trip;
import pt.ipp.isep.esinf.domain.vehicle.Vehicle;
import pt.ipp.isep.esinf.struct.TreeCluster;

import java.util.Optional;
import java.util.Set;

public class Search {
    private TreeCluster tree;


    public Search(TreeCluster tree) {
        this.tree = tree;
    }


    public Optional<VehicleTrips> findVehicleAndTripsById(String id){

        Optional<Vehicle> v = tree.getVehicleTree().search(id);
        if (v.isPresent()){
            Set<Trip> trips= tree.getTripTree().findAllThatMatch(e -> e.getId().getVehicle().equals(id.substring(2)));
            return Optional.of(new VehicleTrips(v.get(),trips));
        }
        return Optional.empty();
    }

    public Optional<TripVehicle> findTripAndVehicle(String id){
        Optional<Trip> opt = tree.getTripTree().search(id);
        if (opt.isEmpty()){
            return Optional.empty();
        }
        Trip t = opt.get();
        Optional<Vehicle> optv = tree.getVehicleTree().search("V-"+t.getId().getVehicle());
        if (optv.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new TripVehicle(optv.get(),t));
    }


}
