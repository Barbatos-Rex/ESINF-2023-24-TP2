package pt.ipp.isep.esinf.struct.auxiliary;

import pt.ipp.isep.esinf.domain.trip.Trip;
import pt.ipp.isep.esinf.domain.vehicle.Vehicle;

public class TripVehicle {
    private final Vehicle vehicle;
    private final Trip trip;

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
        String sb = trip.toString() + "\n" +
                "\n============================================\n" +
                vehicle.toString() + "\n";
        return sb;
    }
}
