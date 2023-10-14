package pt.ipp.isep.esinf.auxiliary;

import pt.ipp.isep.esinf.domain.trip.Trip;
import pt.ipp.isep.esinf.domain.vehicle.Vehicle;

public class TripVehicle {
    private Vehicle vehicle;
    private Trip trip;

    public TripVehicle(Vehicle vehicle, Trip trip) {
        this.vehicle = vehicle;
        this.trip = trip;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Trip getTrip() {
        return trip;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(trip.toString()).append("\n");
        sb.append("\n============================================\n");
        sb.append(vehicle.toString()).append("\n");
        return sb.toString();
    }
}
