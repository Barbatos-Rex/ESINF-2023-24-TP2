package pt.ipp.isep.esinf.struct;

import pt.ipp.isep.esinf.domain.trip.Trip;

import java.util.HashMap;
import java.util.Map;

public class MaxMinAverageOfTripByType {

    private double start;
    private double end;

    private Map<String, MaxMinAverageOfTripEntry> trips;

    public MaxMinAverageOfTripByType(double start, double end) {
        this.trips = new HashMap<>();
        this.start = start;
        this.end = end;
    }

    //O(1)
    public void addEntry(Trip trip, String vehicleType) {
        if (!trips.containsKey(vehicleType)) {
            trips.put(vehicleType, new MaxMinAverageOfTripEntry(vehicleType));
        }
        trips.get(vehicleType).addTrip(trip);
    }

    public void updateValues() {
        for (MaxMinAverageOfTripEntry value : trips.values()) {
            value.generateResults();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Maximum, Minimum and Average of Speed, Load and OAT of all trips between ").append(start).append(" and ").append(end).append(" by vehicle type: ").append("\n");
        sb.append("========================================================================\n");
        for (Map.Entry<String, MaxMinAverageOfTripEntry> stringMaxMinAverageOfTripEntryEntry : trips.entrySet()) {
            sb.append("\n").append(stringMaxMinAverageOfTripEntryEntry.getValue()).append("\n");
            sb.append("-------------------------------------------------------------------------");
        }
        return sb.toString();
    }


}
