package pt.ipp.isep.esinf.domain;

import java.util.Objects;

public class TimeCoordenates {
    private String timeStamp;
    private String latitude;
    private String longitude;


    public TimeCoordenates(String timeStamp, String latitude, String longitude) {
        this.timeStamp = timeStamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
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
        return "timeCoordenates: {" +
                "timeStamp='" + timeStamp + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
