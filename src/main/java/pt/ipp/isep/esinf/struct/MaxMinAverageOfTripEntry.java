package pt.ipp.isep.esinf.struct;

import pt.ipp.isep.esinf.domain.trip.Trip;

import java.util.HashSet;
import java.util.Set;

public class MaxMinAverageOfTripEntry {

    private String type;
    private Set<Trip> trips;

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
        upToDate=true;
    }


    public void addTrip(Trip trip) {
        upToDate = !trips.add(trip);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vehicle Type: ").append(type).append("\n");
        sb.append("Speed [K/h] ------->").append("\n");
        sb.append("Maximum: ").append(speedMax).append("\n");
        sb.append("Minimum: ").append(speedMin).append("\n");
        sb.append("Average: ").append(speedAvg).append("\n").append("\n");
        sb.append("Absolute Load [%] ------->").append("\n");
        sb.append("Maximum: ").append(loadMax).append("\n");
        sb.append("Minimum: ").append(loadMin).append("\n");
        sb.append("Average: ").append(loadAvg).append("\n").append("\n");
        sb.append("OAT [ºC] ------->").append("\n");
        sb.append("Maximum: ").append(oatMax).append("\n");
        sb.append("Minimum: ").append(oatMin).append("\n");
        sb.append("Average: ").append(oatAvg).append("\n").append("\n");
        return sb.toString();
    }
}
