package pt.ipp.isep.esinf.domain.trip;

import java.util.Objects;

public class TripId {
    private int id;
    private int vehicle;
    private String dayNum;

    public TripId(int id, int vehicle, String dayNum) {
        this.id = id;
        this.vehicle = vehicle;
        this.dayNum = dayNum;
    }

    public int getId() {
        return id;
    }

    public int getVehicle() {
        return vehicle;
    }

    public String getDayNum() {
        return dayNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripId tripId = (TripId) o;
        return Objects.equals(id, tripId.id) && Objects.equals(vehicle, tripId.vehicle) && Objects.equals(dayNum, tripId.dayNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehicle, dayNum);
    }

    @Override
    public String toString() {
        return "tripid: {" +
                "id='" + id + '\'' +
                ", vehicle='" + vehicle + '\'' +
                ", dayNum='" + dayNum + '\'' +
                '}';
    }
}
