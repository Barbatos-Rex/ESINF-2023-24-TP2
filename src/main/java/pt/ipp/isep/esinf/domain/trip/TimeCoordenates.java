package pt.ipp.isep.esinf.domain.trip;

import java.util.Comparator;
import java.util.Objects;

public class TimeCoordenates implements Comparator<TimeCoordenates> {
    private static final double R = 6371e3;
    private String timeStamp;
    private double latitude;
    private double longitude;


    public TimeCoordenates(String timeStamp, String latitude, String longitude) {
        this.timeStamp = timeStamp;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getLatitude() {
        return Double.toString(latitude);
    }

    public String getLongitude() {
        return Double.toString(longitude);
    }

    @Override
    public int compare(TimeCoordenates o1, TimeCoordenates o2) {
        return Double.compare(o1.distance(o2), 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeCoordenates that = (TimeCoordenates) o;
        return Objects.equals(timeStamp, that.timeStamp) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStamp, latitude, longitude);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Latitude: ").append(latitude).append("\n").append("Longitude: ")
                .append(longitude).append("\n").append("Instant [ms]: ").append(timeStamp).append("\n");
        return sb.toString();
    }

    public double distance(TimeCoordenates other) {
        double phi1 = latitude * Math.PI / 180;
        double phi2 = other.latitude * Math.PI / 180;
        double deltaPhi = (other.latitude - latitude) * Math.PI / 180;
        double deltaLambda = (other.longitude - longitude) * Math.PI / 180;
        double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) +
                Math.cos(phi1) * Math.cos(phi2) *
                        Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
