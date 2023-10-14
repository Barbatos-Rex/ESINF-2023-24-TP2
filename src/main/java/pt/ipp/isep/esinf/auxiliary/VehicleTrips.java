package pt.ipp.isep.esinf.auxiliary;

import pt.ipp.isep.esinf.domain.trip.Trip;
import pt.ipp.isep.esinf.domain.vehicle.Vehicle;

import java.util.Set;

public class VehicleTrips {

    private Vehicle vehicle;
    private Set<Trip> trips;

    public VehicleTrips(Vehicle vehicle, Set<Trip> trips) {
        this.vehicle = vehicle;
        this.trips = trips;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Set<Trip> getTrips() {
        return trips;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(vehicle.toString());
        sb.append("\n============================================\n");
        for (Trip trip : trips) {
            sb.append(trip).append("\n============================================\n");
        }
        return sb.toString();
    }
}
