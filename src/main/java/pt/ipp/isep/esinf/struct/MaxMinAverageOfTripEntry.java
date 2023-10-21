package pt.ipp.isep.esinf.struct;

import pt.ipp.isep.esinf.domain.trip.Trip;

import java.util.HashSet;
import java.util.Set;

public class MaxMinAverageOfTripEntry {

    private final String type;
    private final Set<Trip> trips;

    private double speedMax;
    private double speedMin;
    private double speedAvg;

    private double loadMax;
    private double loadMin;
    private double loadAvg;

    private double oatMax;
    private double oatMin;
    private double oatAvg;

    private boolean upToDate;

    public MaxMinAverageOfTripEntry(String type) {
        this.type = type;
        trips = new HashSet<>();
        upToDate = false;
        speedMax = 0;
        speedMin = 0;
        speedAvg = 0;
        loadMax = 0;
        loadMin = 0;
        loadAvg = 0;
        oatMax = 0;
        oatMin = 0;
        oatAvg = 0;
    }


    public boolean isUpToDate() {
        return upToDate;
    }

    public void generateResults() {
        if (upToDate) {
            return;
        }

        for (Trip trip : trips) {

            speedMax = Double.MIN_VALUE;
            loadMax = Double.MIN_VALUE;
            oatMax = Double.MIN_VALUE;

            speedMin = Double.MIN_VALUE;
            loadMin = Double.MIN_VALUE;
            oatMin = Double.MIN_VALUE;

            double speedCount = 0;
            double loadCount = 0;
            double oatCount = 0;

            Trip.TripInfo info = trip.getInfo();
            try {
                speedMax = Math.max(info.getSpeedMax(), speedMax);
                speedMin = Math.min(info.getSpeedMin(), speedMin);
                speedCount++;
                speedAvg += info.getSpeedAvg();
            } catch (NumberFormatException ignored) {
                //Go to the next parameter
            }
            try {
                loadMax = Math.max(info.getLoadMax(), loadMax);
                loadMin = Math.min(info.getLoadMin(), loadMin);
                loadCount++;
                loadAvg += info.getLoadAvg();
            } catch (NumberFormatException ignored) {
                //Go to the next parameter
            }
            try {
                oatMax = Math.max(info.getOatMax(), oatMax);
                oatMin = Math.min(info.getOatMin(), oatMin);
                oatCount++;
                oatAvg += info.getOatAvg();
            } catch (NumberFormatException ignored) {
                //Go to the next parameter
            }


            try {
                speedAvg = speedAvg / speedCount;
            } catch (ArithmeticException e) {
                speedAvg = -1;
            }

            try {
                loadAvg = loadAvg / loadCount;
            } catch (ArithmeticException e) {
                loadAvg = -1;
            }
            try {
                oatAvg = oatAvg / oatCount;
            } catch (ArithmeticException e) {
                oatAvg = -1;
            }
        }
        upToDate = true;
    }

    //O(1)
    public void addTrip(Trip trip) {
        upToDate = !trips.add(trip);
    }

    @Override
    public String toString() {
        String sb = "Vehicle Type: " + type + "\n" +
                "Speed [K/h] ------->" + "\n" +
                "Maximum: " + speedMax + "\n" +
                "Minimum: " + speedMin + "\n" +
                "Average: " + speedAvg + "\n" + "\n" +
                "Absolute Load [%] ------->" + "\n" +
                "Maximum: " + loadMax + "\n" +
                "Minimum: " + loadMin + "\n" +
                "Average: " + loadAvg + "\n" + "\n" +
                "OAT [ºC] ------->" + "\n" +
                "Maximum: " + oatMax + "\n" +
                "Minimum: " + oatMin + "\n" +
                "Average: " + oatAvg + "\n" + "\n";
        return sb;
    }
}
